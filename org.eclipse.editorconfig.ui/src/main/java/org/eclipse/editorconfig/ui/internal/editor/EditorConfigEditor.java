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
package org.eclipse.editorconfig.ui.internal.editor;

import org.eclipse.editorconfig.ui.internal.EditorConfigUIPlugin;
import org.eclipse.editorconfig.ui.internal.text.EditorConfigSourceViewerConfiguration;
import org.eclipse.editorconfig.ui.text.EditorConfigTextTools;
import org.eclipse.editorconfig.ui.text.IEditorConfigPartitions;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.editors.text.TextEditor;

/**
 * EditorConfig editor supports '.editorconfig':
 * 
 * <ul>
 * <li>syntax coloration</li>
 * </ul>
 *
 */
public class EditorConfigEditor extends TextEditor {

	@Override
	protected void initializeEditor() {
		setDocumentProvider(EditorConfigUIPlugin.getDefault().getEditorConfigDocumentProvider());
		IPreferenceStore store = EditorConfigUIPlugin.getDefault().getCombinedPreferenceStore();
		setPreferenceStore(store);
		EditorConfigTextTools textTools = EditorConfigUIPlugin.getDefault().getEditorConfigTextTools();
		setSourceViewerConfiguration(new EditorConfigSourceViewerConfiguration(textTools.getColorManager(), store, this,
				IEditorConfigPartitions.EDITOR_CONFIG_PARTITIONING));
	}

	@Override
	protected void handlePreferenceStoreChanged(PropertyChangeEvent event) {
		try {
			ISourceViewer sourceViewer = getSourceViewer();
			if (sourceViewer == null) {
				return;
			}
			((EditorConfigSourceViewerConfiguration) getSourceViewerConfiguration()).handlePropertyChangeEvent(event);
		} finally {
			super.handlePreferenceStoreChanged(event);
		}
	}

	@Override
	protected boolean affectsTextPresentation(PropertyChangeEvent event) {
		return ((EditorConfigSourceViewerConfiguration) getSourceViewerConfiguration()).affectsTextPresentation(event)
				|| super.affectsTextPresentation(event);
	}
}
