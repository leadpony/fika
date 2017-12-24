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
package io.github.leadpony.fika.core.parser.support.nodes;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import io.github.leadpony.fika.core.nodes.Node;

/**
 * @author leadpony
 */
abstract class NodeChildList extends AbstractList<Node> {

    private final List<Node> internal = new ArrayList<>();
    
    @Override
    public Node get(int index) {
        return internal.get(index);
    }

    @Override
    public int size() {
        return internal.size();
    }

    @Override
    public Node set(int index, Node element) {
        if (element == null) {
            throw new NullPointerException("element must not be null");
        }
        Node parentNode = element.getParentNode();
        if (parentNode != null) {
            parentNode.childNodes().remove(element);
        }
        Node previous = internal.set(index, element);
        element.setParentNode(owner());
        return previous;
    }

    @Override
    public void add(int index, Node element) {
        // TODO Auto-generated method stub
        super.add(index, element);
    }

    @Override
    public Node remove(int index) {
        // TODO Auto-generated method stub
        return super.remove(index);
    }
    
    protected abstract Node owner();
}