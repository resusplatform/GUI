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

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.gef.commands.Command;

import de.clausthal.tu.ielf.resusdesigner.ResusMessages;
import de.clausthal.tu.ielf.resusdesigner.model.Connector;
import de.clausthal.tu.ielf.resusdesigner.model.ResusSubpart;

public class ConnectionCommand extends Command {

	protected ResusSubpart oldSource;
	protected String oldSourceTerminal;
	protected ResusSubpart oldTarget;
	protected String oldTargetTerminal;
	protected ResusSubpart source;
	protected String sourceTerminal;
	protected ResusSubpart target;
	protected String targetTerminal;
	protected Connector wire;

	public ConnectionCommand() {
		super(ResusMessages.ConnectionCommand_Label);
	}

	public boolean canExecute() {
		if (target != null) {
			Vector conns = target.getConnections();
			Iterator i = conns.iterator();
			while (i.hasNext()) {
				Connector conn = (Connector) i.next();
				if (targetTerminal != null && conn.getTargetTerminal() != null)
					if (conn.getTargetTerminal().equals(targetTerminal)
							&& conn.getTarget().equals(target))
						return false;
			}
			if(target==source)
				return false;
		}
		return true;
	}

	public void execute() {
		if (source != null) {
			wire.detachSource();
			wire.setSource(source);
			wire.setSourceTerminal(sourceTerminal);
			wire.attachSource();
		}
		if (target != null) {
			wire.detachTarget();
			wire.setTarget(target);
			wire.setTargetTerminal(targetTerminal);
			wire.attachTarget();
		}
		if (source == null && target == null) {
			wire.detachSource();
			wire.detachTarget();
			wire.setTarget(null);
			wire.setSource(null);
		}
		
	}

	public String getLabel() {
		return ResusMessages.ConnectionCommand_Description;
	}

	public ResusSubpart getSource() {
		return source;
	}

	public java.lang.String getSourceTerminal() {
		return sourceTerminal;
	}

	public ResusSubpart getTarget() {
		return target;
	}

	public String getTargetTerminal() {
		return targetTerminal;
	}

	public Connector getWire() {
		return wire;
	}

	public void redo() {
		execute();
	}

	public void setSource(ResusSubpart newSource) {
		source = newSource;
	}

	public void setSourceTerminal(String newSourceTerminal) {
		sourceTerminal = newSourceTerminal;
	}

	public void setTarget(ResusSubpart newTarget) {
		target = newTarget;
	}

	public void setTargetTerminal(String newTargetTerminal) {
		targetTerminal = newTargetTerminal;
	}

	public void setWire(Connector w) {
		wire = w;
		oldSource = w.getSource();
		oldTarget = w.getTarget();
		oldSourceTerminal = w.getSourceTerminal();
		oldTargetTerminal = w.getTargetTerminal();
	}

	public void undo() {
		source = wire.getSource();
		target = wire.getTarget();
		sourceTerminal = wire.getSourceTerminal();
		targetTerminal = wire.getTargetTerminal();

		wire.detachSource();
		wire.detachTarget();

		wire.setSource(oldSource);
		wire.setTarget(oldTarget);
		wire.setSourceTerminal(oldSourceTerminal);
		wire.setTargetTerminal(oldTargetTerminal);

		wire.attachSource();
		wire.attachTarget();
	}

}
