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
package org.apache.maven.internal.aether.extender;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import java.util.Map;

import org.apache.maven.api.spi.session.ConfigurationPropertyContributor;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.internal.aether.RepositorySystemSessionExtender;
import org.eclipse.aether.repository.AuthenticationSelector;
import org.eclipse.aether.repository.MirrorSelector;
import org.eclipse.aether.repository.ProxySelector;

/**
 * Extender that manages {@link org.apache.maven.api.spi.session.ConfigurationPropertyContributor}.
 *
 * @since 4.0.0
 */
@Named
@Singleton
public class ConfigurationPropertyContributorExtender implements RepositorySystemSessionExtender {
    private final Map<String, ConfigurationPropertyContributor> configurationPropertyContributors;

    @Inject
    public ConfigurationPropertyContributorExtender(
            Map<String, ConfigurationPropertyContributor> configurationPropertyContributors) {
        this.configurationPropertyContributors = configurationPropertyContributors;
    }

    @Override
    public void extend(
            MavenExecutionRequest mavenExecutionRequest,
            Map<Object, Object> configProperties,
            MirrorSelector mirrorSelector,
            ProxySelector proxySelector,
            AuthenticationSelector authenticationSelector) {
        for (ConfigurationPropertyContributor contributor : configurationPropertyContributors.values()) {
            contributor.contribute(configProperties);
        }
    }
}