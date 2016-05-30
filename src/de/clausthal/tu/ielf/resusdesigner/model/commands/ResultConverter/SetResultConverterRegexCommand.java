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

public class SetResultConverterRegexCommand extends SectionCommand {

	private String oldRegex;
	private String newRegex;
	private ResultConverter part;


	public void execute() {
		
		
		redo();
	}

	public String getLabel() {
		return "Change ResultConverter Regex Text";

	}
	
	

	public void redo() {
		part.setRegex(newRegex);
		propSection.refresh();
	}

	public void setRegex(String r) {
		newRegex=r;
	}


	public void setPart(ResultConverter part) {
		
		this.part = part;
		oldRegex = part.getRegex();
	}

	

	public void undo() {
		part.setRegex(oldRegex);
		propSection.refresh();
		
	}

	public boolean canExecute(){
		if(oldRegex==null || newRegex==null) return false;
		if(oldRegex.equals(newRegex)) return false;
		return true;
	}


}
