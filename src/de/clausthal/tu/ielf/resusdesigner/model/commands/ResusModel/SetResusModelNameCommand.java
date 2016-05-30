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

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.resus.properties.AbstractSection;

import de.clausthal.tu.ielf.resusdesigner.ResusMessages;
import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.model.ResusSubpart;
import de.clausthal.tu.ielf.resusdesigner.model.commands.SectionCommand;

public class SetResusModelNameCommand extends SectionCommand {

	private String oldName;
	private String newName;
	private ResusModel part;


	public void execute() {
		
		
		redo();
	}

	public String getLabel() {
		return "Change Model Name";
//		if (oldName.equals(newName))
//			return LogicMessages.SetLocationCommand_Label_Location;
//		return LogicMessages.SetLocationCommand_Label_Resize;
	}
	
	

	public void redo() {
		part.setName(newName);
		propSection.refresh();
	}

	public void setName(String r) {
		newName=r;
	}


	public void setPart(ResusModel part) {
		this.part = part;
		oldName = part.getName().toString();
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
