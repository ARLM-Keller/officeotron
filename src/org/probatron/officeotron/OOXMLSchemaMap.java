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
                "opc-coreProperties.xsd" ) );
        
        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "15.2.12.2",
                "application/vnd.openxmlformats-officedocument.custom-properties+xml",
                "http://schemas.openxmlformats.org/officeDocument/2006/custom-properties",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/custom-properties",
                "shared-documentPropertiesCustom.xsd" ) );
        
        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "15.2.12.3",
                "application/vnd.openxmlformats-officedocument.extended-properties+xml",
                "http://schemas.openxmlformats.org/officeDocument/2006/extended-properties",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties",
                "shared-documentPropertiesExtended.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "11.3.3",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.settings+xml",
                "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/settings",
                "wml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "11.3.4",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.endnotes+xml",
                "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/endnotes",
                "wml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "11.3.5",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.fontTable+xml",
                        "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/fontTable",
                        "wml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "11.3.6",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.footer+xml",
                "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/footer",
                "wml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "11.3.7",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.footnotes+xml",
                        "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/footnotes",
                        "wml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "11.3.8",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document.glossary+xml",
                        "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/glossaryDocument",
                        "wml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "11.3.9",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.header+xml",
                "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/header",
                "wml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "11.3.10",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml",
                        "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument",
                        "wml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "11.3.10",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.template.main+xml",
                        "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument",
                        "wml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "11.3.11",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.numbering+xml",
                        "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/numbering",
                        "wml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "11.3.12",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml",
                "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles",
                "wml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "11.3.13",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.webSettings+xml",
                        "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/webSettings",
                        "wml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.1",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.calcChain+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/calcChain",
                        "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.2",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.chartsheet+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/chartsheet",
                        "sml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "12.3.3",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.comments+xml",
                "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/comments",
                "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.4",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.connections+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/connections",
                        "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.7",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.dialogsheet+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/dialogsheet",
                        "sml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "12.3.8",
                "application/vnd.openxmlformats-officedocument.drawing+xml",
                "http://schemas.openxmlformats.org/drawingml/2006/spreadsheetDrawing",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/drawing",
                "dml-spreadsheetDrawing.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.9",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.externalLink+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/externalLink",
                        "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.10",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheetMetadata+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/sheetMetadata",
                        "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.11",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.pivotTable+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/pivotTable",
                        "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.12",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.pivotCacheDefinition+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/pivotCacheDefinition",
                        "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.13",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.pivotCacheRecords+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/pivotCacheRecords",
                        "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.14",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.queryTable+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/queryTable",
                        "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.15",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sharedStrings+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/sharedStrings",
                        "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.16",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.revisionHeaders+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/revisionHeaders",
                        "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.17",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.revisionLog+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/revisionLog",
                        "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.18",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.userNames+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/usernames",
                        "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.19",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.tableSingleCells+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/tableSingleCells",
                        "sml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "12.3.20",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml",
                "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles",
                "sml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "12.3.21",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.table+xml",
                "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/table",
                "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.22",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.volatileDependencies+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/volatileDependencies",
                        "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.23",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument",
                        "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.23",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.template.main+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument",
                        "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "12.3.24",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml",
                        "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet",
                        "sml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.1",
                        "application/vnd.openxmlformats-officedocument.presentationml.commentAuthors+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/commentAuthors",
                        "pml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "13.3.2",
                "application/vnd.openxmlformats-officedocument.presentationml.comments+xml",
                "http://schemas.openxmlformats.org/presentationml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/comments",
                "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.3",
                        "application/vnd.openxmlformats-officedocument.presentationml.handoutMaster+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/handoutMaster",
                        "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.4",
                        "application/vnd.openxmlformats-officedocument.presentationml.notesMaster+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/notesMaster",
                        "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.5",
                        "application/vnd.openxmlformats-officedocument.presentationml.notesSlide+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/notesSlide",
                        "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.6",
                        "application/vnd.openxmlformats-officedocument.presentationml.presentation.main+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument",
                        "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.6",
                        "application/vnd.openxmlformats-officedocument.presentationml.sildeshow.main+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument",
                        "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.6",
                        "application/vnd.openxmlformats-officedocument.presentationml.template.main+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument",
                        "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.7",
                        "application/vnd.openxmlformats-officedocument.presentationml.presProps+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/presProps",
                        "pml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "13.3.8",
                "application/vnd.openxmlformats-officedocument.presentationml.slide+xml",
                "http://schemas.openxmlformats.org/presentationml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/slide",
                "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.9",
                        "application/vnd.openxmlformats-officedocument.presentationml.slideLayout+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/slideLayout",
                        "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.10",
                        "application/vnd.openxmlformats-officedocument.presentationml.slideMaster+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/slideMaster",
                        "pml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "13.3.11",
                "application/vnd.openxmlformats-officedocument.presentationml.tags+xml",
                "http://schemas.openxmlformats.org/presentationml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/tags",
                "pml.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "13.3.11",
                        "application/vnd.openxmlformats-officedocument.presentationml.viewProps+xml",
                        "http://schemas.openxmlformats.org/presentationml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/viewProps",
                        "pml.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "14.2.1",
                "application/vnd.openxmlformats-officedocument.drawingml.chart+xml",
                "http://schemas.openxmlformats.org/drawingml/2006/chart",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/chart",
                "dml-chart.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "14.2.2",
                        "application/vnd.openxmlformats-officedocument.drawingml.chartshapes+xml",
                        "http://schemas.openxmlformats.org/drawingml/2006/chart",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/chartUserShapes",
                        "dml-main.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "14.2.3",
                        "application/vnd.openxmlformats-officedocument.drawingml.diagramColors+xml",
                        "http://schemas.openxmlformats.org/drawingml/2006/diagram",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/diagramColors",
                        "dml-main.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "14.2.4",
                        "application/vnd.openxmlformats-officedocument.drawingml.diagramData+xml",
                        "http://schemas.openxmlformats.org/drawingml/2006/diagram",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/diagramData",
                        "dml-main.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "14.2.5",
                        "application/vnd.openxmlformats-officedocument.drawingml.diagramLayout+xml",
                        "http://schemas.openxmlformats.org/drawingml/2006/diagram",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/diagramLayout",
                        "dml-diagram.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "14.2.6",
                        "application/vnd.openxmlformats-officedocument.drawingml.diagramStyle+xml",
                        "http://schemas.openxmlformats.org/drawingml/2006/diagram",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/diagramQuickStyle",
                        "dml-diagram.xsd" ) );

        OOXMLSchemaMap.addMapping( new OOXMLSchemaMapping( "14.2.7",
                "application/vnd.openxmlformats-officedocument.theme+xml",
                "http://schemas.openxmlformats.org/drawingml/2006/main",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/theme",
                "dml-main.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "14.2.8",
                        "application/vnd.openxmlformats-officedocument.themeOverride+xml",
                        "http://schemas.openxmlformats.org/drawingml/2006/chart",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/themeOverride",
                        "dml-main.xsd" ) );

        OOXMLSchemaMap
                .addMapping( new OOXMLSchemaMapping(
                        "14.2.9",
                        "application/vnd.openxmlformats-officedocument.presentationml.tableStyles+xml",
                        "http://schemas.openxmlformats.org/drawingml/2006/main",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/tableStyles",
                        "dml-main.xsd" ) );

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
                "",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/vmlDrawing",
                "vml-main.xsd" ) );
        
        // http://schemas.openxmlformats.org/officeDocument/2006/relationships/vmlDrawing
        // http://schemas.openxmlformats.org/officeDocument/2006/relationships/vmlDrawing

    }

}
