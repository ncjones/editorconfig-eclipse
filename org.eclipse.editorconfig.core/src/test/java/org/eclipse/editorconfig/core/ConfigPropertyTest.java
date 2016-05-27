package org.eclipse.editorconfig.core;

import static org.eclipse.editorconfig.core.ConfigPropertyType.CHARSET;
import static org.eclipse.editorconfig.core.ConfigPropertyType.END_OF_LINE;
import static org.eclipse.editorconfig.core.ConfigPropertyType.INDENT_SIZE;
import static org.eclipse.editorconfig.core.ConfigPropertyType.INDENT_STYLE;
import static org.eclipse.editorconfig.core.ConfigPropertyType.INSERT_FINAL_NEWLINE;
import static org.eclipse.editorconfig.core.ConfigPropertyType.TAB_WIDTH;
import static org.eclipse.editorconfig.core.ConfigPropertyType.TRIM_TRAILING_WHITESPACE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ConfigPropertyTest {

	@Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(
				new Object[] { CHARSET, "utf-8", "utf-8", },
				new Object[] { END_OF_LINE, EndOfLineOption.CR, "Carriage Return" },
				new Object[] { END_OF_LINE, EndOfLineOption.LF, "Line Feed" },
				new Object[] { END_OF_LINE, EndOfLineOption.CRLF, "Carriage Return + Line Feed" },
				new Object[] { INDENT_SIZE, 8, "8" },
				new Object[] { INDENT_STYLE, IndentStyleOption.SPACE, "Space" },
				new Object[] { INDENT_STYLE, IndentStyleOption.TAB, "Tab" },
				new Object[] { INSERT_FINAL_NEWLINE, true, "Yes" },
				new Object[] { INSERT_FINAL_NEWLINE, false, "No" },
				new Object[] { TAB_WIDTH, 8, "8", },
				new Object[] { TRIM_TRAILING_WHITESPACE, true, "Yes" },
				new Object[] { TRIM_TRAILING_WHITESPACE, false, "No" }
			);
	}

	private final ConfigPropertyType type;

	private final Object value;

	private final String displayValue;

	public ConfigPropertyTest(final ConfigPropertyType type, final Object value, final String displayValue) {
		this.type = type;
		this.value = value;
		this.displayValue = displayValue;
	}

	@Test
	public void configPropertyShouldHaveExpectedDisplayValue() {
		assertThat(new ConfigProperty(type, value).getDisplayValue(), is(displayValue));
	}
}
