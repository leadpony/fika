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

import org.leadpony.fika.format.markdown.block.BlockBuilder;
import org.leadpony.fika.format.markdown.block.BlockMatcher;
import org.leadpony.fika.format.markdown.block.BuilderMode;
import org.leadpony.fika.format.markdown.common.InputSequence;

/**
 * Matcher implementation for list.
 * 
 * @author leadpony
 */
abstract class AbstractListMatcher implements BlockMatcher {
    
    @Override
    public BlockBuilder newBuilder(InputSequence input) {
        return newListBuilder(input);
    }
    
    @Override
    public BlockBuilder newInterruptingBuilder(InputSequence input, BlockBuilder current, BuilderMode mode) {
        ListBuilder builder = newListBuilder(input);
        if (builder == null) {
            return null;
        }
        return builder.canInterrupt(current) ? builder : null;
    }
    
    protected abstract ListBuilder newListBuilder(InputSequence input);
}
