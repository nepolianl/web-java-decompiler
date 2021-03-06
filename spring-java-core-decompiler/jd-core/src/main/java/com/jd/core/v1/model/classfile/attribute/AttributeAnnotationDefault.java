/*
 * Copyright (c) 2008-2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.model.classfile.attribute;

public class AttributeAnnotationDefault implements Attribute {
    protected ElementValue defaultValue;

    public AttributeAnnotationDefault(ElementValue defaultValue) {
        this.defaultValue = defaultValue;
    }

    public ElementValue getDefaultValue() {
        return defaultValue;
    }
}
