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
package de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel;



import org.eclipse.ui.views.properties.tabbed.resus.properties.AbstractSection;

import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.model.commands.SectionCommand;

public class SetResusModelDescriptionCommand extends SectionCommand {

	private String oldDescription;
	private String newDescription;
	private ResusModel part;
	

	public void execute() {
		
		redo();
	}

	public String getLabel() {
		return "Change Model Description";
		
	}
	
	
	public void redo() {
		part.setDescription(newDescription);
		propSection.refresh();
	}

	public void setDescription(String r) {
		newDescription=r;
	}


	public void setPart(ResusModel part) {
		this.part = part;
		oldDescription = part.getDescription();
		
	}

	

	public void undo() {
		part.setDescription(oldDescription);
		propSection.refresh();
		
	}
	public boolean canExecute(){
		if(oldDescription==null || newDescription==null) return false;
		if(oldDescription.equals(newDescription)) return false;
		return true;
	}

}
