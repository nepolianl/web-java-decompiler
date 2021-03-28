/*
 * Copyright (c) 2008-2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.model.javasyntax.declaration;

import com.jd.core.v1.model.javasyntax.reference.BaseAnnotationReference;
import com.jd.core.v1.model.javasyntax.statement.BaseStatement;
import com.jd.core.v1.model.javasyntax.type.BaseType;
import com.jd.core.v1.model.javasyntax.type.BaseTypeParameter;

public class ConstructorDeclaration implements MemberDeclaration {
    protected BaseAnnotationReference annotationReferences;
    protected int flags;
    protected BaseTypeParameter typeParameters;
    protected BaseFormalParameter formalParameters;
    protected BaseType exceptionTypes;
    protected String descriptor;
    protected BaseStatement statements;

    public ConstructorDeclaration(int flags, BaseFormalParameter formalParameters, String descriptor, BaseStatement statements) {
        this.flags = flags;
        this.formalParameters = formalParameters;
        this.descriptor = descriptor;
        this.statements = statements;
    }

    public ConstructorDeclaration(BaseAnnotationReference annotationReferences, int flags, BaseTypeParameter typeParameters, BaseFormalParameter formalParameters, BaseType exceptionTypes, String descriptor, BaseStatement statements) {
        this.annotationReferences = annotationReferences;
        this.flags = flags;
        this.typeParameters = typeParameters;
        this.formalParameters = formalParameters;
        this.exceptionTypes = exceptionTypes;
        this.descriptor = descriptor;
        this.statements = statements;
    }

    public int getFlags() {
        return flags;
    }

    public BaseAnnotationReference getAnnotationReferences() {
        return annotationReferences;
    }

    public BaseTypeParameter getTypeParameters() {
        return typeParameters;
    }

    public BaseFormalParameter getFormalParameters() {
        return formalParameters;
    }

    public BaseType getExceptionTypes() {
        return exceptionTypes;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public BaseStatement getStatements() {
        return statements;
    }

    @Override
    public void accept(DeclarationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ConstructorDeclaration{" + descriptor + "}";
    }
}
