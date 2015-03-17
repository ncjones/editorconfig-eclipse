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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ConfigPropertyMatcher extends TypeSafeMatcher<ConfigProperty> {
	private final Matcher<ConfigPropertyType> typeMatcher;
	private final Matcher<Object> valueMatcher;

	public ConfigPropertyMatcher(final Matcher<ConfigPropertyType> typeMatcher, final Matcher<Object> valueMatcher) {
		this.typeMatcher = typeMatcher;
		this.valueMatcher = valueMatcher;
	}

	@Override
	public void describeTo(final Description description) {
		description.appendText("ConfigProperty(");
		typeMatcher.describeTo(description);
		description.appendText(", ");
		valueMatcher.describeTo(description);
		description.appendText(")");
	}

	@Override
	protected void describeMismatchSafely(final ConfigProperty item, final Description mismatchDescription) {
		mismatchDescription.appendText("was ConfigProperty(").appendValue(item.getType()).appendText(", ")
				.appendValue(item.getValue()).appendText(")");
	}

	@Override
	protected boolean matchesSafely(final ConfigProperty item) {
		return typeMatcher.matches(item.getType()) && valueMatcher.matches(item.getValue());
	}

}
