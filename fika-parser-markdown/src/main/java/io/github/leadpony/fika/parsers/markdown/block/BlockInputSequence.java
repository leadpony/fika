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

import static io.github.leadpony.fika.parsers.markdown.common.Characters.SPACE;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.leadpony.fika.parsers.markdown.common.InputSequence;

/**
 * Content in a line.
 * 
 * @author leadpony
 */
class BlockInputSequence implements InputSequence {
    
    private final String line;
    private final List<Integer> tabs;
    private final int beginIndex;
    private final int endIndex;
    private final int length;
    
    private static final Pattern TAB_PATTERN = Pattern.compile("\\t");
    
    private static final String[] EXPANDED_TAB = { "    ", "   ", "  ", " " }; 
   
    static BlockInputSequence of(String line) {
        List<Integer> tabs = new ArrayList<>();
        String expaned = expandTabs(line, tabs);
        return new BlockInputSequence(expaned, tabs, 0, expaned.length());
    }
    
    private BlockInputSequence(String line, List<Integer> tabs, int beginIndex, int endIndex) {
        this.line = line;
        this.tabs = tabs;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
        this.length = endIndex - beginIndex;
    }
    
    /* CharSequence interface */
    
    @Override
    public char charAt(int index) {
        return this.line.charAt(this.beginIndex + index);
    }

    @Override
    public int length() {
        return length;
    }
    
    @Override
    public CharSequence subSequence(int start, int end) {
        return subContent(start, end);
    }
    
    @Override
    public String toString() {
        if (beginIndex == 0 && endIndex == line.length()) {
            return line;
        } else {
            return line.substring(beginIndex, endIndex);
        }
    }
    
    /* String-like interface */
    
    @Override
    public String substring(int beginIndex, int endIndex) {
        if (beginIndex < 0 || endIndex < 0 || beginIndex > endIndex || endIndex > length) {
            throw new IndexOutOfBoundsException();
        }
        int newBeginIndex = this.beginIndex + beginIndex;
        int newEndIndex = this.beginIndex + endIndex;
        return this.line.substring(newBeginIndex, newEndIndex);
    }
    
    /* */
    
    @Override
    public BlockInputSequence subContent(int beginIndex) {
        if (beginIndex == 0) {
            return this;
        }
        return subContent(beginIndex, length());
    }

    @Override
    public BlockInputSequence subContent(int beginIndex, int endIndex) {
        if (beginIndex < 0 || endIndex < 0 || beginIndex > endIndex || endIndex > length) {
            throw new IndexOutOfBoundsException();
        }
        if (beginIndex == 0 && endIndex == length) {
            return this;
        }
        int newBeginIndex = this.beginIndex + beginIndex;
        int newEndIndex = this.beginIndex + endIndex;
        return new BlockInputSequence(this.line, this.tabs, newBeginIndex, newEndIndex);
    }
    
    BlockInputSequence removeIndentUpTo(int size) {
        int i = 0;
        while (i < size) {
            if (charAt(i) != SPACE) {
                break;
            }
            ++i;
        }
        return subContent(i);
    }
    
    BlockInputSequence trimSpaces() {
        int i = 0;
        while (i < length) {
            char c = charAt(i);
            if (c != SPACE) {
                break;
            }
            ++i;
        }
        int beginIndex = i;
        i = length() - 1;
        while (i > beginIndex) {
            char c = charAt(i);
            if (c != SPACE) {
                break;
            }
            --i;
        }
        int endIndex = i + 1;
        return subContent(beginIndex, endIndex);
    }
    
    BlockInputSequence trimLeadingSpaces() {
        int i = 0;
        while (i < length) {
            char c = charAt(i);
            if (c != SPACE) {
                break;
            }
            ++i;
        }
        return subContent(i, length);
    }
    
    BlockInputSequence trimSmallIndent() {
        int beginIndex = countLeadingSpaces(0, 3);
        return subContent(beginIndex, length());
    }
    
    @Override
    public String toSourceString() {
        return restoreTabs(line, tabs, beginIndex, endIndex);
    }
    
    private static String expandTabs(String line, List<Integer> tabs) {
        StringBuilder b = new StringBuilder();
        int last = 0;
        Matcher m = TAB_PATTERN.matcher(line);
        while (m.find()) {
            int tab = m.start();
            b.append(line, last, tab);
            tabs.add(b.length());
            b.append(EXPANDED_TAB[b.length() % 4]);
            last = tab + 1;
        }
        if (last == 0) {
            return line;
        } else if (last < line.length()) {
            b.append(line, last, line.length());
        }
        return b.toString();
    }

    private static String restoreTabs(String line, List<Integer> tabs, int beginIndex, int endIndex) {
        if (tabs.size() == 0) {
            return line.substring(beginIndex, endIndex);
        }
        StringBuilder b = new StringBuilder();
        int last = beginIndex;
        for (int tab: tabs) {
            if (tab < beginIndex) {
                continue;
            } else if (tab >= endIndex) {
                break;
            }
            b.append(line, last, tab).append('\t');
            last = tab + EXPANDED_TAB[tab % 4].length();
        }
        if (last < endIndex) {
            b.append(line, last, endIndex);
        }
        return b.toString();
    }
}
