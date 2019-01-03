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
package org.leadpony.fika.core.renderer;

import java.io.StringWriter;
import java.io.Writer;

import org.leadpony.fika.core.model.Node;

/**
 * Common renderer interface.
 * 
 * @author leadpony
 */
public interface Renderer {
    
    void render(Node node, Writer writer);
    
    default String render(Node node) {
        StringWriter writer = new StringWriter();
        render(node, writer);
        return writer.toString();
    }
}
