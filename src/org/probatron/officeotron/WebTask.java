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
