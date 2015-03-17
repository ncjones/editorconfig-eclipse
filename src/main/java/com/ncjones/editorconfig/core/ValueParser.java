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
package com.ncjones.editorconfig.core;

interface ValueParser {

	public static ValueParser IDENTITY_VALUE_PARSER = new ValueParser() {
		@Override
		public Object parse(final String value) {
			return value;
		}
	};

	public static ValueParser BOOLEAN_VALUE_PARSER = new ValueParser() {
		@Override
		public Object parse(final String value) {
			return Boolean.valueOf(value.toLowerCase());
		}
	};

	public static ValueParser POSITIVE_INT_VALUE_PARSER = new ValueParser() {
		@Override
		public Object parse(final String value) {
			try {
				final Integer integer = Integer.valueOf(value);
				return integer <= 0 ? null : integer;
			} catch (final NumberFormatException e) {
				return null;
			}
		}
	};

	Object parse(String value);

}
