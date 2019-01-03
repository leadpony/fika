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
package org.leadpony.fika.publication.view;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.leadpony.fika.publication.view.ViewResolver.Builder;

/**
 * @author leadpony
 */
public abstract class AbstractViewResolverBuilder implements ViewResolver.Builder {

    protected final Map<String, Object> sharedValues = new HashMap<>();
    protected Path templateDirectory;
    
    @Override
    public Builder withSharedValue(String name, Object value) {
        this.sharedValues.put(name, value);
        return this;
    }

    @Override
    public Builder withTemplateDirectory(Path path) {
        this.templateDirectory = path;
        return this;
    }
}
