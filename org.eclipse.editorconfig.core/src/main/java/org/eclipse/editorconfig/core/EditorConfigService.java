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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.editorconfig.core.internal.contributions.PreferencesUpdater;
import org.eclipse.editorconfig.core.internal.contributions.PreferencesUpdatersContributionManager;
import org.editorconfig.core.EditorConfig.OutPair;
import org.editorconfig.core.EditorConfigException;

public class EditorConfigService {

	private static final Comparator<ConfigProperty> CONFIG_PROPERTY_TYPE_COMPARATOR = new Comparator<ConfigProperty>() {
		@Override
		public int compare(final ConfigProperty o1, final ConfigProperty o2) {
			return o1.getType().compareTo(o2.getType());
		}
	};

	private final org.editorconfig.core.EditorConfig editorConfig;

	public EditorConfigService(final org.editorconfig.core.EditorConfig editorConfig) {
		this.editorConfig = editorConfig;
	}

	public EditorFileConfig getEditorConfig(final IFile editorFile, String editorId) {
		PreferencesUpdater updater = null;
		if (editorId != null) {
			updater = PreferencesUpdatersContributionManager.getInstance().getUpdater(editorId);
			if (updater == null) {
				return null;
			}
		}
		final List<OutPair> properties;
		try {
			properties = editorConfig.getProperties(editorFile.getLocation().toString());
		} catch (final EditorConfigException e) {
			throw new RuntimeException(e);
		}
		final SortedSet<ConfigProperty> configProperties = new TreeSet<ConfigProperty>(CONFIG_PROPERTY_TYPE_COMPARATOR);
		for (final OutPair outPair : properties) {
			final ConfigProperty configProperty = createConfigProperty(outPair);
			if (configProperty != null) {
				configProperties.add(configProperty);
			}
		}
		return new EditorFileConfig(editorFile, updater, Collections.unmodifiableSet(configProperties));
	}

	private static ConfigProperty createConfigProperty(final OutPair outPair) {
		final ConfigPropertyType configType = ConfigPropertyType.valueOf(outPair.getKey().toUpperCase());
		return configType == null ? null : configType.createConfigProperty(outPair.getVal());
	}

}
