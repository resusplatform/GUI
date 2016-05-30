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
package de.clausthal.tu.ielf.resusdesigner.model.commands.ResultConverter;


import de.clausthal.tu.ielf.resusdesigner.model.ResultConverter;
import de.clausthal.tu.ielf.resusdesigner.model.commands.SectionCommand;

public class SetResultConverterWorkingDirectoryCommand extends SectionCommand {

	private String oldWorkingDirectory;
	private String newWorkingDirectory;
	private ResultConverter part;


	public void execute() {
		
		
		redo();
	}

	public String getLabel() {
		return "Change ResultConverter WorkingDirectory";

	}
	
	

	public void redo() {
		part.setExecutorWorkingDirectory(newWorkingDirectory);
		propSection.refresh();
	}

	public void setWorkingDirectory(String ex) {
		newWorkingDirectory=ex;
	}


	public void setPart(ResultConverter part) {
		
		this.part = part;
		oldWorkingDirectory = part.getExecutorWorkingDirectory();
	}

	

	public void undo() {
		part.setExecutorWorkingDirectory(oldWorkingDirectory);
		propSection.refresh();
		
	}
	
	public boolean canExecute(){
		if(oldWorkingDirectory==null || newWorkingDirectory==null) return false;
		if(oldWorkingDirectory.equals(newWorkingDirectory)) return false;
		return true;
	}

}
