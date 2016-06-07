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
package org.eclipse.editorconfig.ui.internal.preferences;

import org.eclipse.osgi.util.NLS;

/**
 * EditorConfig Preferences messages
 *
 */
public class PreferencesMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.editorconfig.ui.internal.preferences.PreferencesMessages";//$NON-NLS-1$

	// EditorConfig Editor PreferencePage
	public static String EditorConfigEditorPreferencePage_section;
	public static String EditorConfigEditorPreferencePage_comment;
	public static String EditorConfigEditorPreferencePage_key;
	public static String EditorConfigEditorPreferencePage_value;
	public static String EditorConfigEditorPreferencePage_assignment;

	public static String EditorConfigEditorPreferencePage_foreground;
	public static String EditorConfigEditorPreferencePage_color;
	public static String EditorConfigEditorPreferencePage_bold;
	public static String EditorConfigEditorPreferencePage_italic;
	public static String EditorConfigEditorPreferencePage_strikethrough;
	public static String EditorConfigEditorPreferencePage_underline;
	public static String EditorConfigEditorPreferencePage_preview;
	public static String EditorConfigEditorPreferencePage_link;

	private PreferencesMessages() {
		// Do not instantiate
	}

	static {
		NLS.initializeMessages(BUNDLE_NAME, PreferencesMessages.class);
	}

}
