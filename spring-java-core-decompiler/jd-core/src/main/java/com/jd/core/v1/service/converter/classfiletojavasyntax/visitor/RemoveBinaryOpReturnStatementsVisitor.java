/*
 * Copyright (c) 2008, 2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.service.converter.classfiletojavasyntax.visitor;

import com.jd.core.v1.model.javasyntax.AbstractJavaSyntaxVisitor;
import com.jd.core.v1.model.javasyntax.declaration.BodyDeclaration;
import com.jd.core.v1.model.javasyntax.expression.BinaryOperatorExpression;
import com.jd.core.v1.model.javasyntax.expression.Expression;
import com.jd.core.v1.model.javasyntax.statement.ExpressionStatement;
import com.jd.core.v1.model.javasyntax.statement.ReturnExpressionStatement;
import com.jd.core.v1.model.javasyntax.statement.Statement;
import com.jd.core.v1.model.javasyntax.statement.Statements;
import com.jd.core.v1.service.converter.classfiletojavasyntax.model.javasyntax.expression.ClassFileLocalVariableReferenceExpression;
import com.jd.core.v1.service.converter.classfiletojavasyntax.util.LocalVariableMaker;


public class RemoveBinaryOpReturnStatementsVisitor extends AbstractJavaSyntaxVisitor {
    protected LocalVariableMaker localVariableMaker;

    public RemoveBinaryOpReturnStatementsVisitor(LocalVariableMaker localVariableMaker) {
        this.localVariableMaker = localVariableMaker;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void visit(Statements statements) {
        if (statements.size() > 1) {
            // Replace pattern "local_var_2 = ...; return local_var_2;" with "return ...;"
            Statement lastStatement = (Statement)statements.getLast();

            if (lastStatement.getClass() == ReturnExpressionStatement.class) {
                ReturnExpressionStatement res = (ReturnExpressionStatement) lastStatement;

                if (res.getExpression().getClass() == ClassFileLocalVariableReferenceExpression.class) {
                    ClassFileLocalVariableReferenceExpression lvr1 = (ClassFileLocalVariableReferenceExpression)res.getExpression();

                    if (lvr1.getName() == null) {
                        Statement statement = (Statement)statements.get(statements.size()-2);

                        if (statement.getClass() == ExpressionStatement.class) {
                            ExpressionStatement es = (ExpressionStatement)statement;

                            if (es.getExpression().getClass() == BinaryOperatorExpression.class) {
                                BinaryOperatorExpression boe = (BinaryOperatorExpression)es.getExpression();
                                Expression leftExpression = boe.getLeftExpression();

                                if (leftExpression.getClass() == ClassFileLocalVariableReferenceExpression.class) {
                                    ClassFileLocalVariableReferenceExpression lvr2 = (ClassFileLocalVariableReferenceExpression) leftExpression;

                                    if ((lvr1.getLocalVariable() == lvr2.getLocalVariable()) && (lvr1.getLocalVariable().getReferences().size() == 2)) {
                                        // Remove synthetic assignment statement
                                        statements.remove(statements.size() - 2);
                                        // Replace synthetic local variable with expression
                                        res.setExpression(boe.getRightExpression());
                                        // Check line number
                                        int expressionLineNumber = boe.getRightExpression().getLineNumber();
                                        if (res.getLineNumber() > expressionLineNumber) {
                                            res.setLineNumber(expressionLineNumber);
                                        }
                                        // Remove synthetic local variable
                                        localVariableMaker.removeLocalVariable(lvr1.getLocalVariable());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        super.visit(statements);
    }

    @Override
    public void visit(BodyDeclaration declaration) {}
}
