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
package io.github.leadpony.fika.parsers.markdown.block;

/**
 * @author leadpony
 */
abstract class AbstractBlockMatcher implements BlockMatcher {

    private Context context;
    private int lines;
    
    @Override
    public void bind(Context context) {
        this.context = context;
    }
    
    @Override
    public BlockMatcher interrupt(Content content) {
        if (isInterruptible()) {
            return context().match(content, precedence());
        }
        return null;
    }
    
    @Override
    public Status match(Content content) {
        return match(content, lines++);
    }
   
    protected Context context() {
        return context;
    }
    
    protected Status match(Content content, int lineNo) {
        return Status.NOT_MATCHED;
    }
}