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
package org.leadpony.fika.parser.markdown;

import org.junit.Assume;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.leadpony.fika.core.parser.BasicFeature;
import org.leadpony.fika.core.parser.MarkupLanguage;
import org.leadpony.fika.core.parser.ParserFactory;

/**
 * @author leadpony
 */
@SuppressWarnings("unused")
@RunWith(Parameterized.class)
public class DefinitionListTest extends AbstractSpecTest {

    private static final ParserFactory factory = 
            ParserFactory.builder(MarkupLanguage.MARKDOWN)
                .withFeature(BasicFeature.DEFINITION_LIST)
                .build();
    
    public DefinitionListTest(int index, String source, String expected) {
        super(index, source, expected);
    }
  
    @Parameters(name = "{0}: {1}")
    public static Iterable<Object[]> parameters() {
        return parameters("/definition-list.json");
    }
    
    @Before
    public void setUp() {
    }

    @Override
    protected ParserFactory getParserFactory() {
        return factory;
    }
}