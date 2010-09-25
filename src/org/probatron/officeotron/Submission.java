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
import java.util.UUID;

import org.apache.log4j.Logger;

/**
 * Represents the inputs to a validation session. This includes a resource to be validated
 * (which may need to be de-referenced) and options to affect the validation process.
 */
abstract public class Submission
{
    static Logger logger = Logger.getLogger( Submission.class );
    UUID uuid;

    protected OptionMap optionMap = new OptionMap();


    abstract public String getCandidateFile();
    
    public OptionMap getOptionMap()
    {
        return this.optionMap;
    }

}
