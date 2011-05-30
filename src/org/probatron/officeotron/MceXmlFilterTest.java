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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
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
	
	@Rule public TestName name = new TestName();
	
	private MceXmlFilter mTested;
	private CommentatingErrorHandler mErrorHandler;
	
	@Before
	public void setup() throws Exception {
		
		// Create the parser itself
		XMLReader parent = XMLReaderFactory.createXMLReader();
        mTested = new MceXmlFilter( parent );
        
        mErrorHandler = new CommentatingErrorHandler( new StdioValidationReport( false ), name.getMethodName() );
        mTested.setErrorHandler( mErrorHandler );
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
		assertEquals( "Unexpected error during parse", 0, mErrorHandler.getInstanceErrCount() );
	}
	
	@Test
	public void testPreserveElements() throws Exception {
		String test = "<aaa xmlns:ext=\"some-ext\" " +
						   "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" " +
                           "mc:PreserveElements=\"ext:*\" />";
		
		
		String expected = "<aaa xmlns:ext=\"some-ext\" "+
                               "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\"/>";
		
		String actual = filter( test );
		
		assertEquals( expected, actual );
		assertEquals( "Unexpected error during parse", 0, mErrorHandler.getInstanceErrCount() );
	}
	
	@Test
	public void testPreserveAttributes() throws Exception {
		String test = "<aaa xmlns:ext=\"some-ext\" " +
						   "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" " +
                           "mc:PreserveAttributes=\"ext:*\" />";
		
		
		String expected = "<aaa xmlns:ext=\"some-ext\" "+
                               "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\"/>";
		
		String actual = filter( test );
		
		assertEquals( expected, actual );
		assertEquals( "Unexpected error during parse", 0, mErrorHandler.getInstanceErrCount() );
	}
	
	@Test
	public void testMustUnderstand() throws Exception {
		String test = "<aaa xmlns:ext=\"some-ext\" " +
						   "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" " +
                           "mc:MustUnderstand=\"\" />";
		
		
		String expected = "<aaa xmlns:ext=\"some-ext\" "+
                               "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\"/>";
		
		String actual = filter( test );
		
		assertEquals( expected, actual );
		assertEquals( "Unexpected error during parse", 0, mErrorHandler.getInstanceErrCount() );
	}
	
	@Test
	public void testChoiceNormal() throws Exception {
		String test = "<aaa xmlns:ext=\"some-ext\" " +
						   "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\">" +
                          "<mc:AlternateContent>" +
                              "<mc:Choice Requires=\"ext\">" +
                                  "<ext:foo/>" +
                              "</mc:Choice>" +
                          "</mc:AlternateContent>" +
                      "</aaa>";
		
		
		String expected = "<aaa xmlns:ext=\"some-ext\" "+
                               "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\"/>";
		
		String actual = filter( test );
		
		assertEquals( expected, actual );
		assertEquals( "Unexpected error during parse, see run output for more details", 0, mErrorHandler.getInstanceErrCount() );
	}
	
	@Test
	public void testChoiceMissingRequires() throws Exception {
		String test = "<aaa xmlns:ext=\"some-ext\" " +
						   "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\">" +
                          "<mc:AlternateContent>" +
                              "<mc:Choice />" +
                          "</mc:AlternateContent>" +
                      "</aaa>";
		
		
		String expected = "<aaa xmlns:ext=\"some-ext\" "+
                               "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\"/>";
		
		String actual = filter( test );
		
		assertEquals( expected, actual );
		assertEquals( "Missing Requires in <mc:Choice> should be reported", 1, mErrorHandler.getInstanceErrCount() );
	}
	
	@Test
	public void testChoicePrefixedRequires() throws Exception {
		String test = "<aaa xmlns:ext=\"some-ext\" " +
						   "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\">" +
                          "<mc:AlternateContent>" +
                              "<mc:Choice mc:Requires=\"ext\"/>" +
                          "</mc:AlternateContent>" +
                      "</aaa>";
		
		
		String expected = "<aaa xmlns:ext=\"some-ext\" "+
                               "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\"/>";
		
		String actual = filter( test );
		
		assertEquals( expected, actual );
		assertEquals( "Prefixed Requires in <mc:Choice> should be reported", 2, mErrorHandler.getInstanceErrCount() );
	}
	
	@Test
	public void testChoiceUnprefixedIgnorables() throws Exception {
		String test = "<aaa xmlns:ext=\"some-ext\" " +
						   "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\">" +
                          "<mc:AlternateContent>" +
                              "<mc:Choice Requires=\"ext\" Ignorables=\"ext\"/>" +
                          "</mc:AlternateContent>" +
                      "</aaa>";
		
		
		String expected = "<aaa xmlns:ext=\"some-ext\" "+
                               "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\"/>";
		
		String actual = filter( test );
		
		assertEquals( expected, actual );
		assertEquals( "Unprefixed Ignorables in <mc:Choice> should be reported", 1, mErrorHandler.getInstanceErrCount() );
	}
	
	@Test
	public void testChoiceXmlAttributes() throws Exception {
		String test = "<aaa xmlns:ext=\"some-ext\" " +
		                  "xmlns:xml=\"http://www.w3.org/XML/1998/namespace\" " + 
						  "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\">" +
                          "<mc:AlternateContent>" +
                              "<mc:Choice Requires=\"ext\" xml:lang=\"fr\" xml:space=\"preserve\"/>" +
                          "</mc:AlternateContent>" +
                      "</aaa>";
		
		
		String expected = "<aaa xmlns:ext=\"some-ext\" "+
		                       "xmlns:xml=\"http://www.w3.org/XML/1998/namespace\" " + 
                               "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\"/>";
		
		String actual = filter( test );
		
		assertEquals( expected, actual );
		assertEquals( "xml:lang and xml:space in <mc:Choice> should be reported", 2, mErrorHandler.getInstanceErrCount() );
	}
	
	@Test
	public void testChoiceParent() throws Exception {
		String test = "<aaa xmlns:ext=\"some-ext\" " +
		                  "xmlns:xml=\"http://www.w3.org/XML/1998/namespace\" " + 
						  "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\">" +
                              "<mc:Choice Requires=\"ext\"/>" +
                      "</aaa>";
		
		
		String expected = "<aaa xmlns:ext=\"some-ext\" "+
		                       "xmlns:xml=\"http://www.w3.org/XML/1998/namespace\" " + 
                               "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\"/>";
		
		String actual = filter( test );
		
		assertEquals( expected, actual );
		assertEquals( "<mc:Choice> with no <mc:AlternateContent> parent should be reported", 1, mErrorHandler.getInstanceErrCount() );
	}
	
	@Test
	public void testFallbackParent() throws Exception {
		String test = "<aaa xmlns:ext=\"some-ext\" " +
		                  "xmlns:xml=\"http://www.w3.org/XML/1998/namespace\" " + 
						  "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\">" +
                              "<mc:Fallback/>" +
                      "</aaa>";
		
		
		String expected = "<aaa xmlns:ext=\"some-ext\" "+
		                       "xmlns:xml=\"http://www.w3.org/XML/1998/namespace\" " + 
                               "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\"/>";
		
		String actual = filter( test );
		
		assertEquals( expected, actual );
		assertEquals( "<mc:Fallback> with no <mc:AlternateContent> parent should be reported", 1, mErrorHandler.getInstanceErrCount() );
	}
	
	@Test
	public void testFallbackNormal() throws Exception {
		String test = "<aaa xmlns:ext=\"some-ext\" " +
						   "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\">" +
                          "<mc:AlternateContent>" +
                              "<mc:Fallback mc:Ignorable=\"ext\">" +
                                  "<ext:foo/>" +
                                  "<bbb/>" +
                              "</mc:Fallback>" +
                          "</mc:AlternateContent>" +
                      "</aaa>";
		
		
		String expected = "<aaa xmlns:ext=\"some-ext\" "+
                               "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\">" +
                              "<bbb/>" +
                          "</aaa>";
		
		String actual = filter( test );
		
		assertEquals( expected, actual );
		assertEquals( "Unexpected error during parse, see run output for more details", 0, mErrorHandler.getInstanceErrCount() );
	}
	
	@Test
	public void testProcessContent() throws Exception {
		String test = "<aaa xmlns:ext=\"some-ext\" " +
						   "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" " +
                           "mc:Ignorable=\"ext\" " +
                           "mc:ProcessContent=\"ext:compat\">" +
                          "<ext:bar>" +
                              "<ccc>" +
                                  "<ddd/>" +
                              "</ccc>" +
                              "<ext:compat>" +
                                  "<eee/>" +
                              "</ext:compat>" +
                          "</ext:bar>" + 
                      "</aaa>";
		
		
		String expected = "<aaa xmlns:ext=\"some-ext\" "+
                               "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\">" +
                              "<eee/>" +
                          "</aaa>";
		
		String actual = filter( test );
		
		assertEquals( expected, actual );
		assertEquals( "Unexpected error during parse", 0, mErrorHandler.getInstanceErrCount() );
	}
	
	@Test
	public void testProcessContentWildcar() throws Exception {
		String test = "<aaa xmlns:ext=\"some-ext\" " +
                           "xmlns:ext2=\"some-ext2\" " +
						   "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" " +
                           "mc:Ignorable=\"ext\" " +
                           "mc:ProcessContent=\"ext2:*\">" +
                          "<ext:bar>" +
                              "<ccc>" +
                                  "<ddd/>" +
                              "</ccc>" +
                              "<ext2:compat>" +
                                  "<eee/>" +
                              "</ext2:compat>" +
                          "</ext:bar>" + 
                      "</aaa>";
		
		
		String expected = "<aaa xmlns:ext=\"some-ext\" "+
                               "xmlns:ext2=\"some-ext2\" " +
                               "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\">" +
                              "<eee/>" +
                          "</aaa>";
		
		String actual = filter( test );
		
		assertEquals( expected, actual );
		assertEquals( "Unexpected error during parse", 0, mErrorHandler.getInstanceErrCount() );
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
