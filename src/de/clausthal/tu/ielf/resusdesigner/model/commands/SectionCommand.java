package de.clausthal.tu.ielf.resusdesigner.model.commands;

import org.eclipse.ui.views.properties.tabbed.resus.properties.AbstractSection;

import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;

public class SectionCommand extends org.eclipse.gef.commands.Command {

	
	protected AbstractSection propSection;
		
	public void setPropertySection(AbstractSection sec)
	{
		propSection=sec;
	}

}
