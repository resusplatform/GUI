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

public class SetResultConverterInputFileCommand extends SectionCommand {

	private String oldInputFile;
	private String newInputFile;
	private ResultConverter part;


	public void execute() {
		
		
		redo();
	}

	public String getLabel() {
		return "Change ResultConverter InputFile";

	}
	
	

	public void redo() {
		part.setInputFileName(newInputFile);
		propSection.refresh();
	}

	public void setInputFile(String ex) {
		newInputFile=ex;
	}


	public void setPart(ResultConverter part) {
		this.part = part;
		oldInputFile = part.getInputFileName();
	}

	

	public void undo() {
		part.setInputFileName(oldInputFile);
		propSection.refresh();
		
	}
	public boolean canExecute(){
		if(oldInputFile==null || newInputFile==null) return false;
		if(oldInputFile.equals(newInputFile)) return false;
		return true;
	}


}
