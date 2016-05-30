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
 *  Angelo Zerr <angelo.zerr@gmail.com> - initial .editorconfig editor  
 */
package org.eclipse.editorconfig.ui.text;

/**
 * Color keys used for syntax highlighting EditorConfig code and comments. A
 * <code>IColorManager</code> is responsible for mapping concrete colors to
 * these keys.
 * <p>
 * This interface declares static final fields only; it is not intended to be
 * implemented.
 * </p>
 *
 * @see org.eclipse.jdt.ui.text.IColorManager
 *
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IEditorConfigColorConstants {

	/**
	 * The color key for comments in a .editorconfig file (value
	 * <code>"ec_coloring_comment"</code>).
	 *
	 */
	String EDITOR_CONFIG_COLORING_COMMENT = "ec_coloring_comment"; //$NON-NLS-1$

	/**
	 * The color key for sections in a .editorconfig file (value
	 * <code>"ec_coloring_section"</code>).
	 *
	 */
	String EDITOR_CONFIG_COLORING_SECTION = "ec_coloring_section"; //$NON-NLS-1$

	/**
	 * The color key for keys in a .editorconfig file (value
	 * <code>"ec_coloring_key"</code>).
	 *
	 */
	String EDITOR_CONFIG_COLORING_PROPERTY_KEY = "ec_coloring_property_key"; //$NON-NLS-1$

	/**
	 * The color key for values in a .editorconfig file (value
	 * <code>"ec_coloring_value"</code>).
	 *
	 */
	String EDITOR_CONFIG_COLORING_PROPERTY_VALUE = "ec_coloring_property_value"; //$NON-NLS-1$

	/**
	 * The color key for assignment in a .editorconfig file. (value
	 * <code>"ec_coloring_assignment"</code>).
	 *
	 */
	String EDITOR_CONFIG_COLORING_ASSIGNMENT = "ec_coloring_assignment"; //$NON-NLS-1$

}
