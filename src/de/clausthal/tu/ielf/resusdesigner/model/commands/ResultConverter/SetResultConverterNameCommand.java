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

public class SetResultConverterNameCommand extends SectionCommand {

	private String oldName;
	private String newName;
	private ResultConverter part;


	public void execute() {
		
		
		redo();
	}

	public String getLabel() {
		return "Change ResultConverter Name";

	}
	
	

	public void redo() {
		part.setName(newName);
		propSection.refresh();
	}

	public void setName(String n) {
		newName=n;
	}


	public void setPart(ResultConverter part) {
		
		this.part = part;
		oldName = part.getName();
	}

	

	public void undo() {
		part.setName(oldName);
		propSection.refresh();
		
	}

	public boolean canExecute(){
		if(oldName==null || newName==null) return false;
		if(oldName.equals(newName)) return false;
		return true;
	}


}
