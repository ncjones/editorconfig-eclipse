package com.ncjones.editorconfig.core;

import static com.ncjones.editorconfig.core.ConfigType.CHARSET;
import static com.ncjones.editorconfig.core.ConfigType.END_OF_LINE;
import static com.ncjones.editorconfig.core.ConfigType.INDENT_SIZE;
import static com.ncjones.editorconfig.core.ConfigType.INDENT_STYLE;
import static com.ncjones.editorconfig.core.ConfigType.INSERT_FINAL_NEWLINE;
import static com.ncjones.editorconfig.core.ConfigType.TAB_WIDTH;
import static com.ncjones.editorconfig.core.ConfigType.TRIM_TRAILING_WHITESPACE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.ncjones.editorconfig.core.ConfigType;
import com.ncjones.editorconfig.core.ConfigValue;
import com.ncjones.editorconfig.core.EndOfLineOption;
import com.ncjones.editorconfig.core.IndentStyleOption;

@RunWith(Parameterized.class)
public class ConfigValueTest {

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

	private final ConfigType type;

	private final Object value;

	private final String displayValue;

	public ConfigValueTest(final ConfigType type, final Object value, final String displayValue) {
		this.type = type;
		this.value = value;
		this.displayValue = displayValue;
	}

	@Test
	public void configValueShouldHaveExpectedDisplayValue() {
		assertThat(new ConfigValue(type, value).getDisplayValue(), is(displayValue));
	}
}
