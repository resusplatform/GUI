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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import de.clausthal.tu.ielf.resusdesigner.ResusMessages;
import de.clausthal.tu.ielf.resusdesigner.model.Connector;
import de.clausthal.tu.ielf.resusdesigner.model.ConnectorBendpoint;
import de.clausthal.tu.ielf.resusdesigner.model.InputProvider;
import de.clausthal.tu.ielf.resusdesigner.model.LogicGuide;

import de.clausthal.tu.ielf.resusdesigner.model.ResultConverter;
import de.clausthal.tu.ielf.resusdesigner.model.ResusDiagram;
import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.model.ResusSubpart;


public class CloneCommand extends Command {

	private List parts, newTopLevelParts, newConnections;
	private ResusDiagram parent;
	private Map bounds, indices, connectionPartMap;
	private ChangeGuideCommand vGuideCommand, hGuideCommand;
	private LogicGuide hGuide, vGuide;
	private int hAlignment, vAlignment;

	public CloneCommand() {
		super(ResusMessages.CloneCommand_Label);
		parts = new LinkedList();
	}

	public void addPart(ResusSubpart part, Rectangle newBounds) {
		parts.add(part);
		if (bounds == null) {
			bounds = new HashMap();
		}
		bounds.put(part, newBounds);
	}

	public void addPart(ResusSubpart part, int index) {
		parts.add(part);
		if (indices == null) {
			indices = new HashMap();
		}
		indices.put(part, new Integer(index));
	}

	protected void clonePart(ResusSubpart oldPart, ResusDiagram newParent,
			Rectangle newBounds, List newConnections, Map connectionPartMap,
			int index) {
		ResusSubpart newPart = null;

		if (oldPart instanceof ResusModel) {
			newPart = new ResusModel();
		} else if (oldPart instanceof InputProvider) {
			newPart = new InputProvider();
		
		} else if (oldPart instanceof ResultConverter) {
			newPart = new ResultConverter();
		 
		
		}

		if (oldPart instanceof ResusDiagram) {
			Iterator i = ((ResusDiagram) oldPart).getChildren().iterator();
			while (i.hasNext()) {
				// for children they will not need new bounds
				clonePart((ResusSubpart) i.next(), (ResusDiagram) newPart,
						null, newConnections, connectionPartMap, -1);
			}
		}

		Iterator i = oldPart.getTargetConnections().iterator();
		while (i.hasNext()) {
			Connector connection = (Connector) i.next();
			Connector newConnection = new Connector();
			newConnection.setValue(connection.getValue());
			newConnection.setTarget(newPart);
			newConnection.setTargetTerminal(connection.getTargetTerminal());
			newConnection.setSourceTerminal(connection.getSourceTerminal());
			newConnection.setSource(connection.getSource());

			Iterator b = connection.getBendpoints().iterator();
			Vector newBendPoints = new Vector();

			while (b.hasNext()) {
				ConnectorBendpoint bendPoint = (ConnectorBendpoint) b.next();
				ConnectorBendpoint newBendPoint = new ConnectorBendpoint();
				newBendPoint.setRelativeDimensions(
						bendPoint.getFirstRelativeDimension(),
						bendPoint.getSecondRelativeDimension());
				newBendPoint.setWeight(bendPoint.getWeight());
				newBendPoints.add(newBendPoint);
			}

			newConnection.setBendpoints(newBendPoints);
			newConnections.add(newConnection);
		}

		if (index < 0) {
			newParent.addChild(newPart);
		} else {
			newParent.addChild(newPart, index);
		}

		newPart.setSize(oldPart.getSize());

		if (newBounds != null) {
			newPart.setLocation(newBounds.getTopLeft());
		} else {
			newPart.setLocation(oldPart.getLocation());
		}

		// keep track of the new parts so we can delete them in undo
		// keep track of the oldpart -> newpart map so that we can properly
		// attach
		// all connections.
		if (newParent == parent)
			newTopLevelParts.add(newPart);
		connectionPartMap.put(oldPart, newPart);
	}

	public void execute() {
		connectionPartMap = new HashMap();
		newConnections = new LinkedList();
		newTopLevelParts = new LinkedList();

		Iterator i = parts.iterator();

		ResusSubpart part = null;
		while (i.hasNext()) {
			part = (ResusSubpart) i.next();
			if (bounds != null && bounds.containsKey(part)) {
				clonePart(part, parent, (Rectangle) bounds.get(part),
						newConnections, connectionPartMap, -1);
			} else if (indices != null && indices.containsKey(part)) {
				clonePart(part, parent, null, newConnections,
						connectionPartMap,
						((Integer) indices.get(part)).intValue());
			} else {
				clonePart(part, parent, null, newConnections,
						connectionPartMap, -1);
			}
		}

		// go through and set the source of each connection to the proper
		// source.
		Iterator c = newConnections.iterator();

		while (c.hasNext()) {
			Connector conn = (Connector) c.next();
			ResusSubpart source = conn.getSource();
			if (connectionPartMap.containsKey(source)) {
				conn.setSource((ResusSubpart) connectionPartMap.get(source));
				conn.attachSource();
				conn.attachTarget();
			}
		}

		if (hGuide != null) {
			hGuideCommand = new ChangeGuideCommand(
					(ResusSubpart) connectionPartMap.get(parts.get(0)), true);
			hGuideCommand.setNewGuide(hGuide, hAlignment);
			hGuideCommand.execute();
		}

		if (vGuide != null) {
			vGuideCommand = new ChangeGuideCommand(
					(ResusSubpart) connectionPartMap.get(parts.get(0)), false);
			vGuideCommand.setNewGuide(vGuide, vAlignment);
			vGuideCommand.execute();
		}
	}

	public void setParent(ResusDiagram parent) {
		this.parent = parent;
	}

	public void redo() {
		for (Iterator iter = newTopLevelParts.iterator(); iter.hasNext();)
			parent.addChild((ResusSubpart) iter.next());
		for (Iterator iter = newConnections.iterator(); iter.hasNext();) {
			Connector conn = (Connector) iter.next();
			ResusSubpart source = conn.getSource();
			if (connectionPartMap.containsKey(source)) {
				conn.setSource((ResusSubpart) connectionPartMap.get(source));
				conn.attachSource();
				conn.attachTarget();
			}
		}
		if (hGuideCommand != null)
			hGuideCommand.redo();
		if (vGuideCommand != null)
			vGuideCommand.redo();
	}

	public void setGuide(LogicGuide guide, int alignment, boolean isHorizontal) {
		if (isHorizontal) {
			hGuide = guide;
			hAlignment = alignment;
		} else {
			vGuide = guide;
			vAlignment = alignment;
		}
	}

	public void undo() {
		if (hGuideCommand != null)
			hGuideCommand.undo();
		if (vGuideCommand != null)
			vGuideCommand.undo();
		for (Iterator iter = newTopLevelParts.iterator(); iter.hasNext();)
			parent.removeChild((ResusSubpart) iter.next());
	}

}
