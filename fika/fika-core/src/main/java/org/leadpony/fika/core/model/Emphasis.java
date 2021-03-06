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
package org.leadpony.fika.core.model;

/**
 * An emphasis node.
 *
 * @author leadpony
 */
public interface Emphasis extends Inline {

    /**
     * The minimum strength allowed.
     */
    static int MIN_STRENGTH = 1;

    /**
     * The minimum strength allowed.
     */
    static int MAX_STRENGTH = 2;

    /**
     * Returns the strength of this emphasis.
     *
     * @return the strength of this emphasis, from {@link #MIN_STRENGTH} to
     *         {@link #MAX_STRENGTH}.
     */
    int strength();

    /**
     * {@inheritDoc}
     */
    @Override
    default void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
