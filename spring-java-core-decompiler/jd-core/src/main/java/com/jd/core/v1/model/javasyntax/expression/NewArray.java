/*
 * Copyright (c) 2008-2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.model.javasyntax.expression;

import com.jd.core.v1.model.javasyntax.type.Type;

public class NewArray extends AbstractLineNumberTypeExpression {
    protected BaseExpression dimensionExpressionList;

    public NewArray(int lineNumber, Type type, BaseExpression dimensionExpressionList) {
        super(lineNumber, type);
        this.dimensionExpressionList = dimensionExpressionList;
    }

    public BaseExpression getDimensionExpressionList() {
        return dimensionExpressionList;
    }

    public void setDimensionExpressionList(BaseExpression dimensionExpressionList) {
        this.dimensionExpressionList = dimensionExpressionList;
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
        return "NewArray{" + type + "}";
    }
}
