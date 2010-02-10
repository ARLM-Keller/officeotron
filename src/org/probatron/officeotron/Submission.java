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
 * 
 */

package org.probatron.officeotron;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

public class Submission
{
    static Logger logger = Logger.getLogger( Submission.class );

    private HttpServletRequest request;
    private HashMap<String, String> optionMap = new HashMap<String, String>();
    private String responseErr;
    private UUID candidateUuid;


    public Submission( HttpServletRequest req )
    {
        this.request = req;
    }


    public int fetchFromClient() throws IOException
    {
        int ret = 200;

        boolean isMultipart = ServletFileUpload.isMultipartContent( this.request );

        if( isMultipart )
        {
            parseMultiPart( this.request );
        }
        else
        {
            parseDirect( this.request );
        }

        return ret;
    }


    /**
     * Retrieves items contained in a multipart servlet request.
     *
     * @param req
     * @return a map, mapping between item names and the UUIDs for their stored values
     * @throws IOException
     */
    public void parseMultiPart( HttpServletRequest req ) throws IOException
    {
        logger.debug( "Processing multipart submission" );
        ServletFileUpload upload = new ServletFileUpload();

        try
        {
            FileItemIterator iter = upload.getItemIterator( req );

            while( iter.hasNext() )
            {
                FileItemStream item = iter.next();
                String name = item.getFieldName();
                logger.trace( "Got file item named " + name );
                InputStream sis = item.openStream();

                if( name.equalsIgnoreCase( "candidate" ) )
                {
                    this.candidateUuid = Store.putZippedResource( sis ); // closes stream
                    logger.debug( "Persisted candidate item with UUID: " + this.candidateUuid );

                }
                else
                {
                    byte[] ba = Utils.getBytesToEndOfStream( sis, true ); // closes stream
                    String val = new String( ba );
                    logger.debug( "Setting option " + name + " = " + val );
                    this.optionMap.put( name, val );
                }
            }
        }
        catch( FileUploadException e )
        {
            logger.fatal( e.getMessage() );
            throw new RuntimeException( e.getMessage() );
        }
    }


    @SuppressWarnings("unchecked")
    public void parseDirect( HttpServletRequest request ) throws IOException
    {
        logger.debug( "Processing direct submission" );
        InputStream sis = request.getInputStream();
        this.candidateUuid = Store.putZippedResource( sis ); // closes stream

        Enumeration<String> en = request.getParameterNames();
        while( en.hasMoreElements() )
        {
            String name = en.nextElement();
            String val = request.getParameter( name );
            this.optionMap.put( name, val );
        }
    }


    public InputStream getCandidateStream()
    {
        return Store.getStream( candidateUuid );
    }


    public byte[] getCandidateBytes()
    {
        return Store.getBytes( candidateUuid );
    }


    public String getResponseErr()
    {
        return responseErr;
    }


    public String getOption( String name )
    {
        return optionMap.get( name );
    }


    public boolean getBooleanOption( String name )
    {
        String s = optionMap.get( name );
        return  (s == null) ? false : s.equalsIgnoreCase( "true" );
    }


    void cleanup()
    {
        Store.delete( candidateUuid );
    }


    public HttpServletRequest getRequest()
    {
        return request;
    }


    public UUID getCandidateUuid()
    {
        return candidateUuid;
    }
    
    

}
