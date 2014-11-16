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

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

public class Matchers {

	public static ConfigValueMatcher configValue(final ConfigType type, final Object value) {
		return new ConfigValueMatcher(is(type), is(value));
	}

	public static ConfigValueMatcher configValueWithType(final ConfigType type) {
		return new ConfigValueMatcher(is(type), anything());
	}
	
}
