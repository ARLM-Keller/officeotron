/*
 * This file is part of the source of
 * 
 * Office-o-tron - a web-based office document validator for Java(tm)
 * 
 * Copyright (c) 2011 Novell Inc.
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

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


/**
 * Unit test class for the {@link MceXmlFilter} class.
 * 
 * @author Cédric Bosdonnat <cbosdonnat@suse.com>
 *
 */
public class MceXmlFilterTest {
	
	private MceXmlFilter mTested;
	
	@Before
	public void setup() throws Exception {
		
		// Create the parser itself
		XMLReader parent = XMLReaderFactory.createXMLReader();
        mTested = new MceXmlFilter( parent );
	}

	@Test
	public void testIgnorables() throws Exception {
		
		String test = "<aaa xmlns:ext=\"some-ext\" " +
						   "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" " +
                           "mc:Ignorable=\"ext\">" +
                          "<bbb ext:prop=\"foo\"/>" +
                          "<ext:bar>" +
                              "<ccc>" +
                                  "<ddd/>" +
                              "</ccc>" +
                          "</ext:bar>" + 
                      "</aaa>";
		
		
		String expected = "<aaa xmlns:ext=\"some-ext\" "+
                               "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\">" +
                              "<bbb/>" +
                          "</aaa>";
		
		String actual = filter( test );
		
		assertEquals( expected, actual );
	}

	@Test
	public void testNormalizeSpace() {
		String expected = "s1 s2 s3";
		String tested = " s1 \ts2  \rs3\n";
		
		String result = MceXmlFilter.normalizeWhitespaces( tested );
		assertEquals( expected, result );
	}
	
	/**
	 * Actually run the filter and dump it's result to a string.
	 * 
	 * @param test the string to filter
	 * 
	 * @return the filtered string
	 * 
	 * @throws TransformerException if anything goes wrong during the 
	 * 			dumping stage.
	 */
	private String filter( String test ) throws TransformerException {
		
		// Actually run the filter
		InputSource inSource = new InputSource( new StringReader( test ) );
		SAXSource filtered = new SAXSource( mTested, inSource );
		
		// Chain the filter with an identity transformation to dump as a string
		TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty( OutputKeys.OMIT_XML_DECLARATION, "yes" );
        
        StringWriter writer = new StringWriter();
		Result result = new StreamResult( writer );
		
		transformer.transform( filtered, result );
		
		return writer.toString();
	}
}
