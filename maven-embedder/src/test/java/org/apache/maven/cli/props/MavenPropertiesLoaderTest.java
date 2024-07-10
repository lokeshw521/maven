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
package org.apache.maven.cli.props;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class MavenPropertiesLoaderTest {

    @Test
    void testIncludes() throws Exception {
        FileSystem fs = Jimfs.newFileSystem(Configuration.unix());

        Path mavenHome = fs.getPath("/maven");
        Files.createDirectories(mavenHome);
        Path mavenConf = mavenHome.resolve("conf");
        Files.createDirectories(mavenConf);
        Path mavenUserProps = mavenConf.resolve("maven.properties");
        Files.writeString(mavenUserProps, "${includes} = ?\"/user/ma ven.properties\",  ?/foo/bar\n");
        Path userDirectory = fs.getPath("/user");
        Files.createDirectories(userDirectory);
        Path propsPath = userDirectory.resolve("ma ven.properties");
        Files.writeString(propsPath, "fro = ${bar}z\n" + "bar = chti${java.version}\n");

        Properties p = new Properties();
        MavenPropertiesLoader.loadProperties(p, mavenUserProps, null, false);
        assertFalse(p.getProperty("fro").contains("$"));
    }
}
