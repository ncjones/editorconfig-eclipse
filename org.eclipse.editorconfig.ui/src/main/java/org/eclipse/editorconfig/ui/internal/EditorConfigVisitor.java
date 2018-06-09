/*
 * Copyright 2017 Nathan Jones, Jackson Bailey
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
package org.eclipse.editorconfig.ui.internal;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.editorconfig.core.ConfigProperty;
import org.eclipse.editorconfig.core.ConfigPropertyVisitor;
import org.eclipse.editorconfig.core.EndOfLineOption;
import org.eclipse.editorconfig.core.IndentStyleOption;

public class EditorConfigVisitor implements ConfigPropertyVisitor {

	private void setPreference(final String prefsNodeName, final String key, final String value) {
		System.out.println(String.format("Setting preference: %s/%s=%s", prefsNodeName, key, value));
		final IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(prefsNodeName);
		prefs.put(key, value);
	}

	@Override
	public void visitIndentStyle(final ConfigProperty<IndentStyleOption> property) {
		final Boolean spacesForTabs = property.getValue().equals(IndentStyleOption.SPACE);
		setPreference("org.eclipse.ui.editors", "spacesForTabs", spacesForTabs.toString());
		setPreference("org.eclipse.jdt.core", "org.eclipse.jdt.core.formatter.tabulation.char", spacesForTabs ? "space" : "tab");
		setPreference("org.eclipse.wst.xml.core", "indentationChar", spacesForTabs ? "space" : "tab");
		setPreference("org.eclipse.ant.ui", "formatter_tab_char", Boolean.toString(!spacesForTabs));
	}

	@Override
	public void visitIndentSize(final ConfigProperty<Integer> property) {
		final String indentSizeString = property.getValue().toString();
		setPreference("org.eclipse.wst.xml.core", "indentationSize", indentSizeString);
		setPreference("org.eclipse.jdt.core", "org.eclipse.jdt.core.formatter.indentation.size", indentSizeString);
	}

	@Override
	public void visitTabWidth(final ConfigProperty<Integer> property) {
		final String tabWidth = property.getValue().toString();
		setPreference("org.eclipse.ui.editors", "tabWidth", tabWidth);
		setPreference("org.eclipse.jdt.core", "org.eclipse.jdt.core.formatter.tabulation.size", tabWidth);
		setPreference("org.eclipse.ant.ui", "formatter_tab_size", tabWidth);
	}

	@Override
	public void visitEndOfLine(final ConfigProperty<EndOfLineOption> property) {
		setPreference("org.eclipse.core.runtime", "line.separator", property.getValue().getEndOfLineString());
	}

	@Override
	public void visitCharset(final ConfigProperty<String> property) {
		setPreference("org.eclipse.core.resources", "encoding", property.getValue().toUpperCase());
	}

	@Override
	public void visitTrimTrailingWhitespace(final ConfigProperty<Boolean> property) {
		setPreference("org.eclipse.jdt.ui", "sp_cleanup.remove_trailing_whitespaces", property.getValue().toString());
	}

	@Override
	public void visitInsertFinalNewLine(final ConfigProperty<Boolean> property) {
		setPreference("org.eclipse.jdt.core", "org.eclipse.jdt.core.formatter.insert_new_line_at_end_of_file_if_missing",
				property.getValue().toString());
	}

}
