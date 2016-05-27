package org.eclipse.editorconfig.internal.core;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class EditorConfigCorePlugin extends Plugin {

	public static final String PLUGIN_ID = "org.eclipse.editorconfig.core"; //$NON-NLS-1$

	// The shared instance.
	private static EditorConfigCorePlugin plugin;

	/**
	 * The constructor.
	 */
	public EditorConfigCorePlugin() {
		super();
		plugin = this;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static EditorConfigCorePlugin getDefault() {
		return plugin;
	}

}
