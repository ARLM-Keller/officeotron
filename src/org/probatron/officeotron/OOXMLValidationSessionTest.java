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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.apache.log4j.PropertyConfigurator;
import org.probatron.officeotron.sessionstorage.Store;

public class OOXMLValidationSessionTest
{
	private static final File TEST_FILE = new File ( "etc/test-data/torture.pptx" );
	private static final int EXPECTED_ERRORS = 1;
	
    OOXMLValidationSession ovs;
    UUID uuid;

    @BeforeClass
    public static void classSetUp( )
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
    
    @Before
    public void setUp() throws Exception
    {
    	Store.init( System.getProperty( "java.io.tmpdir" ), false );
    	uuid = Store.putZippedResource( new FileInputStream( TEST_FILE ), TEST_FILE.getPath() );
        ovs = new OOXMLValidationSession( uuid, new ReportFactory() {
			
			public ValidationReport create() {
				return new DummyValidationReport();
			}
		});
    }


    @Test
    public void test_packageIntegrity()
    {
    	ovs.validate();
    	assertEquals( "Invalid error number", EXPECTED_ERRORS, ovs.getErrCount() );
    }
    
    
    /**
     * Dummy validation report doing nothing only for the test purpose.
     */
    private class DummyValidationReport implements ValidationReport {

		public void addComment(String s) {
		}

		public void addComment(String klass, String s) {
		}

		public void decIndent() {
		}

		public void endReport() {
		}

		public int getErrCount() {
			return 0;
		}

		public void incErrs() {
		}

		public void incIndent() {
		}

		public void streamOut() throws IOException {
		}
    	
    }
}
