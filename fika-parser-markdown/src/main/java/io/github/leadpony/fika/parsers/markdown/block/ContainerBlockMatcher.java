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

import java.util.ArrayList;
import java.util.List;

import io.github.leadpony.fika.core.nodes.Node;

/**
 * Matcher for container blocks such as documents, block quotes, and lists.
 * 
 * @author leadpony
 */
abstract class ContainerBlockMatcher extends AbstractBlockMatcher {
    
    private BlockMatcher childMatcher;
    private final List<Node> childNodes = new ArrayList<>();
    
    protected ContainerBlockMatcher() {
    }

    @Override
    public boolean hasChildMatcher() {
        return this.childMatcher != null;
    }
    
    @Override
    public BlockMatcher childMatcher() {
        return childMatcher;
    }
    
    @Override
    public Result match(Content content) {
        if (!hasChildMatcher()) {
            if (findChildMatcher(content) == null) {
                return Result.NOT_MATCHED;
            }
        }
        return invokeChildMatcher(childMatcher(), content);
    }

    @Override
    public final Node close() {
        if (hasChildMatcher()) {
            closeCurrentChildMatcher();
        }
        Node node = buildNode();
        node.childNodes().addAll(childNodes());
        return node;
    }
    
    protected Result matchLazyContinuationLine(Content content) {
        BlockMatcher last = lastMatcher();
        if (last.canContinue(content)) {
            invokeChildMatcher(last, content);
            return Result.CONTINUED;
        } else {
            return Result.NOT_MATCHED;
        }
    }
    
    protected Result invokeChildMatcher(BlockMatcher child, Content content) {
        assert(child != null);
        if (child.isInterruptible()) {
            BlockMatcher interrupter = child.interrupt(content);
            if (interrupter != null) {
                openChildMatcher(interrupter);
                child = interrupter;
            }
        }
        Result result = child.match(content);
        switch (result) {
        case COMPLETED:
            closeCurrentChildMatcher();
            break;
        case NOT_MATCHED:
            closeCurrentChildMatcher();
            child = findChildMatcher(content);
            if (child != null) {
                return invokeChildMatcher(child, content);
            }
            break;
        default:
            break;
        }
        return result;
    }
    
    protected BlockMatcher findChildMatcher(Content content) {
        BlockMatcher matched = context().match(content);
        if (matched != null) {
            openChildMatcher(matched);
        }
        return matched;
    }
    
    protected void openChildMatcher(BlockMatcher childMatcher) {
        if (childMatcher == null) {
            throw new NullPointerException();
        }
        closeCurrentChildMatcher();
        this.childMatcher = childMatcher;
        childMatcher.bind(context());
    }
    
    protected void closeChildMatcher(BlockMatcher childMatcher) {
        if (childMatcher == null) {
            throw new NullPointerException();
        }
        Node node = childMatcher.close();
        if (node != null) {
            this.childNodes.add(node);
        }
        this.childMatcher = null;
    }
    
    protected void closeCurrentChildMatcher() {
        if (this.childMatcher != null) {
            closeChildMatcher(this.childMatcher);
        }
    }
    
    protected List<Node> childNodes() {
        return childNodes;
    }
}
