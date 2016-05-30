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


import java.util.ArrayList;

import de.clausthal.tu.ielf.resusdesigner.model.IndexPairs;
import de.clausthal.tu.ielf.resusdesigner.model.ResultConverter;
import de.clausthal.tu.ielf.resusdesigner.model.commands.SectionCommand;

public class SetResultConverterColumnIndexesCommand extends SectionCommand {

	private ArrayList<IndexPairs> oldIndexes;
	private ArrayList<IndexPairs> newIndexes;
	private ResultConverter part;


	public void execute() {
		
		
		redo();
	}

	public String getLabel() {
		return "Change ResultConverter Regex Column Delimiter";

	}	

	public void redo() {
		part.setColumnsIndexes(newIndexes);
		propSection.refresh();
	}

	public void setColumnIndexes(ArrayList<IndexPairs> i) {
		newIndexes=i;
	}


	public void setPart(ResultConverter part) {
		oldIndexes = part.getColumnsIndexes();
		this.part = part;
	}

	

	public void undo() {
		part.setColumnsIndexes(oldIndexes);
		propSection.refresh();
		
	}
	
	public boolean canExecute(){
		if(oldIndexes==null || newIndexes==null) return false;
		if(oldIndexes.equals(newIndexes)) return false;
		return true;
	}
}
