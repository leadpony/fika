/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.leadpony.fika.parsers.markdown.block.matchers;

import static io.github.leadpony.fika.parsers.markdown.common.Strings.trimLeadingWhitespace;
import static io.github.leadpony.fika.parsers.markdown.common.Strings.trimWhitespace;

import java.util.ArrayList;
import java.util.List;

import io.github.leadpony.fika.core.model.Paragraph;
import io.github.leadpony.fika.core.model.Text;
import io.github.leadpony.fika.parsers.markdown.block.AbstractBlockMatcher;
import io.github.leadpony.fika.parsers.markdown.common.InputSequence;

/**
 * @author leadpony
 */
abstract class AbstractParagraphMatcher extends AbstractBlockMatcher {

    private final List<String> lines = new ArrayList<>();
    
    protected void appendLine(InputSequence line) {
        this.lines.add(line.toSourceString());
    }
    
    /**
     * Builds the content of current paragraph.
     * 
     * The paragraph's raw content is formed by
     * concatenating the lines and removing initial and final whitespace.
     * 
     * @param linesToSkip the number lines to skip.
     * @return the content of the paragraph.
     */
    protected String buildContent(int linesToSkip) {
        StringBuilder b = new StringBuilder();
        for (int i = linesToSkip; i < lines.size(); ++i) {
            String line = lines.get(i);
            b.append(trimLeadingWhitespace(line)).append('\n');
        }
        return trimWhitespace(b.toString());
    }
    
    protected Paragraph buildParagraph(int skipLines) {
        String content = buildContent(skipLines);
        if (content.isEmpty()) {
            return null;
        }
        Text text = getNodeFactory().newText();
        text.setContent(content);
        context().addInline(text);
        Paragraph block = getNodeFactory().newParagraph();
        block.appendChild(text);
        return block;
    }
}