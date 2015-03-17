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
		final EditorFileConfig fileEditorConfig = getEditorFileConfig(editorFile);
		System.out.println("Editor activated: " + fileEditorConfig);
		for (final ConfigProperty<?> configProperty : fileEditorConfig.getConfigProperties()) {
			configProperty.accept(this);
		}
	}

	private EditorFileConfig getEditorFileConfig(final IFile file) {
		final String path = file.getLocation().toOSString();
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
	}

	@Override
	public void visitIndentSize(final ConfigProperty<Integer> property) {
		setPreference("org.eclipse.ui.editors", "tabWidth", property.getValue().toString());
	}

	@Override
	public void visitTabWidth(final ConfigProperty<Integer> property) {
		setPreference("org.eclipse.jdt.core", "org.eclipse.jdt.core.formatter.tabulation.size", property.getValue().toString());
	}

	@Override
	public void visitEndOfLine(final ConfigProperty<EndOfLineOption> property) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitCharset(final ConfigProperty<String> property) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitTrimTrailingWhitespace(final ConfigProperty<Boolean> property) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitInsertFinalNewLine(final ConfigProperty<Boolean> property) {
		// TODO Auto-generated method stub

	}

}
