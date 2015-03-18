Eclipse Editorconfig Plugin TODO
================================

Required for v1.0
-----------------

 * make all settings work in simple text editor (at least trim whitespace is not working)
 * verify all config settings for editors other than simple text editor (eg java and xml editors)
   - may need to create or modify a Java code style formatter profile
 * create update site
   - https://eclipse.org/tycho/sitedocs/tycho-packaging-plugin/package-feature-mojo.html
 * publish plugin to eclipse update site
   - host on aws s3, eg:
	  https://github.com/jacoco/eclemma/blob/master/com.mountainminds.eclemma.build/publish.py
 * create entry in eclipse market place
 * add logging
 * run junit tests in maven build
   - http://git.eclipse.org/c/tycho/org.eclipse.tycho-demo.git/tree/itp01/


Nice to have for v1.0
---------------------

 * restore editor prefs to defaults when not defined for current file
 * create automated integration test suite
   - http://www.eclipse.org/swtbot/


Property Page Bugs
------------------

Property page is not required for v1.0.  If these bugs aren't fixed then the feature will be dropped.

 * properties view not available for java files, license file
 * properties view available for binary files
 * incorrect file path is detected when editing plugin.xml from manifest editor and vice versa


Future Ideas
------------

 * implement org.eclipse.ui.views.properties.IPropertySource
 * custom editor for .editorconfig files
 * Apply formatting settings on save. See the following resources for ideas:
  - de.loskutov.anyedit.actions.internal.StartupHelper2.DirtyHookRunnable.run(IWorkbenchWindow)
  - com.welovecoding.netbeans.plugin.editorconfig.processor.EditorConfigProcessor.applyRulesToFile(DataObject)
  - https://github.com/editorconfig/editorconfig-jedit/blob/master/src/org/editorconfig/jedit/EditorConfigPlugin.java
