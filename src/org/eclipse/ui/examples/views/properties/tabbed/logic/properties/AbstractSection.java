/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.examples.views.properties.tabbed.logic.properties;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;

import de.clausthal.tu.ielf.resusdesigner.ResusEditor;
import de.clausthal.tu.ielf.resusdesigner.model.ResusElement;
import de.clausthal.tu.ielf.resusdesigner.model.commands.SectionCommand;

/**
 * Abstract class for a section in a tab in the properties view.
 * 
 * @author Anthony Hunter 
 */
public abstract class AbstractSection
	extends AbstractPropertySection implements PropertyChangeListener {

	private ResusElement element;

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#setInput(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		if (!(selection instanceof IStructuredSelection)) throw new AssertionError("object type not passed");
		
		Object input = ((IStructuredSelection)selection).getFirstElement();
		if (!(input instanceof EditPart)) throw new AssertionError("object is not an edit part");
		
		Object model = ((EditPart) input).getModel();
		if(!(model instanceof ResusElement)) throw new AssertionError("object is not a logicelement");
		this.element = (ResusElement) model;
		
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#aboutToBeShown()
	 */
	public void aboutToBeShown() {
		getElement().addPropertyChangeListener(this);
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#aboutToBeHidden()
	 */
	public void aboutToBeHidden() {
		getElement().removePropertyChangeListener(this);
	}

	/**
	 * Get the element.
	 * @return the element.
	 */
	public ResusElement getElement() {
		return element;
	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		refresh();
	}
	
	public void runCommand(SectionCommand  cmd)
	{
		if(cmd.canExecute()){
			
			IEditorPart e = 
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor(); 
						CommandStack c; 
						if (e instanceof ResusEditor) { 
						ResusEditor editor = (ResusEditor) e; 
						c = (CommandStack) editor.getAdapter(CommandStack.class); 

						}else { 
						c = (CommandStack)getPart().getAdapter(CommandStack.class); 
						} 

						cmd.setPropertySection(this);

						c.execute(cmd); 
						
		}
	}
		
	
}
