/*
 * This file is part of the source of
 * 
 * Office-o-tron - a web-based office document validator for Java(tm)
 * 
 * Copyright (c) 2009 Griffin Brown Digital Publishing Ltd.
 * 
 * All rights reserved world-wide.
 * 
 * The contents of this file are subject to the Mozilla Public License Version 1.1 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at http://www.mozilla.org/MPL/MPL-1.1.html
 * 
 * Software distributed under the License is distributed on an "AS IS" basis, WITHOUT WARRANTY
 * OF ANY KIND, either express or implied. See the License for the specific language governing
 * rights and limitations under the License.
 */

package org.probatron.officeotron;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.probatron.officeotron.sessionstorage.ValidationSession;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class OOXMLValidationSession extends ValidationSession
{
    static Logger logger = Logger.getLogger( OOXMLValidationSession.class );
    private String schemaUrlBase;


    public OOXMLValidationSession( String filename, String schemaUrlBase )
    {
        super( filename );
        this.schemaUrlBase = schemaUrlBase;
    }


    public void validate()
    {
        OPCPackage opc = new OPCPackage( this );
        opc.process();
        checkRelationships( opc );

        if( errCount > 0 )
        {
            getCommentary().addComment( "ERROR",
                    "Grand total of errors in submitted package: " + errCount );
        }
    }


    public void checkRelationships( OPCPackage opc )
    {
        logger.trace( "Beginning package integrity test" );
        this.getCommentary().addComment( "Checking OPC Package ..." );
        this.getCommentary().incIndent();

        logger.trace( "Collection size: " + opc.getEntryCollection().size() );

        for( int i = 0; i < opc.getEntryCollection().size(); i++ )
        {
            OOXMLTarget t = opc.getEntryCollection().get( i );
            String mt = t.getMimeType();

            logger.trace( "Testing entry of MIME type: " + mt );

            OOXMLSchemaMapping osm = OOXMLSchemaMap.getMappingForContentType( mt );

            if( osm == null )
            {
                logger.info( "No mapping found for entry" );
                continue;
            }

            if( !t.getType().equals( osm.getRelType() ) )
            {
                logger.debug( "Relationship type mismatch" );
                this.errCount++;
                this.getCommentary().addComment(
                        "ERROR",
                        "Entry with MIME type \"" + mt
                                + "\" has unrecognized relationship type \"" + t.getType()
                                + "\" (see ISO/IEC 29500-1:2008, Clause " + osm.getClause()
                                + ")" );
            }
            if( t.slashFail )
            {
                logger.debug( "Relative IRI problem in OPC" );
                this.errCount++;
                this
                        .getCommentary()
                        .addComment(
                                "ERROR",
                                "Entry with MIME type \""
                                        + mt
                                        + "\" has a non-conformant Part Name (\""
                                        + t.name
                                        + "\") as it does not start with a mandatory forward slash character (see ISO/IEC 29500-2:2008, Clause 9.1.1.1)" );

            }

        }

        if( this.errCount > 0 )
        {
            this.getCommentary().addComment(
                    "ERROR",
                    "" + this.errCount + " problem" + ( this.errCount > 1 ? "s" : "" )
                            + " found with OPC package" );

        }
        else
        {
            this.getCommentary().addComment( "No problems found with OPC package" );

        }

        this.getCommentary().decIndent();

        this.getCommentary().addComment(
                "Validating " + opc.getEntryCollection().size() + " parts ..." );
        for( int i = 0; i < opc.getEntryCollection().size(); i++ )
        {

            OOXMLTarget t = opc.getEntryCollection().get( i );
            String mt = t.getMimeType();

            logger.trace( "Validating entry of MIME type: " + mt );

            OOXMLSchemaMapping osm = OOXMLSchemaMap.getMappingForContentType( mt );
            if( osm == null )
            {
                this.getCommentary().addComment(
                        "Cannot determine schema for this Target (\"<![CDATA["
                                + t.getQPartname() + "]]>\")" );

            }
            else
            {
                validateTarget( t, osm );
            }

        }

    }


    void validateTarget( OOXMLTarget t, OOXMLSchemaMapping osm )
    {
        synchronized( OOXMLValidationSession.class )
        {
            String schemaName = osm.getSchemaName();
            getCommentary().addComment(
                    "Validating part \"" + t.getQPartname() + "\" using schema \""
                            + osm.getSchemaName() + "\" ..." );
            getCommentary().incIndent();

            if( schemaName == null || schemaName.length() == 0 )
            {
                this.getCommentary().addComment(
                        "No schema known to validate content of type: " + osm.getContentType() );
            }
            else
            {

                try
                {
                    CommentatingErrorHandler h = new CommentatingErrorHandler( this
                            .getCommentary() );
                    XMLReader parser = getConfiguredParser( osm, h );

                    String url = getUrlForEntry( t.getQPartname() ).toString();
                    logger
                            .debug( "Validating: " + url + " using schema "
                                    + osm.getSchemaName() );

                    parser.parse( url );

                    if( h.getInstanceErrCount() > 0 )
                    {
                        getCommentary().addComment(
                                "\"" + t.getQPartname() + "\" contains "
                                        + h.getInstanceErrCount() + " validity error"
                                        + ( h.getInstanceErrCount() > 1 ? "s" : "" ) );
                        errCount += h.getInstanceErrCount();
                    }
                    else
                    {
                        getCommentary().addComment(
                                "\"" + t.getQPartname() + "\" is schema-valid" );
                    }
                    if( h.getInstanceErrCount() > CommentatingErrorHandler.THRESHOLD )
                    {
                        getCommentary()
                                .addComment(
                                        "(<i>"
                                                + ( h.getInstanceErrCount() - CommentatingErrorHandler.THRESHOLD )
                                                + " error(s) omitted for the sake of brevity</i>)" );
                    }

                }
                catch( SAXException e )
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch( IOException e )
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            getCommentary().decIndent();
        }
    }


    XMLReader getConfiguredParser( OOXMLSchemaMapping osm, ErrorHandler h )
    {
        XMLReader parser = null;

        try
        {
            parser = XMLReaderFactory.createXMLReader();

            parser.setErrorHandler( h );

            String schemaUrl = this.schemaUrlBase + osm.getSchemaName();
            logger.debug( "Selecting XSD schema: " + schemaUrl );

            parser.setFeature( "http://xml.org/sax/features/validation", true );
            parser.setFeature( "http://apache.org/xml/features/validation/schema", true );
            parser.setProperty(
                    "http://apache.org/xml/properties/schema/external-schemaLocation", osm
                            .getNs()
                            + " " + schemaUrl );
        }
        catch( SAXNotRecognizedException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch( SAXNotSupportedException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch( SAXException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return parser;

    }

}
