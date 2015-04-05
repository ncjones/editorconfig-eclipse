Eclipse Editorconfig Plugin TODO
================================

Required for v1.0
-----------------

 * make all settings work in simple text editor (at least trim whitespace is not working)
 * verify all config settings for editors other than simple text editor (eg java and xml editors)
   - may need to create or modify a Java code style formatter profile
 * create entry in eclipse market place
 * add logging
 * fix property page
 * set whitespace settings when editor opened to avoid tab size changing on focus
   (note: can't be fixed for multiple split editors)
 * set config for current editor on startup


Nice to have for v1.0
---------------------

 * include full license text in feature details
 * have feature appear on eclipse about page with logo
 * restore editor prefs to defaults when not defined for current file
 * respect tab width when tab style is "tab"
 * create automated integration test suite
   - http://www.eclipse.org/swtbot/
   - test matrix - all editor types with:
     * all editor configs
     * code format


Property Page Bugs
------------------

Property page is not required for v1.0.  If these bugs aren't fixed then the feature will be dropped.

 * properties view not available for java files, license file
 * properties view available for binary files
 * incorrect file path is detected when editing plugin.xml from manifest editor and vice versa


Future Ideas
------------

 * icon for .editorconfig file
 * implement org.eclipse.ui.views.properties.IPropertySource
 * custom editor for .editorconfig files
 * Apply formatting settings on save. See the following resources for ideas:
  - de.loskutov.anyedit.actions.internal.StartupHelper2.DirtyHookRunnable.run(IWorkbenchWindow)
  - com.welovecoding.netbeans.plugin.editorconfig.processor.EditorConfigProcessor.applyRulesToFile(DataObject)
  - https://github.com/editorconfig/editorconfig-jedit/blob/master/src/org/editorconfig/jedit/EditorConfigPlugin.java
