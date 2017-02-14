/*
 * Copyright 2017 Jackson Bailey
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
