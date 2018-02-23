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
package org.leadpony.fika.parser.markdown.block.matchers;

import java.util.ArrayList;
import java.util.List;

import org.leadpony.fika.core.model.CodeBlock;
import org.leadpony.fika.parser.markdown.block.AbstractBlockBuilder;
import org.leadpony.fika.parser.markdown.block.BlockType;
import org.leadpony.fika.parser.markdown.common.InputSequence;

class IndentedCodeBuilder extends AbstractBlockBuilder {
   
    static final int INDENT_SIZE = 4;
    
    private final List<String> lines;
    private int lastNonBlankLineNo;

    IndentedCodeBuilder() {
        this.lines = new ArrayList<>();
    }

    @Override
    public BlockType blockType() {
        return BasicBlockType.INDENTED_CODE;
    }
    
    @Override
    public Result append(InputSequence input) {
        if (lineNo() <= 1 || input.hasLeadingSpaces(INDENT_SIZE)) {
            appendLine(input);
            return Result.CONTINUED;
        } else if (input.isBlank()) {
            appendBlank();
            return Result.CONTINUED;
        }
        return Result.NOT_MATCHED;
    }

    @Override
    protected CodeBlock buildBlock() {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= lastNonBlankLineNo; ++i) {
            builder.append(lines.get(i - 1)).append('\n');
        }
        return getNodeFactory().newCodeBlock(builder.toString());
    }

    private void appendLine(InputSequence input) {
        input = input.subSequence(INDENT_SIZE);
        this.lines.add(input.toSourceString());
        if (!input.isBlank()) {
            this.lastNonBlankLineNo = lineNo();
        }
    }
    
    private void appendBlank() {
        this.lines.add("");
    }
}