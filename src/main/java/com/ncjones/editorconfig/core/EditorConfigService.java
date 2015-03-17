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
package com.ncjones.editorconfig.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.editorconfig.core.EditorConfig;
import org.editorconfig.core.EditorConfig.OutPair;
import org.editorconfig.core.EditorConfigException;

public class EditorConfigService {

	private static final Comparator<ConfigValue> CONFIG_VALUE_TYPE_COMPARATOR = new Comparator<ConfigValue>() {
		@Override
		public int compare(final ConfigValue o1, final ConfigValue o2) {
			return o1.getType().compareTo(o2.getType());
		}
	};

	private final EditorConfig editorConfig;

	public EditorConfigService(final EditorConfig editorConfig) {
		this.editorConfig = editorConfig;
	}

	public FileConfig getFileConfig(final String path) {
		final List<OutPair> properties;
		try {
			properties = editorConfig.getProperties(path);
		} catch (final EditorConfigException e) {
			throw new RuntimeException(e);
		}
		final SortedSet<ConfigValue> configValues = new TreeSet<ConfigValue>(CONFIG_VALUE_TYPE_COMPARATOR);
		for (final OutPair outPair : properties) {
			final ConfigValue configValue = createConfigValue(outPair);
			if (configValue != null) {
				configValues.add(configValue);
			}
		}
		return new FileConfig(path, Collections.unmodifiableSet(configValues));
	}

	private static ConfigValue createConfigValue(final OutPair outPair) {
		final ConfigType configType;
		try {
			configType = ConfigType.valueOf(outPair.getKey().toUpperCase());
		} catch (final IllegalArgumentException e) {
			return null;
		}
		return configType.createConfigValue(outPair.getVal());
	}

}
