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
package io.github.leadpony.fika.parser.markdown.block.matchers;

import java.util.EnumSet;
import java.util.Set;

import io.github.leadpony.fika.parser.markdown.block.BlockBuilder;
import io.github.leadpony.fika.parser.markdown.block.BlockMatcher;
import io.github.leadpony.fika.parser.markdown.block.BuilderMode;
import io.github.leadpony.fika.parser.markdown.block.MatcherType;
import io.github.leadpony.fika.parser.markdown.common.InputSequence;

/**
 * @author leadpony
 */
public class ThematicBreakMatcher implements BlockMatcher {
    
    private final ThematicBreakBuilder builder = new ThematicBreakBuilder();

    @Override
    public MatcherType matcherType() {
        return BasicMatcherType.THEMATIC_BREAK;
    }
    
    @Override
    public Set<? extends MatcherType> interruptible() {
        return EnumSet.of(
                BasicMatcherType.PARAGRAPH,
                BasicMatcherType.LINK_DEFINITION,
                BasicMatcherType.LIST);
    }

    @Override
    public BlockBuilder newBuilder(InputSequence input) {
        return testLine(input) ? builder : null;
    }

    @Override
    public BlockBuilder newInterruptingBuilder(InputSequence input, BlockBuilder current, BuilderMode mode) {
        return newBuilder(input);
    }
    
    private static boolean testLine(InputSequence input) {
        int i = input.countLeadingSpaces(0, 3);
        char lineChar = input.charAt(i);
        if (lineChar != '-' && lineChar != '_' && lineChar != '*') {
            return false;
        }
        return testLine(input, i + 1, lineChar);
    }

    private static boolean testLine(InputSequence input, int offset, char lineChar) {
        int dashes = 1;
        for (int i = offset; i < input.length(); ++i) {
            char c = input.charAt(i);
            if (c == '\u0020' || c == '\t') {
                continue;
            } else if (c == lineChar) {
                ++dashes;
            } else {
                return false;
            }
        }
        return dashes >= 3;
    }
}