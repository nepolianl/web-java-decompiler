/*
 * Copyright (c) 2008, 2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.service.converter.classfiletojavasyntax.visitor;

import com.jd.core.v1.model.javasyntax.AbstractJavaSyntaxVisitor;
import com.jd.core.v1.model.javasyntax.declaration.*;
import com.jd.core.v1.model.javasyntax.expression.*;
import com.jd.core.v1.model.javasyntax.statement.Statements;
import com.jd.core.v1.service.converter.classfiletojavasyntax.model.javasyntax.declaration.*;
import com.jd.core.v1.util.DefaultList;

import java.util.Comparator;

import static com.jd.core.v1.model.javasyntax.declaration.Declaration.*;


public class InitEnumVisitor extends AbstractJavaSyntaxVisitor {
    protected ClassFileBodyDeclaration bodyDeclaration = null;
    protected BodyDeclaration constantBodyDeclaration = null;
    protected DefaultList<ClassFileEnumDeclaration.ClassFileConstant> constants = new DefaultList<>();
    protected int lineNumber;
    protected int index;
    protected BaseExpression arguments;

    @SuppressWarnings("unchecked")
    public DefaultList<EnumDeclaration.Constant> getConstants() {
        if (!constants.isEmpty()) {
            constants.sort(new EnumConstantComparator());
        }

        return new DefaultList(constants);
    }

    @Override
    public void visit(BodyDeclaration declaration) {
        ClassFileBodyDeclaration bd = bodyDeclaration;

        bodyDeclaration = (ClassFileBodyDeclaration)declaration;
        constants.clear();
        safeAcceptListDeclaration(bodyDeclaration.getFieldDeclarations());
        safeAcceptListDeclaration(bodyDeclaration.getMethodDeclarations());
        bodyDeclaration = bd;
    }

    @Override
    public void visit(ConstructorDeclaration declaration) {
        ClassFileConstructorDeclaration cfcd = (ClassFileConstructorDeclaration)declaration;

        if (cfcd.getStatements().getClass() == Statements.class) {
            Statements statements = (Statements)cfcd.getStatements();

            if (statements.size() == 1) {
                cfcd.setFlags(FLAG_SYNTHETIC);
            } else {
                FormalParameters parameters = (FormalParameters)cfcd.getFormalParameters();
                // Remove name & index parameterTypes
                parameters.subList(0, 2).clear();
                // Remove super constructor call
                statements.remove(0);
                // Fix flags
                cfcd.setFlags(0);
            }
        } else {
            cfcd.setFlags(FLAG_SYNTHETIC);
        }
    }

    @Override
    public void visit(StaticInitializerDeclaration declaration) {}

    @Override
    public void visit(MethodDeclaration declaration) {
        if ((declaration.getFlags() & (FLAG_STATIC|FLAG_PUBLIC)) != 0) {
            if (declaration.getName().equals("values") || declaration.getName().equals("valueOf")) {
                ClassFileMethodDeclaration cfmd = (ClassFileMethodDeclaration)declaration;
                cfmd.setFlags(cfmd.getFlags() | FLAG_SYNTHETIC);
            }
        }
    }

    @Override
    public void visit(FieldDeclaration declaration) {
        if ((declaration.getFlags() & FLAG_ENUM) != 0) {
            ClassFileFieldDeclaration cffd = (ClassFileFieldDeclaration)declaration;
            cffd.getFieldDeclarators().accept(this);
            cffd.setFlags(cffd.getFlags() | FLAG_SYNTHETIC);
        }
    }

    @Override
    public void visit(FieldDeclarator declaration) {
        constantBodyDeclaration = null;
        safeAccept(declaration.getVariableInitializer());
        constants.add(new ClassFileEnumDeclaration.ClassFileConstant(lineNumber, declaration.getName(), index, arguments, constantBodyDeclaration));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void visit(NewExpression expression) {
        Expressions parameters = (Expressions)expression.getParameters();
        Expression exp = parameters.get(1);

        if (exp.getClass() == CastExpression.class) {
            exp = ((CastExpression)exp).getExpression();
        }

        IntegerConstantExpression ice = (IntegerConstantExpression)exp;

        lineNumber = expression.getLineNumber();
        index = ice.getValue();

        // Remove name & index
        if (parameters.size() == 2) {
            arguments = null;
        } else {
            parameters.subList(0, 2).clear();
            arguments = parameters;
        }

        String enumInternalTypeName = expression.getObjectType().getInternalName();

        if (!enumInternalTypeName.equals(bodyDeclaration.getInternalTypeName())) {
            ClassFileTypeDeclaration typeDeclaration = bodyDeclaration.getInnerTypeDeclaration(enumInternalTypeName);
            if (typeDeclaration != null) {
                constantBodyDeclaration = typeDeclaration.getBodyDeclaration();
            }
        }
    }

    protected static class EnumConstantComparator implements Comparator<ClassFileEnumDeclaration.ClassFileConstant> {
        public int compare(ClassFileEnumDeclaration.ClassFileConstant ec1, ClassFileEnumDeclaration.ClassFileConstant ec2) {
            return ec1.getIndex() - ec2.getIndex();
        }
    }
}
