package de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel;



import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.ui.views.properties.tabbed.resus.properties.AbstractSection;

import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.model.commands.SectionCommand;

public class SetResusModelNumberOfInputPinsCommand extends SectionCommand {
	private int oldInputPins;
	private int newInputPins;
	private Dimension oldDimension;
	private Dimension newDimension;
	private ResusModel part;
	
	public void execute() {
		
		redo();
	}

	public String getLabel() {
		return "Change Model Number of InputPins";
//		if (oldInputPins.equals(newInputPins))
//			return LogicMessages.SetLocationCommand_Label_Location;
//		return LogicMessages.SetLocationCommand_Label_Resize;
	}
	
	

	public void redo() {
		int num= newInputPins;
		int num2=part.getNumberOfOutputs();
		int max=Math.max(num, num2);
		Dimension d=new Dimension();
		d.height=part.getSize().height;
		d.width=part.getSize().width;
		if(max>4) 
			d.height=(25*max)+25;
		
		part.setSize(d) ;
		
		part.setNumberOfInputs(num) ;
		
		
		
		
		propSection.refresh();
	}

	public void setInputPins(int r) {
		newInputPins=r;
	}


	public void setPart(ResusModel part) {
		this.part = part;
		oldInputPins = part.getNumberOfInputs();
		oldDimension=part.getSize();
	}

	

	public void undo() {
		part.setNumberOfInputs(oldInputPins);
		part.setSize(oldDimension);
		propSection.refresh();
		
	}
	
	public boolean canExecute(){
		if(oldInputPins<0|| newInputPins<0) return false;
		if(oldInputPins==newInputPins) return false;
		return true;
	}

}
