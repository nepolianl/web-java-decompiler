/*
 * Copyright (c) 2008-2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.model.javasyntax.expression;

import com.jd.core.v1.model.javasyntax.type.Type;

public class LocalVariableReferenceExpression extends AbstractLineNumberTypeExpression {
    protected String name;

    public LocalVariableReferenceExpression(Type type, String name) {
        super(type);
        this.name = name;
    }

    public LocalVariableReferenceExpression(int lineNumber, Type type, String name) {
        super(lineNumber, type);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "LocalVariableReferenceExpression{lastType=" + type + ", name=" + name + "}";
    }
}
