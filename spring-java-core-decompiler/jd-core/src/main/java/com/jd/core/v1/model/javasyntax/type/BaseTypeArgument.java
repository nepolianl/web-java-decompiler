/*
 * Copyright (c) 2008, 2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.model.javasyntax.type;

import com.jd.core.v1.util.DefaultList;

import java.util.Map;

@SuppressWarnings("unchecked")
public interface BaseTypeArgument extends TypeArgumentVisitable {
    default boolean isTypeArgumentAssignableFrom(Map<String, BaseType> typeBounds, BaseTypeArgument typeArgument) {
        return false;
    }

    default boolean isTypeArgumentList() {
        return false;
    }

    default TypeArgument getTypeArgumentFirst() {
        return (TypeArgument)this;
    }

    default DefaultList<TypeArgument> getTypeArgumentList() {
        return (DefaultList<TypeArgument>)this;
    }

    default int typeArgumentSize() {
        return 1;
    }
}
