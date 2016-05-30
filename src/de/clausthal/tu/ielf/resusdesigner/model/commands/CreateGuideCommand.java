/*******************************************************************************
 * Copyright (c) 2003, 2010 IBM Corporation and others.
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
import de.clausthal.tu.ielf.resusdesigner.model.LogicGuide;
import de.clausthal.tu.ielf.resusdesigner.model.LogicRuler;

/**
 * @author Pratik Shah
 */
public class CreateGuideCommand extends Command {

	private LogicGuide guide;
	private LogicRuler parent;
	private int position;

	public CreateGuideCommand(LogicRuler parent, int position) {
		super(ResusMessages.CreateGuideCommand_Label);
		this.parent = parent;
		this.position = position;
	}

	public boolean canUndo() {
		return true;
	}

	public void execute() {
		if (guide == null)
			guide = new LogicGuide(!parent.isHorizontal());
		guide.setPosition(position);
		parent.addGuide(guide);
	}

	public void undo() {
		parent.removeGuide(guide);
	}

}
