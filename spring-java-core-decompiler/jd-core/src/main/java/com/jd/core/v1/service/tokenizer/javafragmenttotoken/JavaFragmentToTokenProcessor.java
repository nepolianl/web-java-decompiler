/*
 * Copyright (c) 2008-2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.service.tokenizer.javafragmenttotoken;

import java.util.List;

import com.jd.core.v1.model.javafragment.JavaFragment;
import com.jd.core.v1.model.message.Message;
import com.jd.core.v1.model.processor.Processor;
import com.jd.core.v1.service.tokenizer.javafragmenttotoken.visitor.TokenizeJavaFragmentVisitor;

/**
 * Convert a list of fragments to a list of tokens.<br><br>
 *
 * Input:  List<{@link org.jd.core.v1.model.fragment.Fragment}><br>
 * Output: List<{@link org.jd.core.v1.model.token.Token}><br>
 */
public class JavaFragmentToTokenProcessor implements Processor {

    @Override
    public void process(Message message) throws Exception {
        List<JavaFragment> fragments = message.getBody();
        TokenizeJavaFragmentVisitor visitor = new TokenizeJavaFragmentVisitor(fragments.size() * 3);

        // Create tokens
        for (JavaFragment fragment : fragments) {
            fragment.accept(visitor);
        }

        message.setBody(visitor.getTokens());
    }
}
