/*
 * Copyright (c) 2008-2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.service.converter.classfiletojavasyntax.model.javasyntax.statement;

import com.jd.core.v1.model.javasyntax.expression.Expression;
import com.jd.core.v1.model.javasyntax.statement.CommentStatement;

public class ClassFileMonitorEnterStatement extends CommentStatement {
    protected Expression monitor;

    public ClassFileMonitorEnterStatement(Expression monitor) {
        super("/* monitor enter " + monitor + " */");
        this.monitor = monitor;
    }

    public Expression getMonitor() {
        return monitor;
    }

    @Override
    public String toString() {
        return "ClassFileMonitorEnterStatement{" + monitor + "}";
    }
}
