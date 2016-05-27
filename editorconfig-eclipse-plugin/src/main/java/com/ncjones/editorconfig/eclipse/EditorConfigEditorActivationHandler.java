/*
 * Copyright 2015 Nathan Jones
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
package com.ncjones.editorconfig.eclipse;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;

import com.ncjones.editorconfig.core.ConfigProperty;
import com.ncjones.editorconfig.core.ConfigPropertyVisitor;
import com.ncjones.editorconfig.core.EditorConfigService;
import com.ncjones.editorconfig.core.EditorFileConfig;
import com.ncjones.editorconfig.core.EndOfLineOption;
import com.ncjones.editorconfig.core.IndentStyleOption;

public class EditorConfigEditorActivationHandler implements EditorActivationHandler, ConfigPropertyVisitor {

	private final EditorConfigService editorConfigService;

	public EditorConfigEditorActivationHandler(final EditorConfigService editorConfigService) {
		this.editorConfigService = editorConfigService;
	}

	@Override
	public void editorActivated(final IFile editorFile) {
		if (editorFile != null) {
			final EditorFileConfig fileEditorConfig = getEditorFileConfig(editorFile);
			System.out.println("Editor activated: " + fileEditorConfig);
			for (final ConfigProperty<?> configProperty : fileEditorConfig.getConfigProperties()) {
				configProperty.accept(this);
			}
		}
	}

	private EditorFileConfig getEditorFileConfig(final IFile file) {
		final String path = file.getLocation().toString();
		return editorConfigService.getEditorConfig(path);
	}

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
		setPreference("org.eclipse.ui.editors", "tabWidth", indentSizeString);
		setPreference("org.eclipse.jdt.core", "org.eclipse.jdt.core.formatter.tabulation.size", indentSizeString);
		setPreference("org.eclipse.wst.xml.core", "indentationSize", indentSizeString);
		setPreference("org.eclipse.ant.ui", "formatter_tab_size", indentSizeString);
	}

	@Override
	public void visitTabWidth(final ConfigProperty<Integer> property) {
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
				property.getValue() ? "insert" : "do not insert");
	}

}
