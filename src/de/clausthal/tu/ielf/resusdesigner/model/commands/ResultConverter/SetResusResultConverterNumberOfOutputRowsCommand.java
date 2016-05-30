package de.clausthal.tu.ielf.resusdesigner.model.commands.ResultConverter;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.ui.views.properties.tabbed.resus.properties.AbstractSection;

import de.clausthal.tu.ielf.resusdesigner.model.ResultConverter;
import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.model.commands.SectionCommand;

public class SetResusResultConverterNumberOfOutputRowsCommand extends SectionCommand {
	private int OldNumberOfRows;
	private int oldIndex;
	
	private int newNumberOfRows;
	private int newIndex;
	
	private ResultConverter part;
	
	public void execute() {
		
		
		redo();
	}

	public String getLabel() {
		return "Change Result Converter Number of Output Rows";
//		if (oldOutputPins.equals(newOutputPins))
//			return LogicMessages.SetLocationCommand_Label_Location;
//		return LogicMessages.SetLocationCommand_Label_Resize;
	}
	
	

	public void redo() {		
		if(newIndex==-1) newNumberOfRows=0;
		part.setNumberOfOutputRows(newNumberOfRows) ;
		part.setOutputAffectedIndex(newIndex);
		propSection.refresh();
	}

	public void setOutputRows(int numberOfRows,int colIndex) {
		newNumberOfRows=numberOfRows;
		newIndex=colIndex;
	}


	public void setPart(ResultConverter part) {
		this.part = part;
		OldNumberOfRows = part.getNumberOfOutputRows();
		oldIndex=part.getOutputAffectedIndex();
	}

	

	public void undo() {
		part.setNumberOfOutputRows(OldNumberOfRows);
		part.setOutputAffectedIndex(oldIndex);
		
		propSection.refresh();
		
	}
	
	public boolean canExecute(){
		
		if(OldNumberOfRows<0 || oldIndex<-1 || newNumberOfRows<0 || newIndex<-1) return false;
		if(OldNumberOfRows==newNumberOfRows&& oldIndex==newIndex) return false;
		return true;
	}

}
