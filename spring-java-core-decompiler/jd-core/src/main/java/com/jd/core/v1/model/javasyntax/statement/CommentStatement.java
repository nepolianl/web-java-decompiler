/*
 * Copyright (c) 2008-2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.model.javasyntax.statement;

public class CommentStatement implements Statement {
    protected String text;

    public CommentStatement(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public void accept(StatementVisitor visitor) {
        visitor.visit(this);
    }
}
