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

public class SetResultConverterExecutorOutputFileNameCommand extends SectionCommand {

	private String oldExecutorOutputFileName;
	private String newExecutorOutputFileName;
	private ResultConverter part;


	public void execute() {
		
		
		redo();
	}

	public String getLabel() {
		return "Change ResultConverter Executor Output File Name";

	}
	
	

	public void redo() {
		part.setExecutorOutput(newExecutorOutputFileName);
		propSection.refresh();
	}

	public void setOutputFileName(String ex) {
		newExecutorOutputFileName=ex;
	}


	public void setPart(ResultConverter part) {
		
		this.part = part;
		oldExecutorOutputFileName = part.getExecutorOutput();
	}

	

	public void undo() {
		part.setExecutorOutput(oldExecutorOutputFileName);
		propSection.refresh();
		
	}
	
	public boolean canExecute(){
		if(oldExecutorOutputFileName==null || newExecutorOutputFileName==null) return false;
		if(oldExecutorOutputFileName.equals(newExecutorOutputFileName)) return false;
		return true;
	}

}
