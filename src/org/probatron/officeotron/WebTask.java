/*
 * This file is part of the source of
 * 
 * Office-o-tron - a web-based office document validator for Java(tm)
 * 
 * Copyright (c) 2010 Griffin Brown Digital Publishing Ltd.
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

import javax.servlet.http.HttpServletResponse;

import org.probatron.officeotron.sessionstorage.ValidationSession;

public class WebTask implements Runnable
{
    WebSubmission sub;
    private HttpServletResponse resp;


    WebTask( WebSubmission sub, HttpServletResponse resp )
    {
        this.resp = resp;
        this.sub = sub;

    }

    static private class HttpReportFactory implements ReportFactory
    {
        private HttpServletResponse resp;


        public HttpReportFactory( HttpServletResponse resp )
        {
            this.resp = resp;
        }


        public ValidationReport create()
        {
            return new HtmlValidationReport( resp );
        }
    }


    public void run()
    {
        ValidationSession vs = Utils.autoCreateValidationSession( sub, new HttpReportFactory(
                resp ) ); // determine
        // ODF
        // or
        // OOXML

        if( vs == null )
        {
            try
            {
                resp.sendError( 412,
                        "Submitted resource must be a recognisable Office document package" );
            }
            catch( IOException e )
            {

                e.printStackTrace();
            }

            vs.cleanup();
            return;
        }

        vs.prepare();
        vs.validate();
        vs.getCommentary().endReport();
        vs.cleanup();

        try
        {
            vs.getCommentary().streamOut();
        }
        catch( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
