/*
 * Copyright 2014-2016 Angelo Zerr
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
 * 
 */
package org.eclipse.editorconfig.core.internal.contributions;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.IRegistryChangeListener;
import org.eclipse.core.runtime.Platform;
import org.eclipse.editorconfig.core.internal.EditorConfigCorePlugin;

/**
 * Preferences updaters extension point.
 *
 */
public class PreferencesUpdatersContributionManager implements IRegistryChangeListener {

	private static final String EDITOR_IDS_ATTR = "editorIds";

	/** The preferences updaters contributions extension point */
	private static final String PREFERENCES_UPDATERS_EXTENSION_POINT = "preferencesUpdaters"; //$NON-NLS-1$

	private static final String COMMONS_EDITOR_ID = "*";

	private static PreferencesUpdatersContributionManager INSTANCE;

	/**
	 * Returns the singleton instance of the refactoring contribution manager.
	 *
	 * @return the singleton instance
	 */
	public static PreferencesUpdatersContributionManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PreferencesUpdatersContributionManager();
		}
		return INSTANCE;
	}

	private Map<String /* editor id */, PreferencesUpdater> contributionCache;

	/**
	 * Creates a new preferences updater contribution manager.
	 */
	private PreferencesUpdatersContributionManager() {
		// Not instantiatable
	}

	/**
	 * Populates the preferences updater contribution cache if necessary.
	 *
	 */
	private void populateCache() {
		if (contributionCache == null) {
			contributionCache = new HashMap<String /* editor id */, PreferencesUpdater>();
			final IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(
					EditorConfigCorePlugin.PLUGIN_ID, PREFERENCES_UPDATERS_EXTENSION_POINT);
			for (int index = 0; index < elements.length; index++) {
				// loop for preferencesUpdater elements
				final IConfigurationElement element = elements[index];
				String[] editorIds = getEditorIds(element);
				if (editorIds != null) {
					PreferencesUpdater updater = new PreferencesUpdater(element, COMMONS_EDITOR_ID.equals(editorIds[0]));
					for (int i = 0; i < editorIds.length; i++) {
						contributionCache.put(editorIds[0], updater);
					}
				}
			}
		}
	}

	private String[] getEditorIds(final IConfigurationElement element) {
		String editorIds = element.getAttribute(EDITOR_IDS_ATTR);
		if (editorIds == null || editorIds.length() < 1) {
			return null;
		}
		return editorIds.split(",");
	}

	public PreferencesUpdater getUpdater(String editorId) {
		populateCache();
		PreferencesUpdater updater = contributionCache.get(editorId);
		return updater != null ? updater : getUpdater(COMMONS_EDITOR_ID);
	}

	/**
	 * Connects this manager to the platform's extension registry.
	 */
	public void connect() {
		Platform.getExtensionRegistry().addRegistryChangeListener(this, EditorConfigCorePlugin.PLUGIN_ID);
	}

	/**
	 * Disconnects this manager from the platform's extension registry.
	 */
	public void disconnect() {
		Platform.getExtensionRegistry().removeRegistryChangeListener(this);
	}

	@Override
	public void registryChanged(IRegistryChangeEvent event) {
		this.contributionCache = null;
	}

}
