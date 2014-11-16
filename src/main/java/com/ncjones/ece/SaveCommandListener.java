package com.ncjones.ece;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

public class SaveCommandListener implements IExecutionListener {

	@Override
	public void notHandled(final String command, final NotHandledException exception) {
	}

	@Override
	public void postExecuteFailure(final String command, final ExecutionException exception) {
	}

	@Override
	public void postExecuteSuccess(final String command, final Object returnValue) {
	}

	@Override
	public void preExecute(final String command, final ExecutionEvent event) {
		final IEditorPart editorPart = HandlerUtil.getActiveEditor(event);
		final IEditorInput input = editorPart.getEditorInput();
		if (input instanceof FileEditorInput) {
			final IPath path =((FileEditorInput) input).getPath();
			EditorConfigPluginLogger.log("Saving editor: " + path);
		}
	}
}
