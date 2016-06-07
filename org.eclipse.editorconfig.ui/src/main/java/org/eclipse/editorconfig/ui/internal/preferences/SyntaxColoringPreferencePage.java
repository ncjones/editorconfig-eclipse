/*******************************************************************************
 * Copyright (c) 2000, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Angelo Zerr <angelo.zerr@gmail.com> - copied from org.eclipse.jdt.internal.ui.preferences.PropertiesFileEditorPreferencePage
 *                                           modified in order to process .editorconfig editor.     
 *******************************************************************************/
package org.eclipse.editorconfig.ui.internal.preferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.editorconfig.ui.PreferenceConstants;
import org.eclipse.editorconfig.ui.internal.EditorConfigUIPlugin;
import org.eclipse.editorconfig.ui.internal.dialogs.StatusInfo;
import org.eclipse.editorconfig.ui.internal.dialogs.StatusUtil;
import org.eclipse.editorconfig.ui.internal.preferences.OverlayPreferenceStore.OverlayKey;
import org.eclipse.editorconfig.ui.internal.text.EditorConfigColorManager;
import org.eclipse.editorconfig.ui.internal.text.EditorConfigDocumentSetupParticipant;
import org.eclipse.editorconfig.ui.internal.text.EditorConfigSourceViewerConfiguration;
import org.eclipse.editorconfig.ui.text.EditorConfigTextTools;
import org.eclipse.editorconfig.ui.text.IColorManager;
import org.eclipse.editorconfig.ui.text.IEditorConfigPartitions;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.model.WorkbenchViewerComparator;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;

/**
 * The page for setting the syntax coloring .editorconfig file editor
 * preferences.
 *
 */
public class SyntaxColoringPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private static class SourcePreviewerUpdater {

		/**
		 * Creates a Java source preview updater for the given viewer,
		 * configuration and preference store.
		 *
		 * @param viewer
		 *            the viewer
		 * @param configuration
		 *            the configuration
		 * @param preferenceStore
		 *            the preference store
		 */
		SourcePreviewerUpdater(final SourceViewer viewer, final EditorConfigSourceViewerConfiguration configuration,
				final IPreferenceStore preferenceStore) {
			Assert.isNotNull(viewer);
			Assert.isNotNull(configuration);
			Assert.isNotNull(preferenceStore);
			final IPropertyChangeListener fontChangeListener = new IPropertyChangeListener() {
				/*
				 * @see
				 * org.eclipse.jface.util.IPropertyChangeListener#propertyChange
				 * (org.eclipse.jface.util.PropertyChangeEvent)
				 */
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					if (event.getProperty().equals(PreferenceConstants.EDITOR_CONFIG_EDITOR_TEXT_FONT)) {
						Font font = JFaceResources.getFont(PreferenceConstants.EDITOR_CONFIG_EDITOR_TEXT_FONT);
						viewer.getTextWidget().setFont(font);
					}
				}
			};
			final IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {
				/*
				 * @see
				 * org.eclipse.jface.util.IPropertyChangeListener#propertyChange
				 * (org.eclipse.jface.util.PropertyChangeEvent)
				 */
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					if (configuration.affectsTextPresentation(event)) {
						configuration.handlePropertyChangeEvent(event);
						viewer.invalidateTextPresentation();
					}
				}
			};
			viewer.getTextWidget().addDisposeListener(new DisposeListener() {
				/*
				 * @see
				 * org.eclipse.swt.events.DisposeListener#widgetDisposed(org.
				 * eclipse.swt.events.DisposeEvent)
				 */
				@Override
				public void widgetDisposed(DisposeEvent e) {
					preferenceStore.removePropertyChangeListener(propertyChangeListener);
					JFaceResources.getFontRegistry().removeListener(fontChangeListener);
				}
			});
			JFaceResources.getFontRegistry().addListener(fontChangeListener);
			preferenceStore.addPropertyChangeListener(propertyChangeListener);
		}
	}

	/**
	 * Item in the highlighting color list.
	 */
	private static class HighlightingColorListItem {
		/** Display name */
		private String fDisplayName;
		/** Color preference key */
		private String fColorKey;
		/** Bold preference key */
		private String fBoldKey;
		/** Italic preference key */
		private String fItalicKey;
		/**
		 * Strikethrough preference key.
		 * 
		 * @since 3.1
		 */
		private String fStrikethroughKey;
		/**
		 * Underline preference key.
		 * 
		 * @since 3.1
		 */
		private String fUnderlineKey;
		/** Item color */
		private Color fItemColor;

		/**
		 * Initialize the item with the given values.
		 *
		 * @param displayName
		 *            the display name
		 * @param colorKey
		 *            the color preference key
		 * @param boldKey
		 *            the bold preference key
		 * @param italicKey
		 *            the italic preference key
		 * @param strikethroughKey
		 *            the strikethrough preference key
		 * @param underlineKey
		 *            the underline preference key
		 * @param itemColor
		 *            the item color
		 */
		public HighlightingColorListItem(String displayName, String colorKey, String boldKey, String italicKey,
				String strikethroughKey, String underlineKey, Color itemColor) {
			fDisplayName = displayName;
			fColorKey = colorKey;
			fBoldKey = boldKey;
			fItalicKey = italicKey;
			fStrikethroughKey = strikethroughKey;
			fUnderlineKey = underlineKey;
			fItemColor = itemColor;
		}

		/**
		 * @return the bold preference key
		 */
		public String getBoldKey() {
			return fBoldKey;
		}

		/**
		 * @return the italic preference key
		 */
		public String getItalicKey() {
			return fItalicKey;
		}

		/**
		 * @return the strikethrough preference key
		 * @since 3.1
		 */
		public String getStrikethroughKey() {
			return fStrikethroughKey;
		}

		/**
		 * @return the underline preference key
		 * @since 3.1
		 */
		public String getUnderlineKey() {
			return fUnderlineKey;
		}

		/**
		 * @return the color preference key
		 */
		public String getColorKey() {
			return fColorKey;
		}

		/**
		 * @return the display name
		 */
		public String getDisplayName() {
			return fDisplayName;
		}

		/**
		 * @return the item color
		 */
		public Color getItemColor() {
			return fItemColor;
		}
	}

	/**
	 * Color list label provider.
	 */
	private class ColorListLabelProvider extends LabelProvider implements IColorProvider {

		/*
		 * @see
		 * org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(Object element) {
			return ((HighlightingColorListItem) element).getDisplayName();
		}

		/*
		 * @see
		 * org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.
		 * Object)
		 */
		@Override
		public Color getForeground(Object element) {
			return ((HighlightingColorListItem) element).getItemColor();
		}

		/*
		 * @see
		 * org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.
		 * Object)
		 */
		@Override
		public Color getBackground(Object element) {
			return null;
		}
	}

	/**
	 * Color list content provider.
	 */
	private class ColorListContentProvider implements IStructuredContentProvider {

		/*
		 * @see
		 * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
		 * .lang.Object)
		 */
		@Override
		public Object[] getElements(Object inputElement) {
			return ((java.util.List<?>) inputElement).toArray();
		}

		/*
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		@Override
		public void dispose() {
		}

		/*
		 * @see
		 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.
		 * jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	private static final String BOLD = PreferenceConstants.EDITOR_BOLD_SUFFIX;
	/**
	 * Preference key suffix for italic preferences.
	 */
	private static final String ITALIC = PreferenceConstants.EDITOR_ITALIC_SUFFIX;

	/**
	 * Preference key suffix for strikethrough preferences.
	 * 
	 * @since 3.1
	 */
	private static final String STRIKETHROUGH = PreferenceConstants.EDITOR_STRIKETHROUGH_SUFFIX;

	/**
	 * Preference key suffix for underline preferences.
	 * 
	 * @since 3.1
	 */
	private static final String UNDERLINE = PreferenceConstants.EDITOR_UNDERLINE_SUFFIX;

	/** The keys of the overlay store. */
	private final String[][] fSyntaxColorListModel = new String[][] {
			{ PreferencesMessages.EditorConfigEditorPreferencePage_section,
					PreferenceConstants.EDITOR_CONFIG_COLORING_SECTION },
			{ PreferencesMessages.EditorConfigEditorPreferencePage_comment,
					PreferenceConstants.EDITOR_CONFIG_COLORING_COMMENT },
			{ PreferencesMessages.EditorConfigEditorPreferencePage_key,
					PreferenceConstants.EDITOR_CONFIG_COLORING_PROPERTY_KEY },
			{ PreferencesMessages.EditorConfigEditorPreferencePage_value,
					PreferenceConstants.EDITOR_CONFIG_COLORING_PROPERTY_VALUE },
			{ PreferencesMessages.EditorConfigEditorPreferencePage_assignment,
					PreferenceConstants.EDITOR_CONFIG_COLORING_ASSIGNMENT } };

	private OverlayPreferenceStore fOverlayStore;

	private ColorSelector fSyntaxForegroundColorEditor;
	private Button fBoldCheckBox;

	/**
	 * Check box for italic preference.
	 */
	private Button fItalicCheckBox;

	/**
	 * Check box for strikethrough preference.
	 * 
	 * @since 3.1
	 */
	private Button fStrikethroughCheckBox;

	/**
	 * Check box for underline preference.
	 * 
	 * @since 3.1
	 */
	private Button fUnderlineCheckBox;

	private SourceViewer fPreviewViewer;

	/**
	 * Tells whether the fields are initialized.
	 */
	private boolean fFieldsInitialized = false;

	/**
	 * Highlighting color list
	 */
	private final List<HighlightingColorListItem> fHighlightingColorList = new ArrayList<HighlightingColorListItem>();
	/**
	 * Highlighting color list viewer
	 */
	private TableViewer fHighlightingColorListViewer;

	/**
	 * The color manager.
	 */
	private IColorManager fColorManager;

	/**
	 * Creates a new preference page.
	 */
	public SyntaxColoringPreferencePage() {
		setPreferenceStore(EditorConfigUIPlugin.getDefault().getPreferenceStore());

		fOverlayStore = new OverlayPreferenceStore(getPreferenceStore(), createOverlayStoreKeys());

	}

	private OverlayPreferenceStore.OverlayKey[] createOverlayStoreKeys() {

		ArrayList<OverlayKey> overlayKeys = new ArrayList<OverlayKey>();

		for (int i = 0; i < fSyntaxColorListModel.length; i++) {
			String colorKey = fSyntaxColorListModel[i][1];
			overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, colorKey));
			overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, colorKey + BOLD));
			overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, colorKey + ITALIC));
			overlayKeys.add(
					new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, colorKey + STRIKETHROUGH));
			overlayKeys
					.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, colorKey + UNDERLINE));
		}

		OverlayPreferenceStore.OverlayKey[] keys = new OverlayPreferenceStore.OverlayKey[overlayKeys.size()];
		overlayKeys.toArray(keys);
		return keys;
	}

	/*
	 * @see IWorkbenchPreferencePage#init()
	 */
	@Override
	public void init(IWorkbench workbench) {
	}

	/*
	 * @see PreferencePage#createControl(Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
		// IJavaHelpContextIds.EDITOR_CONFIG_EDITOR_PREFERENCE_PAGE);
	}

	private void handleSyntaxColorListSelection() {
		HighlightingColorListItem item = getHighlightingColorListItem();
		RGB rgb = PreferenceConverter.getColor(fOverlayStore, item.getColorKey());
		fSyntaxForegroundColorEditor.setColorValue(rgb);
		fBoldCheckBox.setSelection(fOverlayStore.getBoolean(item.getBoldKey()));
		fItalicCheckBox.setSelection(fOverlayStore.getBoolean(item.getItalicKey()));
		fStrikethroughCheckBox.setSelection(fOverlayStore.getBoolean(item.getStrikethroughKey()));
		fUnderlineCheckBox.setSelection(fOverlayStore.getBoolean(item.getUnderlineKey()));

		fSyntaxForegroundColorEditor.getButton().setEnabled(true);
		fBoldCheckBox.setEnabled(true);
		fItalicCheckBox.setEnabled(true);
		fStrikethroughCheckBox.setEnabled(true);
		fUnderlineCheckBox.setEnabled(true);
	}

	private Control createSyntaxPage(Composite parent) {

		Label label = new Label(parent, SWT.LEFT);
		label.setText(PreferencesMessages.EditorConfigEditorPreferencePage_foreground);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Composite editorComposite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		editorComposite.setLayout(layout);
		GridData gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		editorComposite.setLayoutData(gd);

		fHighlightingColorListViewer = new TableViewer(editorComposite,
				SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
		fHighlightingColorListViewer.setLabelProvider(new ColorListLabelProvider());
		fHighlightingColorListViewer.setContentProvider(new ColorListContentProvider());
		fHighlightingColorListViewer.setComparator(new WorkbenchViewerComparator());
		gd = new GridData(SWT.BEGINNING, SWT.FILL, false, true);
		gd.heightHint = convertHeightInCharsToPixels(5);
		fHighlightingColorListViewer.getControl().setLayoutData(gd);

		Composite stylesComposite = new Composite(editorComposite, SWT.NONE);
		layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 2;
		stylesComposite.setLayout(layout);
		stylesComposite.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));

		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalAlignment = GridData.BEGINNING;
		gd.horizontalSpan = 2;

		label = new Label(stylesComposite, SWT.LEFT);
		label.setText(PreferencesMessages.EditorConfigEditorPreferencePage_color);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalIndent = getIndent();
		label.setLayoutData(gd);

		fSyntaxForegroundColorEditor = new ColorSelector(stylesComposite);
		Button foregroundColorButton = fSyntaxForegroundColorEditor.getButton();
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		foregroundColorButton.setLayoutData(gd);

		fBoldCheckBox = new Button(stylesComposite, SWT.CHECK);
		fBoldCheckBox.setText(PreferencesMessages.EditorConfigEditorPreferencePage_bold);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalIndent = getIndent();
		gd.horizontalSpan = 2;
		fBoldCheckBox.setLayoutData(gd);

		fItalicCheckBox = new Button(stylesComposite, SWT.CHECK);
		fItalicCheckBox.setText(PreferencesMessages.EditorConfigEditorPreferencePage_italic);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalIndent = getIndent();
		gd.horizontalSpan = 2;
		fItalicCheckBox.setLayoutData(gd);

		fStrikethroughCheckBox = new Button(stylesComposite, SWT.CHECK);
		fStrikethroughCheckBox.setText(PreferencesMessages.EditorConfigEditorPreferencePage_strikethrough);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalIndent = getIndent();
		gd.horizontalSpan = 2;
		fStrikethroughCheckBox.setLayoutData(gd);

		fUnderlineCheckBox = new Button(stylesComposite, SWT.CHECK);
		fUnderlineCheckBox.setText(PreferencesMessages.EditorConfigEditorPreferencePage_underline);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalIndent = getIndent();
		gd.horizontalSpan = 2;
		fUnderlineCheckBox.setLayoutData(gd);

		label = new Label(parent, SWT.LEFT);
		label.setText(PreferencesMessages.EditorConfigEditorPreferencePage_preview);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Control previewer = createPreviewer(parent);
		gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = convertWidthInCharsToPixels(20);
		gd.heightHint = convertHeightInCharsToPixels(5);
		previewer.setLayoutData(gd);

		fHighlightingColorListViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				handleSyntaxColorListSelection();
			}
		});

		foregroundColorButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				HighlightingColorListItem item = getHighlightingColorListItem();
				PreferenceConverter.setValue(fOverlayStore, item.getColorKey(),
						fSyntaxForegroundColorEditor.getColorValue());
			}
		});

		fBoldCheckBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				HighlightingColorListItem item = getHighlightingColorListItem();
				fOverlayStore.setValue(item.getBoldKey(), fBoldCheckBox.getSelection());
			}
		});

		fItalicCheckBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				HighlightingColorListItem item = getHighlightingColorListItem();
				fOverlayStore.setValue(item.getItalicKey(), fItalicCheckBox.getSelection());
			}
		});

		fStrikethroughCheckBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				HighlightingColorListItem item = getHighlightingColorListItem();
				fOverlayStore.setValue(item.getStrikethroughKey(), fStrikethroughCheckBox.getSelection());
			}
		});

		fUnderlineCheckBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				HighlightingColorListItem item = getHighlightingColorListItem();
				fOverlayStore.setValue(item.getUnderlineKey(), fUnderlineCheckBox.getSelection());
			}
		});

		parent.layout();

		return parent;
	}

	private int getIndent() {
		return LayoutConstants.getIndent();
	}

	private Control createPreviewer(Composite parent) {

		IPreferenceStore store = new ChainedPreferenceStore(new IPreferenceStore[] { fOverlayStore,
				EditorConfigUIPlugin.getDefault().getCombinedPreferenceStore() });
		fPreviewViewer = new SourceViewer(parent, null, null, false, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		fColorManager = new EditorConfigColorManager(false);
		EditorConfigSourceViewerConfiguration configuration = new EditorConfigSourceViewerConfiguration(fColorManager,
				store, null, IEditorConfigPartitions.EDITOR_CONFIG_PARTITIONING);
		fPreviewViewer.configure(configuration);
		Font font = JFaceResources.getFont(PreferenceConstants.EDITOR_CONFIG_EDITOR_TEXT_FONT);
		fPreviewViewer.getTextWidget().setFont(font);
		new SourcePreviewerUpdater(fPreviewViewer, configuration, store);
		fPreviewViewer.setEditable(false);

		String content = loadPreviewContentFromFile("EditorConfigEditorColorSettingPreviewCode.txt"); //$NON-NLS-1$
		IDocument document = new Document(content);
		EditorConfigDocumentSetupParticipant.setupDocument(document);
		fPreviewViewer.setDocument(document);

		return fPreviewViewer.getControl();
	}

	/*
	 * @see PreferencePage#createContents(Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		fOverlayStore.load();
		fOverlayStore.start();

		Composite contents = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		contents.setLayout(layout);
		contents.setLayoutData(new GridData(GridData.FILL_BOTH));

		createHeader(contents);

		createSyntaxPage(contents);

		initialize();

		Dialog.applyDialogFont(contents);
		return contents;
	}

	private void createHeader(Composite contents) {
		String text = PreferencesMessages.EditorConfigEditorPreferencePage_link;
		Link link = new Link(contents, SWT.NONE);
		link.setText(text);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ("org.eclipse.ui.preferencePages.GeneralTextEditor".equals(e.text)) //$NON-NLS-1$
					PreferencesUtil.createPreferenceDialogOn(getShell(), e.text, null, null);
				else if ("org.eclipse.ui.preferencePages.ColorsAndFonts".equals(e.text)) //$NON-NLS-1$
					PreferencesUtil.createPreferenceDialogOn(getShell(), e.text, null,
							"selectFont:org.eclipse.jdt.ui.EditorConfigEditor.textfont"); //$NON-NLS-1$
			}
		});

		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gridData.widthHint = 150; // only expand further if anyone else requires
									// it
		link.setLayoutData(gridData);

		addFiller(contents);
	}

	private void addFiller(Composite composite) {
		PixelConverter pixelConverter = new PixelConverter(composite);

		Label filler = new Label(composite, SWT.LEFT);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan = 2;
		gd.heightHint = pixelConverter.convertHeightInCharsToPixels(1) / 2;
		filler.setLayoutData(gd);
	}

	private void initialize() {

		initializeFields();

		for (int i = 0, n = fSyntaxColorListModel.length; i < n; i++)
			fHighlightingColorList.add(new HighlightingColorListItem(fSyntaxColorListModel[i][0],
					fSyntaxColorListModel[i][1], fSyntaxColorListModel[i][1] + BOLD,
					fSyntaxColorListModel[i][1] + ITALIC, fSyntaxColorListModel[i][1] + STRIKETHROUGH,
					fSyntaxColorListModel[i][1] + UNDERLINE, null));

		fHighlightingColorListViewer.setInput(fHighlightingColorList);
		fHighlightingColorListViewer
				.setSelection(new StructuredSelection(fHighlightingColorListViewer.getElementAt(0)));

		// Make sure we propagate the colors to the shared color manager
		IPreferenceStore store = EditorConfigUIPlugin.getDefault().getCombinedPreferenceStore();
		EditorConfigTextTools textTools = EditorConfigUIPlugin.getDefault().getEditorConfigTextTools();
		EditorConfigSourceViewerConfiguration sharedEditorConfigSourceViewerConfiguration = new EditorConfigSourceViewerConfiguration(
				textTools.getColorManager(), store, null, IEditorConfigPartitions.EDITOR_CONFIG_PARTITIONING);
		new SourcePreviewerUpdater(fPreviewViewer, sharedEditorConfigSourceViewerConfiguration, store);

	}

	private void initializeFields() {
		fFieldsInitialized = true;
		updateStatus(new StatusInfo());
	}

	/*
	 * @see PreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		fOverlayStore.propagate();
		EditorConfigUIPlugin.flushInstanceScope();
		return true;
	}

	/*
	 * @see PreferencePage#performDefaults()
	 */
	@Override
	protected void performDefaults() {

		fOverlayStore.loadDefaults();

		initializeFields();

		handleSyntaxColorListSelection();

		super.performDefaults();

		fPreviewViewer.invalidateTextPresentation();
	}

	/*
	 * @see DialogPage#dispose()
	 */
	@Override
	public void dispose() {

		if (fOverlayStore != null) {
			fOverlayStore.stop();
			fOverlayStore = null;
		}

		fColorManager.dispose();

		super.dispose();
	}

	private String loadPreviewContentFromFile(String filename) {
		String line;
		String separator = System.getProperty("line.separator"); //$NON-NLS-1$
		StringBuffer buffer = new StringBuffer(512);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
				buffer.append(separator);
			}
		} catch (IOException io) {
			EditorConfigUIPlugin.log(io);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
		return buffer.toString();
	}

	void updateStatus(IStatus status) {
		if (!fFieldsInitialized)
			return;

		setValid(!status.matches(IStatus.ERROR));
		StatusUtil.applyToStatusLine(this, status);
	}

	/**
	 * Returns the current highlighting color list item.
	 *
	 * @return the current highlighting color list item
	 */
	private HighlightingColorListItem getHighlightingColorListItem() {
		IStructuredSelection selection = (IStructuredSelection) fHighlightingColorListViewer.getSelection();
		return (HighlightingColorListItem) selection.getFirstElement();
	}
}
