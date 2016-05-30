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
package de.clausthal.tu.ielf.resusdesigner.model.commands.IOProvider;


import java.util.ArrayList;

import de.clausthal.tu.ielf.resusdesigner.model.IOProvider;
import de.clausthal.tu.ielf.resusdesigner.model.commands.SectionCommand;

public class SetIOProviderInputFilesCommand extends SectionCommand {

	private ArrayList<String> oldFiles;
	private ArrayList<String> newFiles;
	private IOProvider part;

	public void execute() {
				
		redo();
	}

	public String getLabel() {
		return "Change IOProvider InputFiles";
	}	

	public void redo() {
		part.setFileNames(newFiles);
		propSection.refresh();
	}

	public void setFileNames(ArrayList<String> s) {
		newFiles=new ArrayList<String>(s);
	}

	public void setPart(IOProvider part) {
		this.part = part;
		oldFiles = new  ArrayList<String>(part.getFileNames());
	}	

	public void undo() {
		part.setFileNames(oldFiles);
		
		propSection.refresh();		
	}
	
	
	public boolean canExecute(){
		if(oldFiles==null || newFiles==null) return false;
		if(oldFiles.equals(newFiles)) return false;
		return true;
	}
	
	
}
