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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;

import de.clausthal.tu.ielf.resusdesigner.ResusMessages;
import de.clausthal.tu.ielf.resusdesigner.model.Connector;
import de.clausthal.tu.ielf.resusdesigner.model.LogicGuide;
import de.clausthal.tu.ielf.resusdesigner.model.ResusDiagram;
import de.clausthal.tu.ielf.resusdesigner.model.ResusSubpart;

public class DeleteCommand extends Command {

	private ResusSubpart child;
	private ResusDiagram parent;
	private LogicGuide vGuide, hGuide;
	private int vAlign, hAlign;
	private int index = -1;
	private List sourceConnections = new ArrayList();
	private List targetConnections = new ArrayList();

	public DeleteCommand() {
		super(ResusMessages.DeleteCommand_Label);
	}

	private void deleteConnections(ResusSubpart part) {
		if (part instanceof ResusDiagram) {
			List children = ((ResusDiagram) part).getChildren();
			for (int i = 0; i < children.size(); i++)
				deleteConnections((ResusSubpart) children.get(i));
		}
		sourceConnections.addAll(part.getSourceConnections());
		for (int i = 0; i < sourceConnections.size(); i++) {
			Connector wire = (Connector) sourceConnections.get(i);
			wire.detachSource();
			wire.detachTarget();
		}
		targetConnections.addAll(part.getTargetConnections());
		for (int i = 0; i < targetConnections.size(); i++) {
			Connector wire = (Connector) targetConnections.get(i);
			wire.detachSource();
			wire.detachTarget();
		}
	}

	private void detachFromGuides(ResusSubpart part) {
		if (part.getVerticalGuide() != null) {
			vGuide = part.getVerticalGuide();
			vAlign = vGuide.getAlignment(part);
			vGuide.detachPart(part);
		}
		if (part.getHorizontalGuide() != null) {
			hGuide = part.getHorizontalGuide();
			hAlign = hGuide.getAlignment(part);
			hGuide.detachPart(part);
		}

	}

	public void execute() {
		primExecute();
	}

	protected void primExecute() {
		deleteConnections(child);
		detachFromGuides(child);
		index = parent.getChildren().indexOf(child);
		parent.removeChild(child);
	}

	private void reattachToGuides(ResusSubpart part) {
		if (vGuide != null)
			vGuide.attachPart(part, vAlign);
		if (hGuide != null)
			hGuide.attachPart(part, hAlign);
	}

	public void redo() {
		primExecute();
	}

	private void restoreConnections() {
		for (int i = 0; i < sourceConnections.size(); i++) {
			Connector wire = (Connector) sourceConnections.get(i);
			wire.attachSource();
			wire.attachTarget();
		}
		sourceConnections.clear();
		for (int i = 0; i < targetConnections.size(); i++) {
			Connector wire = (Connector) targetConnections.get(i);
			wire.attachSource();
			wire.attachTarget();
		}
		targetConnections.clear();
	}

	public void setChild(ResusSubpart c) {
		child = c;
	}

	public void setParent(ResusDiagram p) {
		parent = p;
	}

	public void undo() {
		parent.addChild(child, index);
		restoreConnections();
		reattachToGuides(child);
	}

}
