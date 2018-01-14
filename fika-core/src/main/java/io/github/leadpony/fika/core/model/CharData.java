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
package io.github.leadpony.fika.core.model;

/**
 * A holder of character data.
 * 
 * @author leadpony
 */
public interface CharData {
    
    default boolean isEmpty() {
        return length() == 0;
    }
    
    /**
     * Returns the number of characters this object holds.
     * 
     * @return the number of characters.
     */
    default int length() {
        return getContent().length();
    }
    
    /**
     * Returns the character data this object holds.
     * 
     * @return the character data, may be empty but never be {@code null}.
     */
    String getContent();
    
    /**
     * Assigns the character data to this object.
     * 
     * @param content the character data to assign.
     * @throws NullPointerException if {@code content} is {@code null}.
     */
    void setContent(String content);
}
