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
package org.eclipse.editorconfig.internal.ui;

import org.eclipse.editorconfig.core.EditorConfigService;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.editorconfig.core.EditorConfig;

public class EditorConfigEclipseStartup implements IStartup {

	final private EditorConfig editorConfig = new EditorConfig();

	final private EditorConfigService editorConfigService = new EditorConfigService(editorConfig);

	final private EditorConfigEditorActivationHandler editorActivationHandler = new EditorConfigEditorActivationHandler(editorConfigService);

	final private EditorActivationSelectionListener editorActivationSelectionListener = new EditorActivationSelectionListener(
			editorActivationHandler);

	@Override
	public void earlyStartup() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				final IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if (window != null) {
					window.getSelectionService().addSelectionListener(editorActivationSelectionListener);
				}
			}
		});
	}

}
