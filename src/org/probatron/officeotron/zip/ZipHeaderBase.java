package org.probatron.officeotron.zip;

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

import org.apache.log4j.Logger;

public class ZipHeaderBase
{

    static Logger logger = Logger.getLogger( ZipHeaderBase.class );

    protected byte[] extractVersion = new byte[ 2 ];
    protected byte[] general = new byte[ 2 ];
    protected byte[] method = new byte[ 2 ];
    protected byte[] modTime = new byte[ 2 ];
    protected byte[] modDate = new byte[ 2 ];
    protected int crc32;
    protected int compressedSize;
    protected int uncompressedSize;
    protected short filenameLength;
    protected short extraFieldLength;
    protected String filename;
    
    protected ZipHeaderBase()
    {
        // To prevent instantiation
    }


    public byte[] getExtractVersion()
    {
        return extractVersion;
    }


    public byte[] getGeneral()
    {
        return general;
    }


    public byte[] getMethod()
    {
        return method;
    }


    public byte[] getModTime()
    {
        return modTime;
    }


    public byte[] getModDate()
    {
        return modDate;
    }


    public int getCrc32()
    {
        return crc32;
    }


    public int getCompressedSize()
    {
        return compressedSize;
    }


    public int getUncompressedSize()
    {
        return uncompressedSize;
    }


    public short getFilenameLength()
    {
        return filenameLength;
    }


    public short getExtraFieldLength()
    {
        return extraFieldLength;
    }


    public String getFilename()
    {
        return filename;
    }

}
