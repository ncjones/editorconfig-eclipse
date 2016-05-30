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

/**
 * Defines status codes relevant to the EditorConfig UI plug-in. When a Core
 * exception is thrown, it contain a status object describing the cause of the
 * exception. The status objects originating from the EditorConfig UI plug-in
 * use the codes defined in this interface.
 */
public interface IEditorConfigStatusConstants {

	public static final int INTERNAL_ERROR = 10001;

}
