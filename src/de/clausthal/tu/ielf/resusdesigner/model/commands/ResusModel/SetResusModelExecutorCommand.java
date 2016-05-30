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

public class SetResusModelExecutorCommand extends SectionCommand {

	private String oldExecutor;
	private String newExecutor;
	private ResusModel part;
	

	public void execute() {
	
		
		redo();
	}

	public String getLabel() {
		return "Change Model Executor";
		
	}
	
	
	public void redo() {
		part.setExecutor(newExecutor);
		propSection.refresh();
	}

	public void setExecutor(String x) {
		newExecutor=x;
	}


	public void setPart(ResusModel part) {
		this.part = part;
		oldExecutor=part.getExecutor();
	}

	

	public void undo() {
		part.setExecutor(oldExecutor);
		propSection.refresh();
		
	}
	public boolean canExecute(){
		if(oldExecutor==null || newExecutor==null) return false;
		if(oldExecutor.equals(newExecutor)) return false;
		return true;
	}

}
