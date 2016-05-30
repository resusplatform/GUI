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

public class SetResultConverterOutputFileCommand extends SectionCommand {

	private String oldOutputFile;
	private String newOutputFile;
	private ResultConverter part;


	public void execute() {
		
		
		redo();
	}

	public String getLabel() {
		return "Change ResultConverter OutputFile";

	}
	
	

	public void redo() {
		part.setOutputFileName(newOutputFile);
		propSection.refresh();
	}

	public void setOutputFile(String ex) {
		newOutputFile=ex;
	}


	public void setPart(ResultConverter part) {
		this.part = part;
		oldOutputFile = part.getOutputFileName();
	}

	

	public void undo() {
		part.setOutputFileName(oldOutputFile);
		propSection.refresh();
		
	}
	
	public boolean canExecute(){
		if(oldOutputFile==null || newOutputFile==null) return false;
		if(oldOutputFile.equals(newOutputFile)) return false;
		return true;
	}


}
