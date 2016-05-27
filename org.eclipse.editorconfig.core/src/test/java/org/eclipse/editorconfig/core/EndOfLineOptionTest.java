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
package org.eclipse.editorconfig.core;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class EndOfLineOptionTest {

	@Test
	public void testCr() {
		assertThat(EndOfLineOption.CR.getEndOfLineString(), is("\r"));
	}

	@Test
	public void testLf() {
		assertThat(EndOfLineOption.LF.getEndOfLineString(), is("\n"));
	}

	@Test
	public void testCrLf() {
		assertThat(EndOfLineOption.CRLF.getEndOfLineString(), is("\r\n"));
	}

}
