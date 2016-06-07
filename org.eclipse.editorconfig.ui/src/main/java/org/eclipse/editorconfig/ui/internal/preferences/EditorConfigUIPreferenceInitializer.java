/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Angelo Zerr <angelo.zerr@gmail.com> - copied from org.eclipse.jdt.internal.ui.JavaUIPreferenceInitializer
 *                                           modified in order to process .editorconfig editor.     
 *******************************************************************************/
package org.eclipse.editorconfig.ui.internal.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.editorconfig.ui.PreferenceConstants;
import org.eclipse.editorconfig.ui.internal.IEditorConfigThemeConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.EditorsUI;

/**
 * Editor Config UI preferences initializer.
 *
 */
public class EditorConfigUIPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = PreferenceConstants.getPreferenceStore();

		EditorsUI.useAnnotationsPreferencePage(store);
		EditorsUI.useQuickDiffPreferencePage(store);
		PreferenceConstants.initializeDefaultValues(store);
	}

	public static void setThemeBasedPreferences(IPreferenceStore store, boolean fireEvent) {
		ColorRegistry registry = null;
		if (PlatformUI.isWorkbenchRunning())
			registry = PlatformUI.getWorkbench().getThemeManager().getCurrentTheme().getColorRegistry();
		setDefault(store, PreferenceConstants.EDITOR_CONFIG_COLORING_PROPERTY_KEY,
				findRGB(registry, IEditorConfigThemeConstants.EDITOR_CONFIG_COLORING_PROPERTY_KEY, new RGB(0, 0, 0)),
				fireEvent);

		setDefault(store,
				PreferenceConstants.EDITOR_CONFIG_COLORING_PROPERTY_VALUE, findRGB(registry,
						IEditorConfigThemeConstants.EDITOR_CONFIG_COLORING_PROPERTY_VALUE, new RGB(42, 0, 255)),
				fireEvent);

		setDefault(store, PreferenceConstants.EDITOR_CONFIG_COLORING_ASSIGNMENT,
				findRGB(registry, IEditorConfigThemeConstants.EDITOR_CONFIG_COLORING_ASSIGNMENT, new RGB(0, 0, 0)),
				fireEvent);

		setDefault(store, PreferenceConstants.EDITOR_CONFIG_COLORING_COMMENT,
				findRGB(registry, IEditorConfigThemeConstants.EDITOR_CONFIG_COLORING_COMMENT, new RGB(63, 127, 95)),
				fireEvent);

		setDefault(store, PreferenceConstants.EDITOR_CONFIG_COLORING_SECTION,
				findRGB(registry, IEditorConfigThemeConstants.EDITOR_CONFIG_COLORING_SECTION, new RGB(0, 0, 0)),
				fireEvent);

	}

	/**
	 * Sets the default value and fires a property change event if necessary.
	 *
	 * @param store
	 *            the preference store
	 * @param key
	 *            the preference key
	 * @param newValue
	 *            the new value
	 * @param fireEvent
	 *            <code>false</code> if no event should be fired
	 */
	private static void setDefault(IPreferenceStore store, String key, RGB newValue, boolean fireEvent) {
		if (!fireEvent) {
			PreferenceConverter.setDefault(store, key, newValue);
			return;
		}

		RGB oldValue = null;
		if (store.isDefault(key)) {
			oldValue = PreferenceConverter.getDefaultColor(store, key);
		}
		PreferenceConverter.setDefault(store, key, newValue);

		if (oldValue != null && !oldValue.equals(newValue)) {
			store.firePropertyChangeEvent(key, oldValue, newValue);
		}
	}

	/**
	 * Returns the RGB for the given key in the given color registry.
	 *
	 * @param registry
	 *            the color registry
	 * @param key
	 *            the key for the constant in the registry
	 * @param defaultRGB
	 *            the default RGB if no entry is found
	 * @return RGB the RGB
	 */
	private static RGB findRGB(ColorRegistry registry, String key, RGB defaultRGB) {
		if (registry == null) {
			return defaultRGB;
		}
		RGB rgb = registry.getRGB(key);
		if (rgb != null) {
			return rgb;
		}
		return defaultRGB;
	}
}
