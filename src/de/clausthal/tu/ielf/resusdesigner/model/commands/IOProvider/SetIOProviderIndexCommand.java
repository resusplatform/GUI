
package de.clausthal.tu.ielf.resusdesigner.model.commands.IOProvider;


import de.clausthal.tu.ielf.resusdesigner.model.IOProvider;
import de.clausthal.tu.ielf.resusdesigner.model.commands.SectionCommand;

public class SetIOProviderIndexCommand extends SectionCommand {

	private String oldIndex;
	private String newIndex;
	private IOProvider part;


	public void execute() {
		
		oldIndex = part.getIndex();
		
		redo();
	}

	public String getLabel() {
		return "Change IOProvider Index";

	}
	
	

	public void redo() {
		part.setIndex(newIndex);
		propSection.refresh();
	}

	public void setIndex(String idx) {
		newIndex=idx;
	}


	public void setPart(IOProvider part) {
		this.part = part;
		oldIndex = part.getId();
	}

	

	public void undo() {
		part.setId(oldIndex);
		propSection.refresh();
		
	}
	
	public boolean canExecute(){
		if(oldIndex==null|| newIndex==null) return false;
		if(oldIndex.equals(newIndex)) return false;
		return true;
	}

}
