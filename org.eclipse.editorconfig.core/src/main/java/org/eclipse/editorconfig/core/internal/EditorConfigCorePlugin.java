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
package org.eclipse.editorconfig.core.internal;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.editorconfig.core.internal.contributions.PreferencesUpdatersContributionManager;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class EditorConfigCorePlugin extends Plugin {

	public static final String PLUGIN_ID = "org.eclipse.editorconfig.core"; //$NON-NLS-1$

	// The shared instance.
	private static EditorConfigCorePlugin plugin;

	/**
	 * The constructor.
	 */
	public EditorConfigCorePlugin() {
		super();
		plugin = this;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		PreferencesUpdatersContributionManager.getInstance().connect();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		PreferencesUpdatersContributionManager.getInstance().disconnect();
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static EditorConfigCorePlugin getDefault() {
		return plugin;
	}

}
