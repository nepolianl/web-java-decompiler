/*
 * Copyright (c) 2008, 2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.service.converter.classfiletojavasyntax.model.javasyntax.expression;

import com.jd.core.v1.model.javasyntax.expression.LocalVariableReferenceExpression;
import com.jd.core.v1.model.javasyntax.type.Type;
import com.jd.core.v1.service.converter.classfiletojavasyntax.model.localvariable.AbstractLocalVariable;
import com.jd.core.v1.service.converter.classfiletojavasyntax.model.localvariable.LocalVariableReference;

public class ClassFileLocalVariableReferenceExpression extends LocalVariableReferenceExpression implements LocalVariableReference {
    protected AbstractLocalVariable localVariable;

    public ClassFileLocalVariableReferenceExpression(int lineNumber, AbstractLocalVariable localVariable) {
        super(lineNumber, null, null);
        this.localVariable = localVariable;
        localVariable.addReference(this);
    }

    @Override
    public Type getType() {
        return localVariable.getType();
    }

    @Override
    public String getName() {
        return localVariable.getName();
    }

    @Override
    public AbstractLocalVariable getLocalVariable() {
        return localVariable;
    }

    @Override
    public void setLocalVariable(AbstractLocalVariable localVariable) {
        this.localVariable = localVariable;
    }

    @Override
    public String toString() {
        return "ClassFileLocalVariableReferenceExpression{type=" + localVariable.getType() + ", name=" + localVariable.getName() + "}";
    }
}
