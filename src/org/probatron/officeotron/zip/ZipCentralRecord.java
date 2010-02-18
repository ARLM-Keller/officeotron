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

import org.apache.log4j.Logger;
import org.probatron.officeotron.Utils;

public class ZipCentralRecord extends ZipHeaderBase
{

    static Logger logger = Logger.getLogger( ZipCentralRecord.class );

    private byte[] madeByVersion = new byte[ 2 ];

    private short fileCommentLength;
    private short diskNumberStart;
    private byte[] internalFileAttributes = new byte[ 2 ];
    private byte[] externalFileAttributes = new byte[ 4 ];
    private int localHeaderOffset;

    private String fileComment;


    public ZipCentralRecord( InputStream is )
    {
        try
        {
            is.read( madeByVersion );
            is.read( this.extractVersion );
            is.read( this.general );
            is.read( this.method );
            is.read( this.modTime );
            is.read( this.modDate );
            this.crc32 = Utils.readIntLittle( is );
            this.compressedSize = Utils.readIntLittle( is );
            this.uncompressedSize = Utils.readIntLittle( is );
            this.filenameLength = Utils.readShortLittle( is );
            this.extraFieldLength = Utils.readShortLittle( is );
            this.fileCommentLength = Utils.readShortLittle( is );
            this.diskNumberStart = Utils.readShortLittle( is );
            is.read( this.internalFileAttributes );
            is.read( this.externalFileAttributes );
            this.localHeaderOffset = Utils.readIntLittle( is );

            byte[] b = new byte[ filenameLength ];
            is.read( b );
            this.filename = new String( b ); // TODO: encoding??

            is.skip( this.extraFieldLength );

            b = new byte[ fileCommentLength ];
            is.read( b );
            this.fileComment = new String( b ); // TODO: encoding??

        }
        catch( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public byte[] getMadeByVersion()
    {
        return madeByVersion;
    }


    public short getFileCommentLength()
    {
        return fileCommentLength;
    }


    public short getDiskNumberStart()
    {
        return diskNumberStart;
    }


    public byte[] getInternalFileAttributes()
    {
        return internalFileAttributes;
    }


    public byte[] getExternalFileAttributes()
    {
        return externalFileAttributes;
    }


    public int getLocalHeaderOffset()
    {
        return localHeaderOffset;
    }


    public String getFileComment()
    {
        return fileComment;
    }
    
    public String asXmlString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append( "<central-record>" );

        sb.append( Utils.makeElement( "filename", this.filename ) );
        sb.append( Utils.makeByteElement( "version-made-by", this.madeByVersion ) );
        sb.append( Utils.makeByteElement( "version-needed-to-extract", this.extractVersion ) );
        sb.append( Utils.makeByteElement( "general-flag", this.general ) );
        sb.append( Utils.makeByteElement( "compression-method", this.method ) );
        sb.append( Utils.makeByteElement( "mod-time", this.extractVersion ) );
        sb.append( Utils.makeByteElement( "mod-date", this.extractVersion ) );
        sb.append( Utils.makeElement( "crc-32", this.crc32 ) );
        sb.append( Utils.makeElement( "compressed-size", this.compressedSize ) );
        sb.append( Utils.makeElement( "uncompressed-size", this.uncompressedSize ) );
        sb.append( Utils.makeElement( "filename-length", this.filenameLength ) );
        sb.append( Utils.makeElement( "extra-field-length", this.extraFieldLength ) );
        sb.append( Utils.makeElement( "file-comment-length", this.extraFieldLength ) );
        sb.append( Utils.makeByteElement( "internal-file-attributes", this.internalFileAttributes ) );
        sb.append( Utils.makeByteElement( "external-file-attributes", this.externalFileAttributes ) );
        sb.append( Utils.makeElement( "local-header-offset", this.localHeaderOffset ) );
        sb.append( Utils.makeElement( "file-comment", this.fileComment ) );
        

        sb.append( "</central-record>" );

        return sb.toString();
    }

}
