/*
 * This file is part of the source of
 * 
 * Office-o-tron - a web-based office document validator for Java(tm)
 * 
 * Copyright (c) 2010 Griffin Brown Digital Publishing Ltd.
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

package org.probatron.officeotron.zip;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

import junit.framework.TestCase;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.probatron.officeotron.CommandLineSubmission;
import org.probatron.officeotron.Utils;
import org.probatron.officeotron.sessionstorage.Store;
import org.probatron.officeotron.sessionstorage.ValidationSession;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ZipArchiveTest extends TestCase
{

    ValidationSession vs;

    static
    {
        // set up log message format, etc.
        String logLvl = System.getProperty( "property://probatron.org/officeotron-log-level" );
        logLvl = ( logLvl == null ) ? "DEBUG" : logLvl;

        Properties p = new Properties();
        p.setProperty( "log4j.rootCategory", logLvl + ", A1" );
        p.setProperty( "log4j.appender.A1", "org.apache.log4j.ConsoleAppender" );
        p.setProperty( "log4j.appender.A1.target", "System.err" );
        p.setProperty( "log4j.appender.A1.layout", "org.apache.log4j.PatternLayout" );
        p.setProperty( "log4j.appender.A1.layout.ConversionPattern", "%c %p - %m%n" );
        PropertyConfigurator.configure( p );

    }


    @Override
    protected void setUp() throws Exception
    {
        // special set-up for testing
        Store.init( "c:\\officeotron", "cmd /c unzip" );

        String fn = "etc/test-data/maria.xlsx";
        CommandLineSubmission cls = new CommandLineSubmission( fn );
        vs = Utils.autoCreateValidationSession( cls, "file:C:\\tomcat-5.5\\webapps\\29500T\\" );
        vs.prepare();

        super.setUp();
    }


    @Override
    protected void tearDown() throws Exception
    {
       // vs.cleanup();
        super.tearDown();
    }


    @Test
    public void test_notNull()
    {
        ZipArchive zip = vs.getZipArchive();
        assertTrue( zip != null );
    }


    @Test
    public void test_headerCount()
    {
        ZipArchive zip = vs.getZipArchive();
        assertTrue( zip.getLocalHeaderCount() == 15 );
    }


    @Test
    public void test_countsMatch()
    {
        ZipArchive zip = vs.getZipArchive();
        assertTrue( zip.getLocalHeaderCount() == zip.getCentralRecordCount() );
    }


    @Test
    public void test_aHeader()
    {
        ZipArchive zip = vs.getZipArchive();
        ZipLocalHeader h = zip.getLocalHeader( 0 );

        assertTrue( h.getExtractVersion()[ 0 ] == ( byte )0x14 );
        assertTrue( h.getExtractVersion()[ 1 ] == ( byte )0x00 );
        assertTrue( h.getGeneral()[ 0 ] == ( byte )0x06 );
        assertTrue( h.getGeneral()[ 1 ] == ( byte )0x00 );
        assertTrue( h.getMethod()[ 0 ] == ( byte )0x08 );
        assertTrue( h.getMethod()[ 1 ] == ( byte )0x00 );
        assertTrue( h.getModTime()[ 0 ] == ( byte )0x00 );
        assertTrue( h.getModTime()[ 1 ] == ( byte )0x00 );
        assertTrue( h.getModDate()[ 0 ] == ( byte )0x21 );
        assertTrue( h.getModDate()[ 1 ] == ( byte )0x00 );
        assertTrue( h.getCrc32() == 0x65D2794B );
        assertTrue( h.getCompressedSize() == 0x0000019C );
        assertTrue( h.getUncompressedSize() == 0x0000034E );
        assertTrue( h.getFilenameLength() == 16 );
        assertTrue( h.getExtraFieldLength() == 264 );
        assertTrue( h.getFilename().equals( "docProps/app.xml" ) );
    }


    @Test
    public void test_xml()
    {
        ZipArchive zip = vs.getZipArchive();
        String s = zip.asXmlString();
        boolean ok = true;
        try
        {
            XMLReader r = XMLReaderFactory.createXMLReader();
            r.parse( new InputSource( new ByteArrayInputStream( s.getBytes() ) ) );
        }
        catch( Exception e )
        {
            ok = false;
        }

        assertTrue( ok );

    }

}
