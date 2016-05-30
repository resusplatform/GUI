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
package de.clausthal.tu.ielf.resusdesigner.edit;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;

import de.clausthal.tu.ielf.resusdesigner.model.Connector;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ConnectionCommand;

public class WireEditPolicy extends
		org.eclipse.gef.editpolicies.ConnectionEditPolicy {

	protected Command getDeleteCommand(GroupRequest request) {
		ConnectionCommand c = new ConnectionCommand();
		c.setWire((Connector) getHost().getModel());
		return c;
	}

}
