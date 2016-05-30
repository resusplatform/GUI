package org.eclipse.ui.views.properties.tabbed.resus.properties.Model;
import org.eclipse.jface.viewers.IFilter;

import de.clausthal.tu.ielf.resusdesigner.edit.ResusModelEditPart;
import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;


public class ModelFilter implements IFilter {

	@Override
	public boolean select(Object toTest) {
		if(toTest instanceof ResusModelEditPart)
			return true;
		return false;
	}

}
