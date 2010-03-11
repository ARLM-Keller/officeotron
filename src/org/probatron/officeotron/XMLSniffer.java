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
 * 
 */

package org.probatron.officeotron;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.XMLReaderFactory;

public class XMLSniffer implements ContentHandler
{
    static Logger logger = Logger.getLogger( XMLSniffer.class );

    private String contextNs;
    private String contextElement;
    boolean referencesEntities;

    private XMLSniffData sniffData;


    XMLSniffData doSniff( String url ) throws SAXException, IOException
    {
        sniffData = null;
        XMLReader parser;

        parser = XMLReaderFactory.createXMLReader();
        parser.setContentHandler( this );
        parser.setEntityResolver( new BlankingResolver( this) );
        parser.parse( url );

        return this.sniffData;
    }


    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters( char[] ch, int start, int length ) throws SAXException
    {
    // do nothing
    }


    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endDocument()
     */
    public void endDocument() throws SAXException
    {
    // do nothing
    }


    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public void endElement( String uri, String localName, String name ) throws SAXException
    {
    // do nothing
    }


    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
     */
    public void endPrefixMapping( String prefix ) throws SAXException
    {
    // do nothing
    }


    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
     */
    public void ignorableWhitespace( char[] ch, int start, int length ) throws SAXException
    {
    // do nothing
    }


    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
     */
    public void processingInstruction( String target, String data ) throws SAXException
    {
    // do nothing
    }


    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
     */
    public void setDocumentLocator( Locator locator )
    {
    // do nothing
    }


    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
     */
    public void skippedEntity( String name ) throws SAXException
    {
    // do nothing
    }


    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startDocument()
     */
    public void startDocument() throws SAXException
    {
    // do nothing
    }


    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement( String uri, String localName, String name, Attributes atts )
            throws SAXException
    {
        this.contextElement = localName;
        this.contextNs = uri;
        if( sniffData == null )
        {
            sniffData = new XMLSniffData();
            sniffData.setRootNs( uri );
            sniffData.setRootElementName( localName );
            sniffData.setAtts( new AttributesImpl( atts ) );
            logger.debug( "Done sniff. ns=" + uri + "; local-name=" + localName );
        }
    }


    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
     */
    public void startPrefixMapping( String prefix, String uri ) throws SAXException
    {
    // do nothing
    }


    public String getContextNs()
    {
        return contextNs;
    }


    public String getContextElement()
    {
        return contextElement;
    }

}
