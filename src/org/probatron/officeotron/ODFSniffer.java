package org.probatron.officeotron;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ODFSniffer extends XMLSniffer
{
    private String generator = "";
    private boolean harvestGenerator;
    private ValidationReport commentary;
    private boolean checkIds;
    private int dupCount;

    final static String ODF_ANIM_NS = "urn:oasis:names:tc:opendocument:xmlns:animation:1.0";
    final static String ODF_CHART_NS = "urn:oasis:names:tc:opendocument:xmlns:chart:1.0";
    final static String ODF_CONFIG_NS = "urn:oasis:names:tc:opendocument:xmlns:config:1.0";
    final static String ODF_DC_NS = "http://purl.org/dc/elements/1.1/";
    final static String ODF_DR3D_NS = "urn:oasis:names:tc:opendocument:xmlns:dr3d:1.0";
    final static String ODF_DRAW_NS = "urn:oasis:names:tc:opendocument:xmlns:drawing:1.0";
    final static String ODF_FO_NS = "urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0";
    final static String ODF_FORM_NS = "urn:oasis:names:tc:opendocument:xmlns:form:1.0";
    final static String ODF_MATH_NS = "http://www.w3.org/1998/Math/MathML";
    final static String ODF_META_NS = "urn:oasis:names:tc:opendocument:xmlns:meta:1.0";
    final static String ODF_NUMBER_NS = "urn:oasis:names:tc:opendocument:xmlns:datastyle:1.0";
    final static String ODF_OFFICE_NS = "urn:oasis:names:tc:opendocument:xmlns:office:1.0";
    final static String ODF_PRESENTATION_NS = "urn:oasis:names:tc:opendocument:xmlns:presentation:1.0";
    final static String ODF_SCRIPT_NS = "urn:oasis:names:tc:opendocument:xmlns:script:1.0";
    final static String ODF_SMIL_NS = "urn:oasis:names:tc:opendocument:xmlns:smil-compatible:1.0";
    final static String ODF_STYLE_NS = "urn:oasis:names:tc:opendocument:xmlns:style:1.0";
    final static String ODF_SVG_NS = "urn:oasis:names:tc:opendocument:xmlns:svg-compatible:1.0";
    final static String ODF_TABLE_NS = "urn:oasis:names:tc:opendocument:xmlns:table:1.0";
    final static String ODF_TEXT_NS = "urn:oasis:names:tc:opendocument:xmlns:text:1.0";
    final static String ODF_XFORMS_NS = "http://www.w3.org/2002/xforms";
    final static String ODF_XLINK_NS = "http://www.w3.org/1999/xlink";

    private static XMLNameSet idTypes = new XMLNameSet();
    private static XMLNameSet idRefTypes = new XMLNameSet();

    private ArrayList<String> idValues = new ArrayList<String>();
    private ArrayList<String> idRefValues = new ArrayList<String>();

    static
    {
        // The 4 ID type attributes
        idTypes.put( ODF_TEXT_NS, "id" );
        idTypes.put( ODF_DRAW_NS, "id" );
        idTypes.put( ODF_FORM_NS, "id" );
        idTypes.put( ODF_TEXT_NS, "id" );

        // The 8 IDREF(S) type attributes
        idRefTypes.put( ODF_TEXT_NS, "change-id" );
        idRefTypes.put( ODF_DRAW_NS, "nav-order" );
        idRefTypes.put( ODF_DRAW_NS, "start-shape" );
        idRefTypes.put( ODF_DRAW_NS, "end-shape" );
        idRefTypes.put( ODF_DRAW_NS, "control" );
        idRefTypes.put( ODF_DRAW_NS, "caption-id" );
        idRefTypes.put( ODF_DRAW_NS, "shape-id" );
        idRefTypes.put( ODF_PRESENTATION_NS, "master-element" );
        idRefTypes.put( ODF_SMIL_NS, "target-element" );
    }


    public ODFSniffer( ValidationReport commentary, boolean checkIds )
    {
        this.commentary = commentary;
        this.checkIds = checkIds;
    }


    @Override
    public void startElement( String uri, String localName, String name, Attributes atts )
            throws SAXException
    {
        super.startElement( uri, localName, name, atts );

        if( this.checkIds )
        {
            harvestIdStuff( atts );
        }

        this.harvestGenerator = ( uri.equals( ODF_META_NS ) && localName.equals( "generator" ) );
    }


    private void harvestIdStuff( Attributes atts )
    {
        for( int i = 0; i < atts.getLength(); i++ )
        {
            String atturi = atts.getURI( i );
            String local = atts.getLocalName( i );

            if( idTypes.contains( atturi, local ) )
            {
                // CASE: we've got an ID value!
                String val = atts.getValue( i );
                logger.trace( "Got ID value" + val );
                int n = Collections.binarySearch( idValues, val );
                if( n >= 0 )
                {
                    this.dupCount++;
                    if( this.dupCount <= ODFErrorHandler.THRESHOLD )
                    {
                        this.commentary.addComment( "ERROR", "Duplicate ID value found: \""
                                + val + "\"" );
                    }
                }
                else
                {
                    logger.trace( "Adding ID value" + val );
                    idValues.add( - n - 1, val );
                }
            }
            else if( idRefTypes.contains( atturi, local ) )
            {
                // CASE: we've got an IDREF (or IDREFS) value!
                String val = atts.getValue( i );
                StringTokenizer st = new StringTokenizer( val );
                while( st.hasMoreTokens() )
                {
                    String tok = st.nextToken();
                    logger.trace( "Got IDREF value" + tok );
                    int n = Collections.binarySearch( idValues, tok );
                    if( n < 0 )
                    {
                        logger.trace( "Adding IDREF value" + tok );
                        this.idRefValues.add( - n - 1, tok );
                    }
                }
            }
        }
    }


    @Override
    public void characters( char[] ch, int start, int length ) throws SAXException
    {
        super.characters( ch, start, length );

        if( this.harvestGenerator )
        {
            this.generator += new String( ch, start, length );
        }
    }


    public String getGenerator()
    {
        return generator;
    }


    @Override
    public void endDocument() throws SAXException
    {
        if( this.checkIds )
        {
            if( this.dupCount > ODFErrorHandler.THRESHOLD )
            {
                this.commentary.addComment( "WARN", "<i>" + ( this.dupCount - ODFErrorHandler.THRESHOLD )
                        + " duplicate ID message(s) omitted for the sake of brevity</i>" );
            }
            
            int errCount = 0;

            Iterator<String> iter = this.idRefValues.iterator();
            while( iter.hasNext() )
            {
                String ref = iter.next();
                int n = Collections.binarySearch( this.idValues, ref );
                logger.trace( "Checking ID references for value: " + ref );

                if( n < 0 )
                {
                    errCount++;
                    if( errCount <= ODFErrorHandler.THRESHOLD )
                    {
                        this.commentary.addComment( "WARNING", "Reference to absent ID \""
                                + ref + "\"" );
                    }
                }
            }

            if( errCount > ODFErrorHandler.THRESHOLD )
            {
                this.commentary.addComment( "WARN", "<i>" + ( errCount - ODFErrorHandler.THRESHOLD )
                        + " absent ID message(s) omitted for the sake of brevity</i>" );
            }
        }

        super.endDocument();
    }
}