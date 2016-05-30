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

public class setResusModelExecutorLogFileCriticalWordsCommand extends SectionCommand {

	private String oldCriticalWords;
	private String newCriticalWords;
	private ResusModel part;
	

	public void execute() {
	
		
		redo();
	}

	public String getLabel() {
		return "Change Model Log File Critical Words";
		
	}
	
	
	public void redo() {
		part.getResusModelLogFile().setCriticalWords(newCriticalWords);
		propSection.refresh();
	}

	public void setCriticalWords(String t) {
		newCriticalWords=t;
		
	}


	public void setPart(ResusModel part) {
		this.part = part;
		oldCriticalWords=part.getResusModelLogFile().getCriticalWords();
	}

	

	public void undo() {
		part.getResusModelLogFile().setCriticalWords(oldCriticalWords);		
		propSection.refresh();
		
	}
	public boolean canExecute(){
		if(oldCriticalWords==null || newCriticalWords==null) return false;
		if(oldCriticalWords.equals(newCriticalWords)) return false;
		return true;
	}

}




