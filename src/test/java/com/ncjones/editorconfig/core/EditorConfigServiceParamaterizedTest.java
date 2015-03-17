/*
 * Copyright 2014 Nathan Jones
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

import static com.ncjones.editorconfig.core.ConfigPropertyType.CHARSET;
import static com.ncjones.editorconfig.core.ConfigPropertyType.END_OF_LINE;
import static com.ncjones.editorconfig.core.ConfigPropertyType.INDENT_SIZE;
import static com.ncjones.editorconfig.core.ConfigPropertyType.INDENT_STYLE;
import static com.ncjones.editorconfig.core.ConfigPropertyType.INSERT_FINAL_NEWLINE;
import static com.ncjones.editorconfig.core.ConfigPropertyType.TAB_WIDTH;
import static com.ncjones.editorconfig.core.ConfigPropertyType.TRIM_TRAILING_WHITESPACE;
import static com.ncjones.editorconfig.core.EndOfLineOption.CR;
import static com.ncjones.editorconfig.core.EndOfLineOption.CRLF;
import static com.ncjones.editorconfig.core.EndOfLineOption.LF;
import static com.ncjones.editorconfig.core.IndentStyleOption.SPACE;
import static com.ncjones.editorconfig.core.IndentStyleOption.TAB;
import static com.ncjones.editorconfig.core.Matchers.configProperty;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.editorconfig.core.EditorConfig.OutPair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

@RunWith(Parameterized.class)
public class EditorConfigServiceParamaterizedTest {
	
	@Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(
					new Object[] { "charset", "utf8", CHARSET, "utf8" },
					new Object[] { "CHARSET", "utf8", CHARSET, "utf8" },
					new Object[] { "end_of_line", "cr", END_OF_LINE, CR },
					new Object[] { "end_of_line", "lf", END_OF_LINE, LF },
					new Object[] { "end_of_line", "crlf", END_OF_LINE, CRLF },
					new Object[] { "END_OF_LINE", "CRLF", END_OF_LINE, CRLF },
					new Object[] { "indent_size", "8", INDENT_SIZE,  8 },
					new Object[] { "INDENT_SIZE", "8", INDENT_SIZE,  8 },
					new Object[] { "indent_style", "tab", INDENT_STYLE, TAB },
					new Object[] { "indent_style", "space", INDENT_STYLE, SPACE },
					new Object[] { "INDENT_STYLE", "SPACE", INDENT_STYLE, SPACE },
					new Object[] { "insert_final_newline", "true", INSERT_FINAL_NEWLINE, true },
					new Object[] { "insert_final_newline", "false", INSERT_FINAL_NEWLINE, false },
					new Object[] { "INSERT_FINAL_NEWLINE", "FALSE", INSERT_FINAL_NEWLINE, false },
					new Object[] { "tab_width", "10", TAB_WIDTH, 10 },
					new Object[] { "TAB_WIDTH", "10", TAB_WIDTH, 10 },
					new Object[] { "trim_trailing_whitespace", "true", TRIM_TRAILING_WHITESPACE, true },
					new Object[] { "trim_trailing_whitespace", "false", TRIM_TRAILING_WHITESPACE, false },
					new Object[] { "TRIM_TRAILING_WHITESPACE", "false", TRIM_TRAILING_WHITESPACE, false }
				);
	}

	private final String key;

	private final String rawValue;

	private final ConfigPropertyType type;

	private final Object parsedValue;

	public EditorConfigServiceParamaterizedTest(final String key, final String rawValue, final ConfigPropertyType type, final Object parsedValue) {
		this.key = key;
		this.rawValue = rawValue;
		this.type = type;
		this.parsedValue = parsedValue;
	}

	@Test
	public void test() throws Exception {
		final org.editorconfig.core.EditorConfig editorConfigRules = Mockito.mock(org.editorconfig.core.EditorConfig.class);
		final EditorConfigService editorConfigService = new EditorConfigService(editorConfigRules);
		when(editorConfigRules.getProperties("test/path")).thenReturn(asList(new OutPair(key, rawValue)));
		final EditorFileConfig fileConfig = editorConfigService.getEditorConfig("test/path");
		assertThat(fileConfig.getConfigProperties(), contains(configProperty(type, parsedValue)));
	}


}
