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
package io.github.leadpony.fika.parsers.markdown.inline.handlers;

import static io.github.leadpony.fika.parsers.markdown.common.Characters.isUnicodePunctuation;

import io.github.leadpony.fika.core.model.Text;
import io.github.leadpony.fika.parsers.markdown.inline.Delimiter;
import io.github.leadpony.fika.parsers.markdown.inline.HandlerType;

/**
 * @author leadpony
 */
public class UnderscoreEmphasisHandler extends AbstractEmphasisHandler {

    public UnderscoreEmphasisHandler() {
        super('_');
    }

    @Override
    public HandlerType handlerType() {
        return BasicHandlerType.UNDERSCORE_EMPHASIS;
    }
    
    @Override
    protected Delimiter buildDelimiterRun(Text text, int preceding, int following) {
        boolean leftFlanking = calculateLeftFlanking(preceding, following);
        boolean rightFlanking = calculateRightFlanking(preceding, following);
        boolean opener = leftFlanking && (!rightFlanking || isUnicodePunctuation(preceding));
        boolean closer = rightFlanking && (!leftFlanking || isUnicodePunctuation(following));
        return createDelimiterRun(text, opener, closer);
    }
}
