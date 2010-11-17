/*
 * This file is part of the source of
 * 
 * Office-o-tron - a web-based office document validator for Java(tm)
 * 
 * Copyright (c) 2010 Novell Inc.
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.probatron.officeotron.sessionstorage.Store;
import org.probatron.officeotron.sessionstorage.ValidationSession;

public class Driver
{
    static Logger logger = Logger.getLogger( Driver.class );

    static
    {
        // set up log message format, etc.
        String logLvl = System.getProperty( "property://probatron.org/officeotron-log-level" );
        logLvl = ( logLvl == null ) ? "ERROR" : logLvl;

        Properties p = new Properties();
        p.setProperty( "log4j.rootCategory", logLvl + ", A1" );
        p.setProperty( "log4j.appender.A1", "org.apache.log4j.ConsoleAppender" );
        p.setProperty( "log4j.appender.A1.target", "System.err" );
        p.setProperty( "log4j.appender.A1.layout", "org.apache.log4j.PatternLayout" );
        p.setProperty( "log4j.appender.A1.layout.ConversionPattern", "%c %p - %m%n" );
        PropertyConfigurator.configure( p );

    }


    public static void main( String[] args )
    {
    	ArrayList<String> fns = new ArrayList<String>();
    	boolean onlyErrors = false;
    	
    	for ( int i = 0; i < args.length; i++ ) {
    		if ( args[i].equals("--errors-only") ) {
    			onlyErrors = true;
    		} else if ( args[i].equals("--help") ) {
    			System.out.println( "arguments: [--errors-only] file1 ..." );
    		} else {
    			fns.add( args[i] );
    		}
    	}
    	
    	final boolean showInfos = !onlyErrors; 
        Store.init( System.getProperty( "java.io.tmpdir" ), false );

        for (String fn : fns) {
        	logger.debug( "Validating file " + new File( fn ).getAbsolutePath() );
        	
        	CommandLineSubmission cls = new CommandLineSubmission( fn );

            ValidationSession vs = Utils.autoCreateValidationSession( cls, new ReportFactory() {
                public ValidationReport create()
                {
                    return new StdioValidationReport( showInfos );
                }
            } );

            vs.prepare();
            vs.validate();

            vs.cleanup();

            try
            {
                vs.getCommentary().streamOut();
            }
            catch( IOException e )
            {
                e.printStackTrace();
            }
		}
    }
}
