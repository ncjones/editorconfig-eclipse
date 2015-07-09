/*
 * Copyright 2015 Nathan Jones
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
package com.ncjones.editorconfig.eclipse.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;

public class EditorConfigTestContext {

	private final SWTWorkbenchBot bot;
	private final String projectName;

	public EditorConfigTestContext(final SWTWorkbenchBot bot, final String projectName) {
		this.bot = bot;
		this.projectName = projectName;
		closeWelcomePageIfPresent();
		createJavaProjectIfNotExists(projectName);
		configureEditors();
		SWTBotPreferences.KEYBOARD_LAYOUT = "EN_US";
	}

	private void configureEditors() {
		setPreference("org.eclipse.wst.xml.ui", "org.eclipse.wst.xml.ui.internal.tabletree.XMLMultiPageEditorPart.lastActivePage", "1");
	}

	private void setPreference(final String prefsNodeName, final String key, final String value) {
		final IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(prefsNodeName);
		prefs.put(key, value);
	}


	private void closeWelcomePageIfPresent() {
		try {
			bot.viewByTitle("Welcome").close();
		} catch (final WidgetNotFoundException e) {
		}
	}

	private void createJavaProjectIfNotExists(final String projectName) {
		final Path projectFilePath = Paths.get(workspaceDir(), projectName, ".project");
		if (!Files.isRegularFile(projectFilePath, LinkOption.NOFOLLOW_LINKS)) {
			newJavaProject(projectName);
		}
	}

	private void newJavaProject(final String projectName) {
		bot.menu("File").menu("New").menu("Project...").click();
		bot.shell("New Project").activate();
		bot.tree().expandNode("Java").select("Java Project");
		bot.button("Next >").click();
		bot.textWithLabel("Project name:").setText(projectName);
		bot.button("Finish").click();
		bot.button("Yes").click();
	}

	public String fileContents(final String fileName) {
		return new String(fileBytes(fileName));
	}

	public byte[] fileBytes(final String fileName) {
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(filePath(fileName));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		return bytes;
	}

	private static String workspaceDir() {
		return Platform.getInstanceLocation().getURL().getFile();
	}

	public void newFile(final String fileName) {
		bot.menu("File").menu("New").menu("File").click();
		bot.textWithLabel("Enter or select the parent folder:").setText(projectName);
		bot.textWithLabel("File na&me:").setText(fileName);
		bot.button("Finish").click();
	}

	public void editFile(final String fileName, final String contents) {
		if (Files.exists(filePath(fileName), LinkOption.NOFOLLOW_LINKS)) {
			bot.tree().getTreeItem(projectName).getNode(fileName).doubleClick();
		} else {
			newFile(fileName);
		}
		final SWTBotEclipseEditor textEditor = bot.editorByTitle(fileName).toTextEditor();
		textEditor.setText("");
		textEditor.typeText(contents);
		textEditor.save();
	}

	public void editorConfig(final String... configLines) {
		final Path editorConfigPath = filePath(".editorconfig");
		try {
			Files.deleteIfExists(editorConfigPath);
			Files.createFile(editorConfigPath);
			Files.write(editorConfigPath, Arrays.asList(configLines), StandardOpenOption.WRITE);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Path filePath(final String fileName) {
		return Paths.get(workspaceDir(), projectName, fileName);
	}

}
