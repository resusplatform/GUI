package de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel;

import org.eclipse.ui.views.properties.tabbed.resus.properties.AbstractSection;

import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.model.commands.SectionCommand;

public class setResusModelExecutorLogFileBreakIfHappendCommand extends SectionCommand {


	private boolean oldSelection;
	private boolean newSelection;
	private ResusModel part;
	

	public void execute() {
	
		
		redo();
	}

	public String getLabel() {
		return "Change Model Log File Break If Critical Words Found";
		
	}
	
	
	public void redo() {
		part.getResusModelLogFile().setBreakIfHappend(newSelection);
		propSection.refresh();
	}

	public void setSelection(boolean t) {
		newSelection=t;
		
	}


	public void setPart(ResusModel part) {
		this.part = part;
		oldSelection=part.getResusModelLogFile().isBreakIfHappend();
	}

	

	public void undo() {
		part.getResusModelLogFile().setBreakIfHappend(oldSelection);
		propSection.refresh();
		
	}
	public boolean canExecute(){
		
		if(oldSelection== newSelection) return false;
		return true;
	}

}

