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
package de.clausthal.tu.ielf.resusdesigner.model.commands.InputProvider;


import org.eclipse.draw2d.geometry.Dimension;

import de.clausthal.tu.ielf.resusdesigner.model.InputProvider;
import de.clausthal.tu.ielf.resusdesigner.model.commands.SectionCommand;

public class SetInputProviderNumberOfInputPinsCommand extends SectionCommand {

	private int oldNumber;
	private Dimension oldDimension;
	private int newNumber;
	private Dimension newDimension;
	private InputProvider part;


	public void execute() {
		
		redo();
	}

	public String getLabel() {
		return "Change InputProvider Number Of Input Pins";

	}
	
	

	public void redo() {
		part.setNumberOfInputs(newNumber);
		part.setSize(newDimension);
		propSection.refresh();
	}

	public void setNumberOfInputs(int n, Dimension d) {
		newNumber=n;
		newDimension=d;
	}


	public void setPart(InputProvider part) {
		this.part = part;
		oldNumber = part.getNumberOfInputs();
		oldDimension=part.getSize();
	}

	

	public void undo() {
		part.setNumberOfInputs(oldNumber);
		part.setSize(oldDimension);
		propSection.refresh();
		
	}
	public boolean canExecute(){
		if(oldNumber<0|| newNumber<0) return false;
		if(oldNumber== newNumber) return false;
		return true;
	}


}
