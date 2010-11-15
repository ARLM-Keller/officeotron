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
        Store.init( System.getProperty( "java.io.tmpdir" ), "unzip", false );

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
