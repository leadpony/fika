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
package org.leadpony.fika.parser.markdown;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.leadpony.fika.parser.core.Parser;
import org.leadpony.fika.parser.core.ParserService;
import org.leadpony.fika.parser.model.Document;
import org.leadpony.fika.parser.renderer.HtmlRenderer;

/**
 * @author leadpony
 */
public class SpecDocumentTest {

    private static final ParserService service = ParserService.get("text/markdown");

    private static final Path OUTPUT_PATH = Paths.get("target", "spec.html");
    private InputStream resource;

    @BeforeEach
    public void setUp() {
        resource = getClass().getResourceAsStream("/spec.txt");
        Assumptions.assumeTrue(resource != null);
    }

    @Test
    public void test() {
        Document doc = null;
        try (Parser parser = service.createParser(new InputStreamReader(resource))) {
            doc = parser.parse();
        }

        HtmlRenderer renderer = HtmlRenderer.builder().build();
        try (Writer writer = Files.newBufferedWriter(OUTPUT_PATH)) {
            renderer.render(doc, writer);
        } catch (IOException e) {
        }
    }
}