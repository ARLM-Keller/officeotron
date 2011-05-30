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

import java.text.MessageFormat;

import org.apache.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class CommentatingErrorHandler implements ErrorHandler
{
    private ValidationReport commentary;
    static Logger logger = Logger.getLogger( CommentatingErrorHandler.class );
    private int instanceErrCount;
    public final static int THRESHOLD = 10;

    private String fragmentName;

    public CommentatingErrorHandler( ValidationReport commentary, String fragmentName )
    {
        this.commentary = commentary;
        this.fragmentName = fragmentName;
    }


    public void error( SAXParseException e ) throws SAXException
    {
        if( instanceErrCount < THRESHOLD )
        {
        	String name = fragmentName;
        	
        	// Use the systemId when the error is located in the schemas (unlikely to happen)
        	if ( e.getSystemId() != null && !e.getSystemId().endsWith(fragmentName) )
        		name = e.getSystemId();
        	String message = MessageFormat.format( "({0}:{1} col:{2}) {3}", name, e.getLineNumber(), e.getColumnNumber(), e.getMessage());
            commentary.addComment( "ERROR", message );
        }
        commentary.incErrs();
        instanceErrCount++;
    }


    public void fatalError( SAXParseException e ) throws SAXException
    {
        logger.debug( "Fatal Error: " + e.getMessage() );

        commentary.addComment( "ERROR", e.getMessage() );
    }


    public void warning( SAXParseException e ) throws SAXException
    {
        logger.debug( "Warning: " + e.getMessage() );
        commentary.addComment( "WARN", e.getMessage() );
    }


    public int getInstanceErrCount()
    {
        return instanceErrCount;
    }

}
