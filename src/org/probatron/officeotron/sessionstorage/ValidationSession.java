/*
 * This file is part of the source of
 * 
 * Office-o-tron - a web-based office document validator for Java(tm)
 * 
 * Copyright (c) 2009 Griffin Brown Digital Publishing Ltd.
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
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.probatron.officeotron.Utils;
import org.probatron.officeotron.ValidationReport;
import org.probatron.officeotron.zip.ZipArchive;

public class ValidationSession
{
    static Logger logger = Logger.getLogger( ValidationSession.class );

    private UUID uuid;
    private String filename;
    private ValidationReport commentary = new ValidationReport();
    protected int errCount;
    private boolean donePrep;
    private ZipArchive zipArchive;


    public ValidationSession( String filename )
    {
        assert Store.tmpFolder != null : "Store not initialized";
        this.filename = filename;
    }


    public String getCandidateFilename()
    {
        return filename;
    }


    public ValidationReport getCommentary()
    {
        return commentary;
    }


    public int getErrCount()
    {
        return errCount;
    }


    public UUID getUuid()
    {
        return uuid;
    }


    public InputStream getPackageStream()
    {
        return Store.getStream( uuid );

    }


    public void prepare()
    {
        logger.debug( "Preparing session" );
        try
        {
            File f = new File( this.filename );
            FileInputStream fis = new FileInputStream( f );
            this.uuid = Store.putZippedResource( fis );
        }
        catch( Exception e )
        {
            logger.fatal( e.getMessage() );
        }

        this.zipArchive = new ZipArchive( this );

        String fn = Store.getDirectory( uuid ) + File.separator + uuid + "-zip.xml";
        try
        {
            Utils.writeBytesToFile( this.zipArchive.asXmlString().getBytes(), fn );
        }
        catch( IOException e )
        {
            logger.error( e.getMessage() );
        }

        donePrep = true;
    }


    public void cleanup()
    {
        Store.delete( uuid );
    }


    public URI getUrlForEntry( String entry )
    {
        return Store.urlForEntry( uuid, entry );

    }


    public ZipArchive getZipArchive()
    {
        return this.zipArchive;
    }


    public void validate()
    {
        if( !donePrep )
        {
            throw new IllegalStateException( "prepare() has not been called" );
        }
        prepare();
    }

}
