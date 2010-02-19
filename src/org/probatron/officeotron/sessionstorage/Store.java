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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.probatron.officeotron.Utils;

public class Store
{
    static Logger logger = Logger.getLogger( Store.class );

    static String tmpFolder;
    static String unzipInvocation;


 
    public static void init( String tmpFolder, String unzipInvocation )
    {
        Store.tmpFolder = tmpFolder;
        Store.unzipInvocation = unzipInvocation;
    }


    public static UUID putZippedResource( InputStream is ) throws IOException
    {

        UUID uuid = UUID.randomUUID();
        String fn = asFilename( uuid );
        new File( getDirectory( uuid ) ).mkdir();
        File cwd = new File( getDirectory( uuid ) );

        long written = Utils.streamToFile( is, fn, true );

        logger.debug( "Wrote " + written + " bytes to file" );

        // get the zipinfo
//        String cmd = unzipInvocation + " -Z -v " + asFilename( uuid ) + " >"
//                + getDirectory( uuid ) + File.separator + uuid + "-zipinfo.txt";
//
//        try
//        {
//
//            Process p = Runtime.getRuntime().exec( cmd, null, cwd );
//            int ret = p.waitFor();
//            logger.debug( "Done cmd: " + cmd + ". return code=" + ret );
//
//            cmd = unzipInvocation + " -Z " + asFilename( uuid ) + " >" + getDirectory( uuid )
//                    + File.separator + uuid + "-ziplist.txt";
//
//        }
//        catch( Exception e )
//        {
//            logger.fatal( e.getMessage() );
//        }

        // unzip it
        String cmd = unzipInvocation + " -qq " + asFilename( uuid ) + " -d" + getDirectory( uuid );
        try
        {

            Process p = Runtime.getRuntime().exec( cmd, null, cwd );
            int ret = p.waitFor();
            logger.debug( "Done cmd: " + cmd + ". return code=" + ret );

        }
        catch( Exception e )
        {
            logger.fatal( e.getMessage() );
        }

        return uuid;
    }


    static InputStream getStream( UUID uuid )
    {
        String fn = asFilename( uuid );
        File f = new File( fn );
        InputStream is = null;

        try
        {
            is = new  FileInputStream( f ) ;
        }
        catch( FileNotFoundException e )
        {
            // we'll return null in this case
        }

        return is;
    }


    static byte[] getBytes( UUID uuid )
    {
        byte[] ba = null;

        try
        {
            String fn = asFilename( uuid );
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Utils.streamFromFile( fn, baos, true );
            ba = baos.toByteArray();
        }
        catch( IOException e )
        {
            // we'll return null in this case
        }

        return ba;
    }


    static URI urlForEntry( UUID uuid, String name )
    {
        String fn = getDirectory( uuid ) + File.separator + name;
        URI uri = new File( fn ).toURI();
        return uri;
    }


    static void delete( UUID uuid )
    {
        File dir = new File( getDirectory( uuid ) );
        Utils.deleteDir( dir );
    }


    static String asUrlRef( UUID uuid )
    {
        return "file:" + asFilename( uuid );
    }


    static String getDirectory( UUID uuid )
    {
        return tmpFolder + File.separator + uuid;
    }


    public static String asFilename( UUID uuid )
    {
        return getDirectory( uuid ) + File.separator + uuid;
    }

}
