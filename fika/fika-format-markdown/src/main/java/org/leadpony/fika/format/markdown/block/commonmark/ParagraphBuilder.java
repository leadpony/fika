/*
 * Copyright 2017-2019 the Fika authors.
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
package org.leadpony.fika.format.markdown.block.commonmark;

import org.leadpony.fika.format.markdown.block.AbstractBlockBuilder;
import org.leadpony.fika.format.markdown.block.BlockBuilder;
import org.leadpony.fika.format.markdown.block.BlockType;
import org.leadpony.fika.format.markdown.block.BuilderMode;
import org.leadpony.fika.format.markdown.common.InputSequence;
import org.leadpony.fika.core.model.Block;
import org.leadpony.fika.core.model.Paragraph;
import org.leadpony.fika.core.model.Text;

/**
 * Builder of paragraphs.
 * 
 * @author leadpony
 */
public class ParagraphBuilder extends AbstractBlockBuilder {
    
    private final ParagraphContentBuilder contentBuilder = new ParagraphContentBuilder();
    
    @Override
    public BlockType blockType() {
        return BasicBlockType.PARAGRAPH;
    }
    
    @Override
    public Result appendLazyLine(InputSequence input) {
        BlockBuilder interrupter = interrupt(input, BuilderMode.LAZY_CONTINUATION);
        if (interrupter != null || input.isBlank()) {
            return Result.NOT_MATCHED;
        }
        return processLine(input);
    }
    
    public String getContent() {
        return contentBuilder.toContent();
    }
    
    @Override
    protected Result processLine(InputSequence input) {
        if (lineCount() == 0) {
            contentBuilder.addLine(input);
            return Result.CONTINUED;
        } else if (input.isBlank()) {
            return Result.COMPLETED;
        } else {
            contentBuilder.addLine(input);
            return Result.CONTINUED;
        }
    }

    @Override
    protected boolean isInterruptible() {
        return true;
    }
    
    @Override
    protected Block buildBlock() {
        String content = getContent();
        if (content.isEmpty()) {
            return null;
        }
        Text text = getNodeFactory().createText(content);
        context().addInline(text);
        Paragraph block = getNodeFactory().createParagraph();
        block.appendChild(text);
        return block;
    }
}    
