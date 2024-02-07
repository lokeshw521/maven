/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.api;

import java.util.*;
import java.util.stream.Collectors;

abstract class ExtensibleEnums {

    static Language language(String id) {
        return new DefaultLanguage(id);
    }

    static PathScope pathScope(String id, ProjectScope projectScope, DependencyScope... dependencyScopes) {
        return new DefaultPathScope(id, projectScope, dependencyScopes);
    }

    static ProjectScope projectScope(String id) {
        return new DefaultProjectScope(id);
    }

    private static class DefaultExtensibleEnum implements ExtensibleEnum {

        private final String id;

        DefaultExtensibleEnum(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public String id() {
            return id;
        }

        @Override
        public int hashCode() {
            return id().hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return obj != null && getClass() == obj.getClass() && id().equals(((DefaultExtensibleEnum) obj).id());
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "[" + id() + "]";
        }
    }

    private static class DefaultPathScope extends DefaultExtensibleEnum implements PathScope {
        private final Set<ProjectScope> projectScope;
        private final Set<DependencyScope> dependencyScopes;

        DefaultPathScope(String id, ProjectScope projectScope, DependencyScope... dependencyScopes) {
            this(id, Collections.singleton(projectScope), Arrays.asList(dependencyScopes));
        }

        DefaultPathScope(
                String id, Collection<ProjectScope> projectScopes, Collection<DependencyScope> dependencyScopes) {
            super(id);
            this.projectScope = Collections.unmodifiableSet(new HashSet<>(projectScopes));
            this.dependencyScopes = Collections.unmodifiableSet(new HashSet<>(dependencyScopes));
        }

        @Override
        public Set<ProjectScope> projectScope() {
            return projectScope;
        }

        @Override
        public Set<DependencyScope> dependencyScopes() {
            return dependencyScopes;
        }

        @Override
        public PathScope merge(PathScope pathScope) {
            if (pathScope == null || this.equals(pathScope)) {
                return this;
            }
            HashSet<ProjectScope> mergedProjectScopes = new HashSet<>(projectScope);
            mergedProjectScopes.addAll(pathScope.projectScope());
            HashSet<DependencyScope> mergedScopes = new HashSet<>(dependencyScopes);
            mergedScopes.addAll(pathScope.dependencyScopes());
            return new DefaultPathScope(id() + "+" + pathScope.id(), mergedProjectScopes, mergedScopes);
        }

        @Override
        public PathScope merge(DependencyScope... dependencyScope) {
            HashSet<DependencyScope> mergedScopes = new HashSet<>(dependencyScopes);
            mergedScopes.addAll(Arrays.asList(dependencyScope));
            if (mergedScopes.equals(dependencyScopes)) {
                return this;
            }
            return new DefaultPathScope(
                    id() + "+"
                            + Arrays.stream(dependencyScope)
                                    .map(DependencyScope::id)
                                    .collect(Collectors.joining("+")),
                    projectScope(),
                    mergedScopes);
        }
    }

    private static class DefaultProjectScope extends DefaultExtensibleEnum implements ProjectScope {

        DefaultProjectScope(String id) {
            super(id);
        }
    }

    private static class DefaultLanguage extends DefaultExtensibleEnum implements Language {

        DefaultLanguage(String id) {
            super(id);
        }
    }
}
