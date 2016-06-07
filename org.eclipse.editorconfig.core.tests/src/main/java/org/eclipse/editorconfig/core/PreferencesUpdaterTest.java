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
package org.eclipse.editorconfig.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.internal.preferences.InstancePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.editorconfig.core.internal.contributions.PreferencesUpdater;
import org.eclipse.editorconfig.core.internal.contributions.PreferencesUpdatersContributionManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.osgi.service.prefs.BackingStoreException;

/**
 * Tests with preferences updater extension point.
 *
 */
public class PreferencesUpdaterTest {

	// Editor ID's
	private final String CUSTOM_EDITOR_ID = "com.youcompany.youapp.youeditor";
	private final String JAVA_EDITOR_ID = "org.eclipse.jdt.ui.CompilationUnitEditor";
	private final String ANT_EDITOR_ID = "org.eclipse.ant.ui.internal.editor.AntEditor";
	private final String XML_EDITOR_ID = "org.eclipse.wst.xml.ui.internal.tabletree.XMLMultiPageEditorPart";

	// Preference names
	private final String[] PREFERENCE_NAMES = { "org.eclipse.core.runtime", "org.eclipse.core.resources",
			"org.eclipse.ui.editors", "org.eclipse.jdt.core", "org.eclipse.jdt.ui", "org.eclipse.ant.ui",
			"org.eclipse.wst.xml.core" };

	private static final ConfigProperty indent_style_space = ConfigPropertyType.valueOf("INDENT_STYLE")
			.createConfigProperty(IndentStyleOption.SPACE.name());
	private static final ConfigProperty indent_size_4 = ConfigPropertyType.valueOf("INDENT_SIZE")
			.createConfigProperty("4");
	private static final ConfigProperty end_of_line_lf = ConfigPropertyType.valueOf("END_OF_LINE")
			.createConfigProperty(EndOfLineOption.LF.name());
	private static final ConfigProperty charset_utf8 = ConfigPropertyType.valueOf("CHARSET")
			.createConfigProperty("utf-8");
	private static final ConfigProperty trim_trailing_whitespace_true = ConfigPropertyType
			.valueOf("TRIM_TRAILING_WHITESPACE").createConfigProperty("true");
	private static final ConfigProperty insert_final_newline_true = ConfigPropertyType.valueOf("INSERT_FINAL_NEWLINE")
			.createConfigProperty("true");

	private PreferencesUpdated preferencesUpdated;

	/**
	 * Test apply preferences for a Custom Editor.
	 */
	@Test
	public void testCustomEditor() {
		PreferencesUpdatersContributionManager manager = PreferencesUpdatersContributionManager.getInstance();
		PreferencesUpdater updater = manager.getUpdater(CUSTOM_EDITOR_ID);
		updater.applyConfig(null, indent_style_space);
		updater.applyConfig(null, indent_size_4);
		updater.applyConfig(null, end_of_line_lf);
		updater.applyConfig(null, charset_utf8);
		updater.applyConfig(null, trim_trailing_whitespace_true);
		updater.applyConfig(null, insert_final_newline_true);

		// test number of preferences which was updated
		Assert.assertEquals(4, preferencesUpdated.getTotal());
		// test preferences values
		preferencesUpdated.assertExists("org.eclipse.ui.editors", "spacesForTabs", "true");
		preferencesUpdated.assertExists("org.eclipse.ui.editors", "tabWidth", "4");
		preferencesUpdated.assertExists("org.eclipse.core.runtime", "line.separator", "\n");
		preferencesUpdated.assertExists("org.eclipse.core.resources", "encoding", "UTF-8");
	}

	/**
	 * Test apply preferences for Java Editor.
	 */
	@Test
	public void testJavaEditor() {
		PreferencesUpdatersContributionManager manager = PreferencesUpdatersContributionManager.getInstance();
		PreferencesUpdater updater = manager.getUpdater(JAVA_EDITOR_ID);
		updater.applyConfig(null, indent_style_space);
		updater.applyConfig(null, indent_size_4);
		updater.applyConfig(null, end_of_line_lf);
		updater.applyConfig(null, charset_utf8);
		updater.applyConfig(null, trim_trailing_whitespace_true);
		updater.applyConfig(null, insert_final_newline_true);

		// test number of preferences which was updated
		Assert.assertEquals(6, preferencesUpdated.getTotal());
		// test preferences values
		preferencesUpdated.assertExists("org.eclipse.jdt.core", "org.eclipse.jdt.core.formatter.tabulation.char",
				"space");
		preferencesUpdated.assertExists("org.eclipse.jdt.core", "org.eclipse.jdt.core.formatter.tabulation.size", "4");
		preferencesUpdated.assertExists("org.eclipse.core.runtime", "line.separator", "\n");
		preferencesUpdated.assertExists("org.eclipse.core.resources", "encoding", "UTF-8");
		preferencesUpdated.assertExists("org.eclipse.jdt.ui", "sp_cleanup.remove_trailing_whitespaces", "true");
		preferencesUpdated.assertExists("org.eclipse.jdt.core",
				"org.eclipse.jdt.core.formatter.insert_new_line_at_end_of_file_if_missing", "true");
	}

	/**
	 * Test apply preferences for Ant Editor.
	 */
	@Test
	public void testAntEditor() {
		PreferencesUpdatersContributionManager manager = PreferencesUpdatersContributionManager.getInstance();
		PreferencesUpdater updater = manager.getUpdater(ANT_EDITOR_ID);
		updater.applyConfig(null, indent_style_space);
		updater.applyConfig(null, indent_size_4);
		updater.applyConfig(null, end_of_line_lf);
		updater.applyConfig(null, charset_utf8);
		updater.applyConfig(null, trim_trailing_whitespace_true);
		updater.applyConfig(null, insert_final_newline_true);

		// test number of preferences which was updated
		Assert.assertEquals(4, preferencesUpdated.getTotal());
		// test preferences values
		preferencesUpdated.assertExists("org.eclipse.ant.ui", "formatter_tab_char", "false");
		preferencesUpdated.assertExists("org.eclipse.ant.ui", "formatter_tab_size", "4");
		preferencesUpdated.assertExists("org.eclipse.core.runtime", "line.separator", "\n");
		preferencesUpdated.assertExists("org.eclipse.core.resources", "encoding", "UTF-8");
	}

	/**
	 * Test apply preferences for XML Editor.
	 */
	@Test
	public void testXMLEditor() {
		PreferencesUpdatersContributionManager manager = PreferencesUpdatersContributionManager.getInstance();
		PreferencesUpdater updater = manager.getUpdater(XML_EDITOR_ID);
		updater.applyConfig(null, indent_style_space);
		updater.applyConfig(null, indent_size_4);
		updater.applyConfig(null, end_of_line_lf);
		updater.applyConfig(null, charset_utf8);
		updater.applyConfig(null, trim_trailing_whitespace_true);
		updater.applyConfig(null, insert_final_newline_true);

		// test number of preferences which was updated
		Assert.assertEquals(4, preferencesUpdated.getTotal());
		// test preferences values
		preferencesUpdated.assertExists("org.eclipse.wst.xml.core", "indentationChar", "space");
		preferencesUpdated.assertExists("org.eclipse.wst.xml.core", "indentationSize", "4");
		preferencesUpdated.assertExists("org.eclipse.core.runtime", "line.separator", "\n");
		preferencesUpdated.assertExists("org.eclipse.core.resources", "encoding", "UTF-8");
	}
	
	private class PreferencesUpdated implements IPreferenceChangeListener {

		private final List<String> preferences;

		public PreferencesUpdated() {
			this.preferences = new ArrayList<String>();
		}

		public void assertExists(String preferenceName, String key, Object value) {
			String s = getKey(preferenceName, key, value);
			Assert.assertTrue("Preference " + s + " should exists!", preferences.contains(s));
		}

		public int getTotal() {
			return preferences.size();
		}

		@Override
		public void preferenceChange(PreferenceChangeEvent event) {
			String preferenceName = ((InstancePreferences) event.getSource()).name();
			String key = event.getKey();
			Object newValue = event.getNewValue();
			preferences.add(getKey(preferenceName, key, newValue));
		}

		private String getKey(String preferenceName, String key, Object value) {
			return new StringBuilder(preferenceName).append("/").append(key).append("=")
					.append(value != null ? value.toString() : "null").toString();
		}
	}

	@Before
	public void init() {
		this.preferencesUpdated = new PreferencesUpdated();
		for (String preferenceName : PREFERENCE_NAMES) {
			IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(preferenceName);
			try {
				preferences.clear();
			} catch (BackingStoreException e) {
				e.printStackTrace();
			}
			preferences.addPreferenceChangeListener(preferencesUpdated);
		}
	}

	@After
	public void destroy() {
		for (String preferenceName : PREFERENCE_NAMES) {
			IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(preferenceName);
			try {
				preferences.clear();
			} catch (BackingStoreException e) {
				e.printStackTrace();
			}
			preferences.removePreferenceChangeListener(preferencesUpdated);
		}
	}
}
