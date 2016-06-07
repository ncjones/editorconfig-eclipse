/*******************************************************************************
 * Copyright (c) 2007, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Angelo Zerr <angelo.zerr@gmail.com> - copied from org.eclipse.wst.jsdt.internal.ui.IJavaThemeConstants
 *                                           modified in order to process .editorconfig editor.               
 *******************************************************************************/
package org.eclipse.editorconfig.ui.internal;

import org.eclipse.editorconfig.ui.PreferenceConstants;

/**
 * Defines the constants used in the <code>org.eclipse.ui.themes</code>
 * extension contributed by this plug-in.
 * 
 */
public interface IEditorConfigThemeConstants {

	String ID_PREFIX = EditorConfigUIPlugin.PLUGIN_ID + "."; //$NON-NLS-1$

	/**
	 * Theme constant for the color used to render comments in a .editorconfig
	 * file.
	 */
	String EDITOR_CONFIG_COLORING_COMMENT = ID_PREFIX + PreferenceConstants.EDITOR_CONFIG_COLORING_COMMENT;

	/**
	 * Theme constant for the color used to render section in a .editorconfig
	 * file.
	 */
	String EDITOR_CONFIG_COLORING_SECTION = ID_PREFIX + PreferenceConstants.EDITOR_CONFIG_COLORING_SECTION;

	/**
	 * Theme constant for the color used to render values in a .editorconfig
	 * file.
	 */
	String EDITOR_CONFIG_COLORING_PROPERTY_VALUE = ID_PREFIX
			+ PreferenceConstants.EDITOR_CONFIG_COLORING_PROPERTY_VALUE;

	/**
	 * Theme constant for the color used to render keys in a .editorconfig file.
	 */
	String EDITOR_CONFIG_COLORING_PROPERTY_KEY = ID_PREFIX + PreferenceConstants.EDITOR_CONFIG_COLORING_PROPERTY_KEY;

	/**
	 * Theme constant for the color used to render assignments in a
	 * .editorconfig file.
	 */
	String EDITOR_CONFIG_COLORING_ASSIGNMENT = ID_PREFIX + PreferenceConstants.EDITOR_CONFIG_COLORING_ASSIGNMENT;

}
