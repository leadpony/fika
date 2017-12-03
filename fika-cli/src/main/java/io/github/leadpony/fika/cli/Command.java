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

package io.github.leadpony.fika.cli;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import io.github.leadpony.fika.api.project.Project;

/**
 *
 */
public abstract class Command {

    private static final Path DEFAULT_PATH = Paths.get("fika.yml");

    private Path path = DEFAULT_PATH;
    protected Project project;
    
    protected Command() {
    }
    
    public void prepare(List<String> options) {
    }
    
    public void execute() throws Exception {
        this.project = loadProject();
        run();
    }
    
    abstract protected void run() throws Exception;
    
    private Project loadProject() throws IOException {
        return Project.loadFrom(this.path);
    }
}
