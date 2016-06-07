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
 *  Contributors:
 *  	Angelo Zerr <angelo.zerr@gmail.com> - initial .editorconfig editor  
 */
package org.eclipse.editorconfig.ui.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.editorconfig.ui.internal.text.EditorConfigDocumentProvider;
import org.eclipse.editorconfig.ui.text.EditorConfigTextTools;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.BackingStoreException;

/**
 * The activator class controls the plug-in life cycle
 */
public class EditorConfigUIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.editorconfig.ui"; //$NON-NLS-1$

	// The shared instance
	private static EditorConfigUIPlugin plugin;

	// The shared editorconfig document provider
	private IDocumentProvider editorConfigDocumentProvider;

	// The shared editorconfig text tools
	private EditorConfigTextTools editorConfigTextTools;

	// The shared combined preference store
	private IPreferenceStore combinedPreferenceStore;

	/**
	 * The constructor
	 */
	public EditorConfigUIPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
	 * BundleContext )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);

		if (editorConfigTextTools != null) {
			editorConfigTextTools.dispose();
			editorConfigTextTools = null;
		}
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static EditorConfigUIPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns a combined preference store, this store is read-only.
	 *
	 * @return the combined preference store
	 *
	 */
	public IPreferenceStore getCombinedPreferenceStore() {
		if (combinedPreferenceStore == null) {
			IPreferenceStore generalTextStore = EditorsUI.getPreferenceStore();
			combinedPreferenceStore = new ChainedPreferenceStore(new IPreferenceStore[] {
					getPreferenceStore() /*
											 * , new PreferencesAdapter(
											 * getJavaCorePluginPreferences())
											 */, generalTextStore });
		}
		return combinedPreferenceStore;
	}

	/**
	 * Returns the shared document provider for .editorconfig files used by this
	 * plug-in instance.
	 *
	 * @return the shared document provider for .editorconfig files
	 */
	public synchronized IDocumentProvider getEditorConfigDocumentProvider() {
		if (editorConfigDocumentProvider == null) {
			editorConfigDocumentProvider = new EditorConfigDocumentProvider();
		}
		return editorConfigDocumentProvider;
	}

	/**
	 * Returns the shared text tools for .editorconfig files used by this
	 * plug-in instance.
	 *
	 * @return the shared text tools for .editorconfig files
	 */
	public synchronized EditorConfigTextTools getEditorConfigTextTools() {
		if (editorConfigTextTools == null) {
			editorConfigTextTools = new EditorConfigTextTools(getPreferenceStore());
		}
		return editorConfigTextTools;

	}

	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}

	public static void logErrorMessage(String message) {
		log(new Status(IStatus.ERROR, PLUGIN_ID, IEditorConfigStatusConstants.INTERNAL_ERROR, message, null));
	}

	public static void logErrorStatus(String message, IStatus status) {
		if (status == null) {
			logErrorMessage(message);
			return;
		}
		MultiStatus multi = new MultiStatus(PLUGIN_ID, IEditorConfigStatusConstants.INTERNAL_ERROR, message, null);
		multi.add(status);
		log(multi);
	}

	public static void log(Throwable e) {
		log(new Status(IStatus.ERROR, PLUGIN_ID, IEditorConfigStatusConstants.INTERNAL_ERROR,
				EditorConfigUIMessages.EditorConfigUIPlugin_internal_error, e));
	}

	/**
	 * Flushes the instance scope of this plug-in.
	 * 
	 */
	public static void flushInstanceScope() {
		try {
			InstanceScope.INSTANCE.getNode(EditorConfigUIPlugin.PLUGIN_ID).flush();
		} catch (BackingStoreException e) {
			log(e);
		}
	}

}
