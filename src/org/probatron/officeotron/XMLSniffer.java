/*
 * This file is part of the source of
 * 
 * Office-o-tron - a web-based ODF document validator for Java(tm)
 * 
 * Copyright (C) 2009 Griffin Brown Digitial Publishing Ltd
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
    
    private XMLSniffData sniffData;

    XMLSniffData doSniff( String url )
    {
        sniffData = null;
        XMLReader parser;
        try
        {
            parser = XMLReaderFactory.createXMLReader();
            parser.setContentHandler( this );    
            parser.parse( url );
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
        
        return this.sniffData;

    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters( char[] ch, int start, int length ) throws SAXException
    {
     // TODO Auto-generated method stub        
        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endDocument()
     */
    public void endDocument() throws SAXException
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public void endElement( String uri, String localName, String name ) throws SAXException
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
     */
    public void endPrefixMapping( String prefix ) throws SAXException
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
     */
    public void ignorableWhitespace( char[] ch, int start, int length ) throws SAXException
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
     */
    public void processingInstruction( String target, String data ) throws SAXException
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
     */
    public void setDocumentLocator( Locator locator )
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
     */
    public void skippedEntity( String name ) throws SAXException
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startDocument()
     */
    public void startDocument() throws SAXException
    {
      
        
        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement( String uri, String localName, String name, Attributes atts )
            throws SAXException
    {
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
        // TODO Auto-generated method stub
        
    }
    
    

}
