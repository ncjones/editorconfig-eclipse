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

interface ValueParser<T> {

	public static ValueParser<String> IDENTITY_VALUE_PARSER = new ValueParser<String>() {
		@Override
		public String parse(final String value) {
			return value;
		}
	};

	public static ValueParser<Boolean> BOOLEAN_VALUE_PARSER = new ValueParser<Boolean>() {
		@Override
		public Boolean parse(final String value) {
			return Boolean.valueOf(value.toLowerCase());
		}
	};

	public static ValueParser<Integer> POSITIVE_INT_VALUE_PARSER = new ValueParser<Integer>() {
		@Override
		public Integer parse(final String value) {
			try {
				final Integer integer = Integer.valueOf(value);
				return integer <= 0 ? null : integer;
			} catch (final NumberFormatException e) {
				return null;
			}
		}
	};

	T parse(String value);

}
