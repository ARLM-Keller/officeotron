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

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class BlankingResolver implements EntityResolver
{

    static Logger logger = Logger.getLogger( BlankingResolver.class );
    XMLSniffer sniffer;


    BlankingResolver( XMLSniffer sniffer )
    {
        this.sniffer = sniffer;
    }


    public InputSource resolveEntity( String publicId, String systemId ) throws SAXException,
            IOException
    {
        logger.info( "Request made to resolve entity with SYSTEM ID: " + systemId );

        if( sniffer != null )
        {
            sniffer.referencesEntities = true;
        }
        
        return new InputSource( new ByteArrayInputStream( "".getBytes() ) );
    }
}
