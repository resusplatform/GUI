package de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel;

import org.eclipse.ui.views.properties.tabbed.resus.properties.AbstractSection;

import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.model.commands.SectionCommand;

public class SetResusModelModelTypeCommand extends SectionCommand {
	private String oldModelType;
	private String newModelType;
	private ResusModel part;
	

	public void execute() {
		
		redo();
	}

	public String getLabel() {
		return "Change Model ModelType";
//		if (oldModelType.equals(newModelType))
//			return LogicMessages.SetLocationCommand_Label_Location;
//		return LogicMessages.SetLocationCommand_Label_Resize;
	}
	
	

	public void redo() {
		part.setModelType(newModelType);
		propSection.refresh();
	}

	public void setModelType(String r) {
		newModelType=r;
	}


	public void setPart(ResusModel part) {
		this.part = part;
		oldModelType = part.getModelType();		
	}

	

	public void undo() {
		part.setModelType(oldModelType);
		propSection.refresh();
		
	}
	
	public boolean canExecute(){
		if(oldModelType==null || newModelType==null) return false;
		if(oldModelType.equals(newModelType)) return false;
		return true;
	}

}
