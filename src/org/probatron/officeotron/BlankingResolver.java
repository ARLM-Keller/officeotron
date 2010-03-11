package org.probatron.officeotron;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class BlankingResolver implements EntityResolver
{

    static Logger logger = Logger.getLogger( BlankingResolver.class );
    XMLSniffer sniffer;


    BlankingResolver( XMLSniffer sniffer )
    {
        this.sniffer = sniffer;
    }


    public InputSource resolveEntity( String publicId, String systemId ) throws SAXException,
            IOException
    {
        logger.info( "Request made to resolve entity with SYSTEM ID: " + systemId );

        if( sniffer != null )
        {
            sniffer.referencesEntities = true;
        }
        
        return new InputSource( new ByteArrayInputStream( "".getBytes() ) );
    }
}
