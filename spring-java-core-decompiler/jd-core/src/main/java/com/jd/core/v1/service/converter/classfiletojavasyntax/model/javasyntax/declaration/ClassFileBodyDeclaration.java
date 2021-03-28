/*
 * Copyright (c) 2008, 2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.service.converter.classfiletojavasyntax.model.javasyntax.declaration;

import com.jd.core.v1.model.javasyntax.declaration.BaseMemberDeclaration;
import com.jd.core.v1.model.javasyntax.declaration.BodyDeclaration;
import com.jd.core.v1.model.javasyntax.type.BaseType;
import com.jd.core.v1.model.javasyntax.type.ObjectType;
import com.jd.core.v1.model.javasyntax.type.TypeArgument;
import com.jd.core.v1.util.DefaultList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassFileBodyDeclaration extends BodyDeclaration implements ClassFileMemberDeclaration {
    protected List<ClassFileFieldDeclaration> fieldDeclarations;
    protected List<ClassFileConstructorOrMethodDeclaration> methodDeclarations;
    protected List<ClassFileTypeDeclaration> innerTypeDeclarations;
    protected Map<String, ClassFileTypeDeclaration> innerTypeMap = Collections.emptyMap();
    protected int firstLineNumber;
    protected ObjectType outerType;
    protected DefaultList<String> syntheticInnerFieldNames;
    protected ClassFileBodyDeclaration outerBodyDeclaration;
    protected Map<String, TypeArgument> bindings;
    protected Map<String, BaseType> typeBounds;

    public ClassFileBodyDeclaration(String internalTypeName, Map<String, TypeArgument> bindings, Map<String, BaseType> typeBounds, ClassFileBodyDeclaration outerBodyDeclaration) {
        super(internalTypeName, null);
        this.bindings = bindings;
        this.typeBounds = typeBounds;
        this.outerBodyDeclaration = outerBodyDeclaration;
    }

    public void setMemberDeclarations(BaseMemberDeclaration memberDeclarations) {
        this.memberDeclarations = memberDeclarations;
    }

    public List<ClassFileFieldDeclaration> getFieldDeclarations() {
        return fieldDeclarations;
    }

    public void setFieldDeclarations(List<ClassFileFieldDeclaration> fieldDeclarations) {
        if (fieldDeclarations != null) {
            updateFirstLineNumber(this.fieldDeclarations = fieldDeclarations);
        }
    }

    public List<ClassFileConstructorOrMethodDeclaration> getMethodDeclarations() {
        return methodDeclarations;
    }

    public void setMethodDeclarations(List<ClassFileConstructorOrMethodDeclaration> methodDeclarations) {
        if (methodDeclarations != null) {
            updateFirstLineNumber(this.methodDeclarations = methodDeclarations);
        }
    }

    public List<ClassFileTypeDeclaration> getInnerTypeDeclarations() {
        return innerTypeDeclarations;
    }

    public void setInnerTypeDeclarations(List<ClassFileTypeDeclaration> innerTypeDeclarations) {
        if (innerTypeDeclarations != null) {
            updateFirstLineNumber(this.innerTypeDeclarations = innerTypeDeclarations);

            innerTypeMap = new HashMap<>();

            for (ClassFileTypeDeclaration innerType : innerTypeDeclarations) {
                innerTypeMap.put(innerType.getInternalTypeName(), innerType);
            }
        }
    }

    public ClassFileTypeDeclaration getInnerTypeDeclaration(String internalName) {
        ClassFileTypeDeclaration declaration = innerTypeMap.get(internalName);

        if ((declaration == null) && (outerBodyDeclaration != null)) {
            return outerBodyDeclaration.getInnerTypeDeclaration(internalName);
        }

        return declaration;
    }

    public ClassFileMemberDeclaration removeInnerType(String internalName) {
        ClassFileMemberDeclaration removed = innerTypeMap.remove(internalName);
        innerTypeDeclarations.remove(removed);
        return removed;
    }

    protected void updateFirstLineNumber(List<? extends ClassFileMemberDeclaration> members) {
        for (ClassFileMemberDeclaration member : members) {
            int lineNumber = member.getFirstLineNumber();

            if (lineNumber > 0) {
                if (firstLineNumber == 0) {
                    firstLineNumber = lineNumber;
                } else if (firstLineNumber > lineNumber) {
                    firstLineNumber = lineNumber;
                }

                break;
            }
        }
    }

    @Override
    public int getFirstLineNumber() {
        return firstLineNumber;
    }

    public ObjectType getOuterType() {
        return outerType;
    }

    public void setOuterType(ObjectType outerType) {
        this.outerType = outerType;
    }

    public DefaultList<String> getSyntheticInnerFieldNames() {
        return syntheticInnerFieldNames;
    }

    public void setSyntheticInnerFieldNames(DefaultList<String> syntheticInnerFieldNames) {
        this.syntheticInnerFieldNames = syntheticInnerFieldNames;
    }

    public ClassFileBodyDeclaration getOuterBodyDeclaration() {
        return outerBodyDeclaration;
    }

    public Map<String, TypeArgument> getBindings() {
        return bindings;
    }

    public Map<String, BaseType> getTypeBounds() {
        return typeBounds;
    }

    @Override
    public String toString() {
        return "ClassFileBodyDeclaration{firstLineNumber=" + firstLineNumber + "}";
    }
}
