/*
 * Copyright (c) 2008, 2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.service.converter.classfiletojavasyntax.model.javasyntax.expression;

import com.jd.core.v1.model.javasyntax.expression.BaseExpression;
import com.jd.core.v1.model.javasyntax.expression.Expression;
import com.jd.core.v1.model.javasyntax.expression.MethodInvocationExpression;
import com.jd.core.v1.model.javasyntax.type.BaseType;
import com.jd.core.v1.model.javasyntax.type.BaseTypeParameter;
import com.jd.core.v1.model.javasyntax.type.Type;
import com.jd.core.v1.service.converter.classfiletojavasyntax.util.TypeParametersToTypeArgumentsBinder;

public class ClassFileMethodInvocationExpression extends MethodInvocationExpression {
    protected TypeParametersToTypeArgumentsBinder binder;
    protected BaseTypeParameter typeParameters;
    protected BaseType parameterTypes;

    public ClassFileMethodInvocationExpression(
            TypeParametersToTypeArgumentsBinder binder,
            int lineNumber, BaseTypeParameter typeParameters, Type type, Expression expression,
            String internalTypeName, String name, String descriptor, BaseType parameterTypes, BaseExpression parameters) {
        super(lineNumber, type, expression, internalTypeName, name, descriptor, parameters);
        this.binder = binder;
        this.typeParameters = typeParameters;
        this.parameterTypes = parameterTypes;
    }

    public TypeParametersToTypeArgumentsBinder getBinder() {
        return binder;
    }

    public BaseTypeParameter getTypeParameters() {
        return typeParameters;
    }

    public BaseType getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(BaseType parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}
