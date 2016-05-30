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

public class SetResultConverterNumberOfOutputRowsCommand extends SectionCommand {

	private int oldRows;
	private int newRows;
	private ResultConverter part;


	public void execute() {
		
		
		redo();
	}

	public String getLabel() {
		return "Change ResultConverter Number Of Output Rows";

	}
	
	

	public void redo() {
		part.setNumberOfOutputRows(newRows);
		propSection.refresh();
	}

	public void setOutputRows(int rows) {
		newRows=rows;
	}


	public void setPart(ResultConverter part) {
		this.part = part;
		oldRows = part.getNumberOfOutputRows();
	}

	

	public void undo() {
		part.setNumberOfOutputRows(oldRows);
		propSection.refresh();
		
	}
	public boolean canExecute(){
		if(oldRows<0 || newRows<0) return false;
		if(oldRows==newRows) return false;
		return true;
	}


}
