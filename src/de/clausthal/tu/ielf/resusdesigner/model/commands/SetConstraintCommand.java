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

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import de.clausthal.tu.ielf.resusdesigner.ResusMessages;
import de.clausthal.tu.ielf.resusdesigner.model.ResusSubpart;

public class SetConstraintCommand extends org.eclipse.gef.commands.Command {

	private Point newPos;
	private Dimension newSize;
	private Point oldPos;
	private Dimension oldSize;
	private ResusSubpart part;

	public void execute() {
		oldSize = part.getSize();
		oldPos = part.getLocation();
		redo();
	}

	public String getLabel() {
		if (oldSize.equals(newSize))
			return ResusMessages.SetLocationCommand_Label_Location;
		return ResusMessages.SetLocationCommand_Label_Resize;
	}

	public void redo() {
		part.setSize(newSize);
		part.setLocation(newPos);
	}

	public void setLocation(Rectangle r) {
		setLocation(r.getLocation());
		setSize(r.getSize());
	}

	public void setLocation(Point p) {
		newPos = p;
	}

	public void setPart(ResusSubpart part) {
		this.part = part;
	}

	public void setSize(Dimension p) {
		newSize = p;
	}

	public void undo() {
		part.setSize(oldSize);
		part.setLocation(oldPos);
	}

}
