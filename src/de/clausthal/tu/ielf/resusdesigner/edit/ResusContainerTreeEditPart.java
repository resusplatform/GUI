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

import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

import de.clausthal.tu.ielf.resusdesigner.model.ResusDiagram;

/**
 * Tree EditPart for the Container.
 */
public class ResusContainerTreeEditPart extends ResusTreeEditPart {

	/**
	 * Constructor, which initializes this using the model given as input.
	 */
	public ResusContainerTreeEditPart(Object model) {
		super(model);
	}

	/**
	 * Creates and installs pertinent EditPolicies.
	 */
	protected void createEditPolicies() {
		super.createEditPolicies();
		
		// If this editpart is the contents of the viewer, then it is not
		// deletable!
		if (getParent() instanceof RootEditPart)
			installEditPolicy(EditPolicy.COMPONENT_ROLE,
					new RootComponentEditPolicy());
	}

	/**
	 * Returns the model of this as a LogicDiagram.
	 * 
	 * @return Model of this.
	 */
	protected ResusDiagram getLogicDiagram() {
		return (ResusDiagram) getModel();
	}

	/**
	 * Returns the children of this from the model, as this is capable enough of
	 * holding EditParts.
	 * 
	 * @return List of children.
	 */
	protected List getModelChildren() {
		return getLogicDiagram().getChildren();
	}

}
