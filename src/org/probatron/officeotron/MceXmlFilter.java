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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
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
		String qName;
		
		ArrayList<String> elements;
		boolean ignoreContent = false;
		
		public ElementInfos( String pUri, String pLocalName, String pQName ) {
			uri = pUri;
			localName = pLocalName;
			qName = pQName;
			
			elements = new ArrayList<String>();
		}
		
		public boolean isElement( String pUri, String pLocalName, String pQName ) {
			boolean uriOk = ( uri == null && pUri == null ) || uri.equals( pUri );
			boolean localOk = ( localName == null && pLocalName == null ) || localName.equals( pLocalName );
			boolean qnameOk = ( qName == null && pQName == null ) || qName.equals( pQName );
			
			return uriOk && localOk && qnameOk;
		}
	}

	private static final String MCE_NAMESPACE = "http://schemas.openxmlformats.org/markup-compatibility/2006";
	private static final String ATTR_IGNORABLE = "Ignorable";
	
	private Stack<ElementInfos> mInfos;
	
	
	public MceXmlFilter(XMLReader parent) {
		super( parent );
	}


	@Override
	public void startDocument() throws SAXException {
		
		// Reset the ignorables for the document
		mInfos = new Stack<ElementInfos>();
		super.startDocument();
	}
	

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		
		List<String> ignorables = getCurrentIgnorables();
		
		ElementInfos infos = new ElementInfos( uri, localName, qName );
		
		// Is there an Ignorable attribute? If yes, store its values
		String names = atts.getValue( MCE_NAMESPACE, ATTR_IGNORABLE );
		if ( names != null ) {
			String[] namesArr = normalizeWhitespaces( names ).split( " " );
			infos.elements.addAll( Arrays.asList( namesArr ) );
		}
		mInfos.push( infos );
		
		// Is the current element to be ignored?
		boolean ignoreThis = isIgnorable( qName, ignorables );
		infos.ignoreContent = ignoreThis; // TODO This should be altered by the PreserveContent
		
		// Is the element in content to be ignored or not?
		if ( !ignoreThis && !isIgnoredContent( ) ) {
			
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
	            	if ( MCE_NAMESPACE.equals( attrUri ) && ATTR_IGNORABLE.equals( attrName ) ) {
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
	}

	private boolean isIgnoredContent() {
		
		boolean ignoreContent = false;
		Iterator< ElementInfos > it = mInfos.iterator();
		
		while ( !ignoreContent && it.hasNext() ) {
			ignoreContent = it.next().ignoreContent;
		}
		
		return ignoreContent;
	}


	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if ( !mInfos.isEmpty() && mInfos.peek().isElement( uri, localName, qName ) ) {
			mInfos.pop();
		}
		
		List<String> ignorables = getCurrentIgnorables();
		
		// Is the element to be ignored?
		if ( !isIgnorable( qName, ignorables ) && !isIgnoredContent() ) {
			super.endElement(uri, localName, qName);
		}
	}
	
	private List<String> getCurrentIgnorables( ) {
		ArrayList<String> ignorables = new ArrayList<String>();
		
		for ( ElementInfos names : mInfos ) {
			for ( String name : names.elements ) {
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
}
