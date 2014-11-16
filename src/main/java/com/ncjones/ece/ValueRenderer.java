package com.ncjones.ece;

interface ValueRenderer {
	final ValueRenderer TO_STRING_VALUE_RENDERER = new ValueRenderer() {
		@Override
		public String renderValue(final Object value) {
			return value.toString();
		}
	};
	final ValueRenderer BOOLEAN_VALUE_RENDERER = new ValueRenderer() {
		@Override
		public String renderValue(final Object value) {
			return ((Boolean) value) ? "Yes" : "No";
		}
	};
	final ValueRenderer DISPLAYABLE_VALUE_RENDERER = new ValueRenderer() {
		@Override
		public String renderValue(final Object value) {
			return ((Displayable) value).getDisplayValue();
		}
	};

	String renderValue(Object value);
}