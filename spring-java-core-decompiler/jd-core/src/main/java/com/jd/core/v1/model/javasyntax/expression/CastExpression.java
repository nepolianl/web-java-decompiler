/*
 * Copyright (c) 2008-2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.model.javasyntax.expression;

import com.jd.core.v1.model.javasyntax.type.Type;

public class CastExpression extends AbstractLineNumberTypeExpression {
    protected Expression expression;
    protected boolean explicit;

    public CastExpression(Type type, Expression expression) {
        super(type);
        this.expression = expression;
        this.explicit = true;
    }

    public CastExpression(int lineNumber, Type type, Expression expression) {
        super(lineNumber, type);
        this.expression = expression;
        this.explicit = true;
    }

    public CastExpression(int lineNumber, Type type, Expression expression, boolean explicit) {
        super(lineNumber, type);
        this.expression = expression;
        this.explicit = explicit;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public boolean isExplicit() {
        return explicit;
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "CastExpression{cast (" + type + ") " + expression + "}";
    }
}
