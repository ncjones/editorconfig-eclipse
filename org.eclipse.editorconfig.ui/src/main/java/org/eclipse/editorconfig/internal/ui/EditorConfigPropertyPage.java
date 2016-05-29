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
package org.eclipse.editorconfig.internal.ui;

import org.eclipse.core.resources.IResource;
import org.eclipse.editorconfig.core.ConfigProperty;
import org.eclipse.editorconfig.core.EditorConfigService;
import org.eclipse.editorconfig.core.EditorFileConfig;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;
import org.editorconfig.core.EditorConfig;

public class EditorConfigPropertyPage extends PropertyPage {

	private final EditorConfigService editorConfigService = new EditorConfigService(new EditorConfig());

	@Override
	protected Control createContents(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		final GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);
		createConfigProperties(composite, getFileEditorConfig());
		return composite;
	}

	private EditorFileConfig getFileEditorConfig() {
		final String path = getResource().getWorkspace().getRoot().getFile(getResource().getFullPath()).getLocation().toOSString();
		return editorConfigService.getEditorConfig(path);
	}

	private IResource getResource() {
		return (IResource) getElement();
	}

	private void createConfigProperties(final Composite parent, final EditorFileConfig fileEditorConfig) {
		final Composite composite = createPropertiesComposite(parent);
		for (final ConfigProperty configProperty : fileEditorConfig.getConfigProperties()) {
			addProperty(composite, configProperty.getType().getDisplayLabel() + ":", configProperty.getDisplayValue());
		}
	}

	private Composite createPropertiesComposite(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NULL);
		final GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		final GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);
		return composite;
	}

	private void addProperty(final Composite parent, final String labelText, final String value) {
		final Label label = new Label(parent, SWT.NONE);
		label.setText(labelText);
		final Text valueText = new Text(parent, SWT.WRAP | SWT.READ_ONLY);
		valueText.setText(value);
		valueText.setBackground(valueText.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
	}

}
