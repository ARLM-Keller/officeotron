/*
 * This file is part of the source of
 * 
 * Office-o-tron - a web-based office document validator for Java(tm)
 * 
 * Copyright (c) 2009-2010 Griffin Brown Digital Publishing Ltd.
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

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.probatron.officeotron.sessionstorage.ValidationSession;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ODFPackage extends AbstractPackage implements ContentHandler
{
    static Logger logger = Logger.getLogger( ODFPackage.class );
    private final static String ODF_MANIFEST_NS = "urn:oasis:names:tc:opendocument:xmlns:manifest:1.0";
    private ArrayList< String > itemRefs = new ArrayList< String >();


    public ODFPackage( ValidationSession vs )
    {
        super( vs );
    }


    void process( String sysId )
    {
        try
        {
            XMLReader parser = XMLReaderFactory.createXMLReader();
            parser.setContentHandler( this );
            parser.setEntityResolver( new BlankingResolver( null ) );
         
            parser.parse( sysId );
        }
        catch( Exception e )
        {
            // this will have already been parsed once, so this should never happen
            e.printStackTrace();
            throw new RuntimeException( e.getMessage() );
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters( char[] arg0, int arg1, int arg2 ) throws SAXException
    {
    // do nothing
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.ContentHandler#endDocument()
     */
    public void endDocument() throws SAXException
    {
    // do nothing
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    public void endElement( String arg0, String arg1, String arg2 ) throws SAXException
    {
    // do nothing
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
     */
    public void endPrefixMapping( String arg0 ) throws SAXException
    {
    // do nothing
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
     */
    public void ignorableWhitespace( char[] arg0, int arg1, int arg2 ) throws SAXException
    {
    // do nothing
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
     */
    public void processingInstruction( String arg0, String arg1 ) throws SAXException
    {
    // do nothing
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
     */
    public void setDocumentLocator( Locator arg0 )
    {
    // do nothing
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
     */
    public void skippedEntity( String arg0 ) throws SAXException
    {
    // do nothing
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.ContentHandler#startDocument()
     */
    public void startDocument() throws SAXException
    {
    // do nothing
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String,
     * java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement( String uri, String localName, String qName, Attributes atts )
            throws SAXException
    {
        if( uri.equals( ODF_MANIFEST_NS ) && localName.equals( "file-entry" ) )
        {
            logger.debug( "processing file-entry in manifest" );
            String mimeType = Utils.getQAtt( atts, ODF_MANIFEST_NS, "media-type" );
            if( mimeType.indexOf( "/xml" ) != -1 )
            {
                String entryName = Utils.getQAtt( atts, ODF_MANIFEST_NS, "full-path" );
                itemRefs.add( entryName );
                logger.debug( "Found entry: " + entryName );
            }
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
     */
    public void startPrefixMapping( String arg0, String arg1 ) throws SAXException
    {
    // do nothing
    }


    /**
     * @return the itemRefs
     */
    public ArrayList< String > getItemRefs()
    {
        return itemRefs;
    }

}
