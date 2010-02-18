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

import java.util.Properties;

import junit.framework.TestCase;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

public class OOXMLValidationSessionTest extends TestCase
{
    OPCPackage opc, opc2;
    OOXMLValidationSession ovs, ovs2;

    static
    {
        // set up log message format, etc.
        String logLvl = System.getProperty( "property://probatron.org/officeotron-log-level" );
        logLvl = ( logLvl == null ) ? "TRACE" : logLvl;

        Properties p = new Properties();
        p.setProperty( "log4j.rootCategory", logLvl + ", A1" );
        p.setProperty( "log4j.appender.A1", "org.apache.log4j.ConsoleAppender" );
        p.setProperty( "log4j.appender.A1.target", "System.err" );
        p.setProperty( "log4j.appender.A1.layout", "org.apache.log4j.PatternLayout" );
        p.setProperty( "log4j.appender.A1.layout.ConversionPattern", "%c %p - %m%n" );
        PropertyConfigurator.configure( p );
    }

// FIXME
    @Override
    protected void setUp() throws Exception
    {
    // TODO: mew test code
    // opc = new OPCPackage( "file:etc/test-data/maria.xlsx" );
    // ovs = new OOXMLValidationSession(
    // new DummySubmission( "file:etc/test-data/maria.xlsx" ),
    // "file:etc/schema/29500T/" );
    //        
    // opc2 = new OPCPackage( "file:etc/test-data/torture.pptx" );
    // ovs2 = new OOXMLValidationSession(
    // new DummySubmission( "file:etc/test-data/torture.pptx" ),
    // "file:etc/schema/29500T/" );
    }


    @Test
    public void test_packageIntegrity()
    {
        opc.process();
        opc2.process();
        ovs.checkRelationships( opc );
        assertTrue( ovs.getErrCount() == 2 );

        ovs2.checkRelationships( opc2 );
        assertTrue( ovs.getErrCount() == 2 );
    }

}
