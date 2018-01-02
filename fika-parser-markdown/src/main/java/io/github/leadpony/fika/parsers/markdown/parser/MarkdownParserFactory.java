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
package io.github.leadpony.fika.parsers.markdown.parser;

import java.io.Reader;

import io.github.leadpony.fika.core.nodes.NodeFactory;
import io.github.leadpony.fika.core.parser.Parser;
import io.github.leadpony.fika.core.parser.ParserFactory;
import io.github.leadpony.fika.core.parser.support.nodes.DefaultNodeFactory;

/**
 * @author leadpony
 *
 */
public class MarkdownParserFactory implements ParserFactory {
    
    private final NodeFactory nodeFactory;
    
    public MarkdownParserFactory() {
        this.nodeFactory = new DefaultNodeFactory();
    }

    @Override
    public Parser newParser(Reader reader) {
        return new MarkdownParser(reader, this.nodeFactory);
    }
}
