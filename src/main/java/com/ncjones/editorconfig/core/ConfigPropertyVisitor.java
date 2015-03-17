/*
 * Copyright 2015 Nathan Jones
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
 */
package com.ncjones.editorconfig.core;

public interface ConfigPropertyVisitor {

	void visitIndentStyle(ConfigProperty<IndentStyleOption> property);

	void visitIndentSize(ConfigProperty<Integer> property);

	void visitTabWidth(ConfigProperty<Integer> property);

	void visitEndOfLine(ConfigProperty<EndOfLineOption> property);

	void visitCharset(ConfigProperty<String> property);

	void visitTrimTrailingWhitespace(ConfigProperty<Boolean> property);

	void visitInsertFinalNewLine(ConfigProperty<Boolean> property);

}
