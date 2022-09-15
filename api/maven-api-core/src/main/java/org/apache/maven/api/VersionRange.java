package org.apache.maven.api;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.api.annotations.Experimental;
import org.apache.maven.api.annotations.Nonnull;

/**
 * A range of versions.
 */
@Experimental
public interface VersionRange
{

    /**
     * Determines whether the specified version is contained within this range.
     *
     * @param version The version to test, must not be {@code null}.
     * @return {@code true} if this range contains the specified version, {@code false} otherwise.
     */
    boolean contains( @Nonnull Version version );

    /**
     * Returns a string representation of this version range
     * @return the string representation of this version range
     */
    @Nonnull
    String toString();
}