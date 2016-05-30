
package de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel;



import org.eclipse.ui.views.properties.tabbed.resus.properties.AbstractSection;

import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.model.commands.SectionCommand;

public class setResusModelExecutorWorkingDirectoryCommand extends SectionCommand {

	private String oldWorkingDirectory;
	private String newWorkingDirectory;
	private ResusModel part;
	

	public void execute() {
	
		
		redo();
	}

	public String getLabel() {
		return "Change Model WorkingDirectory Working Directory ";
		
	}
	
	
	public void redo() {
		part.setWorkingDirectory(newWorkingDirectory);
		propSection.refresh();
	}

	public void setWorkingDirectory(String x) {
		newWorkingDirectory=x;
	}


	public void setPart(ResusModel part) {
		this.part = part;
		oldWorkingDirectory=part.getWorkingDirectory();
	}

	

	public void undo() {
		part.setWorkingDirectory(oldWorkingDirectory);
		propSection.refresh();
		
	}
	public boolean canExecute(){
		if(oldWorkingDirectory==null || newWorkingDirectory==null) return false;
		if(oldWorkingDirectory.equals(newWorkingDirectory)) return false;
		return true;
	}

}
