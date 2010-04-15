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

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class OOXMLRelationshipPartHandler implements ContentHandler
{
    static Logger logger = Logger.getLogger( OOXMLRelationshipPartHandler.class );
    OOXMLTargetCollection col = new OOXMLTargetCollection();
    String hostRelationshipPartEntryName;


    public OOXMLRelationshipPartHandler(  String hostRelationshipPartEntryName )
    {
        this.hostRelationshipPartEntryName = hostRelationshipPartEntryName;
    }


    public void characters( char[] arg0, int arg1, int arg2 ) throws SAXException
    {
    // do nothing
    }


    public void endDocument() throws SAXException
    {
    // do nothing
    }


    public void endElement( String arg0, String arg1, String arg2 ) throws SAXException
    {
    // do nothing
    }


    public void endPrefixMapping( String arg0 ) throws SAXException
    {
    // do nothing
    }


    public void ignorableWhitespace( char[] arg0, int arg1, int arg2 ) throws SAXException
    {
    // do nothing
    }


    public void processingInstruction( String arg0, String arg1 ) throws SAXException
    {
    // do nothing
    }


    public void setDocumentLocator( Locator arg0 )
    {
    // do nothing
    }


    public void skippedEntity( String arg0 ) throws SAXException
    {
    // do nothing
    }


    public void startDocument() throws SAXException
    {
    // do nothing
    }


    public void startElement( String uri, String localName, String qName, Attributes atts )
            throws SAXException
    {
        if( localName.equals( "Relationship" )
                && uri.equals( OOXMLTarget.NS_PACKAGE_RELATIONSHIPS ) )
        {
            OOXMLTarget t = new OOXMLTarget( this.hostRelationshipPartEntryName,
                    new AttributesImpl( atts ) );
            this.col.add( t );
        }

    }


    public void startPrefixMapping( String arg0, String arg1 ) throws SAXException
    {
    // do nothing
    }

}
