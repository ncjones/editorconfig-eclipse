/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Angelo Zerr <angelo.zerr@gmail.com> - copied from org.eclipse.wst.jsdt.ui.PreferenceConstants
 *                                           modified in order to process .editorconfig editor.     
 *******************************************************************************/
package org.eclipse.editorconfig.ui;

import org.eclipse.editorconfig.ui.internal.EditorConfigUIPlugin;
import org.eclipse.editorconfig.ui.internal.preferences.EditorConfigUIPreferenceInitializer;
import org.eclipse.editorconfig.ui.text.IEditorConfigColorConstants;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Preference constants used in the EditorConfig-UI preference store. Clients
 * should only read the EditorConfig-UI preference store using these values.
 * Clients are not allowed to modify the preference store programmatically.
 * 
 * Provisional API: This class/interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is being made available at this early stage to solicit feedback
 * from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
 */
public class PreferenceConstants {

	/**
	 * Preference key suffix for bold text style preference keys.
	 *
	 */
	public static final String EDITOR_BOLD_SUFFIX = "_bold"; //$NON-NLS-1$

	/**
	 * Preference key suffix for italic text style preference keys.
	 *
	 */
	public static final String EDITOR_ITALIC_SUFFIX = "_italic"; //$NON-NLS-1$

	/**
	 * Preference key suffix for strikethrough text style preference keys.
	 *
	 */
	public static final String EDITOR_STRIKETHROUGH_SUFFIX = "_strikethrough"; //$NON-NLS-1$

	/**
	 * Preference key suffix for underline text style preference keys.
	 *
	 */
	public static final String EDITOR_UNDERLINE_SUFFIX = "_underline"; //$NON-NLS-1$

	/**
	 * A named preference that holds the color used to render keys in a
	 * .editorconfig files.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a
	 * string using class <code>PreferenceConverter</code>
	 * </p>
	 *
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public static final String EDITOR_CONFIG_COLORING_PROPERTY_KEY = IEditorConfigColorConstants.EDITOR_CONFIG_COLORING_PROPERTY_KEY;

	/**
	 * A named preference that controls whether keys in a .editorconfig files
	 * are rendered in bold.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_PROPERTY_KEY_BOLD = EDITOR_CONFIG_COLORING_PROPERTY_KEY
			+ EDITOR_BOLD_SUFFIX;

	/**
	 * A named preference that controls whether keys in a .editorconfig files
	 * are rendered in italic.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_PROPERTY_KEY_ITALIC = EDITOR_CONFIG_COLORING_PROPERTY_KEY
			+ EDITOR_ITALIC_SUFFIX;

	/**
	 * A named preference that controls whether keys in a .editorconfig files
	 * are rendered in strikethrough.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_PROPERTY_KEY_STRIKETHROUGH = EDITOR_CONFIG_COLORING_PROPERTY_KEY
			+ EDITOR_STRIKETHROUGH_SUFFIX;

	/**
	 * A named preference that controls whether keys in a .editorconfig files
	 * are rendered in underline.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_PROPERTY_KEY_UNDERLINE = EDITOR_CONFIG_COLORING_PROPERTY_KEY
			+ EDITOR_UNDERLINE_SUFFIX;

	// ---------------- Comment

	/**
	 * A named preference that holds the color used to render comments in a
	 * .editorconfig files.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a
	 * string using class <code>PreferenceConverter</code>
	 * </p>
	 *
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public static final String EDITOR_CONFIG_COLORING_COMMENT = IEditorConfigColorConstants.EDITOR_CONFIG_COLORING_COMMENT;

	/**
	 * A named preference that controls whether comments in a .editorconfig
	 * files are rendered in bold.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_COMMENT_BOLD = EDITOR_CONFIG_COLORING_COMMENT
			+ EDITOR_BOLD_SUFFIX;

	/**
	 * A named preference that controls whether comments in a .editorconfig
	 * files are rendered in italic.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_COMMENT_ITALIC = EDITOR_CONFIG_COLORING_COMMENT
			+ EDITOR_ITALIC_SUFFIX;

	/**
	 * A named preference that controls whether comments in a .editorconfig
	 * files are rendered in strikethrough.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_COMMENT_STRIKETHROUGH = EDITOR_CONFIG_COLORING_COMMENT
			+ EDITOR_STRIKETHROUGH_SUFFIX;

	/**
	 * A named preference that controls whether comments in a .editorconfig
	 * files are rendered in underline.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_COMMENT_UNDERLINE = EDITOR_CONFIG_COLORING_COMMENT
			+ EDITOR_UNDERLINE_SUFFIX;

	// ---------------- Comment

	/**
	 * A named preference that holds the color used to render SECTION_NAMEs in a
	 * .editorconfig files.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a
	 * string using class <code>PreferenceConverter</code>
	 * </p>
	 *
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public static final String EDITOR_CONFIG_COLORING_SECTION = IEditorConfigColorConstants.EDITOR_CONFIG_COLORING_SECTION;

	/**
	 * A named preference that controls whether SECTION_NAMEs in a properties
	 * file are rendered in bold.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_SECTION_BOLD = EDITOR_CONFIG_COLORING_SECTION
			+ EDITOR_BOLD_SUFFIX;

	/**
	 * A named preference that controls whether SECTION_NAMEs in a properties
	 * file are rendered in italic.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_SECTION_ITALIC = EDITOR_CONFIG_COLORING_SECTION
			+ EDITOR_ITALIC_SUFFIX;

	/**
	 * A named preference that controls whether SECTION_NAMEs in a properties
	 * file are rendered in strikethrough.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_SECTION_NAME_STRIKETHROUGH = EDITOR_CONFIG_COLORING_SECTION
			+ EDITOR_STRIKETHROUGH_SUFFIX;

	/**
	 * A named preference that controls whether SECTION_NAMEs in a properties
	 * file are rendered in underline.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_SECTION_NAME_UNDERLINE = EDITOR_CONFIG_COLORING_SECTION
			+ EDITOR_UNDERLINE_SUFFIX;

	// Value
	/**
	 * A named preference that holds the color used to render values in a
	 * .editorconfig files.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a
	 * string using class <code>PreferenceConverter</code>
	 * </p>
	 *
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public static final String EDITOR_CONFIG_COLORING_PROPERTY_VALUE = IEditorConfigColorConstants.EDITOR_CONFIG_COLORING_PROPERTY_VALUE;

	/**
	 * A named preference that controls whether values in a .editorconfig files
	 * are rendered in bold.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_PROPERTY_VALUE_BOLD = EDITOR_CONFIG_COLORING_PROPERTY_VALUE
			+ EDITOR_BOLD_SUFFIX;

	/**
	 * A named preference that controls whether values in a .editorconfig files
	 * are rendered in italic.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_PROPERTY_VALUE_ITALIC = EDITOR_CONFIG_COLORING_PROPERTY_VALUE
			+ EDITOR_ITALIC_SUFFIX;

	/**
	 * A named preference that controls whether values in a .editorconfig files
	 * are rendered in strikethrough.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_PROPERTY_VALUE_STRIKETHROUGH = EDITOR_CONFIG_COLORING_PROPERTY_VALUE
			+ EDITOR_STRIKETHROUGH_SUFFIX;

	/**
	 * A named preference that controls whether values in a .editorconfig files
	 * are rendered in underline.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_PROPERTY_VALUE_UNDERLINE = EDITOR_CONFIG_COLORING_PROPERTY_VALUE
			+ EDITOR_UNDERLINE_SUFFIX;

	/**
	 * A named preference that holds the color used to render assignments in a
	 * .editorconfig files.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a
	 * string using class <code>PreferenceConverter</code>
	 * </p>
	 *
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public static final String EDITOR_CONFIG_COLORING_ASSIGNMENT = IEditorConfigColorConstants.EDITOR_CONFIG_COLORING_ASSIGNMENT;

	/**
	 * A named preference that controls whether assignments in a .editorconfig
	 * files are rendered in bold.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_ASSIGNMENT_BOLD = EDITOR_CONFIG_COLORING_ASSIGNMENT
			+ EDITOR_BOLD_SUFFIX;

	/**
	 * A named preference that controls whether assignments in a .editorconfig
	 * files are rendered in italic.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_ASSIGNMENT_ITALIC = EDITOR_CONFIG_COLORING_ASSIGNMENT
			+ EDITOR_ITALIC_SUFFIX;

	/**
	 * A named preference that controls whether assignments in a .editorconfig
	 * files are rendered in strikethrough.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_ASSIGNMENT_STRIKETHROUGH = EDITOR_CONFIG_COLORING_ASSIGNMENT
			+ EDITOR_STRIKETHROUGH_SUFFIX;

	/**
	 * A named preference that controls whether assignments in a .editorconfig
	 * files are rendered in underline.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public static final String EDITOR_CONFIG_COLORING_ASSIGNMENT_UNDERLINE = EDITOR_CONFIG_COLORING_ASSIGNMENT
			+ EDITOR_UNDERLINE_SUFFIX;

	/**
	 * The symbolic font name for the JavaScript editor text font (value
	 * <code>"org.eclipse.editorconfig.ui.editors.textfont"</code>).
	 * 
	 */
	public final static String EDITOR_CONFIG_EDITOR_TEXT_FONT = "org.eclipse.editorconfig.ui.editors.textfont"; //$NON-NLS-1$

	/**
	 * Returns the EditorConfig preference store.
	 *
	 * @return the EditorConfig preference store
	 */
	public static IPreferenceStore getPreferenceStore() {
		return EditorConfigUIPlugin.getDefault().getPreferenceStore();
	}

	/**
	 * Initializes the given preference store with the default values.
	 *
	 * @param store
	 *            the preference store to be initialized
	 *
	 */
	public static void initializeDefaultValues(IPreferenceStore store) {
		store.setDefault(PreferenceConstants.EDITOR_CONFIG_COLORING_PROPERTY_KEY_BOLD, false);
		store.setDefault(PreferenceConstants.EDITOR_CONFIG_COLORING_PROPERTY_KEY_ITALIC, false);

		store.setDefault(PreferenceConstants.EDITOR_CONFIG_COLORING_PROPERTY_VALUE_BOLD, false);
		store.setDefault(PreferenceConstants.EDITOR_CONFIG_COLORING_PROPERTY_VALUE_ITALIC, false);

		store.setDefault(PreferenceConstants.EDITOR_CONFIG_COLORING_ASSIGNMENT_BOLD, false);
		store.setDefault(PreferenceConstants.EDITOR_CONFIG_COLORING_ASSIGNMENT_ITALIC, false);

		store.setDefault(PreferenceConstants.EDITOR_CONFIG_COLORING_COMMENT_BOLD, false);
		store.setDefault(PreferenceConstants.EDITOR_CONFIG_COLORING_COMMENT_ITALIC, false);

		store.setDefault(PreferenceConstants.EDITOR_CONFIG_COLORING_SECTION_BOLD, true);
		store.setDefault(PreferenceConstants.EDITOR_CONFIG_COLORING_SECTION_ITALIC, false);

		// Colors that are set by the current theme
		EditorConfigUIPreferenceInitializer.setThemeBasedPreferences(store, false);

	}
}
