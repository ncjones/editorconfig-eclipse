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

public class ConfigProperty<T> {

	private final ConfigPropertyType<T> type;

	private final T value;

	public ConfigProperty(final ConfigPropertyType<T> type, final T value) {
		this.type = type;
		this.value = value;
	}

	public ConfigPropertyType<T> getType() {
		return type;
	}

	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "ConfigProperty [type=" + type + ", value=" + value + "]";
	}

	public String getDisplayValue() {
		return type.getDisplayValue(this);
	}

	public void accept(final ConfigPropertyVisitor visitor) {
		type.accept(visitor, this);
	}
}
