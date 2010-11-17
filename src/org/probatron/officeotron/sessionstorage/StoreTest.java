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
package org.probatron.officeotron.sessionstorage;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

import org.junit.After;
import org.junit.Test;
import org.probatron.officeotron.Utils;

public class StoreTest {

	private static final String TMP_DIR = System.getProperty( "java.io.tmpdir" ) + File.separator + "officeotron";
	private static final File TEST_FILE = new File( "etc/test-data/maria.xlsx" );
	private static final String CONTAINED_FILE = "[Content_Types].xml";
	
	@After
	public void tearDown() {
		Utils.deleteDir( new File( TMP_DIR ) );
	}

	@Test
	public void testPutZippedResourceWeb() {
		Store.init( TMP_DIR, true );
		
		try {
			UUID uuid = Store.putZippedResource( new FileInputStream( TEST_FILE ), TEST_FILE.getAbsolutePath() );
			
			File dir = new File( Store.getDirectory( uuid ) );
			assertTrue( "No unzipped folder", dir.isDirectory() );
			
			File file = new File( dir, uuid.toString() + ".bin" );
			assertTrue( "No cached file in " + file.getAbsolutePath(), file.isFile() );

			
			File containedFile = new File( dir, CONTAINED_FILE );
			assertTrue( "File hasn't been uncompressed", containedFile.isFile() );
			
		} catch (Exception e) {
			fail("shouldn't throw any exception");
		}
	}
	
	@Test
	public void testPutZippedResourceNoweb() {
		Store.init( TMP_DIR, false );
		
		try {
			UUID uuid = Store.putZippedResource( new FileInputStream( TEST_FILE ), TEST_FILE.getAbsolutePath() );
			
			File dir = new File( Store.getDirectory( uuid ) );
			assertTrue( "No unzipped folder", dir.isDirectory() );
			
			File file = new File( dir, uuid.toString() );
			assertFalse( "Unexpectedly cached file in " + file.getAbsolutePath(), file.isFile() );
			
			File containedFile = new File( dir, CONTAINED_FILE );
			assertTrue( "File hasn't been uncompressed", containedFile.isFile() );
			
		} catch (Exception e) {
			fail("shouldn't throw any exception");
		}
	}
	
	@Test
	public void testPutZippedResourceRelativePath() {
		Store.init( TMP_DIR, false );
		
		try {
			UUID uuid = Store.putZippedResource( new FileInputStream( TEST_FILE ), TEST_FILE.getPath() );
			
			File dir = new File( Store.getDirectory( uuid ) );
			assertTrue( "No unzipped folder", dir.isDirectory() );
			
			File file = new File( dir, uuid.toString() );
			assertFalse( "Unexpectedly cached file in " + file.getAbsolutePath(), file.isFile() );
			
			File containedFile = new File( dir, CONTAINED_FILE );
			assertTrue( "File hasn't been uncompressed", containedFile.isFile() );
			
		} catch (Exception e) {
			fail("shouldn't throw any exception");
		}
	}
}
