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
import java.text.MessageFormat;

public class StdioValidationReport implements ValidationReport {

    private int indent;
    private int errCount;
    private boolean showInfos;
    
    public StdioValidationReport( boolean showInfos ) {
    	this.showInfos = showInfos;
    }
    
    public void addComment(String s) {
    	if ( showInfos ) {
    		addComment( "INFO", s );
    	}
    }

    public void addComment(String klass, String s) {

    	
        String indent = new String();
        for( int i = 0; i < this.indent * 5; i++ )
        {
            indent += " "; 
        }
        
        String msg = MessageFormat.format( "{0}{1} - {2}", klass, indent, s );
        System.out.println( msg );
    }

    public void decIndent() {
    	this.indent--;
    }

    public void endReport() {
    }

    public int getErrCount() {
    	return errCount;
    }

    public void incErrs() {
    	this.errCount++;
    }

    public void incIndent() {
    	this.indent++;
    }

    public void streamOut() throws IOException {
    }
}
