/*
 * Copyright 2014-2016 Angelo Zerr
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
 * 
 */
package org.eclipse.editorconfig.core.internal.contributions;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.editorconfig.core.ConfigProperty;

/**
 * Preferences updater is used to apply properties declared in an .editorconfig
 * file to a given file opened in an editor. It converts .editorconfig
 * properties to the Eclipse preferences used by the editor and updates the
 * Eclipse preferences {@link IEclipsePreferences}:
 * 
 * <ul>
 * <li>global preferences</li>
 * <li>or project preferences (if the editor support it)</li>
 * </ul>
 *
 */
public class PreferencesUpdater {

	private static final String FROM_ATTR = "from";
	private static final String TO_ATTR = "to";
	private static final String TYPE_ATTR = "type";
	private static final String CASE_ATTR = "case";
	private static final String VALUE_ATTR = "value";
	private static final String PREFERENCE_NAME_ATTR = "preferenceName";
	private static final String PROPERTY_ATTR = "property";
	private static final String SCOPE_ATTR = "scope";

	private final Map<String, Property> properties;
	private boolean commons;
	private Scope scope;

	/**
	 * Eclipse Preferences Scope
	 *
	 */
	private enum Scope {
		global, project, file;

		public static Scope getScope(String id) {
			try {
				return Scope.valueOf(id);
			} catch (Throwable e) {
				return Scope.global;
			}
		}
	}

	/**
	 * Eclipse preference value type.
	 *
	 */
	private enum ValueType {
		StringType("string"), BooleanType("boolean"), IntegerType("integer");

		private String id;

		private ValueType(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

		public static ValueType getType(String id) {
			ValueType[] types = values();
			for (ValueType type : types) {
				if (type.getId().equals(id)) {
					return type;
				}
			}
			return ValueType.StringType;
		}
	}

	/**
	 * Case type.
	 *
	 */
	private enum CaseType {

		none, upper, lower;

		public static CaseType getType(String id) {
			try {
				return CaseType.valueOf(id);
			} catch (Throwable e) {
				return none;
			}
		}
	}

	/**
	 * Mapping between an .editorconfig property value and an Eclipse preference
	 * value.
	 *
	 */
	private class Value {

		private final String from;
		private final String to;

		public Value(IConfigurationElement propertyElt) {
			this.from = propertyElt.getAttribute(FROM_ATTR);
			// get the to by keeping \n and \r
			this.to = propertyElt.getAttribute(TO_ATTR).replaceAll("\\\\n", "\n").replaceAll("\\\\r", "\r");
		}
	}

	/**
	 * Rule to convert an .editorconfig property value to an Eclipse preferences
	 * {@link IEclipsePreferences}.
	 *
	 */
	private class Property {

		private final String from;
		private final String to;
		private final String preferenceName;
		private final ValueType type;
		private final Map<String, Value> values = new HashMap<String, Value>();
		private CaseType caseType;

		public Property(IConfigurationElement propertyElt) {
			this.from = propertyElt.getAttribute(FROM_ATTR);
			this.to = propertyElt.getAttribute(TO_ATTR);
			this.preferenceName = propertyElt.getAttribute(PREFERENCE_NAME_ATTR);
			this.type = ValueType.getType(propertyElt.getAttribute(TYPE_ATTR));
			this.caseType = CaseType.getType(propertyElt.getAttribute(CASE_ATTR));
			// Loop for values mapping
			IConfigurationElement[] values = propertyElt.getChildren(VALUE_ATTR);
			for (int i = 0; i < values.length; i++) {
				Value value = new Value(values[i]);
				if (value.from != null) {
					this.values.put(value.from.toUpperCase(), value);
				}
			}
		}

		/**
		 * Returns the Eclipse property name to update in the
		 * {@link IEclipsePreferences}.
		 * 
		 * @return
		 */
		public String getTo() {
			return to;
		}

		/**
		 * Returns the preference name to use to retrieve the well
		 * {@link IEclipsePreferences} to update.
		 * 
		 * @return
		 */
		public String getPreferenceName() {
			return preferenceName;
		}

		/**
		 * Returns the converted value to use to update
		 * {@link IEclipsePreferences}.
		 * 
		 * @param value
		 * @return
		 */
		public Object getValue(Object value) {
			Object v = getMappingValue(value);
			switch (type) {
			case StringType:
				switch (caseType) {
				case lower:
					return v.toString().toLowerCase();
				case upper:
					return v.toString().toUpperCase();
				default:
					return v.toString();
				}
			case BooleanType:
				if (v instanceof Boolean) {
					return v;
				}
				return Boolean.valueOf(v.toString());
			case IntegerType:
				if (v instanceof Integer) {
					return v;
				}
				return Integer.parseInt(v.toString());
			default:
				return v;
			}
		}

		private Object getMappingValue(Object value) {
			if (values.isEmpty()) {
				return value;
			}
			Value mapping = values.get(value.toString().toUpperCase());
			return mapping != null ? mapping.to : value;
		}

		public ValueType getType() {
			return type;
		}
	}

	/**
	 * PreferencesUpdater constructor initialized with extension point.
	 * 
	 * @param element
	 * @param commons
	 */
	PreferencesUpdater(IConfigurationElement element, boolean commons) {
		// loop for property
		this.properties = createProperties(element.getChildren(PROPERTY_ATTR));
		// get scope
		this.scope = Scope.getScope(element.getAttribute(SCOPE_ATTR));
		this.commons = commons;
	}

	private Map<String, Property> createProperties(IConfigurationElement[] children) {
		Map<String, Property> properties = new HashMap<String, PreferencesUpdater.Property>();
		for (int i = 0; i < children.length; i++) {
			Property property = new Property(children[i]);
			if (property.from != null) {
				properties.put(property.from.toUpperCase(), property);
			}
		}
		return properties;
	}

	/**
	 * Apply the given .editorconfig property to the given file.
	 * 
	 * @param editorFile
	 * @param configProperty
	 */
	public void applyConfig(IFile editorFile, ConfigProperty<?> configProperty) {
		Property property = getProperty(configProperty.getType().getName());
		if (property == null) {
			return;
		}
		String propertyName = property.getTo();
		Object propertyValue = property.getValue(configProperty.getValue());
		String preferenceName = property.getPreferenceName();
		ValueType type = property.getType();
		updateScopePreference(editorFile, propertyName, propertyValue, type, preferenceName);
	}

	private Property getProperty(String name) {
		Property property = properties.get(name);
		if (property != null) {
			return property;
		}
		if (commons) {
			return null;
		}
		return PreferencesUpdatersContributionManager.getInstance().getUpdater("*").getProperty(name);
	}

	private void updateScopePreference(IFile file, String propertyName, Object propertyValue, ValueType type,
			String preferenceName) {
		IEclipsePreferences preferences = getScopePreferences(file, preferenceName);
		switch (type) {
		case StringType:
			preferences.put(propertyName, propertyValue.toString());
			break;
		case BooleanType:
			preferences.putBoolean(propertyName, (Boolean) propertyValue);
			break;
		case IntegerType:
			preferences.putInt(propertyName, (Integer) propertyValue);
			break;
		}
	}

	private IEclipsePreferences getScopePreferences(IFile file, String preferenceName) {
		switch (scope) {
		case project:
			if (file != null) {
				ProjectScope scope = new ProjectScope(file.getProject());
				return scope.getNode(preferenceName);
			} else {
				return InstanceScope.INSTANCE.getNode(preferenceName);
			}
		case file:
			// TODO: create FileScope
		default:
			return InstanceScope.INSTANCE.getNode(preferenceName);
		}
	}
}
