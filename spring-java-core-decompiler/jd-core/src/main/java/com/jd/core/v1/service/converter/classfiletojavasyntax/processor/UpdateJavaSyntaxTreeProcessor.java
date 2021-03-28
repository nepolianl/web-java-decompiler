/*
 * Copyright (c) 2008, 2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.service.converter.classfiletojavasyntax.processor;

import com.jd.core.v1.model.javasyntax.CompilationUnit;
import com.jd.core.v1.model.message.Message;
import com.jd.core.v1.model.processor.Processor;
import com.jd.core.v1.service.converter.classfiletojavasyntax.util.TypeMaker;
import com.jd.core.v1.service.converter.classfiletojavasyntax.visitor.UpdateJavaSyntaxTreeStep1Visitor;
import com.jd.core.v1.service.converter.classfiletojavasyntax.visitor.UpdateJavaSyntaxTreeStep2Visitor;

/**
 * Create statements, init fields, merge declarations.<br><br>
 *
 * Input:  {@link CompilationUnit}<br>
 * Output: {@link CompilationUnit}<br>
 */
public class UpdateJavaSyntaxTreeProcessor implements Processor {

    @Override
    public void process(Message message) throws Exception {
        TypeMaker typeMaker = message.getHeader("typeMaker");
        CompilationUnit compilationUnit = message.getBody();

        UpdateJavaSyntaxTreeStep1Visitor updateJavaSyntaxTreeStep1Visitor = new UpdateJavaSyntaxTreeStep1Visitor(typeMaker);
        updateJavaSyntaxTreeStep1Visitor.visit(compilationUnit);

        UpdateJavaSyntaxTreeStep2Visitor updateJavaSyntaxTreeStep2Visitor = new UpdateJavaSyntaxTreeStep2Visitor(typeMaker);
        updateJavaSyntaxTreeStep2Visitor.visit(compilationUnit);
    }
}
