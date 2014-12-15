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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.Map.Entry;

import javax.xml.namespace.QName;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.XMLFilterImpl;

/**
 * Filters out the MCE ignorables. 
 * 
 * @author Cédric Bosdonnat <cbosdonnat@suse.com>
 * @see OOXML spec Part 3
 *
 */
public class MceXmlFilter extends XMLFilterImpl {

	/**
	 * Internal class storing the MCE names for a given element.
	 */
	private class ElementInfos {
		String uri;
		String localName;
		
		ArrayList<String> ignorableValue;
		ArrayList<QName> processContentValue;
		
		boolean ignoreContent = false;
		boolean processContent = false;
		
		public ElementInfos( String pUri, String pLocalName ) {
			uri = pUri;
			localName = pLocalName;
			
			ignorableValue = new ArrayList<String>();
			processContentValue = new ArrayList<QName>();
		}
		
		public boolean isElement( String pUri, String pLocalName ) {
			boolean uriOk = ( uri == null && pUri == null ) || uri.equals( pUri );
			boolean localOk = ( localName == null && pLocalName == null ) || localName.equals( pLocalName );
			
			return uriOk && localOk;
		}
	}

	private static final String NS_XML = "http://www.w3.org/XML/1998/namespace";
	private static final String NS_MCE = "http://schemas.openxmlformats.org/markup-compatibility/2006";
	private static final String ATTR_IGNORABLE = "Ignorable";
	private static final String ATTR_PROCESS_CONTENT = "ProcessContent";
	private static final String TAG_ALTERNATE_CONTENT = "AlternateContent";
	private static final String TAG_CHOICE = "Choice";
	private static final String TAG_FALLBACK = "Fallback";
	
	
	
	
	private Stack<ElementInfos> mInfos;
	private HashMap<String, String> mPrefixes;
	private Locator mLocator;
	
	
	public MceXmlFilter(XMLReader parent) {
		super( parent );
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		mLocator = locator;
		super.setDocumentLocator(locator);
	}

	@Override
	public void startDocument() throws SAXException {
		
		// Reset the ignorables for the document
		mInfos = new Stack<ElementInfos>();
		mPrefixes = new HashMap<String, String>();
		
		super.startDocument();
	}
	
	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		mPrefixes.put( prefix, uri );
		super.startPrefixMapping(prefix, uri);
	}
	
	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		super.endPrefixMapping(prefix);
		mPrefixes.remove( prefix );
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		
		List<String> ignorables = getCurrentIgnorables();
		
		ElementInfos infos = new ElementInfos( uri, localName );

		boolean mceElement = checkMceElement( uri, localName, qName, atts );
		
		// Is there an Ignorable attribute?
		String names = atts.getValue( NS_MCE, ATTR_IGNORABLE );
		if ( names != null ) {
			String[] namesArr = normalizeWhitespaces( names ).split( " " );
			infos.ignorableValue.addAll( Arrays.asList( namesArr ) );
		}
		
		// Is there a ProcessContent attribute?
		String processContent = atts.getValue( NS_MCE, ATTR_PROCESS_CONTENT );
		if ( processContent != null ) {
			String[] items = normalizeWhitespaces( processContent ).split( " " );
			for ( String item : items ) {
				infos.processContentValue.add( parsePrefixedName( item ) );
			}
		}
		
		// Is the current element to be ignored?
		boolean ignoreThis = isIgnorable( qName, ignorables );
		infos.ignoreContent = ignoreThis;
		infos.processContent = isProcessContent( uri, localName );
		
		// Is the element in content to be ignored or not?
		if ( !ignoreThis && !isInIgnoredContent() && !mceElement ) {
			
			// Cleanup all attributes
			AttributesImpl newAtts = new AttributesImpl( );
			for ( int i = 0; i < atts.getLength(); i++ ) {
				String attrUri = atts.getURI( i );
				String attrName = atts.getLocalName( i );
				String attrQName = atts.getQName( i );
				
				boolean removeAttr = false;
				
				// Is it an ignorable attribute?
				removeAttr = isIgnorable( attrQName, ignorables );
	
	            if ( !removeAttr ) {
	            	// Remove the MCE attributes if any
	            	if ( NS_MCE.equals( attrUri ) ) {
	            		removeAttr = true;
	            	}
	            }
				
				if ( !removeAttr ) {
					newAtts.addAttribute( attrUri, attrName,
							atts.getQName( i ),
							atts.getType( i ),
							atts.getValue( i ) );
				}
			}
			
			super.startElement(uri, localName, qName, newAtts);
		}
		
		mInfos.push( infos );
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (!isInIgnoredContent())
		{
			super.characters(ch, start, length);
		}
	};

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if ( !mInfos.isEmpty() && mInfos.peek().isElement( uri, localName ) ) {
			mInfos.pop();
		}
		
		List<String> ignorables = getCurrentIgnorables();
		
		boolean mceElement = checkMceElement( uri, localName, qName, null );
		
		// Is the element to be ignored?
		if ( !isIgnorable( qName, ignorables ) && !isInIgnoredContent() && !mceElement ) {
			super.endElement(uri, localName, qName);
		}
	}

	/**
	 * Check the validity of an MCE element.
	 * 
	 * @param uri the URI of the element to check
	 * @param localName the localname of the element to check
	 * @param qName the Qualified name of the element to check
	 * @param atts the attributes of the element to check of <code>null</code> if 
	 * 				checking an end element.
	 * 
	 * @return true if the element is in the MCE namespace
	 * 
	 * @throws SAXException if there is anything invalid with that element
	 */
	private boolean checkMceElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		boolean mceElement = NS_MCE.equals( uri );
				
		if ( mceElement ) {
			// Check for invalid elements in MCE namespace
			if ( !TAG_ALTERNATE_CONTENT.equals( localName ) &&
				 !TAG_CHOICE.equals( localName ) &&
				 !TAG_FALLBACK.equals( localName ) ) {
				SAXParseException e = new SAXParseException( "Invalid MCE element: " + localName, mLocator );
				getErrorHandler().error( e );
			}
		
			String mcePrefix = getPrefix( uri );
			
			// Check the attributes in case of an element start
			if ( atts != null ) {
				
				// Check the parenting, but only with the start of an element
				if ( !TAG_ALTERNATE_CONTENT.equals( localName ) ) {
					if ( mInfos.isEmpty() ) {
						String msg = MessageFormat.format( "Invalid root element: {0}:{1}", mcePrefix, localName );
						SAXParseException e = new SAXParseException( msg, mLocator );
						getErrorHandler().error( e );
						
					} else {
						ElementInfos parent = mInfos.peek();
						
						if ( !parent.uri.equals( NS_MCE ) || !parent.localName.equals( TAG_ALTERNATE_CONTENT ) ) {
							String msg = MessageFormat.format(
									"Parent of {0}:{1} element should be {0}:AlternateContent",
									mcePrefix, localName );
							SAXParseException e = new SAXParseException( msg, mLocator );
							getErrorHandler().error( e );
						}
					}
				}
				
				String requiresValue = null;
				boolean isChoice = mceElement && TAG_CHOICE.equals( localName );
				
				for ( int i = 0; i < atts.getLength(); i++ ) {
					String attUri = atts.getURI( i );
					String attLocalname = atts.getLocalName( i );
	
					if ( attUri.isEmpty() && !( isChoice && attLocalname.equals( "Requires" ) ) ) {
						
						String msg = MessageFormat.format(
								"{0}:{1} element shouldn't have any attribute with no namespace",
								mcePrefix, localName);
						if ( isChoice ) {
							msg = MessageFormat.format( "Invalid attribute {0} with no namespace in {1}:Choice element",
									attLocalname, mcePrefix );
						}
						
						SAXParseException e = new SAXParseException( msg, mLocator );
						getErrorHandler().error( e );
					} else if ( NS_XML.equals( attUri ) &&
							( attLocalname.equals( "lang" ) || attLocalname.equals( "space" ) ) ) {
						SAXParseException e = new SAXParseException(
								MessageFormat.format( "xml:{2} is not allowed in {0}:{1} element",
										mcePrefix, localName, attLocalname ),
								mLocator );
						getErrorHandler().error( e );
					}
	
					// Choice needs a Requires attribute to be checked on top of the others
					if ( isChoice ) {
						if ( attUri.isEmpty() && "Requires".equals( attLocalname ) ) {
							requiresValue = atts.getValue( i );
						} else if ( NS_MCE.equals( attUri ) && "Requires".equals( attLocalname ) ) {
							SAXParseException e = new SAXParseException(
									MessageFormat.format( "{0}:Choice element shouldn''t have a Requires element with the {0} prefix",
											mcePrefix ),
									mLocator );
							getErrorHandler().error( e );
						}
					}
				}
	
				// Check the attributes of Choice elements
				if ( isChoice ) {
					if ( requiresValue == null || requiresValue.isEmpty() ) {
						SAXParseException e = new SAXParseException(
								MessageFormat.format( "{0}:Choice element needs a Requires attribute with no namespace",
										mcePrefix ),
								mLocator );
						getErrorHandler().error( e );
					}
				}
			}
		}
		
		return mceElement;
	}
	
	private List<String> getCurrentIgnorables( ) {
		ArrayList<String> ignorables = new ArrayList<String>();
		
		for ( ElementInfos names : mInfos ) {
			for ( String name : names.ignorableValue ) {
				if ( !ignorables.contains( name ) ) {
					ignorables.add( name );
				}
			}
		}
		
		return ignorables;
	}
	
	/**
	 * Normalizes the values containing spaces as mentioned in ISO/IEC 29500 Part 3, Chap 10.
	 * 
	 * @param pValue the value for which to normalize the spaces
	 * 
	 * @return normalized string
	 */
	protected static String normalizeWhitespaces( String pValue ) {
		return pValue.replaceAll( "\\p{Space}+", " ").trim();
	}
	
	private boolean isIgnorable(String pAttrQName, List<String> pIgnorables) {
		boolean result = false;
		for (String ignorable : pIgnorables) {
			if ( pAttrQName.startsWith( ignorable + ":" ) ) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	private boolean isProcessContent(String uri, String localName) {
		
		boolean processContent = false;
		Iterator< ElementInfos > infosIt = mInfos.iterator();
		
		while ( !processContent && infosIt.hasNext() ) {
			ArrayList<QName> values = infosIt.next().processContentValue;
			Iterator<QName> valuesIt = values.iterator();
			
			while ( !processContent && valuesIt.hasNext() ) {
				QName toMatch = valuesIt.next();
				boolean matchesUri = toMatch.getNamespaceURI().equals( uri );
				boolean matchesLocal = toMatch.getLocalPart().equals( localName ) ||
						toMatch.getLocalPart().equals( "*" );
				
				processContent = matchesUri && matchesLocal;
			}
		}
		
		return processContent;
	}


	private boolean isInIgnoredContent() {
		
		boolean ignoreContent = false;
		Iterator< ElementInfos > it = mInfos.iterator();
		
		while ( it.hasNext() ) {
			ElementInfos infos = it.next();
			if ( infos.ignoreContent )
				ignoreContent = true;
			if ( ignoreContent && infos.processContent )
				ignoreContent = false;
			
			// Ignore everything in Choice tags as it's most likely to be defined by other schemas 
			if ( infos.isElement( NS_MCE, TAG_CHOICE ) )
				ignoreContent = true;
		}
		
		return ignoreContent;
	}
	
	/**
	 * Parse an XML name and create a QName of it y resolving the prefix if needed.
	 *  
	 * @param pPrefixed a string like "xmlns:my" or "my"
	 * 
	 * @return the corresponding QName or <code>null</code> if the string doesn't
	 * 		match the pattern.
	 */
	private QName parsePrefixedName( String pPrefixed ) {
		QName qname = null; 
		String[] split = pPrefixed.split( ":" );
		
		if ( split.length <= 2 ) {
			String uri = new String();
			String prefix = new String();
			String localname = split[0];

			if ( split.length == 2 ) {
				prefix = split[0];
				uri = mPrefixes.get( prefix );
				localname = split[1];
			}
			qname = new QName( uri, localname, prefix );
		}
		return qname;
	}
	
	private String getPrefix( String pUri ) {
		String prefix = new String();
		
		Iterator<Entry<String, String>> it = mPrefixes.entrySet().iterator();
		while ( prefix.isEmpty() && it.hasNext() ) {
			Entry<String, String> entry = it.next();
			if ( entry.getValue().equals( pUri ) ) {
				prefix = entry.getKey();
			}
		}
		return prefix;
	}
}
