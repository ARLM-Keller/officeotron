/*
 * This file is part of the source of
 * 
 * Office-o-tron - a web-based office document validator for Java(tm)
 * 
 * Copyright (C) 2009 Griffin Brown Digital Publishing Ltd
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * the GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this
 * program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.probatron.officeotron;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class OOXMLValidationSession extends ValidationSession
{
    static Logger logger = Logger.getLogger( OOXMLValidationSession.class );
    private String schemaUrlBase;


    public OOXMLValidationSession( Submission submission, String schemaUrlBase )
    {
        super( submission );
        this.schemaUrlBase = schemaUrlBase;
    }


    public void validate()
    {
        OPCPackage opc = new OPCPackage( this.getSubmission().getCandidateUrl() );
        checkRelationships( opc );
    }


    public void checkRelationships( OPCPackage opc )
    {
        logger.trace( "Beginning package integrity test" );
        this.getCommentary().addComment( "Checking Package relationship integrity" );
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

            if( ! t.getType().equals( osm.getRelType() ) )
            {
                logger.debug( "Relationship type mismatch" );
                this.errCount++;
                this.getCommentary().addComment(
                        "ERROR",
                        "Entry with MIME type \"" + mt
                                + "\" has unrecognized relationship type \"" + t.getType()
                                + "\"" );
            }

            validateTarget( t, osm );

        }

        this.getCommentary().decIndent();

    }


    void validateTarget( OOXMLTarget t, OOXMLSchemaMapping osm )
    {
        String schemaName = osm.getSchemaName();
        getCommentary().addComment( "Validating package item \"" + t.getQPartname() + "\"" );
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
                XMLReader parser;

                parser = XMLReaderFactory.createXMLReader();
                CommentatingErrorHandler h = new CommentatingErrorHandler( this.getCommentary() );
                parser.setErrorHandler( h );

                String schemaUrl = this.schemaUrlBase + osm.getSchemaName();
                logger.debug( "Selecting XSD schema: " + schemaUrl );

                parser.setFeature( "http://xml.org/sax/features/validation", true );
                parser.setFeature( "http://apache.org/xml/features/validation/schema", true );
                parser.setProperty(
                        "http://apache.org/xml/properties/schema/external-schemaLocation", osm
                                .getNs()
                                + " " + schemaUrl );

                String packageUrl = this.getSubmission().getCandidateUrl();

                String url = "jar:" + packageUrl + "!" + t.getQPartname();
                logger.debug( "Validating: " + url );

                parser.parse( url );

                if( h.getInstanceErrCount() > 0 )
                {
                    getCommentary().addComment(
                            "\"" + t.getQPartname() + "\" contains " + h.getInstanceErrCount()
                                    + " validity error"
                                    + ( h.getInstanceErrCount() > 1 ? "s" : "" ) );
                    errCount += h.getInstanceErrCount();
                }
                else
                {
                    getCommentary().addComment( "\"" + t.getQPartname() + "\" is schema-valid" );
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
