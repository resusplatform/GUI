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

public class SetResultConverterOutputAffectedIndexCommand extends SectionCommand {

	private int oldIndex;
	private int newIndex;
	private ResultConverter part;


	public void execute() {
			
		redo();
	}

	public String getLabel() {
		return "Change ResultConverter Selected Column";

	}
	
	

	public void redo() {
		part.setOutputAffectedIndex(newIndex);
		propSection.refresh();
	}

	public void setOutputIndex(int idx) {
		newIndex=idx;
	}


	public void setPart(ResultConverter part) {
		this.part = part;
		oldIndex = part.getOutputAffectedIndex();	
	}

	

	public void undo() {
		part.setOutputAffectedIndex(oldIndex);
		propSection.refresh();
		
	}
	
	
	public boolean canExecute(){
		if(oldIndex<0 || newIndex<0) return false;
		if(oldIndex==newIndex) return false;
		return true;
	}


}
