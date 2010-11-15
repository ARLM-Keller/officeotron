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

package org.probatron.officeotron.sessionstorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.probatron.officeotron.Utils;

public class Store
{
    static Logger logger = Logger.getLogger( Store.class );

    static String tmpFolder;
    static String unzipInvocation;
    static boolean webMode;
    
    static HashMap<UUID, String> localMap = new HashMap<UUID, String>();


    public static void init( String tmpFolder, String unzipInvocation, boolean webMode )
    {
        Store.tmpFolder = tmpFolder;
        File tmp = new File( tmpFolder );
        if ( !tmp.isDirectory() ) {
        	tmp.mkdirs();
        	tmp.mkdir();
        }
        
        Store.unzipInvocation = unzipInvocation;
        Store.webMode = webMode;
    }


    public static UUID putZippedResource( InputStream is, String filename ) throws IOException
    {

        UUID uuid = UUID.randomUUID();
        String fn = filename;
        new File( getDirectory( uuid ) ).mkdir();
        File cwd = new File( getDirectory( uuid ) );

        // Save the file locally only for web mode
        if ( webMode ) {
        	fn = getDirectory( uuid ) + File.separator + uuid + ".bin";
	        long written = Utils.streamToFile( is, fn, false );
	        logger.debug( "Wrote " + written + " bytes to file" );
        }
        
        localMap.put( uuid, fn );

        // unzip it
        String cmd = unzipInvocation + " -qq " + fn + " -d"
                + getDirectory( uuid );
        try
        {

            Process p = Runtime.getRuntime().exec( cmd, null, cwd );
            p.getErrorStream();
            
            int ret = p.waitFor();
            
            p.destroy();
            logger.debug( "Done cmd: " + cmd + ". return code=" + ret );

        }
        catch( Exception e )
        {
            logger.fatal( e.getMessage() );
        }

        return uuid;
    }


    public static InputStream getStream( UUID uuid )
    {
        String fn = localMap.get( uuid );
        InputStream is = null;
        if ( fn != null ) {
        	File f = new File( fn );

        	try
        	{
        		is = new FileInputStream( f );
        	}
        	catch( FileNotFoundException e )
        	{
        		// we'll return null in this case
        	}
        }

        return is;
    }


    public static URI urlForEntry( UUID uuid, String name )
    {
        String fn = getDirectory( uuid ) + File.separator + name;
        URI uri = new File( fn ).toURI();
        return uri;
    }


    public static void delete( UUID uuid )
    {
        File dir = new File( getDirectory( uuid ) );
        Utils.deleteDir( dir );
        localMap.remove( uuid );
    }
    

    public static String getDirectory( UUID uuid )
    {
        return tmpFolder + File.separator + uuid;
    }


    public static String getFilename( UUID uuid )
    {
        return localMap.get( uuid );
    }

}
