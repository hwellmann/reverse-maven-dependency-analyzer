/*
 * Copyright (C) 2012 Pieter van der Meer (pieter(at)elucidator.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.elucidator.maven.analyzer.aether;

import org.sonatype.aether.graph.DependencyNode;
import org.sonatype.aether.graph.DependencyVisitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

/**
 * Dumper
 */
public class TransitiveDependencyGraphDumper implements DependencyVisitor {
    Stack<DependencyNode> stack = new Stack<DependencyNode>();
    List<DependencyResultRecord> nodes = new ArrayList<DependencyResultRecord>();

    @Override
    public boolean visitEnter(DependencyNode node) {
        if (stack.empty()) {
            stack.push(node);
            return true;
        }
        nodes.add(new DependencyResultRecord(stack.peek().getDependency().getArtifact(), node.getDependency().getArtifact(), node.getDependency().getScope()));
        //stack.peek().getDependency().getArtifact() + " -> " + node.getDependency().getArtifact() + "-> " + node.getDependency().getScope());
        stack.push(node);

        return true;
    }

    @Override
    public boolean visitLeave(DependencyNode node) {
        if (!stack.empty()) {
            stack.pop();
        }
        return true;
    }

    public Collection<? extends DependencyResultRecord> getNodes() {
        return nodes;
    }
}
