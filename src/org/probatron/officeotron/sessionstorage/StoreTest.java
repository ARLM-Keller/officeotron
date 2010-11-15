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
		Store.init( TMP_DIR, getUnzip(), true );
		
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
		Store.init( TMP_DIR, getUnzip(), false );
		
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
		Store.init( TMP_DIR, getUnzip(), false );
		
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

	
	public String getUnzip() {
		String unzip = "unzip";
		if ( File.separator.equals( "\\" ) ) {
			unzip = "cmd /c unzip";
		}
		return unzip;
	}
}
