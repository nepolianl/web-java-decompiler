/*
 * Copyright (c) 2008, 2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.service.converter.classfiletojavasyntax.visitor;

import com.jd.core.v1.model.javasyntax.AbstractJavaSyntaxVisitor;
import com.jd.core.v1.model.javasyntax.declaration.*;
import com.jd.core.v1.model.javasyntax.expression.BinaryOperatorExpression;
import com.jd.core.v1.model.javasyntax.expression.Expression;
import com.jd.core.v1.model.javasyntax.expression.FieldReferenceExpression;
import com.jd.core.v1.model.javasyntax.statement.BaseStatement;
import com.jd.core.v1.model.javasyntax.statement.ExpressionStatement;
import com.jd.core.v1.model.javasyntax.statement.Statement;
import com.jd.core.v1.model.javasyntax.statement.Statements;
import com.jd.core.v1.model.javasyntax.type.PrimitiveType;
import com.jd.core.v1.service.converter.classfiletojavasyntax.model.javasyntax.declaration.ClassFileBodyDeclaration;
import com.jd.core.v1.service.converter.classfiletojavasyntax.model.javasyntax.declaration.ClassFileConstructorOrMethodDeclaration;
import com.jd.core.v1.service.converter.classfiletojavasyntax.model.javasyntax.declaration.ClassFileFieldDeclaration;
import com.jd.core.v1.service.converter.classfiletojavasyntax.model.javasyntax.declaration.ClassFileStaticInitializerDeclaration;
import com.jd.core.v1.util.DefaultList;

import java.util.Iterator;
import java.util.List;

public class InitStaticFieldVisitor extends AbstractJavaSyntaxVisitor {
    protected SearchFirstLineNumberVisitor searchFirstLineNumberVisitor = new SearchFirstLineNumberVisitor();
    protected SearchLocalVariableReferenceVisitor searchLocalVariableReferenceVisitor = new SearchLocalVariableReferenceVisitor();
    protected String internalTypeName;
    protected DefaultList<FieldDeclarator> fields = new DefaultList<>();
    protected List<ClassFileConstructorOrMethodDeclaration> methods;
    protected Boolean deleteStaticDeclaration;

    public void setInternalTypeName(String internalTypeName) {
        this.internalTypeName = internalTypeName;
    }

    @Override
    public void visit(AnnotationDeclaration declaration) {
        this.internalTypeName = declaration.getInternalTypeName();
        safeAccept(declaration.getBodyDeclaration());
    }

    @Override
    public void visit(ClassDeclaration declaration) {
        this.internalTypeName = declaration.getInternalTypeName();
        safeAccept(declaration.getBodyDeclaration());
    }

    @Override
    public void visit(EnumDeclaration declaration) {
        this.internalTypeName = declaration.getInternalTypeName();
        safeAccept(declaration.getBodyDeclaration());
    }

    @Override
    public void visit(InterfaceDeclaration declaration) {
        this.internalTypeName = declaration.getInternalTypeName();
        safeAccept(declaration.getBodyDeclaration());
    }

    @Override
    public void visit(BodyDeclaration declaration) {
        ClassFileBodyDeclaration bodyDeclaration = (ClassFileBodyDeclaration)declaration;

        // Store field declarations
        fields.clear();
        safeAcceptListDeclaration(bodyDeclaration.getFieldDeclarations());

        if (!fields.isEmpty()) {
            methods = bodyDeclaration.getMethodDeclarations();

            if (methods != null) {
                deleteStaticDeclaration = null;

                for (int i=0, len=methods.size(); i<len; i++) {
                    methods.get(i).accept(this);

                    if (deleteStaticDeclaration != null) {
                        if (deleteStaticDeclaration.booleanValue()) {
                            methods.remove(i);
                        }
                        break;
                    }
                }
            }
        }

        safeAcceptListDeclaration(bodyDeclaration.getInnerTypeDeclarations());
    }

    @Override
    public void visit(FieldDeclarator declaration) {
        fields.add(declaration);
    }

    @Override
    public void visit(ConstructorDeclaration declaration) {}

    @Override
    public void visit(MethodDeclaration declaration) {}

    @Override
    public void visit(InstanceInitializerDeclaration declaration) {}

    @Override
    @SuppressWarnings("unchecked")
    public void visit(StaticInitializerDeclaration declaration) {
        ClassFileStaticInitializerDeclaration sid = (ClassFileStaticInitializerDeclaration) declaration;

        BaseStatement statements = sid.getStatements();

        if (statements != null) {
            if (statements.isList()) {
                // Multiple statements
                if ((statements.size() > 0) && isAssertionsDisabled(statements.getFirst())) {
                    // Remove assert initialization statement
                    statements.getList().removeFirst();
                }

                if (statements.size() > 0) {
                    DefaultList<Statement> list = statements.getList();
                    Iterator<FieldDeclarator> fieldDeclaratorIterator = fields.iterator();
//                    int lastLineNumber = 0;

                    for (int i=0, len=list.size(); i<len; i++) {
                        Statement statement = list.get(i);

                        if (setStaticFieldInitializer(statement, fieldDeclaratorIterator)) {
                            if (i > 0) {
                                // Split 'static' block
                                BaseStatement newStatements;

                                if (i == 1) {
                                    newStatements = list.removeFirst();
                                } else {
                                    List<Statement> subList = list.subList(0, i);
                                    newStatements = new Statements(subList);
                                    subList.clear();
                                }

                                // Removes statements from original list
                                len -= newStatements.size();
                                i = 0;

                                addStaticInitializerDeclaration(sid, getFirstLineNumber(newStatements), newStatements);
                            }

                            // Remove field initialization statement
                            list.remove(i--);
                            len--;
// TODO Fix problem with local variable declarations before split static block
//                            lastLineNumber = 0;
//                        } else {
//                            int newLineNumber = getFirstLineNumber(statement);
//
//                            if ((lastLineNumber > 0) && (newLineNumber > 0) && (lastLineNumber + 3 < newLineNumber)) {
//                                // Split 'static' block
//                                BaseStatement newStatements;
//
//                                if (i == 1) {
//                                    newStatements = list.removeFirst();
//                                } else {
//                                    List<Statement> subList = list.subList(0, i);
//                                    newStatements = new Statements(subList);
//                                    subList.clear();
//                                }
//
//                                // Removes statements from original list
//                                len -= newStatements.size();
//                                i = 0;
//
//                                addStaticInitializerDeclaration(sid, newLineNumber, newStatements);
//                            }
//
//                            lastLineNumber = newLineNumber;
                        }
                    }
                }
            } else {
                // Single statement
                if (isAssertionsDisabled(statements.getFirst())) {
                    // Remove assert initialization statement
                    statements = null;
                }
                if ((statements != null) && setStaticFieldInitializer(statements.getFirst(), fields.iterator())) {
                    // Remove field initialization statement
                    statements = null;
                }
            }

            if ((statements == null) || (statements.size() == 0)) {
                deleteStaticDeclaration = Boolean.TRUE;
            } else {
                int firstLineNumber = getFirstLineNumber(statements);
                sid.setFirstLineNumber((firstLineNumber==-1) ? 0 : firstLineNumber);
                deleteStaticDeclaration = Boolean.FALSE;
            }
        }
    }

    protected boolean isAssertionsDisabled(Statement statement) {
        if ((statement.getClass() == ExpressionStatement.class)) {
            ExpressionStatement cdes = (ExpressionStatement) statement;

            if (cdes.getExpression().getClass() == BinaryOperatorExpression.class) {
                BinaryOperatorExpression cfboe = (BinaryOperatorExpression) cdes.getExpression();

                if (cfboe.getLeftExpression().getClass() == FieldReferenceExpression.class) {
                    FieldReferenceExpression fre = (FieldReferenceExpression) cfboe.getLeftExpression();

                    if ((fre.getType() == PrimitiveType.TYPE_BOOLEAN) && fre.getInternalTypeName().equals(internalTypeName) && fre.getName().equals("$assertionsDisabled")) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    protected boolean setStaticFieldInitializer(Statement statement, Iterator<FieldDeclarator> fieldDeclaratorIterator) {
        if (statement.getClass() == ExpressionStatement.class) {
            ExpressionStatement cdes = (ExpressionStatement) statement;

            if (cdes.getExpression().getClass() == BinaryOperatorExpression.class) {
                BinaryOperatorExpression cfboe = (BinaryOperatorExpression) cdes.getExpression();

                if (cfboe.getLeftExpression().getClass() == FieldReferenceExpression.class) {
                    FieldReferenceExpression fre = (FieldReferenceExpression) cfboe.getLeftExpression();

                    if (fre.getInternalTypeName().equals(internalTypeName)) {
                        while (fieldDeclaratorIterator.hasNext()) {
                            FieldDeclarator fdr = fieldDeclaratorIterator.next();
                            FieldDeclaration fdn = fdr.getFieldDeclaration();

                            if (((fdn.getFlags() & Declaration.FLAG_STATIC) != 0) && fdr.getName().equals(fre.getName()) && fdn.getType().getDescriptor().equals(fre.getDescriptor())) {
                                Expression expression = cfboe.getRightExpression();

                                searchLocalVariableReferenceVisitor.init(-1);
                                expression.accept(searchLocalVariableReferenceVisitor);

                                if (searchLocalVariableReferenceVisitor.containsReference()) {
                                    return false;
                                }

                                fdr.setVariableInitializer(new ExpressionVariableInitializer(expression));
                                ((ClassFileFieldDeclaration)fdr.getFieldDeclaration()).setFirstLineNumber(expression.getLineNumber());

                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    protected int getFirstLineNumber(BaseStatement baseStatement) {
        searchFirstLineNumberVisitor.init();
        baseStatement.accept(searchFirstLineNumberVisitor);
        return searchFirstLineNumberVisitor.getLineNumber();
    }

    protected void addStaticInitializerDeclaration(ClassFileStaticInitializerDeclaration sid, int lineNumber, BaseStatement statements) {
        methods.add(new ClassFileStaticInitializerDeclaration(
            sid.getBodyDeclaration(), sid.getClassFile(), sid.getMethod(), sid.getBindings(),
            sid.getTypeBounds(), lineNumber, statements));
    }
}
