package org.eclipse.editorconfig.core;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.editorconfig.core.ConfigPropertyType.IndentSize;
import org.eclipse.editorconfig.core.ConfigPropertyType.IndentStyle;
import org.junit.Test;

public class EditorFileConfigTest {

	@Test
	public void getPropertyReturnsProperty() {

		final ConfigProperty<IndentStyleOption> indentStyle =
				new ConfigProperty<IndentStyleOption>(new IndentStyle(), IndentStyleOption.TAB);
		final ConfigProperty<Integer> indentSize = new ConfigProperty<Integer>(new IndentSize(), 2);

		@SuppressWarnings("rawtypes")
		Set<ConfigProperty> properties = new HashSet<ConfigProperty>();
		properties.add(indentStyle);
		properties.add(indentSize);

		EditorFileConfig config = new EditorFileConfig("", properties);

		assertSame(indentStyle, config.getConfigProperty("INDENT_STYLE"));
		assertSame(indentSize, config.getConfigProperty("INDENT_SIZE"));
		assertNull(config.getConfigProperty("TAB_WIDTH"));
	}
}
