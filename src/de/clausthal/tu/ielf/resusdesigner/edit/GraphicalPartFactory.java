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

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import de.clausthal.tu.ielf.resusdesigner.model.Connector;
import de.clausthal.tu.ielf.resusdesigner.model.InputProvider;

import de.clausthal.tu.ielf.resusdesigner.model.ResultConverter;
import de.clausthal.tu.ielf.resusdesigner.model.ResusDiagram;
import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;

public class GraphicalPartFactory implements EditPartFactory {

	public EditPart createEditPart(EditPart context, Object model) {
		EditPart child = null;

		if (model instanceof Connector)
			child = new WireEditPart();		
		else if (model instanceof ResusModel)
			child = new ResusModelEditPart((ResusModel)model);
		else if (model instanceof InputProvider)
			child = new InputProviderEditPart((InputProvider)model);
		else if (model instanceof ResultConverter)
			child = new ResultConverterEditPart((ResultConverter)model);
		
		
		// Note that subclasses of LogicDiagram have already been matched above,
		// like ResusModel
		else if (model instanceof ResusDiagram)
			child = new ResusDiagramEditPart();
		child.setModel(model);
		return child;
	}

}
