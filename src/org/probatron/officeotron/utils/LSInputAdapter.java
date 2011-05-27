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
package org.probatron.officeotron.utils;

import java.io.InputStream;
import java.io.Reader;

import org.w3c.dom.ls.LSInput;

/**
 * Dummy class implementing the {@link LSInput} interface.
 * 
 * @author Cédric Bosdonnat <cbosdonnat@suse.com>
 *
 */
public class LSInputAdapter implements LSInput {


	private String mBaseURI;
	private InputStream mInputStream;
	private boolean mCertified;
	private Reader mReader;
	private String mEncoding;
	private String mPublicId;
	private String mStringData;
	private String mSystemId;
	
	public String getBaseURI() {
		return mBaseURI;
	}

	public InputStream getByteStream() {
		return mInputStream;
	}

	public boolean getCertifiedText() {
		return mCertified;
	}

	public Reader getCharacterStream() {
		return mReader;
	}

	public String getEncoding() {
		return mEncoding;
	}

	public String getPublicId() {
		return mPublicId;
	}

	public String getStringData() {
		return mStringData;
	}

	public String getSystemId() {
		return mSystemId;
	}

	public void setBaseURI(String baseURI) {
		mBaseURI = baseURI;
	}

	public void setByteStream(InputStream byteStream) {
		mInputStream = byteStream;
	}

	public void setCertifiedText(boolean certifiedText) {
		mCertified = certifiedText;
	}

	public void setCharacterStream(Reader characterStream) {
		mReader = characterStream;
	}

	public void setEncoding(String encoding) {
		mEncoding = encoding;
	}

	public void setPublicId(String publicId) {
		mPublicId = publicId;
	}

	public void setStringData(String stringData) {
		mStringData = stringData;
	}

	public void setSystemId(String systemId) {
		mSystemId = systemId;
	}
}
