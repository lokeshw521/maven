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

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.apache.maven.api.Session;
import org.apache.maven.api.annotations.Experimental;
import org.apache.maven.api.annotations.Nonnull;
import org.apache.maven.api.annotations.NotThreadSafe;
import org.apache.maven.api.annotations.Nullable;

import static org.apache.maven.api.services.BaseRequest.nonNull;

/**
 *
 * @since 4.0.0
 */
@Experimental
public interface ToolchainsBuilderRequest {
    @Nonnull
    Session getSession();

    /**
     * Gets the install Toolchains source.
     *
     * @return the install Toolchains source or {@code null} if none
     */
    @Nonnull
    Optional<Source> getInstallToolchainsSource();

    /**
     * Gets the user Toolchains source.
     *
     * @return the user Toolchains source or {@code null} if none
     */
    @Nonnull
    Optional<Source> getUserToolchainsSource();

    @Nonnull
    static ToolchainsBuilderRequest build(
            @Nonnull Session session, @Nullable Source systemToolchainsSource, @Nullable Source userToolchainsSource) {
        return builder()
                .session(nonNull(session, "session cannot be null"))
                .installToolchainsSource(systemToolchainsSource)
                .userToolchainsSource(userToolchainsSource)
                .build();
    }

    @Nonnull
    static ToolchainsBuilderRequest build(
            @Nonnull Session session, @Nullable Path systemToolchainsPath, @Nullable Path userToolchainsPath) {
        return builder()
                .session(nonNull(session, "session cannot be null"))
                .installToolchainsSource(
                        systemToolchainsPath != null && Files.exists(systemToolchainsPath)
                                ? Source.fromPath(systemToolchainsPath)
                                : null)
                .userToolchainsSource(
                        userToolchainsPath != null && Files.exists(userToolchainsPath)
                                ? Source.fromPath(userToolchainsPath)
                                : null)
                .build();
    }

    @Nonnull
    static ToolchainsBuilderRequestBuilder builder() {
        return new ToolchainsBuilderRequestBuilder();
    }

    @NotThreadSafe
    class ToolchainsBuilderRequestBuilder {
        Session session;
        Source installToolchainsSource;
        Source userToolchainsSource;

        public ToolchainsBuilderRequestBuilder session(Session session) {
            this.session = session;
            return this;
        }

        public ToolchainsBuilderRequestBuilder installToolchainsSource(Source installToolchainsSource) {
            this.installToolchainsSource = installToolchainsSource;
            return this;
        }

        public ToolchainsBuilderRequestBuilder userToolchainsSource(Source userToolchainsSource) {
            this.userToolchainsSource = userToolchainsSource;
            return this;
        }

        public ToolchainsBuilderRequest build() {
            return new ToolchainsBuilderRequestBuilder.DefaultToolchainsBuilderRequest(
                    session, installToolchainsSource, userToolchainsSource);
        }

        private static class DefaultToolchainsBuilderRequest extends BaseRequest implements ToolchainsBuilderRequest {
            private final Source installToolchainsSource;
            private final Source userToolchainsSource;

            @SuppressWarnings("checkstyle:ParameterNumber")
            DefaultToolchainsBuilderRequest(
                    @Nonnull Session session,
                    @Nullable Source installToolchainsSource,
                    @Nullable Source userToolchainsSource) {
                super(session);
                this.installToolchainsSource = installToolchainsSource;
                this.userToolchainsSource = userToolchainsSource;
            }

            @Nonnull
            @Override
            public Optional<Source> getInstallToolchainsSource() {
                return Optional.ofNullable(installToolchainsSource);
            }

            @Nonnull
            @Override
            public Optional<Source> getUserToolchainsSource() {
                return Optional.ofNullable(userToolchainsSource);
            }
        }
    }
}
