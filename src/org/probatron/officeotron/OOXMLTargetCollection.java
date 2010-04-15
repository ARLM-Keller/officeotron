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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class OOXMLTargetCollection extends ArrayList<OOXMLTarget>
{
    static Logger logger = Logger.getLogger( OOXMLTargetCollection.class );

    private HashMap<String, OOXMLTarget> partNameMap = new HashMap<String, OOXMLTarget>();
    


    @Override
    public boolean add( OOXMLTarget t )
    {
        logger.debug( "Adding entry to target collection " + t.getTargetAsPartName() );
        partNameMap.put( t.getTargetAsPartName(), t );
        return super.add( t );
       
    }


    public Collection<String> getPartNamesSet()
    {
        return this.partNameMap.keySet();
    }


    public OOXMLTarget getTargetByName( String s )
    {
        return this.partNameMap.get( s );
    }

}
