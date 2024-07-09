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

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.maven.api.annotations.Experimental;
import org.apache.maven.api.annotations.Immutable;
import org.apache.maven.api.model.Plugin;

/**
 * Lifecycle definition
 *
 * @since 4.0.0
 */
@Experimental
@Immutable
public interface Lifecycle extends ExtensibleEnum {

    String CLEAN = "clean";

    String DEFAULT = "default";

    String SITE = "site";

    String WRAPPER = "wrapper";

    /**
     * Name or identifier of this lifecycle.
     *
     * @return the unique identifier for this lifecycle
     */
    @Override
    String id();

    /**
     * Collection of phases for this lifecycle
     */
    Collection<Phase> phases();

    /**
     * Pre-ordered list of phases.
     * If not provided, a default order will be computed.
     */
    default Optional<List<String>> orderedPhases() {
        return Optional.empty();
    }

    /**
     * A phase in the lifecycle.
     */
    interface Phase {
        String name();

        List<Plugin> plugins();
    }
}
