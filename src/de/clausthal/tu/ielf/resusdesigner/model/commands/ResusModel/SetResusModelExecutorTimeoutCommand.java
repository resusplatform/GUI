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

public class SetResusModelExecutorTimeoutCommand extends SectionCommand {

	private String oldtimeout;
	private String newtimeout;
	private ResusModel part;
	

	public void execute() {
	
		
		redo();
	}

	public String getLabel() {
		return "Change Model Executor Timeout";
		
	}
	
	
	public void redo() {
		part.setTimeout(Long.parseLong(newtimeout));
		propSection.refresh();
	}

	public void setTimeout(String t) {
		newtimeout=t;
		
	}


	public void setPart(ResusModel part) {
		this.part = part;
		oldtimeout=String.valueOf(part.getTimeout());
	}

	

	public void undo() {
		part.setTimeout(Long.parseLong(oldtimeout));
		propSection.refresh();
		
	}
	public boolean canExecute(){
		if(oldtimeout==null || newtimeout==null) return false;
		if(oldtimeout.equals(newtimeout)) return false;
		return true;
	}

}
