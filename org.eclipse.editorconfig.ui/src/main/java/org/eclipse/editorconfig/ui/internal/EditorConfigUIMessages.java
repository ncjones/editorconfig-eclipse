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
package org.eclipse.editorconfig.ui.internal;

import org.eclipse.osgi.util.NLS;

/**
 * EditorConfig UI messages.
 *
 */
public class EditorConfigUIMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.editorconfig.ui.internal.EditorConfigUIMessages";//$NON-NLS-1$

	public static String EditorConfigUIPlugin_internal_error;

	private EditorConfigUIMessages() {
		// Do not instantiate
	}

	static {
		NLS.initializeMessages(BUNDLE_NAME, EditorConfigUIMessages.class);
	}

}
