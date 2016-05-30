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

public class SetResultConverterColDelimiterCommand extends SectionCommand {

	private String oldCD;
	private String newCD;
	private ResultConverter part;


	public void execute() {
		
		
		redo();
	}

	public String getLabel() {
		return "Change ResultConverter Regex Column Delimiter";

	}
	
	

	public void redo() {
		part.setColumnDelimiter(newCD);
		propSection.refresh();
	}

	public void setColumnDelimiter(String cd) {
		newCD=cd;
	}


	public void setPart(ResultConverter part) {
		
		this.part = part;
		oldCD = part.getColumnDelimiter();
	}

	

	public void undo() {
		part.setColumnDelimiter(oldCD);
		propSection.refresh();
		
	}
	
	public boolean canExecute(){
		if(oldCD==null || newCD ==null) return false;
		if(oldCD.equals(newCD)) return false;
		return true;
	}

	

}
