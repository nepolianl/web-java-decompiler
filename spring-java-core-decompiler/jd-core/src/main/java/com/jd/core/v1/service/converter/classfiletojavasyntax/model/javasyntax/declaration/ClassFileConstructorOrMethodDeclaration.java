/*
 * Copyright (c) 2008, 2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.service.converter.classfiletojavasyntax.model.javasyntax.declaration;

import com.jd.core.v1.model.classfile.ClassFile;
import com.jd.core.v1.model.classfile.Method;
import com.jd.core.v1.model.javasyntax.declaration.BaseFormalParameter;
import com.jd.core.v1.model.javasyntax.statement.BaseStatement;
import com.jd.core.v1.model.javasyntax.type.BaseType;
import com.jd.core.v1.model.javasyntax.type.BaseTypeParameter;
import com.jd.core.v1.model.javasyntax.type.Type;
import com.jd.core.v1.model.javasyntax.type.TypeArgument;

import java.util.Map;

public interface ClassFileConstructorOrMethodDeclaration extends ClassFileMemberDeclaration {
    int getFlags();
    void setFlags(int flags);

    ClassFile getClassFile();

    Method getMethod();

    BaseTypeParameter getTypeParameters();

    BaseType getParameterTypes();

    Type getReturnedType();

    ClassFileBodyDeclaration getBodyDeclaration();

    Map<String, TypeArgument> getBindings();

    Map<String, BaseType> getTypeBounds();

    void setFormalParameters(BaseFormalParameter formalParameters);

    BaseStatement getStatements();
    void setStatements(BaseStatement statements);
}
