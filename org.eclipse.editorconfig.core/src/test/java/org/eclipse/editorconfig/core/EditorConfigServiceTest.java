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
package org.eclipse.editorconfig.core;

import static org.eclipse.editorconfig.core.ConfigPropertyType.CHARSET;
import static org.eclipse.editorconfig.core.ConfigPropertyType.END_OF_LINE;
import static org.eclipse.editorconfig.core.ConfigPropertyType.INDENT_SIZE;
import static org.eclipse.editorconfig.core.ConfigPropertyType.INDENT_STYLE;
import static org.eclipse.editorconfig.core.ConfigPropertyType.INSERT_FINAL_NEWLINE;
import static org.eclipse.editorconfig.core.ConfigPropertyType.TAB_WIDTH;
import static org.eclipse.editorconfig.core.ConfigPropertyType.TRIM_TRAILING_WHITESPACE;
import static org.eclipse.editorconfig.core.EndOfLineOption.CRLF;
import static org.eclipse.editorconfig.core.Matchers.configProperty;
import static org.eclipse.editorconfig.core.Matchers.configPropertyWithType;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.editorconfig.core.EditorConfig;
import org.editorconfig.core.EditorConfig.OutPair;
import org.editorconfig.core.EditorConfigException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EditorConfigServiceTest {

	@Mock
	private EditorConfig mockEditorConfig;

	private EditorConfigService editorConfigService;

	@Before
	public void setUp() {
		this.editorConfigService = new EditorConfigService(mockEditorConfig);
	}

	@Test
	public void getFileConfigShouldReturnFileConfigWithValuesMatchingEditorConfigProperties() throws Exception {
		when(mockEditorConfig.getProperties("path")).thenReturn(Arrays.asList(
					new OutPair("tab_width", "8"),
					new OutPair("charset", "utf8")
				));
		final EditorFileConfig fileConfig = editorConfigService.getEditorConfig("path");
		assertThat(fileConfig.getConfigProperties(), contains(configProperty(TAB_WIDTH, 8), configProperty(CHARSET, "utf8")));
	}

	@Test
	public void getFileConfigShouldReturnFileConfigWithGivenPath() throws Exception {
		when(mockEditorConfig.getProperties("path")).thenReturn(Arrays.<OutPair> asList());
		final EditorFileConfig fileConfig = editorConfigService.getEditorConfig("path");
		assertThat(fileConfig.getPath(), is("path"));
	}

	@Test
	public void getfileConfigShouldReturnFileConfigWithValuesSortedByType() throws Exception {
		when(mockEditorConfig.getProperties("path")).thenReturn(Arrays.asList(
					new OutPair("charset", "utf-8"),
					new OutPair("end_of_line", "crlf"),
					new OutPair("indent_size", "4"),
					new OutPair("indent_style", "tab"),
					new OutPair("insert_final_newline", "true"),
					new OutPair("tab_width", "4"),
					new OutPair("trim_trailing_whitespace", "true")
				));
		final EditorFileConfig fileConfig = editorConfigService.getEditorConfig("path");
		assertThat(fileConfig.getConfigProperties(), contains(
					configPropertyWithType(INDENT_STYLE),
					configPropertyWithType(INDENT_SIZE),
					configPropertyWithType(TAB_WIDTH),
					configPropertyWithType(END_OF_LINE),
					configPropertyWithType(CHARSET),
					configPropertyWithType(TRIM_TRAILING_WHITESPACE),
					configPropertyWithType(INSERT_FINAL_NEWLINE)
				));
	}

	@Test
	public void getFileConfigShouldReturnFileConfigWithEmptyValuesWhenEditorConfigHasNoProperties() throws Exception {
		when(mockEditorConfig.getProperties("path")).thenReturn(Collections.<OutPair> emptyList());
		final EditorFileConfig fileConfig = editorConfigService.getEditorConfig("path");
		assertThat(fileConfig.getConfigProperties(), is(empty()));
	}

	@Test(expected = RuntimeException.class)
	public void getFileConfigShouldThrowRuntimeExceptionWhenEditorConfigThrowsException() throws Exception {
		when(mockEditorConfig.getProperties("path")).thenThrow(EditorConfigException.class);
		editorConfigService.getEditorConfig("path");
	}
	
	@Test
	public void getFileConfigShouldIgnoreUnknownEditorConfigPropertyKeys() throws Exception {
		when(mockEditorConfig.getProperties("path")).thenReturn(Arrays.asList(
					new OutPair("tab_width", "8"),
					new OutPair("unkown_config", "val")
				));
		final EditorFileConfig fileConfig = editorConfigService.getEditorConfig("path");
		assertThat(fileConfig.getConfigProperties(), contains(configProperty(TAB_WIDTH, 8)));
	}
		
	@Test
	public void getFileConfigShouldIgnoreUnknownIndentStyleValue() throws Exception {
		when(mockEditorConfig.getProperties("path")).thenReturn(Arrays.asList(
					new OutPair("tab_width", "8"),
					new OutPair("indent_style", "unknown_val")
				));
		final EditorFileConfig fileConfig = editorConfigService.getEditorConfig("path");
		assertThat(fileConfig.getConfigProperties(), contains(configProperty(TAB_WIDTH, 8)));
	}
		
	@Test
	public void getFileConfigShouldIgnoreUnknownEndOfLineValue() throws Exception {
		when(mockEditorConfig.getProperties("path")).thenReturn(Arrays.asList(
					new OutPair("tab_width", "8"),
					new OutPair("end_of_line", "unknown_val")
				));
		final EditorFileConfig fileConfig = editorConfigService.getEditorConfig("path");
		assertThat(fileConfig.getConfigProperties(), contains(configProperty(TAB_WIDTH, 8)));
	}

	@Test
	public void getFileConfigShouldTreatUnknownInsertFinalNewlineValueAsFalse() throws Exception {
		when(mockEditorConfig.getProperties("path")).thenReturn(Arrays.asList(
					new OutPair("insert_final_newline", "unknown_val")
				));
		final EditorFileConfig fileConfig = editorConfigService.getEditorConfig("path");
		assertThat(fileConfig.getConfigProperties(), contains(configProperty(INSERT_FINAL_NEWLINE, false)));
	}
		
	@Test
	public void getFileConfigShouldTreatUnknownTrimTrailingWhitespaceValueAsFalse() throws Exception {
		when(mockEditorConfig.getProperties("path")).thenReturn(Arrays.asList(
					new OutPair("trim_trailing_whitespace", "unknown_val")
				));
		final EditorFileConfig fileConfig = editorConfigService.getEditorConfig("path");
		assertThat(fileConfig.getConfigProperties(), contains(configProperty(TRIM_TRAILING_WHITESPACE, false)));
	}
		
	@Test
	public void getFileConfigShouldIgnoreInvalidIndentSizeValue() throws Exception {
		when(mockEditorConfig.getProperties("path")).thenReturn(Arrays.asList(
					new OutPair("indent_size", "yes"),
					new OutPair("end_of_line", "crlf")
				));
		final EditorFileConfig fileConfig = editorConfigService.getEditorConfig("path");
		assertThat(fileConfig.getConfigProperties(), contains(configProperty(END_OF_LINE, CRLF)));
	}
		
	@Test
	public void getFileConfigShouldIgnoreZeroIndentSizeValue() throws Exception {
		when(mockEditorConfig.getProperties("path")).thenReturn(Arrays.asList(
					new OutPair("indent_size", "0"),
					new OutPair("end_of_line", "crlf")
				));
		final EditorFileConfig fileConfig = editorConfigService.getEditorConfig("path");
		assertThat(fileConfig.getConfigProperties(), contains(configProperty(END_OF_LINE, CRLF)));
	}

	@Test
	public void getFileConfigShouldIgnoreNegativeIndentSizeValue() throws Exception {
		when(mockEditorConfig.getProperties("path")).thenReturn(Arrays.asList(
					new OutPair("indent_size", "-1"),
					new OutPair("end_of_line", "crlf")
				));
		final EditorFileConfig fileConfig = editorConfigService.getEditorConfig("path");
		assertThat(fileConfig.getConfigProperties(), contains(configProperty(END_OF_LINE, CRLF)));
	}
		
	@Test
	public void getFileConfigShouldIgnoreInvalidTabWidthValue() throws Exception {
		when(mockEditorConfig.getProperties("path")).thenReturn(Arrays.asList(
					new OutPair("tab_width", "yes"),
					new OutPair("end_of_line", "crlf")
				));
		final EditorFileConfig fileConfig = editorConfigService.getEditorConfig("path");
		assertThat(fileConfig.getConfigProperties(), contains(configProperty(END_OF_LINE, CRLF)));
	}
		
	@Test
	public void getFileConfigShouldIgnoreZeroTabWidthValue() throws Exception {
		when(mockEditorConfig.getProperties("path")).thenReturn(Arrays.asList(
					new OutPair("tab_width", "0"),
					new OutPair("end_of_line", "crlf")
				));
		final EditorFileConfig fileConfig = editorConfigService.getEditorConfig("path");
		assertThat(fileConfig.getConfigProperties(), contains(configProperty(END_OF_LINE, CRLF)));
	}

	@Test
	public void getFileConfigShouldIgnoreNegativeTabWidthValue() throws Exception {
		when(mockEditorConfig.getProperties("path")).thenReturn(Arrays.asList(
					new OutPair("tab_width", "-1"),
					new OutPair("end_of_line", "crlf")
				));
		final EditorFileConfig fileConfig = editorConfigService.getEditorConfig("path");
		assertThat(fileConfig.getConfigProperties(), contains(configProperty(END_OF_LINE, CRLF)));
	}

}
