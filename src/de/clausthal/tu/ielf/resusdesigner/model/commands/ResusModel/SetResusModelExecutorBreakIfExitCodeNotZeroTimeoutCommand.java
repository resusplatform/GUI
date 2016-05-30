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

public class SetResusModelExecutorBreakIfExitCodeNotZeroTimeoutCommand extends SectionCommand {

	private boolean oldBreak;
	private boolean newBreak;
	private ResusModel part;
	

	public void execute() {
	
		
		redo();
	}

	public String getLabel() {
		return "Change Model Executor Break If Exit Code Is Not Zero";
		
	}
	
	
	public void redo() {
		part.setBreakIfExitCodeNotZero(newBreak);
		propSection.refresh();
	}

	public void setBreakIfExitCodeIsNotZero(boolean b) {
		newBreak=b;
	}


	public void setPart(ResusModel part) {
		this.part = part;
		this.oldBreak=part.getBreakIfExitCodeNotZero();
	}

	

	public void undo() {
		part.setBreakIfExitCodeNotZero(oldBreak);
		propSection.refresh();
		
	}
	public boolean canExecute(){
		
		if(oldBreak==newBreak) return false;
		return true;
	}

}
