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
package de.clausthal.tu.ielf.resusdesigner.model.commands.IOProvider;


import de.clausthal.tu.ielf.resusdesigner.model.IOProvider;
import de.clausthal.tu.ielf.resusdesigner.model.commands.SectionCommand;

public class SetIOProviderIdCommand extends SectionCommand {

	private String oldId;
	private String newId;
	private IOProvider part;


	public void execute() {
		
		oldId = part.getId();
		
		redo();
	}

	public String getLabel() {
		return "Change IOProvider ID";

	}
	
	

	public void redo() {
		part.setId(newId);
		propSection.refresh();
	}

	public void setId(String id) {
		newId=id;
	}


	public void setPart(IOProvider part) {
		this.part = part;
		oldId = part.getId();
	}

	

	public void undo() {
		part.setId(oldId);
		propSection.refresh();
		
	}
	
	public boolean canExecute(){
		if(oldId==null|| newId==null) return false;
		if(oldId.equals(newId)) return false;
		return true;
	}

}
