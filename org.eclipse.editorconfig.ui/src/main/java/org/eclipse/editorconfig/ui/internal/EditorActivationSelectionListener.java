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
package org.eclipse.editorconfig.ui.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

public class EditorActivationSelectionListener implements ISelectionListener {

	private final EditorActivationHandler editorActivationListener;

	private IEditorPart currentEditorPart;

	public EditorActivationSelectionListener(final EditorActivationHandler editorActivationHandler) {
		this.editorActivationListener = editorActivationHandler;
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		if (part != currentEditorPart && part instanceof IEditorPart) {
			currentEditorPart = (IEditorPart) part;
			final IFile file = (IFile) currentEditorPart.getEditorInput().getAdapter(IFile.class);
			editorActivationListener.editorActivated(file);
		}
	}
}
