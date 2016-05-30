/*******************************************************************************
 * Copyright (c) 2000, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package de.clausthal.tu.ielf.resusdesigner.model.commands;

import org.eclipse.gef.commands.Command;

import de.clausthal.tu.ielf.resusdesigner.ResusMessages;
import de.clausthal.tu.ielf.resusdesigner.model.ResusDiagram;
import de.clausthal.tu.ielf.resusdesigner.model.ResusSubpart;

public class ReorderPartCommand extends Command {

	private int oldIndex, newIndex;
	private ResusSubpart child;
	private ResusDiagram parent;

	public ReorderPartCommand(ResusSubpart child, ResusDiagram parent,
			int newIndex) {
		super(ResusMessages.ReorderPartCommand_Label);
		this.child = child;
		this.parent = parent;
		this.newIndex = newIndex;
	}

	public void execute() {
		oldIndex = parent.getChildren().indexOf(child);
		parent.removeChild(child);
		parent.addChild(child, newIndex);
	}

	public void undo() {
		parent.removeChild(child);
		parent.addChild(child, oldIndex);
	}

}
