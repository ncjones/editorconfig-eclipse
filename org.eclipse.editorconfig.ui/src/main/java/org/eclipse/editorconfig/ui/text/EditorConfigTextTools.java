/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Angelo Zerr <angelo.zerr@gmail.com> - copied from org.eclipse.wst.jsdt.ui.text.JavaTextTools
 *                                           modified in order to process .editorconfig editor.     
 *******************************************************************************/
package org.eclipse.editorconfig.ui.text;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.editorconfig.ui.internal.text.EditorConfigColorManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

/**
 * Tools required to configure a EditorConfig text viewer. The color manager and
 * all scanner exist only one time, i.e. the same instances are returned to all
 * clients. Thus, clients share those tools.
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 *
 * @noextend This class is not intended to be subclassed by clients.
 */
public class EditorConfigTextTools {

	public static final IContentType EDITORCONFIG_CONTENT_TYPE = Platform.getContentTypeManager()
			.getContentType("org.eclipse.editorconfig.core.editorconfigsource"); //$NON-NLS-1$
	
	/**
	 * This tools' preference listener.
	 */
	private class PreferenceListener implements IPropertyChangeListener, Preferences.IPropertyChangeListener {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			// adaptToPreferenceChange(event);
		}

		@Override
		public void propertyChange(Preferences.PropertyChangeEvent event) {
			// adaptToPreferenceChange(new
			// PropertyChangeEvent(event.getSource(), event.getProperty(),
			// event.getOldValue(), event.getNewValue()));
		}
	}

	/** The color manager. */
	private EditorConfigColorManager colorManager;
	private IPreferenceStore preferenceStore;
	private PreferenceListener preferenceListener = new PreferenceListener();

	public EditorConfigTextTools(IPreferenceStore store) {
		this(store, true);
	}

	public EditorConfigTextTools(IPreferenceStore store, boolean autoDisposeOnDisplayDispose) {
		preferenceStore = store;
		preferenceStore.addPropertyChangeListener(preferenceListener);
		colorManager = new EditorConfigColorManager(autoDisposeOnDisplayDispose);

	}

	public IColorManager getColorManager() {
		return colorManager;
	}

	/**
	 * Disposes all the individual tools of this tools collection.
	 */
	public void dispose() {
		if (colorManager != null) {
			colorManager.dispose();
			colorManager = null;
		}
		if (preferenceStore != null) {
			preferenceStore.removePropertyChangeListener(preferenceListener);
			preferenceStore = null;
			preferenceListener = null;
		}
	}

}
