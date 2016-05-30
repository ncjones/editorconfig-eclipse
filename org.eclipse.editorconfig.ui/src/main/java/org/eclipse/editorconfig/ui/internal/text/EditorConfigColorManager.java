/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Angelo Zerr <angelo.zerr@gmail.com> - copied from org.eclipse.wst.jsdt.internal.ui.text.JavaColorManager
 *                                           modified in order to process .editorconfig editor.     
 *******************************************************************************/
package org.eclipse.editorconfig.ui.internal.text;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.editorconfig.ui.text.IColorManager;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * EditorConfig color manager.
 */
public class EditorConfigColorManager implements IColorManager {

	protected Map<String, RGB> fKeyTable = new HashMap<String, RGB>(10);
	protected Map<Display, Map<RGB, Color>> fDisplayTable = new HashMap<Display, Map<RGB, Color>>(2);

	/**
	 * Flag which tells if the colors are automatically disposed when the
	 * current display gets disposed.
	 */
	private boolean fAutoDisposeOnDisplayDispose;

	/**
	 * Creates a new EditorConfig color manager which automatically disposes the
	 * allocated colors when the current display gets disposed.
	 */
	public EditorConfigColorManager() {
		this(true);
	}

	/**
	 * Creates a new EditorConfig color manager.
	 *
	 * @param autoDisposeOnDisplayDispose
	 *            if <code>true</code> the color manager automatically disposes
	 *            all managed colors when the current display gets disposed and
	 *            all calls to
	 *            {@link org.eclipse.jface.text.source.ISharedTextColors#dispose()}
	 *            are ignored.
	 *
	 */
	public EditorConfigColorManager(boolean autoDisposeOnDisplayDispose) {
		fAutoDisposeOnDisplayDispose = autoDisposeOnDisplayDispose;
	}

	public void dispose(Display display) {
		Map<RGB, Color> colorTable = fDisplayTable.get(display);
		if (colorTable != null) {
			Iterator<Color> e = colorTable.values().iterator();
			while (e.hasNext()) {
				Color color = e.next();
				if (color != null && !color.isDisposed())
					color.dispose();
			}
		}
	}

	/*
	 * @see IColorManager#getColor(RGB)
	 */
	@Override
	public Color getColor(RGB rgb) {

		if (rgb == null)
			return null;

		final Display display = Display.getCurrent();
		Map<RGB, Color> colorTable = fDisplayTable.get(display);
		if (colorTable == null) {
			colorTable = new HashMap<RGB, Color>(10);
			fDisplayTable.put(display, colorTable);
			if (fAutoDisposeOnDisplayDispose) {
				display.disposeExec(new Runnable() {
					@Override
					public void run() {
						dispose(display);
					}
				});
			}
		}

		Color color = colorTable.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			colorTable.put(rgb, color);
		}

		return color;
	}

	/*
	 * @see IColorManager#dispose
	 */
	@Override
	public void dispose() {
		if (!fAutoDisposeOnDisplayDispose)
			dispose(Display.getCurrent());
	}

	/*
	 * @see IColorManager#getColor(String)
	 */
	@Override
	public Color getColor(String key) {

		if (key == null)
			return null;

		RGB rgb = fKeyTable.get(key);
		return getColor(rgb);
	}

	/*
	 * @see IColorManagerExtension#bindColor(String, RGB)
	 */
	@Override
	public void bindColor(String key, RGB rgb) {
		Object value = fKeyTable.get(key);
		if (value != null)
			throw new UnsupportedOperationException();

		fKeyTable.put(key, rgb);
	}

	/*
	 * @see IColorManagerExtension#unbindColor(String)
	 */
	@Override
	public void unbindColor(String key) {
		fKeyTable.remove(key);
	}
}
