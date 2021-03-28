/*
 * Copyright (c) 2008-2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.service.writer;

import java.util.List;

import com.jd.core.v1.api.printer.Printer;
import com.jd.core.v1.model.message.Message;
import com.jd.core.v1.model.processor.Processor;
import com.jd.core.v1.model.token.Token;
import com.jd.core.v1.service.writer.visitor.PrintTokenVisitor;

/**
 * Write a list of tokens to a {@link org.jd.core.v1.api.printer.Printer}.<br><br>
 *
 * Input:  List<{@link org.jd.core.v1.model.token.Token}><br>
 * Output: -<br>
 */
public class WriteTokenProcessor implements Processor {

    @Override
    public void process(Message message) throws Exception {
        Printer printer = message.getHeader("printer");
        List<Token> tokens = message.getBody();
        PrintTokenVisitor visitor = new PrintTokenVisitor();
        int maxLineNumber = message.getHeader("maxLineNumber");
        int majorVersion = message.getHeader("majorVersion");
        int minorVersion = message.getHeader("minorVersion");

        printer.start(maxLineNumber, majorVersion, minorVersion);
        visitor.start(printer, tokens);

        for (Token token : tokens) {
            token.accept(visitor);
        }

        visitor.end();
        printer.end();
    }
}
