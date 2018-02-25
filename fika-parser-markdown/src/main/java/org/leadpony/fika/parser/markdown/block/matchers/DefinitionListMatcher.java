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

import java.util.EnumSet;
import java.util.Set;

import org.leadpony.fika.parser.markdown.block.AbstractBlocKMatcher;
import org.leadpony.fika.parser.markdown.block.BlockBuilder;
import org.leadpony.fika.parser.markdown.block.BlockType;
import org.leadpony.fika.parser.markdown.block.BuilderMode;
import org.leadpony.fika.parser.markdown.common.InputSequence;

/**
 * Matcher implementation for definition list.
 * 
 * @author leadpony
 */
public class DefinitionListMatcher extends AbstractBlocKMatcher {

    @Override
    public BlockType blockType() {
        return BasicBlockType.DEFINITION_LIST;
    }

    @Override
    public Set<? extends BlockType> interruptible() {
        return EnumSet.of(BasicBlockType.PARAGRAPH);
    }
    
    @Override
    public BlockBuilder newBuilder(InputSequence input) {
        DefinitionBuilder definitionBuilder = DefinitionBuilder.builder(input);
        if (definitionBuilder != null) {
            return new DefinitionListBuilder(definitionBuilder);
        }
        return null;
    }

    @Override
    public BlockBuilder newInterruptingBuilder(InputSequence input, BlockBuilder current, BuilderMode mode) {
        if (mode == BuilderMode.LAZY_CONTINUATION) {
            return null;
        }
        if (current.lineNo() > 2) {
            return null;
        }
        DefinitionBuilder definitionBuilder = DefinitionBuilder.builder(input);
        if (definitionBuilder == null) {
            return null;
        }
        ParagraphBuilder paragraphBuilder = (ParagraphBuilder)current;
        String term = paragraphBuilder.buildContent(0);
        paragraphBuilder.cancel();
        return new DefinitionListBuilder(new TermBuilder(term), definitionBuilder);
    }
}
