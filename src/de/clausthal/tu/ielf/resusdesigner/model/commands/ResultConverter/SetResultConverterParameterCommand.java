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

public class SetResultConverterParameterCommand extends SectionCommand {

	private String oldParameter;
	private String newParameter;
	private ResultConverter part;


	public void execute() {
		
		
		redo();
	}

	public String getLabel() {
		return "Change ResultConverter Executor Parameter";

	}
	
	

	public void redo() {
		part.setExecutorParameter(newParameter);
		propSection.refresh();
	}

	public void setParameter(String ex) {
		newParameter=ex;
	}


	public void setPart(ResultConverter part) {
		
		this.part = part;
		oldParameter = part.getExecutorParameter();
	}

	

	public void undo() {
		part.setExecutorParameter(oldParameter);
		propSection.refresh();
		
	}
	
	public boolean canExecute(){
		if(oldParameter==null || newParameter==null) return false;
		if(oldParameter.equals(newParameter)) return false;
		return true;
	}

}
