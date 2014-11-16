package com.ncjones.ece;

import org.eclipse.core.commands.Command;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class EditorConfigPlugin extends AbstractUIPlugin implements IStartup {

	@Override
	public void earlyStartup() {
		EditorConfigPluginLogger.log("hello editorconfig");
		final ICommandService service = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		final Command command = service.getCommand("org.eclipse.ui.file.save");
		command.addExecutionListener(new SaveCommandListener());
	}

}
