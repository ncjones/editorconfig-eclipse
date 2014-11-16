Eclipse Editorconfig Plugin TODO
================================

 * Apply formatting settings on save. See the following resources for ideas:
  - de.loskutov.anyedit.actions.internal.StartupHelper2.DirtyHookRunnable.run(IWorkbenchWindow)
  - com.welovecoding.netbeans.plugin.editorconfig.processor.EditorConfigProcessor.applyRulesToFile(DataObject)
  - https://github.com/editorconfig/editorconfig-jedit/blob/master/src/org/editorconfig/jedit/EditorConfigPlugin.java
 * run tests in maven build
 * incorrect file path is detected when editing plugin.xml from manifest editor and vice versa
   editor and vice versa.
 * properties view not available for java files, license file
 * properties view available for binary files
 * implement org.eclipse.ui.views.properties.IPropertySource

