/*
 * Copyright (c) 2008, 2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.model.javasyntax.expression;

import com.jd.core.v1.model.javasyntax.declaration.BodyDeclaration;
import com.jd.core.v1.model.javasyntax.type.ObjectType;
import com.jd.core.v1.model.javasyntax.type.Type;

public class NewExpression extends AbstractLineNumberExpression {
    protected ObjectType type;
    protected String descriptor;
    protected BaseExpression parameters;
    protected BodyDeclaration bodyDeclaration;

    public NewExpression(int lineNumber, ObjectType type, String descriptor) {
        super(lineNumber);
        this.type = type;
        this.descriptor = descriptor;
    }

    public NewExpression(int lineNumber, ObjectType type, String descriptor, BodyDeclaration bodyDeclaration) {
        super(lineNumber);
        this.type = type;
        this.descriptor = descriptor;
        this.bodyDeclaration = bodyDeclaration;
    }

    public ObjectType getObjectType() {
        return type;
    }

    public void setObjectType(ObjectType type) {
        this.type = type;
    }

    @Override
    public Type getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }

    @Override
    public int getPriority() {
        return 3;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public BaseExpression getParameters() {
        return parameters;
    }

    public void setParameters(BaseExpression parameters) {
        this.parameters = parameters;
    }

    public BodyDeclaration getBodyDeclaration() {
        return bodyDeclaration;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "NewExpression{new " + type + "}";
    }
}
