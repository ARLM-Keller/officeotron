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
