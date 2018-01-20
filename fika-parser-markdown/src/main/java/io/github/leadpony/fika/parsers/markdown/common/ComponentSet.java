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
package io.github.leadpony.fika.parsers.markdown.common;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Registry of components.
 * 
 * @author leadpony
 */
public class ComponentSet<T> implements Iterable<T> {

    private final Set<T> components = new HashSet<>();
    private final Set<Class<?>> classes = new HashSet<>();
    
    public boolean containsTypeOf(Class<? extends T> clazz) {
        return classes.contains(clazz);
    }
    
    public void add(T component) {
        if (component != null) {
            components.add(component);
            classes.add(component.getClass());
        }
    }

    @Override
    public Iterator<T> iterator() {
        return components.iterator();
    }
}
