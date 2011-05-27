/*
 * This file is part of the source of
 * 
 * Office-o-tron - a web-based office document validator for Java(tm)
 * 
 * Copyright (c) 2010-2011 Novell Inc.
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
package org.probatron.officeotron.utils;

import java.io.IOException;
import java.net.URL;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

/**
 * Entity resolver caching the downloaded files.
 * 
 * @author Cedric Bosdonnat <cbosdonnat@suse.com>
 *
 */
public class CachingResourceResolver implements LSResourceResolver
{
	private static final String OTHERS_SCHEMA = "others/";

	public LSInput resolveResource(String type, String namespaceURI,
			String publicId, String systemId, String baseURI)
	{
		LSInput src = null;
		String schemaPath = null;
		
		if ( "http://dublincore.org/schemas/xmls/qdc/2003/04/02/dc.xsd".equals( systemId ) )
		{
			schemaPath = OTHERS_SCHEMA + "dc.xsd";
		}
		else if ( "http://dublincore.org/schemas/xmls/qdc/2003/04/02/dcterms.xsd".equals( systemId ) )
		{
			schemaPath = OTHERS_SCHEMA + "dcterms.xsd";
		}
		else if ( "dcmitype.xsd".equals(systemId) )
		{
			schemaPath = OTHERS_SCHEMA + "dcmitype.xsd";
		}
		else if ( "http://www.w3.org/2001/xml.xsd".equals( systemId ) )
		{
			schemaPath = OTHERS_SCHEMA + "xml.xsd";
		}
		else if ( "http://dublincore.org/schemas/xmls/qdc/2003/04/02/dc.xsd".equals( systemId ) )
		{
			schemaPath = OTHERS_SCHEMA + "dc.xsd";
		}
		else if ( "http://dublincore.org/schemas/xmls/qdc/2003/04/02/dcterms.xsd".equals( systemId ) )
		{
			schemaPath = OTHERS_SCHEMA + "dcterms.xsd";
		}
		else if ( "XMLSchema.dtd".equals( systemId ) )
		{
			schemaPath = OTHERS_SCHEMA + "XMLSchema.dtd";
		}
		else if ( "datatypes.dtd".equals( systemId ) )
		{
			schemaPath = OTHERS_SCHEMA + "datatypes.dtd";
		}
		
		if ( schemaPath != null )
		{
			URL schema = ClassLoader.getSystemResource( "schema/" + schemaPath );
			src = new LSInputAdapter( );
			try {
				src.setByteStream( schema.openStream() );
			} catch (IOException e) {
				src = null;
			}
		}
		
		return src;
	}

}
