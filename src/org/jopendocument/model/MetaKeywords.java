/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2008-2013 jOpenDocument, by ILM Informatique. All rights reserved.
 * 
 * The contents of this file are subject to the terms of the GNU
 * General Public License Version 3 only ("GPL").  
 * You may not use this file except in compliance with the License. 
 * You can obtain a copy of the License at http://www.gnu.org/licenses/gpl-3.0.html
 * See the License for the specific language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each file.
 * 
 */

package org.jopendocument.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class MetaKeywords {

    protected List<MetaKeyword> metaKeyword;

    /**
     * Gets the value of the metaKeyword property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the metaKeyword property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getMetaKeyword().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link MetaKeyword }
     * 
     * 
     */
    public List<MetaKeyword> getMetaKeyword() {
        if (this.metaKeyword == null) {
            this.metaKeyword = new ArrayList<MetaKeyword>();
        }
        return this.metaKeyword;
    }

}
