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
package io.github.leadpony.fika.parsers.markdown;

import org.junit.Assume;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author leadpony
 */
@RunWith(Parameterized.class)
public class ParagraphTest extends AbstractSpecTest {

    public ParagraphTest(int index, String source, String expected) {
        super(index, source, expected);
    }
  
    @Parameters(name = "{0}: {1}")
    public static Iterable<Object[]> parameters() {
        return parameters("/paragraphs.json");
    }

    @Before
    public void setUp() {
        Assume.assumeFalse(index() == 189);
    }
}
