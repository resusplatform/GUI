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

import de.clausthal.tu.ielf.resusdesigner.model.ResusDiagram;
import de.clausthal.tu.ielf.resusdesigner.model.ResusSubpart;
import de.clausthal.tu.ielf.resusdesigner.model.commands.DeleteCommand;

public class ResusElementEditPolicy extends
		org.eclipse.gef.editpolicies.ComponentEditPolicy {

	protected Command createDeleteCommand(GroupRequest request) {
		Object parent = getHost().getParent().getModel();
		DeleteCommand deleteCmd = new DeleteCommand();
		deleteCmd.setParent((ResusDiagram) parent);
		deleteCmd.setChild((ResusSubpart) getHost().getModel());
		return deleteCmd;
	}

}
