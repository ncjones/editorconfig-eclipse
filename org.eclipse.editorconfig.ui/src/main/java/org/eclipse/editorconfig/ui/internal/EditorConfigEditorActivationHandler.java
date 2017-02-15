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

import org.eclipse.core.resources.IFile;
import org.eclipse.editorconfig.core.ConfigProperty;
import org.eclipse.editorconfig.core.EditorConfigService;
import org.eclipse.editorconfig.core.EditorFileConfig;

public class EditorConfigEditorActivationHandler implements EditorActivationHandler {

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
				configProperty.accept(new EditorConfigVisitor(fileEditorConfig));
			}
		}
	}

	private EditorFileConfig getEditorFileConfig(final IFile file) {
		final String path = file.getLocation().toString();
		return editorConfigService.getEditorConfig(path);
	}

}
