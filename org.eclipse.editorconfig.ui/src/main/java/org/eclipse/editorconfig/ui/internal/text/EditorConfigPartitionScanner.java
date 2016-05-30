/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Angelo Zerr <angelo.zerr@gmail.com> - copied from org.eclipse.jdt.internal.ui.propertiesfileeditor.PropertiesFilePartitionScanner
 *                                           modified in order to process .editorconfig editor.     
 *******************************************************************************/
package org.eclipse.editorconfig.ui.internal.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.editorconfig.ui.text.IEditorConfigPartitions;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;

/**
 * This scanner recognizes the comments, sections, property keys and property
 * values for .editorconfig content.
 */
public class EditorConfigPartitionScanner extends RuleBasedPartitionScanner implements IEditorConfigPartitions {

	/**
	 * Detector for empty comments.
	 */
	static class EmptyCommentDetector implements IWordDetector {

		@Override
		public boolean isWordStart(char c) {
			return (c == '#');
		}

		@Override
		public boolean isWordPart(char c) {
			return (c == '#');
		}
	}

	/**
	 * Word rule for empty comments.
	 */
	static class EmptyCommentRule extends WordRule implements IPredicateRule {

		private IToken fSuccessToken;

		/**
		 * Constructor for EmptyCommentRule.
		 * 
		 * @param successToken
		 *            the success token
		 */
		public EmptyCommentRule(IToken successToken) {
			super(new EmptyCommentDetector());
			fSuccessToken = successToken;
			addWord("#", fSuccessToken); //$NON-NLS-1$
		}

		@Override
		public IToken evaluate(ICharacterScanner scanner, boolean resume) {
			return evaluate(scanner);
		}

		@Override
		public IToken getSuccessToken() {
			return fSuccessToken;
		}
	}

	/**
	 * Creates the partitioner and sets up the appropriate rules.
	 */
	public EditorConfigPartitionScanner() {
		super();

		IToken comment = new Token(COMMENT);
		IToken sectionName = new Token(SECTION);
		IToken propertyValue = new Token(PROPERTY_VALUE);
		IToken key = new Token(IDocument.DEFAULT_CONTENT_TYPE);

		List<IPredicateRule> rules = new ArrayList<IPredicateRule>();

		// Add rule for leading white space.
		rules.add(new LeadingWhitespacePredicateRule(key, "\t")); //$NON-NLS-1$
		rules.add(new LeadingWhitespacePredicateRule(key, " ")); //$NON-NLS-1$

		// Add rules for comments.
		rules.add(new EndOfLineRule("#", comment, (char) 0, true)); //$NON-NLS-1$
		// rules.add(new EndOfLineRule("!", comment, (char) 0, true));
		// //$NON-NLS-1$

		// Add rules for sections.
		rules.add(new SingleLineRule("[", "]", sectionName, '\\', true, true)); //$NON-NLS-1$

		// Add rules for property values.
		rules.add(new SingleLineRule("=", null, propertyValue, '\\', true, true)); //$NON-NLS-1$
		rules.add(new SingleLineRule(":", null, propertyValue, '\\', true, true)); //$NON-NLS-1$
		rules.add(new SingleLineRule(" ", null, propertyValue, '\\', true, true)); //$NON-NLS-1$
		rules.add(new SingleLineRule("\t", null, propertyValue, '\\', true, true)); //$NON-NLS-1$

		// Add special case word rule.
		EmptyCommentRule wordRule = new EmptyCommentRule(comment);
		rules.add(wordRule);

		IPredicateRule[] result = new IPredicateRule[rules.size()];
		rules.toArray(result);
		setPredicateRules(result);
	}
}
