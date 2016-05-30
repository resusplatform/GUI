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

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;

import de.clausthal.tu.ielf.resusdesigner.ResusMessages;
import de.clausthal.tu.ielf.resusdesigner.model.InputProvider;
import de.clausthal.tu.ielf.resusdesigner.model.ResultConverter;
import de.clausthal.tu.ielf.resusdesigner.model.ResusDiagram;
import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.model.ResusSubpart;

public class CreateCommand extends org.eclipse.gef.commands.Command {

	private ResusSubpart child;
	private Rectangle rect;
	private ResusDiagram parent;
	private int index = -1;

	public CreateCommand() {
		super(ResusMessages.CreateCommand_Label);
	}

	public boolean canExecute() {
		return child != null && parent != null;
	}

	public void execute() {
		if (rect != null) {
			Insets expansion = getInsets();
			if (!rect.isEmpty())
				rect.expand(expansion);
			else {
				rect.x -= expansion.left;
				rect.y -= expansion.top;
			}
			child.setLocation(rect.getLocation());
			if (!rect.isEmpty())
				child.setSize(rect.getSize());
		}
		redo();
	}

	private Insets getInsets() {
		if ( child instanceof ResusModel || child instanceof InputProvider|| child instanceof ResultConverter)
			return new Insets(2, 0, 2, 0);
		return new Insets();
	}

	public void redo() {
		parent.addChild(child, index);
	}

	public void setChild(ResusSubpart subpart) {
		child = subpart;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setLocation(Rectangle r) {
		rect = r;
	}

	public void setParent(ResusDiagram newParent) {
		parent = newParent;
	}

	public void undo() {
		parent.removeChild(child);
	}

}
