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
 * 
 */

package org.probatron.officeotron;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.AttributesImpl;

public class OOXMLTarget
{

    static Logger logger = Logger.getLogger( OOXMLTarget.class );

    public final static String NS_PACKAGE_RELATIONSHIPS = "http://schemas.openxmlformats.org/package/2006/relationships";
    String hostPartEntryName;
    String type;
    String mimeType;
    String name;
    boolean slashFail;


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


    public void setMimeType( String mimeType )
    {
        this.mimeType = mimeType;
    }


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


    public String getTargetFolder()
    {
        String s = this.name.replaceFirst( "/[^/]+$", "" );
        return s;
    }


    public String getFilename()
    {
        String s = this.name.replaceFirst( "^.*/", "" );
        return s;
    }


    public String getQPartname()
    {
        String base = this.hostPartEntryName.replaceFirst( "_rels/.*$", "" );
        String s = base + getName();
        if( !s.startsWith( "/" ) )
        {
            this.slashFail = true;
            s = "/" + s; // OPC spec is contradictory on whether leading "/" is required
        }
        return s;
    }


    @Override
    public String toString()
    {
        return "type=" + type + "; target=" + name + "; host=" + this.hostPartEntryName;
    }

}
