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
package io.github.leadpony.fika.core.project;

import java.nio.file.Path;

import io.github.leadpony.fika.core.common.MediaTypeRegistry;

/**
 * @author leadpony
 */
class BasicPageSource implements PageSource {
    
    private final Path path;
    
    public BasicPageSource(Path path) {
        this.path = path;
    }

    @Override
    public Path path() {
        return path;
    }
    
    @Override
    public String mediaType() {
        return MediaTypeRegistry.get().guessMediaType(path());
    }
    
    @Override
    public String toString() {
        return path.toString();
    }
}
