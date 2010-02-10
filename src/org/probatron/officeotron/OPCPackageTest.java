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

import java.util.Properties;

import junit.framework.TestCase;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

public class OPCPackageTest extends TestCase
{
    OPCPackage opc;
    OPCPackage opc2;

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
        // TODO: new test code
//        opc = new OPCPackage( "file:etc/test-data/maria.xlsx" );
//        opc2 = new OPCPackage( "file:etc/test-data/maria.xlsx" );
//        opc.process();
//        opc2 = new OPCPackage( "file:etc/test-data/torture.pptx" );
//        opc2.process();
//        super.setUp();
    }


    @Test
    public void test_targetCount()
    {
        assertTrue( opc.getEntryCollection().size() == 10 );
        assertTrue( opc2.getEntryCollection().size() == 10);
    }


    @Test
    public void test_entrySizeMatch()
    {
        assertTrue( opc.getEntryCollection().size() == opc.getEntryCollection().getPartNamesSet().size() );
        assertTrue( opc2.getEntryCollection().size() == opc2.getEntryCollection().getPartNamesSet().size() );
    }


    @Test
    public void test_anEntry()
    {
        OOXMLTarget t = opc.getEntryCollection().getTargetByName( "/xl/worksheets/sheet1.xml" );
        assertTrue( t != null );
        t = opc2.getEntryCollection().getTargetByName( "/ppt/slides/slide1.xml" );
        assertTrue( t != null );
    }


    @Test
    public void test_entryType()
    {
        OOXMLTarget t = opc.getEntryCollection().getTargetByName( "/xl/worksheets/sheet1.xml" );
        assertTrue( t
                .getType()
                .equals(
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet" ) );
        t = opc2.getEntryCollection().getTargetByName( "/ppt/slides/slide1.xml" );
        assertTrue( t
                .getType()
                .equals(
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/slide" ) );
    }
    
    @Test
    public void test_mimeType()
    {
        OOXMLTarget t = opc.getEntryCollection().getTargetByName( "/xl/worksheets/sheet1.xml" );
        assertTrue( t
                .getMimeType()
                .equals(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml" ) );
        t = opc2.getEntryCollection().getTargetByName( "/ppt/slides/slide1.xml" );
        assertTrue( t
                .getMimeType()
                .equals(
                        "application/vnd.openxmlformats-officedocument.presentationml.slide+xml" ) );
    }

}
