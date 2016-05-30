package de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.ui.views.properties.tabbed.resus.properties.AbstractSection;

import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.model.commands.SectionCommand;

public class SetResusModelNumberOfOutputPinsCommand extends SectionCommand {
	private int oldOutputPins;
	private int newOutputPins;
	private Dimension oldDimension;
	private Dimension newDimension;
	private ResusModel part;
	
	public void execute() {
		
		redo();
	}

	public String getLabel() {
		return "Change Model Number of OutputPins";
//		if (oldOutputPins.equals(newOutputPins))
//			return LogicMessages.SetLocationCommand_Label_Location;
//		return LogicMessages.SetLocationCommand_Label_Resize;
	}
	
	

	public void redo() {
		int num= newOutputPins;
		int num2=part.getNumberOfInputs();
		int max=Math.max(num, num2);
		Dimension d=new Dimension();
		d.height=part.getSize().height;
		d.width=part.getSize().width;
		if(max>4) 
			d.height=(25*max)+25;
		
		part.setSize(d) ;
		
		part.setNumberOfOutputs(num) ;
		
		
		
		
		propSection.refresh();
	}

	public void setOutputPins(int r) {
		newOutputPins=r;
	}


	public void setPart(ResusModel part) {
		this.part = part;
		oldOutputPins = part.getNumberOfOutputs();
		oldDimension=part.getSize();
	}

	

	public void undo() {
		part.setNumberOfOutputs(oldOutputPins);
		part.setSize(oldDimension);
		propSection.refresh();
		
	}
	
	public boolean canExecute(){
		if(oldOutputPins<0 || newOutputPins<0) return false;
		if(oldOutputPins==newOutputPins) return false;
		return true;
	}

}
