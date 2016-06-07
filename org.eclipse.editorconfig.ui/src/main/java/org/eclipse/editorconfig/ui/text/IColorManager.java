/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Angelo Zerr <angelo.zerr@gmail.com> - copied from org.eclipse.wst.jsdt.ui.text.IColorManager.org.eclipse.wst.jsdt.ui.text.IColorManagerExtension
*                                            modified in order to process .editorconfig editor.
 *******************************************************************************/
package org.eclipse.editorconfig.ui.text;

import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

/**
 * Manages SWT color objects for the given color keys and given <code>RGB</code>
 * objects. Until the <code>dispose</code> method is called, the same color
 * object is returned for equal keys and equal <code>RGB</code> values and give
 * the ability to bind and un-bind colors.
 * 
 */
public interface IColorManager extends ISharedTextColors {

	/**
	 * Returns a color object for the given key. The color objects are
	 * remembered internally; the same color object is returned for equal keys.
	 *
	 * @param key
	 *            the color key
	 * @return the color object for the given key
	 */
	Color getColor(String key);

	/**
	 * Remembers the given color specification under the given key.
	 *
	 * @param key
	 *            the color key
	 * @param rgb
	 *            the color specification
	 * @throws java.lang.UnsupportedOperationException
	 *             if there is already a color specification remembered under
	 *             the given key
	 */
	void bindColor(String key, RGB rgb);

	/**
	 * Forgets the color specification remembered under the given key.
	 *
	 * @param key
	 *            the color key
	 */
	void unbindColor(String key);

}
