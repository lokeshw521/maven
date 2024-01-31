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
package org.apache.maven.api.services;

import java.util.List;

import org.apache.maven.api.BuildPathScope;
import org.apache.maven.api.DependencyCoordinate;
import org.apache.maven.api.Node;
import org.apache.maven.api.Project;
import org.apache.maven.api.Service;
import org.apache.maven.api.Session;
import org.apache.maven.api.annotations.Experimental;
import org.apache.maven.api.annotations.Nonnull;

/**
 * Collects, flattens and resolves dependencies.
 */
@Experimental
public interface DependencyResolver extends Service {

    List<Node> flatten(Session session, Node node, BuildPathScope scope) throws DependencyResolverException;

    /**
     * This method collects, flattens and resolves the dependencies.
     *
     * @param request the request to resolve
     * @return the result of the resolution
     * @throws DependencyCollectorException
     * @throws DependencyResolverException
     * @throws ArtifactResolverException
     *
     * @see DependencyCollector#collect(DependencyCollectorRequest)
     * @see #flatten(Session, Node, BuildPathScope)
     * @see ArtifactResolver#resolve(ArtifactResolverRequest)
     */
    DependencyResolverResult resolve(DependencyResolverRequest request)
            throws DependencyCollectorException, DependencyResolverException, ArtifactResolverException;

    @Nonnull
    default DependencyResolverResult resolve(@Nonnull Session session, @Nonnull Project project) {
        return resolve(DependencyResolverRequest.build(session, project));
    }

    @Nonnull
    default DependencyResolverResult resolve(
            @Nonnull Session session, @Nonnull Project project, @Nonnull BuildPathScope scope) {
        return resolve(DependencyResolverRequest.build(session, project, scope));
    }

    @Nonnull
    default DependencyResolverResult resolve(@Nonnull Session session, @Nonnull DependencyCoordinate dependency) {
        return resolve(DependencyResolverRequest.build(session, dependency));
    }

    @Nonnull
    default DependencyResolverResult resolve(
            @Nonnull Session session, @Nonnull DependencyCoordinate dependency, @Nonnull BuildPathScope scope) {
        return resolve(DependencyResolverRequest.build(session, dependency, scope));
    }

    @Nonnull
    default DependencyResolverResult resolve(
            @Nonnull Session session, @Nonnull List<DependencyCoordinate> dependencies) {
        return resolve(DependencyResolverRequest.build(session, dependencies));
    }

    @Nonnull
    default DependencyResolverResult resolve(
            @Nonnull Session session, @Nonnull List<DependencyCoordinate> dependencies, @Nonnull BuildPathScope scope) {
        return resolve(DependencyResolverRequest.build(session, dependencies, scope));
    }
}
