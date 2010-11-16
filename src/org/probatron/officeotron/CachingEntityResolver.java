/*
 * This file is part of the source of
 * 
 * Office-o-tron - a web-based office document validator for Java(tm)
 * 
 * Copyright (c) 2010 Novell Inc.
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

import java.io.IOException;
import java.net.URL;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Entity resolver caching the downloaded files.
 * 
 * @author Cedric Bosdonnat <cbosdonnat@novell.com>
 *
 */
public class CachingEntityResolver implements EntityResolver
{

	public InputSource resolveEntity(String publicId, String systemId)
			throws SAXException, IOException
	{
		InputSource src = null;
		String schemaPath = null;
		
		if ( systemId.equals( "http://dublincore.org/schemas/xmls/qdc/2003/04/02/dc.xsd" ) )
		{
			schemaPath = "dc.xsd";
		}
		else if ( systemId.equals( "http://dublincore.org/schemas/xmls/qdc/2003/04/02/dcterms.xsd" ) )
		{
			schemaPath = "dcterms.xsd";
		}
		else if ( systemId.endsWith( "dcmitype.xsd" ) )
		{
			schemaPath = "dcmitype.xsd";
		}
		
		if ( schemaPath != null )
		{
			URL schema = ClassLoader.getSystemResource( "schema/" + schemaPath );
			src = new InputSource( schema.openStream() );
		}
		
		return src;
	}

}
