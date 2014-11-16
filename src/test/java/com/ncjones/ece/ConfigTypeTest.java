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
package com.ncjones.ece;

import static com.ncjones.ece.ConfigType.CHARSET;
import static com.ncjones.ece.ConfigType.END_OF_LINE;
import static com.ncjones.ece.ConfigType.INDENT_SIZE;
import static com.ncjones.ece.ConfigType.INDENT_STYLE;
import static com.ncjones.ece.ConfigType.INSERT_FINAL_NEWLINE;
import static com.ncjones.ece.ConfigType.TAB_WIDTH;
import static com.ncjones.ece.ConfigType.TRIM_TRAILING_WHITESPACE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ConfigTypeTest {

	@Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(
				new Object[] { CHARSET, "Charset" },
				new Object[] { END_OF_LINE, "End of Line" },
				new Object[] { INDENT_SIZE, "Indent Size" },
				new Object[] { INDENT_STYLE, "Indent Style" },
				new Object[] { INSERT_FINAL_NEWLINE, "Insert Final Newline" },
				new Object[] { TAB_WIDTH, "Tab Width" },
				new Object[] { TRIM_TRAILING_WHITESPACE, "Trim Trailing Whitespace" }
			);
	}
	
	private final ConfigType configType;
	
	private final String label;

	public ConfigTypeTest(final ConfigType configType, final String label) {
		this.configType = configType;
		this.label = label;
	}

	@Test
	public void configTypeShouldHaveExpectedLabel() throws Exception {
		assertThat(configType.getDisplayLabel(), is(label));
	}

}
