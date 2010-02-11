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
 * 
 */

package org.probatron.officeotron;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ValidationReport
{
    static Logger logger = Logger.getLogger( ValidationReport.class );

    private StringBuffer sb = new StringBuffer();

    private int indent;
    private int errCount;


    public ValidationReport()
    {
        sb.append( "<?xml version='1.0' standalone='yes'?>"
                + "<div xmlns='http://www.w3.org/1999/xhtml'>" );

        String ver = Package.getPackage( "org.probatron.officeotron" )
                .getImplementationVersion();

        sb.append( "<div class='meta'>Beginning validation using "
                + "<a href='http://code.google.com/p/officeotron/'>Office-o-tron</a> <span class='officeotron-version'>" + ver
                + "</span> at <span class='timestamp'>" + new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).format( new Date() )
                + "</span></div>" );
    }


    void addComment( String s )
    {
        addComment( "INFO", s );
    }


    void addComment( String klass, String s )
    {

        sb.append( "<div class='" + klass + "'>" );
        for( int i = 0; i < this.indent * 5; i++ )
        {
            sb.append( "&#160;" );
        }
        sb.append( s );
        sb.append( "</div>" );
    }


    void streamOut( HttpServletResponse resp ) throws IOException
    {
        sb.append( "</div>" );

        byte[] ba = sb.toString().getBytes( "us-ascii" ); // utf-8 compatible natch

        resp.setContentLength( ba.length );
        resp.setContentType( "application/xml" );
        resp.setHeader( "Cache-Control", "no-cache" );
        resp.setCharacterEncoding( "utf-8" );

        try
        {
            XMLReader parser = XMLReaderFactory.createXMLReader();
            parser.parse( new InputSource( new ByteArrayInputStream( ba ) ) );
        }
        catch( SAXException e )
        {
            logger.fatal( "Validation report is not conformant XML" );
        }

        ByteArrayInputStream bis = new ByteArrayInputStream( ba );
        Utils.transferBytesToEndOfStream( bis, resp.getOutputStream(), Utils.CLOSE_IN
                | Utils.CLOSE_OUT );
    }


    public void decIndent()
    {
        this.indent--;
    }


    public void incIndent()
    {
        this.indent++;
    }


    public void incErrs()
    {
        this.errCount++;
    }


    public int getErrCount()
    {
        return errCount;
    }

}