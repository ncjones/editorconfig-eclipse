/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Angelo Zerr <angelo.zerr@gmail.com> - copied from org.eclipse.jdt.internal.ui.propertiesfileeditor.PropertyValueScanner
 *                                           modified in order to process .editorconfig editor.
 *******************************************************************************/

package org.eclipse.editorconfig.ui.internal.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.editorconfig.ui.PreferenceConstants;
import org.eclipse.editorconfig.ui.text.IColorManager;
import org.eclipse.editorconfig.ui.text.IEditorConfigPartitions;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPartitioningException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;

/**
 * A rule based property value scanner.
 *
 */
public final class PropertyValueScanner extends AbstractEditorConfigScanner {

	public class AssignmentDetector implements IWordDetector {

		/*
		 * @see IWordDetector#isWordStart
		 */
		@Override
		public boolean isWordStart(char c) {
			if ('=' != c && ':' != c || fDocument == null)
				return false;

			try {
				// check whether it is the first '=' in the logical line

				int i = fOffset - 2;
				while (Character.isWhitespace(fDocument.getChar(i))) {
					i--;
				}

				ITypedRegion partition = null;
				if (fDocument instanceof IDocumentExtension3)
					partition = ((IDocumentExtension3) fDocument)
							.getPartition(IEditorConfigPartitions.EDITOR_CONFIG_PARTITIONING, i, false);
				return partition != null && IDocument.DEFAULT_CONTENT_TYPE.equals(partition.getType());
			} catch (BadLocationException ex) {
				return false;
			} catch (BadPartitioningException e) {
				return false;
			}
		}

		/*
		 * @see IWordDetector#isWordPart
		 */
		@Override
		public boolean isWordPart(char c) {
			return false;
		}
	}

	private static String[] fgTokenProperties = { PreferenceConstants.EDITOR_CONFIG_COLORING_PROPERTY_VALUE,
			PreferenceConstants.EDITOR_CONFIG_COLORING_ASSIGNMENT };

	/**
	 * Creates a property value code scanner
	 *
	 * @param manager
	 *            the color manager
	 * @param store
	 *            the preference store
	 */
	public PropertyValueScanner(IColorManager manager, IPreferenceStore store) {
		super(manager, store);
		initialize();
	}

	/*
	 * @see
	 * org.eclipse.jdt.internal.ui.text.AbstractJavaScanner#getTokenProperties()
	 */
	@Override
	protected String[] getTokenProperties() {
		return fgTokenProperties;
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.text.AbstractJavaScanner#createRules()
	 */
	@Override
	protected List<IRule> createRules() {
		setDefaultReturnToken(getToken(PreferenceConstants.EDITOR_CONFIG_COLORING_PROPERTY_VALUE));
		List<IRule> rules = new ArrayList<IRule>();

		// Add word rule for assignment operator.
		IToken token = getToken(PreferenceConstants.EDITOR_CONFIG_COLORING_ASSIGNMENT);
		WordRule wordRule = new WordRule(new AssignmentDetector(), token);
		rules.add(wordRule);

		// Add generic whitespace rule.
		rules.add(new WhitespaceRule(new EditorConfigWhitespaceDetector()));

		return rules;
	}
}
