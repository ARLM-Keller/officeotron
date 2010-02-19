package org.probatron.officeotron;

import java.io.File;
import java.io.IOException;
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
        logLvl = ( logLvl == null ) ? "DEBUG" : logLvl;

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
        String fn = args[ 0 ];
        logger.debug( "Command line request to validate file:" + new File(fn).getAbsolutePath() );
        Store.init( "c:\\officeotron", "cmd /c unzip" );
        
        CommandLineSubmission cls = new CommandLineSubmission( fn );
        
        ValidationSession vs = Utils.autoCreateValidationSession( cls, "file:C:\\tomcat-5.5\\webapps\\29500T\\" );
        vs.prepare();
        vs.validate();
       
        
      //  vs.cleanup();
        
        try
        {
            vs.getCommentary().streamOut( System.out  );
        }
        catch( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        
        
    }
}
