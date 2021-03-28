/*
 * Copyright (c) 2008, 2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.service.converter.classfiletojavasyntax.visitor;

import com.jd.core.v1.model.javasyntax.AbstractJavaSyntaxVisitor;
import com.jd.core.v1.model.javasyntax.declaration.*;
import com.jd.core.v1.model.javasyntax.expression.BaseExpression;
import com.jd.core.v1.model.javasyntax.expression.Expression;
import com.jd.core.v1.model.javasyntax.expression.SuperConstructorInvocationExpression;
import com.jd.core.v1.model.javasyntax.statement.ExpressionStatement;
import com.jd.core.v1.model.javasyntax.statement.Statement;
import com.jd.core.v1.model.javasyntax.statement.Statements;
import com.jd.core.v1.service.converter.classfiletojavasyntax.model.javasyntax.declaration.ClassFileBodyDeclaration;
import com.jd.core.v1.service.converter.classfiletojavasyntax.model.javasyntax.declaration.ClassFileConstructorDeclaration;
import com.jd.core.v1.service.converter.classfiletojavasyntax.model.javasyntax.declaration.ClassFileConstructorOrMethodDeclaration;
import com.jd.core.v1.service.converter.classfiletojavasyntax.model.javasyntax.declaration.ClassFileMemberDeclaration;
import com.jd.core.v1.service.converter.classfiletojavasyntax.model.javasyntax.expression.ClassFileSuperConstructorInvocationExpression;

import java.util.Iterator;
import java.util.List;

public class RemoveDefaultConstructorVisitor extends AbstractJavaSyntaxVisitor {
    protected int constructorCounter;
    protected ClassFileMemberDeclaration constructor;

    @Override
    public void visit(AnnotationDeclaration declaration) {
        safeAccept(declaration.getBodyDeclaration());
    }

    @Override
    public void visit(BodyDeclaration declaration) {
        ClassFileBodyDeclaration bodyDeclaration = (ClassFileBodyDeclaration)declaration;
        List<ClassFileConstructorOrMethodDeclaration> methods = bodyDeclaration.getMethodDeclarations();

        constructor = null;
        constructorCounter = 0;
        safeAcceptListDeclaration(methods);

        if ((constructorCounter == 1) && (constructor != null)) {
            // Remove empty default constructor
            methods.remove(constructor);
        }
    }

    @Override
    public void visit(FieldDeclaration declaration) {}

    @Override
    @SuppressWarnings("unchecked")
    public void visit(ConstructorDeclaration declaration) {
        if ((declaration.getFlags() & ConstructorDeclaration.FLAG_ABSTRACT) == 0) {
            ClassFileConstructorDeclaration cfcd = (ClassFileConstructorDeclaration)declaration;

            if (cfcd.getStatements().getClass() == Statements.class) {
                Statements statements = (Statements) cfcd.getStatements();

                // Remove 'super();'
                Iterator<Statement> iterator = statements.iterator();

                while (iterator.hasNext()) {
                    Statement statement = iterator.next();

                    if (statement.getClass() == ExpressionStatement.class) {
                        Expression es = ((ExpressionStatement) statement).getExpression();

                        if (es.getClass() == ClassFileSuperConstructorInvocationExpression.class) {
                            SuperConstructorInvocationExpression scie = (SuperConstructorInvocationExpression) es;
                            BaseExpression parameters = scie.getParameters();

                            if ((parameters == null) || (parameters.size() == 0)) {
                                iterator.remove();
                                break;
                            }
                        }
                    }
                }

                // Store empty default constructor
                if (statements.isEmpty()) {
                    if ((cfcd.getFormalParameters() == null) || (cfcd.getFormalParameters().size() == 0)) {
                        constructor = cfcd;
                    }
                }
            }

            // Inc constructor counter
            constructorCounter++;
        }
    }

    @Override
    public void visit(MethodDeclaration declaration) {}

    @Override
    public void visit(StaticInitializerDeclaration declaration) {}

    @Override
    public void visit(ClassDeclaration declaration) {
        safeAccept(declaration.getBodyDeclaration());
    }

    @Override
    public void visit(EnumDeclaration declaration) {
        safeAccept(declaration.getBodyDeclaration());
    }

    @Override
    public void visit(InterfaceDeclaration declaration) {
        safeAccept(declaration.getBodyDeclaration());
    }
}
