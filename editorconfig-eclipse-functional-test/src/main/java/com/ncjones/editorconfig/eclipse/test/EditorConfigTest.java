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
package com.ncjones.editorconfig.eclipse.test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(ParameterizedSWTBotRunner.class)
public class EditorConfigTest {

	@Parameters(name = "{0}")
	public static Collection<String[]> params() {
		return Arrays.asList(
			new String[] {"test.xml"},
			new String[] {"build.xml"},
			new String[] {"test.java"},
			new String[] {"test.txt"}
		);
	}

	@Parameter
	public String fileName;

	private SWTWorkbenchBot bot;
	private EditorConfigTestContext context;

	@Before
	public void setUp() throws Exception {
		bot = new SWTWorkbenchBot();
		context = new EditorConfigTestContext(bot, "editorconfig-test");
		bot.closeAllEditors();
	}

	@Test
	public void testIndentStyleSpace() throws Exception {
		context.editorConfig(
			"root = true",
			"[*]",
			"indent_style = space",
			"indent_size = 2"
		);
		context.editFile(fileName, "\t");
		assertThat(context.fileContents(fileName), equalTo("  "));
	}

	@Test
	public void testIndentStyleTab() throws Exception {
		context.editorConfig(
			"root = true",
			"[*]",
			"indent_style = tab"
		);
		context.editFile(fileName, "\t");
		assertThat(context.fileContents(fileName), equalTo("\t"));
	}

	@Test
	public void testEndOfLineCr() {
		context.editorConfig(
			"root = true",
			"[*]",
			"end_of_line = cr"
		);
		context.editFile(fileName, "a\nb");
		assertThat(context.fileBytes(fileName), equalTo(new byte[] { 'a', '\r', 'b' }));
	}

	@Test
	public void testEndOfLineLf() {
		context.editorConfig(
			"root = true",
			"[*]",
			"end_of_line = lf"
		);
		context.editFile(fileName, "a\nb");
		assertThat(context.fileBytes(fileName), equalTo(new byte[] { 'a', '\n', 'b' }));
	}

	@Test
	public void testEndOfLineCrLf() {
		context.editorConfig(
			"root = true",
			"[*]",
			"end_of_line = crlf"
		);
		context.editFile(fileName, "a\nb");
		assertThat(context.fileBytes(fileName), equalTo(new byte[] { 'a', '\r', '\n', 'b' }));
	}

}
