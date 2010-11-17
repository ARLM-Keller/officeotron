/*
 * This file is part of the source of
 * 
 * Office-o-tron - a web-based office document validator for Java(tm)
 * 
 * Copyright (c) 2009 Griffin Brown Digital Publishing Ltd.
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
 * 
 */

package org.probatron.officeotron;

import java.util.HashMap;
import java.util.Set;

public class OOXMLSchemaMap
{
    private static final String OPC_SCHEMA = "OPC/";
	private static final String OOXML_SCHEMA = "29500T/";
    
	private static HashMap< String, OOXMLSchemaMapping > map = new HashMap< String, OOXMLSchemaMapping >();


    private static void addMapping( OOXMLSchemaMapping sm )
    {
        map.put( sm.getContentType(), sm );
    }

    public static Set< String > getContentTypes( )
    {
    	return map.keySet();
    }

    public static OOXMLSchemaMapping getMappingForContentType( String s )
    {
        return map.get( s );
    }
    
    static
    {
        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "15.2.12.1",
                "application/vnd.openxmlformats-package.core-properties+xml",
                "http://schemas.openxmlformats.org/package/2006/metadata/core-properties",
                "http://schemas.openxmlformats.org/officedocument/2006/relationships/metadata/core-properties",
                OPC_SCHEMA + "opc-coreProperties.xsd" ) );
        
        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "15.2.12.2",
                "application/vnd.openxmlformats-officedocument.custom-properties+xml",
                "http://schemas.openxmlformats.org/officeDocument/2006/custom-properties",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/custom-properties",
                OOXML_SCHEMA + "shared-documentPropertiesCustom.xsd" ) );
        
        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "15.2.12.3",
                "application/vnd.openxmlformats-officedocument.extended-properties+xml",
                "http://schemas.openxmlformats.org/officeDocument/2006/extended-properties",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties",
                OOXML_SCHEMA + "shared-documentPropertiesExtended.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "11.3.3",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.settings+xml",
                "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/settings",
                OOXML_SCHEMA + "wml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "11.3.4",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.endnotes+xml",
                "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/endnotes",
                OOXML_SCHEMA + "wml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "11.3.5",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.fontTable+xml",
                        "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/fontTable",
                        OOXML_SCHEMA + "wml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "11.3.6",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.footer+xml",
                "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/footer",
                OOXML_SCHEMA + "wml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "11.3.7",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.footnotes+xml",
                        "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/footnotes",
                        OOXML_SCHEMA + "wml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "11.3.8",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document.glossary+xml",
                        "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/glossaryDocument",
                        OOXML_SCHEMA + "wml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "11.3.9",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.header+xml",
                "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/header",
                OOXML_SCHEMA + "wml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "11.3.10",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml",
                        "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument",
                        OOXML_SCHEMA + "wml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "11.3.10",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.template.main+xml",
                        "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument",
                        OOXML_SCHEMA + "wml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "11.3.11",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.numbering+xml",
                        "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/numbering",
                        OOXML_SCHEMA + "wml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "11.3.12",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml",
                "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles",
                OOXML_SCHEMA + "wml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "11.3.13",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.webSettings+xml",
                        "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/webSettings",
                        OOXML_SCHEMA + "wml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.1",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.calcChain+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/calcChain",
                        OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.2",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.chartsheet+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/chartsheet",
                        OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "12.3.3",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.comments+xml",
                "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/comments",
                OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.4",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.connections+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/connections",
                        OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.7",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.dialogsheet+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/dialogsheet",
                        OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "12.3.8",
                "application/vnd.openxmlformats-officedocument.drawing+xml",
                "http://schemas.openxmlformats.org/drawingml/2006/spreadsheetDrawing",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/drawing",
                OOXML_SCHEMA + "dml-spreadsheetDrawing.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.9",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.externalLink+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/externalLink",
                        OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.10",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheetMetadata+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/sheetMetadata",
                        OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.11",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.pivotTable+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/pivotTable",
                        OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.12",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.pivotCacheDefinition+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/pivotCacheDefinition",
                        OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.13",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.pivotCacheRecords+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/pivotCacheRecords",
                        OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.14",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.queryTable+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/queryTable",
                        OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.15",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sharedStrings+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/sharedStrings",
                        OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.16",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.revisionHeaders+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/revisionHeaders",
                        OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.17",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.revisionLog+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/revisionLog",
                        OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.18",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.userNames+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/usernames",
                        OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.19",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.tableSingleCells+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/tableSingleCells",
                        OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "12.3.20",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml",
                "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles",
                OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "12.3.21",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.table+xml",
                "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/table",
                OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.22",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.volatileDependencies+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/volatileDependencies",
                        OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.23",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument",
                        OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.23",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.template.main+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument",
                        OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.24",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet",
                        OOXML_SCHEMA + "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.1",
                        "application/vnd.openxmlformats-officedocument.presentationml.commentAuthors+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/commentAuthors",
                        OOXML_SCHEMA + "pml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "13.3.2",
                "application/vnd.openxmlformats-officedocument.presentationml.comments+xml",
                "http://schemas.openxmlformats.org/presentationml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/comments",
                OOXML_SCHEMA + "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.3",
                        "application/vnd.openxmlformats-officedocument.presentationml.handoutMaster+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/handoutMaster",
                        OOXML_SCHEMA + "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.4",
                        "application/vnd.openxmlformats-officedocument.presentationml.notesMaster+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/notesMaster",
                        OOXML_SCHEMA + "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.5",
                        "application/vnd.openxmlformats-officedocument.presentationml.notesSlide+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/notesSlide",
                        OOXML_SCHEMA + "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.6",
                        "application/vnd.openxmlformats-officedocument.presentationml.presentation.main+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument",
                        OOXML_SCHEMA + "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.6",
                        "application/vnd.openxmlformats-officedocument.presentationml.sildeshow.main+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument",
                        OOXML_SCHEMA + "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.6",
                        "application/vnd.openxmlformats-officedocument.presentationml.template.main+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument",
                        OOXML_SCHEMA + "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.7",
                        "application/vnd.openxmlformats-officedocument.presentationml.presProps+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/presProps",
                        OOXML_SCHEMA + "pml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "13.3.8",
                "application/vnd.openxmlformats-officedocument.presentationml.slide+xml",
                "http://schemas.openxmlformats.org/presentationml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/slide",
                OOXML_SCHEMA + "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.9",
                        "application/vnd.openxmlformats-officedocument.presentationml.slideLayout+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/slideLayout",
                        OOXML_SCHEMA + "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.10",
                        "application/vnd.openxmlformats-officedocument.presentationml.slideMaster+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/slideMaster",
                        OOXML_SCHEMA + "pml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "13.3.11",
                "application/vnd.openxmlformats-officedocument.presentationml.tags+xml",
                "http://schemas.openxmlformats.org/presentationml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/tags",
                OOXML_SCHEMA + "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.11",
                        "application/vnd.openxmlformats-officedocument.presentationml.viewProps+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/viewProps",
                        OOXML_SCHEMA + "pml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "14.2.1",
                "application/vnd.openxmlformats-officedocument.drawingml.chart+xml",
                "http://schemas.openxmlformats.org/drawingml/2006/chart",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/chart",
                OOXML_SCHEMA + "dml-chart.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "14.2.2",
                        "application/vnd.openxmlformats-officedocument.drawingml.chartshapes+xml",
                        "http://schemas.openxmlformats.org/drawingml/2006/chart",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/chartUserShapes",
                        OOXML_SCHEMA + "dml-chart.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "14.2.3",
                        "application/vnd.openxmlformats-officedocument.drawingml.diagramColors+xml",
                        "http://schemas.openxmlformats.org/drawingml/2006/diagram",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/diagramColors",
                        OOXML_SCHEMA + "dml-diagram.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "14.2.4",
                        "application/vnd.openxmlformats-officedocument.drawingml.diagramData+xml",
                        "http://schemas.openxmlformats.org/drawingml/2006/diagram",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/diagramData",
                        OOXML_SCHEMA + "dml-diagram.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "14.2.5",
                        "application/vnd.openxmlformats-officedocument.drawingml.diagramLayout+xml",
                        "http://schemas.openxmlformats.org/drawingml/2006/diagram",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/diagramLayout",
                        OOXML_SCHEMA + "dml-diagram.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "14.2.6",
                        "application/vnd.openxmlformats-officedocument.drawingml.diagramStyle+xml",
                        "http://schemas.openxmlformats.org/drawingml/2006/diagram",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/diagramQuickStyle",
                        OOXML_SCHEMA + "dml-diagram.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "14.2.7",
                "application/vnd.openxmlformats-officedocument.theme+xml",
                "http://schemas.openxmlformats.org/drawingml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/theme",
                OOXML_SCHEMA + "dml-main.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "14.2.8",
                        "application/vnd.openxmlformats-officedocument.themeOverride+xml",
                        "http://schemas.openxmlformats.org/drawingml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/themeOverride",
                        OOXML_SCHEMA + "dml-main.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "14.2.9",
                        "application/vnd.openxmlformats-officedocument.presentationml.tableStyles+xml",
                        "http://schemas.openxmlformats.org/drawingml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/tableStyles",
                        OOXML_SCHEMA + "dml-main.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "15.2.6",
                        "application/vnd.openxmlformats-officedocument.customXmlProperties+xml",
                        "http://schemas.openxmlformats.org/officeDocument/2006/customXmlDataProps",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/customXmlProps",
                        "" ) );
        
        OOXMLSchemaMap
        .addMapping( new OOXMLSchemaMapping(
                "8.1 (Part 4)",
                "application/vnd.openxmlformats-officedocument.vmlDrawing",
                "urn:schemas-microsoft-com:vml",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/vmlDrawing",
                OOXML_SCHEMA + "vml-main.xsd" ) );
    }

}
