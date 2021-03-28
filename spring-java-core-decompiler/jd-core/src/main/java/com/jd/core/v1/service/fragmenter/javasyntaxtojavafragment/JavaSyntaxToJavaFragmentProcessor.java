/*
 * Copyright (c) 2008, 2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.service.fragmenter.javasyntaxtojavafragment;

import com.jd.core.v1.api.loader.Loader;
import com.jd.core.v1.model.javafragment.ImportsFragment;
import com.jd.core.v1.model.javasyntax.CompilationUnit;
import com.jd.core.v1.model.message.Message;
import com.jd.core.v1.model.processor.Processor;
import com.jd.core.v1.service.fragmenter.javasyntaxtojavafragment.visitor.CompilationUnitVisitor;
import com.jd.core.v1.service.fragmenter.javasyntaxtojavafragment.visitor.SearchImportsVisitor;

/**
 * Convert a Java syntax model to a list of fragments.<br><br>
 *
 * Input:  {@link com.jd.core.v1.model.javasyntax.CompilationUnit}<br>
 * Output: List<{@link com.jd.core.v1.model.fragment.Fragment}><br>
 */
public class JavaSyntaxToJavaFragmentProcessor implements Processor {

    public void process(Message message) throws Exception {
        Loader loader = message.getHeader("loader");
        String mainInternalTypeName = message.getHeader("mainInternalTypeName");
        int majorVersion = message.getHeader("majorVersion");
        CompilationUnit compilationUnit = message.getBody();

        SearchImportsVisitor importsVisitor = new SearchImportsVisitor(mainInternalTypeName);
        importsVisitor.visit(compilationUnit);
        ImportsFragment importsFragment = importsVisitor.getImportsFragment();
        message.setHeader("maxLineNumber", importsVisitor.getMaxLineNumber());

        CompilationUnitVisitor visitor = new CompilationUnitVisitor(loader, mainInternalTypeName, majorVersion, importsFragment);
        visitor.visit(compilationUnit);
        message.setBody(visitor.getFragments());
    }
}
