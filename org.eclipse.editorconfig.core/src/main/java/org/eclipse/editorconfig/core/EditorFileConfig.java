/*
 * Copyright 2014 Nathan Jones
 *
 * This file is part of "EditorConfig Eclipse".
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
package org.eclipse.editorconfig.core;

import java.util.Set;

/**
 * Editor config for a file.
 */
public class EditorFileConfig {

	private final String path;
	
	private final Set<ConfigProperty> configProperties;

	public EditorFileConfig(final String path, final Set<ConfigProperty> configProperties) {
		this.path = path;
		this.configProperties = configProperties;
	}

	public String getPath() {
		return path;
	}

	public Set<ConfigProperty> getConfigProperties() {
		return configProperties;
	}

	@Override
	public String toString() {
		return "EditorFileConfig [path=" + path + ", configProperties=" + configProperties + "]";
	}

}
