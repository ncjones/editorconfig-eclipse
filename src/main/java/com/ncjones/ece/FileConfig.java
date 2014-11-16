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
package com.ncjones.ece;

import java.util.Set;

/**
 * Editor config for a file.
 */
public class FileConfig {

	private final String path;
	
	private final Set<ConfigValue> configValues;

	public FileConfig(final String path, final Set<ConfigValue> configValues) {
		this.path = path;
		this.configValues = configValues;
	}

	public String getPath() {
		return path;
	}

	public Set<ConfigValue> getConfigValues() {
		return configValues;
	}

}
