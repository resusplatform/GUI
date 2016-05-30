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


import de.clausthal.tu.ielf.resusdesigner.model.InputProvider;
import de.clausthal.tu.ielf.resusdesigner.model.commands.SectionCommand;

public class SetInputProviderLogFileCommand extends SectionCommand {

	private String oldLogFile;
	private String newLogFile;
	private InputProvider part;


	public void execute() {
		
		
		redo();
	}

	public String getLabel() {
		return "Change InputProvider LogFileName";
	}
	
	

	public void redo() {
		part.setLogFileName(newLogFile);
		propSection.refresh();
	}

	public void setLogFile(String LogFile) {
		newLogFile=LogFile;
	}


	public void setPart(InputProvider part) {
		
		this.part = part;
		oldLogFile = part.getLogFileName();
	}

	

	public void undo() {
		part.setLogFileName(oldLogFile);
		propSection.refresh();
		
	}
	
	public boolean canExecute(){
		if(oldLogFile==null || newLogFile==null) return false;
		if(oldLogFile.equals(newLogFile)) return false;
		return true;
	}

}
