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

public class SetResusModelExecutorParametersCommand extends SectionCommand {

	private String oldParams;
	private String newParams;
	private ResusModel part;
	

	public void execute() {
		
		
		redo();
	}

	public String getLabel() {
		return "Change Model Exec Parameters";
		
	}
	
	
	public void redo() {
		part.setExecutionParameters(newParams);
		propSection.refresh();
	}

	public void setExecutorParameters(String p) {
		newParams=p;
	}


	public void setPart(ResusModel part) {
		this.part = part;
		oldParams= part.getExecutionParameters();
	}

	

	public void undo() {
		part.setExecutionParameters(oldParams);
		propSection.refresh();
		
	}
	public boolean canExecute(){
		if(oldParams==null || newParams==null) return false;
		if(oldParams.equals(newParams)) return false;
		return true;
	}

}
