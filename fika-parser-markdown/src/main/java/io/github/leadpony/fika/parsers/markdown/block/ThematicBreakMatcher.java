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
package io.github.leadpony.fika.parsers.markdown.block;

import io.github.leadpony.fika.core.nodes.Node;
import io.github.leadpony.fika.core.parser.helper.nodes.SimpleThematicBreak;

/**
 * @author leadpony
 */
class ThematicBreakMatcher extends AbstractBlockMatcher {

    private static final int PRECEDENCE = 1;
    private static final ThematicBreakMatcher instance = new ThematicBreakMatcher();
    
    static Factory factory() {
        return Factory.instance;
    }
    
    private ThematicBreakMatcher() {
    }
    
    @Override
    public Status match(Content content) {
        return Status.COMPLETED;
    }
    
    @Override
    public Node close() {
        return new SimpleThematicBreak();
    }

    private static boolean testLine(Content content) {
        int i = content.detectSmallIndent();
        char lineChar = content.charAt(i);
        if (lineChar != '-' && lineChar != '_' && lineChar != '*') {
            return false;
        }
        return testLine(content, i + 1, lineChar);
    }

    private static boolean testLine(Content content, int offset, char lineChar) {
        int dashes = 1;
        for (int i = offset; i < content.length(); ++i) {
            char c = content.charAt(i);
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

    static class Factory implements BlockMatcher.Factory {
        
        private static final Factory instance = new Factory();
        
        @Override
        public int precedence() {
            return PRECEDENCE;
        }

        @Override
        public BlockMatcher newMatcher(Content content) {
            return testLine(content) ? ThematicBreakMatcher.instance : null;
        }
    }
}