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

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.UUID;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.apache.log4j.PropertyConfigurator;
import org.probatron.officeotron.sessionstorage.Store;

public class OPCPackageTest
{

    private static final File TEST_FILE = new File ( "etc/test-data/torture.pptx" );

    private static final int TARGETS_COUNT = 21;
    
	private static final String TARGET_OK_NAME = "/ppt/slides/slide1.xml";
	private static final String TARGET_OK_TYPE = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/slide";
	private static final String TARGET_OK_MIMETYPE = "application/vnd.openxmlformats-officedocument.presentationml.slide+xml";
	
	private static final String TARGET_NOK_NAME = "/ppt/slides/slide999.xml";
    
    private OPCPackage opc;
    private UUID uuid;

    @BeforeClass
    public static void classSetUp( )
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


    @Before
    public void setUp() throws Exception
    {
    	Store.init( System.getProperty( "java.io.tmpdir" ), false );
    	uuid = Store.putZippedResource( new FileInputStream( TEST_FILE ), TEST_FILE.getPath() );
    	opc = new OPCPackage( new File( Store.getDirectory( uuid ) ) );
        opc.process();
    }


    @After
    public void tearDown() throws Exception
    {
    	Store.delete( uuid );
    }


    @Test
    public void test_targetCount()
    {
        assertEquals( TARGETS_COUNT, opc.getEntryCollection().size() );
    }


    @Test
    public void test_anEntry()
    {
        OOXMLTarget t = opc.getEntryCollection().getTargetByName( TARGET_OK_NAME );
        assertNotNull( "Target should exist: " + TARGET_OK_NAME, t );

        t = opc.getEntryCollection().getTargetByName( TARGET_NOK_NAME );
        assertNull( "Target shouldn't exist: " + TARGET_NOK_NAME, t );
    }


    @Test
    public void test_entryType()
    {
        OOXMLTarget t = opc.getEntryCollection().getTargetByName( TARGET_OK_NAME );
        assertEquals( TARGET_OK_TYPE, t.getType() );
    }


    @Test
    public void test_mimeType()
    {
        OOXMLTarget t = opc.getEntryCollection().getTargetByName( TARGET_OK_NAME );
        assertEquals( TARGET_OK_MIMETYPE, t.getMimeType() );
    }

}
