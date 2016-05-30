package de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel;
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



import org.eclipse.ui.views.properties.tabbed.resus.properties.AbstractSection;

import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.model.commands.SectionCommand;

public class setResusModelExecutorLogFileFileNameCommand extends SectionCommand {

	private String oldFileName;
	private String newFileName;
	private ResusModel part;
	

	public void execute() {
	
		
		redo();
	}

	public String getLabel() {
		return "Change Model Log File Name";
		
	}
	
	
	public void redo() {
		part.getResusModelLogFile().setFileName(newFileName);
		propSection.refresh();
	}

	public void setFileName(String t) {
		newFileName=t;
		
	}


	public void setPart(ResusModel part) {
		this.part = part;
		oldFileName=part.getResusModelLogFile().getFileName();
	}

	

	public void undo() {
		part.getResusModelLogFile().setFileName(oldFileName);
		propSection.refresh();
		
	}
	public boolean canExecute(){
		if(oldFileName==null || newFileName==null) return false;
		if(oldFileName.equals(newFileName)) return false;
		return true;
	}

}




