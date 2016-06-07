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
 *  Angelo Zerr <angelo.zerr@gmail.com> - implement .editorconfig editor  
 */
package org.eclipse.editorconfig.ui.text;

/**
 * Definition of .editorconfig partitioning and its partitions.
 * 
 */
public interface IEditorConfigPartitions {

	/**
	 * The name of the .editorconfig partitioning.
	 */
	String EDITOR_CONFIG_PARTITIONING = "___ec_partitioning"; //$NON-NLS-1$

	/**
	 * The name of a comment partition.
	 */
	String COMMENT = "__ec_comment"; //$NON-NLS-1$

	/**
	 * The name of a section partition.
	 */
	String SECTION = "__ec_section"; //$NON-NLS-1$

	/**
	 * The name of a property value partition.
	 */
	String PROPERTY_VALUE = "__ec_property_value"; //$NON-NLS-1$

	/**
	 * Array with .editorconfig partitions.
	 */
	String[] PARTITIONS = new String[] { COMMENT, SECTION, PROPERTY_VALUE };
}
