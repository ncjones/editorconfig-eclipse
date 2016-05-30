/*******************************************************************************
 * Copyright (c) 2000, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Brock Janiczak <brockj@tpg.com.au> - [nls tooling] Properties file editor should have "toggle comment" action - https://bugs.eclipse.org/bugs/show_bug.cgi?id=192045
 *     Angelo Zerr <angelo.zerr@gmail.com> - copied fromorg.eclipse.jdt.internal.ui.propertiesfileeditor.PropertiesFileSourceViewerConfiguration
 *                                           modified in order to process .editorconfig editor.     
 *******************************************************************************/
package org.eclipse.editorconfig.ui.internal.text;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.editorconfig.ui.PreferenceConstants;
import org.eclipse.editorconfig.ui.text.EditorConfigPresentationReconciler;
import org.eclipse.editorconfig.ui.text.EditorConfigTextTools;
import org.eclipse.editorconfig.ui.text.IColorManager;
import org.eclipse.editorconfig.ui.text.IEditorConfigPartitions;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.spelling.SpellingReconcileStrategy;
import org.eclipse.ui.texteditor.spelling.SpellingService;

/**
 * Configuration for a source viewer which shows .editorconfig code.
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 *
 * @noextend This class is not intended to be subclassed by clients.
 */
public class EditorConfigSourceViewerConfiguration extends TextSourceViewerConfiguration {

	/**
	 * The text editor.
	 */
	private ITextEditor textEditor;

	/**
	 * The document partitioning.
	 */
	private String documentPartitioning;

	/**
	 * The comment scanner.
	 */
	private AbstractEditorConfigScanner commentScanner;

	/**
	 * The section scanner.
	 */
	private AbstractEditorConfigScanner sectionScanner;

	/**
	 * The property key scanner.
	 */
	private AbstractEditorConfigScanner propertyKeyScanner;

	/**
	 * The property value scanner.
	 */
	private AbstractEditorConfigScanner propertyValueScanner;

	/**
	 * The color manager.
	 */
	private IColorManager colorManager;

	public EditorConfigSourceViewerConfiguration(IColorManager colorManager, IPreferenceStore preferenceStore,
			ITextEditor editor, String partitioning) {
		super(preferenceStore);
		this.colorManager = colorManager;
		this.textEditor = editor;
		this.documentPartitioning = partitioning;
		initializeScanners();
	}

	/**
	 * Returns the property key scanner for this configuration.
	 *
	 * @return the property key scanner
	 */
	protected RuleBasedScanner getPropertyKeyScanner() {
		return propertyKeyScanner;
	}

	/**
	 * Returns the comment scanner for this configuration.
	 *
	 * @return the comment scanner
	 */
	protected RuleBasedScanner getCommentScanner() {
		return commentScanner;
	}

	/**
	 * Returns the section scanner for this configuration.
	 *
	 * @return the section scanner
	 */
	protected RuleBasedScanner getSectionNameScanner() {
		return sectionScanner;
	}

	/**
	 * Returns the property value scanner for this configuration.
	 *
	 * @return the property value scanner
	 */
	protected RuleBasedScanner getPropertyValueScanner() {
		return propertyValueScanner;
	}

	/**
	 * Returns the color manager for this configuration.
	 *
	 * @return the color manager
	 */
	protected IColorManager getColorManager() {
		return colorManager;
	}

	/**
	 * Returns the editor in which the configured viewer(s) will reside.
	 *
	 * @return the enclosing editor
	 */
	protected ITextEditor getEditor() {
		return textEditor;
	}

	/**
	 * Initializes the scanners.
	 */
	private void initializeScanners() {
		propertyKeyScanner = new SingleTokenEditorConfigScanner(getColorManager(), fPreferenceStore,
				PreferenceConstants.EDITOR_CONFIG_COLORING_PROPERTY_KEY);
		propertyValueScanner = new PropertyValueScanner(getColorManager(), fPreferenceStore);
		commentScanner = new SingleTokenEditorConfigScanner(getColorManager(), fPreferenceStore,
				PreferenceConstants.EDITOR_CONFIG_COLORING_COMMENT);
		sectionScanner = new SectionScanner(getColorManager(), fPreferenceStore);
	}

	/*
	 * @see SourceViewerConfiguration#getPresentationReconciler(ISourceViewer)
	 */
	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {

		PresentationReconciler reconciler = new EditorConfigPresentationReconciler();
		reconciler.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));

		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getPropertyKeyScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		dr = new DefaultDamagerRepairer(getCommentScanner());
		reconciler.setDamager(dr, IEditorConfigPartitions.COMMENT);
		reconciler.setRepairer(dr, IEditorConfigPartitions.COMMENT);

		dr = new DefaultDamagerRepairer(getSectionNameScanner());
		reconciler.setDamager(dr, IEditorConfigPartitions.SECTION);
		reconciler.setRepairer(dr, IEditorConfigPartitions.SECTION);

		dr = new DefaultDamagerRepairer(getPropertyValueScanner());
		reconciler.setDamager(dr, IEditorConfigPartitions.PROPERTY_VALUE);
		reconciler.setRepairer(dr, IEditorConfigPartitions.PROPERTY_VALUE);

		return reconciler;
	}

	/*
	 * @see SourceViewerConfiguration#getConfiguredContentTypes(ISourceViewer)
	 */
	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		int length = IEditorConfigPartitions.PARTITIONS.length;
		String[] contentTypes = new String[length + 1];
		contentTypes[0] = IDocument.DEFAULT_CONTENT_TYPE;
		for (int i = 0; i < length; i++)
			contentTypes[i + 1] = IEditorConfigPartitions.PARTITIONS[i];

		return contentTypes;
	}

	/*
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#
	 * getConfiguredDocumentPartitioning(org.eclipse.jface.text.source.
	 * ISourceViewer)
	 */
	@Override
	public String getConfiguredDocumentPartitioning(ISourceViewer sourceViewer) {
		if (documentPartitioning != null) {
			return documentPartitioning;
		}
		return super.getConfiguredDocumentPartitioning(sourceViewer);
	}

	/**
	 * Determines whether the preference change encoded by the given event
	 * changes the behavior of one of its contained components.
	 *
	 * @param event
	 *            the event to be investigated
	 * @return <code>true</code> if event causes a behavioral change
	 */
	public boolean affectsTextPresentation(PropertyChangeEvent event) {
		return propertyKeyScanner.affectsBehavior(event) || commentScanner.affectsBehavior(event)
				|| sectionScanner.affectsBehavior(event) || propertyValueScanner.affectsBehavior(event);
	}

	/**
	 * Adapts the behavior of the contained components to the change encoded in
	 * the given event.
	 *
	 * @param event
	 *            the event to which to adapt
	 * @see EditorConfigSourceViewerConfiguration#EditorConfigSourceViewerConfiguration(IColorManager,
	 *      IPreferenceStore, ITextEditor, String)
	 */
	public void handlePropertyChangeEvent(PropertyChangeEvent event) {
		if (propertyKeyScanner.affectsBehavior(event))
			propertyKeyScanner.adaptToPreferenceChange(event);
		if (commentScanner.affectsBehavior(event))
			commentScanner.adaptToPreferenceChange(event);
		if (sectionScanner.affectsBehavior(event))
			sectionScanner.adaptToPreferenceChange(event);
		if (propertyValueScanner.affectsBehavior(event))
			propertyValueScanner.adaptToPreferenceChange(event);
	}

	@Override
	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		if (!EditorsUI.getPreferenceStore().getBoolean(SpellingService.PREFERENCE_SPELLING_ENABLED))
			return null;

		IReconcilingStrategy strategy = new SpellingReconcileStrategy(sourceViewer, EditorsUI.getSpellingService()) {
			@Override
			protected IContentType getContentType() {
				return EditorConfigTextTools.EDITORCONFIG_CONTENT_TYPE;
			}
		};

		MonoReconciler reconciler = new MonoReconciler(strategy, false);
		reconciler.setDelay(500);
		return reconciler;
	}

	@Override
	public String[] getDefaultPrefixes(ISourceViewer sourceViewer, String contentType) {
		return new String[] { "#", "" }; //$NON-NLS-1$ //$NON-NLS-2$
	}

}
