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
package org.leadpony.fika.format.markdown.parser.features.inline;

import org.leadpony.fika.format.markdown.inline.commonmark.ImageHandler;
import org.leadpony.fika.format.markdown.inline.commonmark.LinkCloserHandler;
import org.leadpony.fika.format.markdown.parser.FeatureProvider;
import org.leadpony.fika.format.markdown.parser.ParserBuilder;
import org.leadpony.fika.core.core.BasicFeature;
import org.leadpony.fika.core.core.Feature;

/**
 * @author leadpony
 */
public class Image implements FeatureProvider {

    @Override
    public Feature feature() {
        return BasicFeature.IMAGE;
    }

    @Override
    public void provide(ParserBuilder builder) {
        builder.add(new ImageHandler());
        if (!builder.hasAdded(LinkCloserHandler.class)) {
            builder.add(new LinkCloserHandler());
        }
    }
}
