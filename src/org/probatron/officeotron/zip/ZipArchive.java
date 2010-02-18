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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.probatron.officeotron.Utils;
import org.probatron.officeotron.sessionstorage.ValidationSession;

public class ZipArchive
{
    static Logger logger = Logger.getLogger( ZipArchive.class );

    public final static int ZIP_LOCAL_SIGNATURE = 0x04034b50;
    public final static int ZIP_CENTRAL_SIGNATURE = 0x02014b50;

    private ArrayList< ZipLocalHeader > localHeaders = new ArrayList< ZipLocalHeader >();
    private ArrayList< ZipCentralRecord > centralRecords = new ArrayList< ZipCentralRecord >();

    ValidationSession session;


    public ZipArchive( ValidationSession session )
    {
        this.session = session;
        process();
    }


    private void process()
    {
        InputStream is = this.session.getPackageStream();

        try
        {
            for( ;; )
            {
                int sig = Utils.readIntLittle( is );

                if( sig == ZIP_LOCAL_SIGNATURE )
                {
                    logger.debug( "Reading local header" );
                    ZipLocalHeader zlh = new ZipLocalHeader( is );
                    localHeaders.add( zlh );
                    logger.debug( "Read header for entry: " + zlh.getFilename() );

                    // skip over data
                    is.skip( zlh.getCompressedSize() );

                }
                else if( sig == ZIP_CENTRAL_SIGNATURE )
                {
                    logger.debug( "Reading central directory header" );
                    ZipCentralRecord zcr = new ZipCentralRecord( is );
                    centralRecords.add( zcr );
                    logger.debug( "Read central record for entry: " + zcr.getFilename() );

                }
                else
                {
                    logger.debug( "unrecognized 4-byte sequence " + Integer.toHexString( sig )
                            + "; skipping" );
                    break;
                }
            }
        }
        catch( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Utils.streamClose( is );

    }


    public ZipLocalHeader getLocalHeader( int n )
    {
        return this.localHeaders.get( n );
    }


    public ZipCentralRecord getCentralRecord( int n )
    {
        return this.centralRecords.get( n );
    }


    public int getLocalHeaderCount()
    {
        return this.localHeaders.size();
    }


    public int getCentralRecordCount()
    {
        return this.centralRecords.size();
    }


    public String asXmlString()
    {
        logger.debug( "Creating XML representation of ZIP" );
        StringBuffer sb = new StringBuffer();
        sb.append( "<?xml version='1.0' standalone='yes'?><zip-archive><local-headers>" );

        for( int i = 0; i < this.localHeaders.size(); i++ )
        {
            sb.append( getLocalHeader( i ).asXmlString() );

        }

        sb.append( "</local-headers><central-directory>" );

        for( int i = 0; i < this.centralRecords.size(); i++ )
        {
            sb.append( getCentralRecord( i ).asXmlString() );
        }

        sb.append( "</central-directory></zip-archive>" );

        return sb.toString();

    }

}
