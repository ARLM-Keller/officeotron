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

package org.probatron.officeotron;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Targets of Relationships in OPC Packages.
 */
public class OOXMLTarget
{

    static Logger logger = Logger.getLogger( OOXMLTarget.class );

    public final static String NS_PACKAGE_RELATIONSHIPS = "http://schemas.openxmlformats.org/package/2006/relationships";
    String hostPartEntryName;
    String type;
    String mimeType;
    String name;


    public OOXMLTarget( String hostPartEntryName, AttributesImpl atts )
    {
        this.hostPartEntryName = hostPartEntryName;
        this.type = atts.getValue( "Type" );
        this.name = atts.getValue( "Target" );

        logger.debug( "Created new OOXMLTarget " + this );
    }


    public String getType()
    {
        return type;
    }


    public void setType( String type )
    {
        this.type = type;
    }


    public String getMimeType()
    {
        return mimeType;
    }


    public String getExtension()
    {
        String[] split = this.name.split( "\\." );
        return split[ split.length - 1 ];
    }


    public void setMimeType( String mimeType )
    {
        this.mimeType = mimeType;
    }


    /**
     * @return the full name of the rels file in which this Relationship occurs
     */
    public String getHostPartEntryName()
    {
        return hostPartEntryName;
    }


    public String getName()
    {
        return name;
    }


    public void setName( String target )
    {
        this.name = target;
    }


    public String getHostFolder()
    {
        String base = this.hostPartEntryName.replaceFirst( "_rels/.*$", "" );
        return base;
    }


    public String getFilename()
    {
        String s = this.name.replaceFirst( "^.*/", "" );
        return s;
    }


    public String getTargetFolder()
    {
        String pn = getTargetAsPartName();

        return pn.replaceFirst( "[^/]+$", "" );
    }


    public String getTargetAsPartName()
    {
        String s = getName();

        if( s.startsWith( "/" ) )
        {
            return s;
        }

        // else, we're into relative target reference territory

        // the "source part" URL base is the parent of the folder in which the .rels file sits
        String sbase = this.hostPartEntryName.replaceFirst( "_rels/.*$", "" );

        String result = null;

        // resolve the relative address
        try
        {
            URI base = new URI( sbase );
            URI resolved = base.resolve( new URI( this.getName() ) );

            result = resolved.toString();

            if( !result.startsWith( "/" ) )
            {
                result = "/" + result;
            }

            logger.debug( "Target " + this.getName() + " is resolved to Part Name " + result );

        }
        catch( URISyntaxException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;

    }


    @Override
    public String toString()
    {
        return "type=" + type + "; target=" + name + "; host=" + this.hostPartEntryName;
    }

}
