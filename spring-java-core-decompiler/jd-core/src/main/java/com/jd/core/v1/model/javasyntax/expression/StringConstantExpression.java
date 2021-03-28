/*
 * Copyright (c) 2008-2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.model.javasyntax.expression;

import com.jd.core.v1.model.javasyntax.type.ObjectType;
import com.jd.core.v1.model.javasyntax.type.Type;

public class StringConstantExpression extends AbstractLineNumberExpression {
    public static final StringConstantExpression EMPTY_STRING = new StringConstantExpression("");

    protected String string;

    public StringConstantExpression(String string) {
        this.string = string;
    }

    public StringConstantExpression(int lineNumber, String string) {
        super(lineNumber);
        this.string = string;
    }

    public String getString() {
        return string;
    }

    @Override
    public Type getType() {
        return ObjectType.TYPE_STRING;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "StringConstantExpression{\"" + string + "\"}";
    }
}
