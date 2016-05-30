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
package de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel;



import java.util.ArrayList;

import org.eclipse.ui.views.properties.tabbed.resus.properties.AbstractSection;

import de.clausthal.tu.ielf.resusdesigner.model.OutputPair;
import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.model.commands.SectionCommand;

public class SetResusModelOutputFilesCommand extends SectionCommand {

	private ArrayList<OutputPair> oldFiles;
	private ArrayList<OutputPair> newFiles;
	private ResusModel part;
	

	public void execute() {
		
		
		redo();
	}

	public String getLabel() {
		return "Change Model Output Files";
		
	}
	
	
	public void redo() {
		part.setOutputFileNames(newFiles);
		propSection.refresh();
	}

	public void setOutputFiles(ArrayList<OutputPair> f) {
		newFiles=f;
		
	}


	public void setPart(ResusModel part) {
		this.part = part;
		oldFiles= part.getOutputFileNames();
	}

	

	public void undo() {
		part.setOutputFileNames(oldFiles);
		propSection.refresh();
		
	}
	public boolean canExecute(){
		if(oldFiles==null || newFiles==null) return false;
		if(oldFiles.equals(newFiles)) return false;
		return true;
	}

}
