/*******************************************************************************
 * Copyright (c) 2000, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package de.clausthal.tu.ielf.resusdesigner.edit;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.AccessibleAnchorProvider;
import org.eclipse.gef.AutoexposeHelper;
import org.eclipse.gef.ExposeHelper;
import org.eclipse.gef.MouseWheelHelper;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.ViewportAutoexposeHelper;
import org.eclipse.gef.editparts.ViewportExposeHelper;
import org.eclipse.gef.editparts.ViewportMouseWheelHelper;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import de.clausthal.tu.ielf.resusdesigner.figures.FigureFactory;
import de.clausthal.tu.ielf.resusdesigner.figures.InputProviderFigure;
import de.clausthal.tu.ielf.resusdesigner.model.InputProvider;
import de.clausthal.tu.ielf.resusdesigner.model.ResusSubpart;

/**
 * Holds a circuit, which is a container capable of holding other
 * LogicEditParts.
 */
public class InputProviderEditPart extends ResusContainerEditPart 
		 {

	private InputProvider source;
	public InputProviderEditPart(InputProvider src){
		source=src;
	}
	protected void createEditPolicies() {
		super.createEditPolicies();
	}

	/**
	 * Creates a new Circuit Figure and returns it.
	 * 
	 * @return Figure representing the circuit.
	 */
	protected IFigure createFigure() {
		return FigureFactory.createNewInputProvider(source);
	}
	
	@Override
	public void performRequest(Request req) {
	    if(req.getType() == RequestConstants.REQ_OPEN) {
	        System.out.println("requested double-click.");
	        try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("org.eclipse.ui.views.PropertySheet");
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

	

	public Object getAdapter(Class key) {
		
		
		if (key == AutoexposeHelper.class)
			return new ViewportAutoexposeHelper(this);
		if (key == ExposeHelper.class)
			return new ViewportExposeHelper(this);
		if (key == AccessibleAnchorProvider.class)
			return new DefaultAccessibleAnchorProvider() {
				public List getSourceAnchorLocations() {
					List list = new ArrayList();
					Vector sourceAnchors = getNodeFigure()
							.getSourceConnectionAnchors();
					Vector targetAnchors = getNodeFigure()
							.getTargetConnectionAnchors();
					for (int i = 0; i < sourceAnchors.size(); i++) {
						ConnectionAnchor sourceAnchor = (ConnectionAnchor) sourceAnchors
								.get(i);
						ConnectionAnchor targetAnchor = (ConnectionAnchor) targetAnchors
								.get(i);
						list.add(new Rectangle(
								sourceAnchor.getReferencePoint(), targetAnchor
										.getReferencePoint()).getCenter());
					}
					return list;
				}

				public List getTargetAnchorLocations() {
					return getSourceAnchorLocations();
				}
			};
		if (key == MouseWheelHelper.class)
			return new ViewportMouseWheelHelper(this);
		return super.getAdapter(key);
	}

	/**
	 * Returns the Figure of this as a InputProviderFigure.
	 * 
	 * @return InputProviderFigure of this.
	 */
	protected InputProviderFigure getInputProviderBoardFigure() {
		return (InputProviderFigure) getFigure();
	}
	
	public void propertyChange(PropertyChangeEvent ev){
		 if (ev.getPropertyName().equals(ResusSubpart.ID_NumberOfInputs))			 			
			refreshVisuals();
		 else super.propertyChange(ev);
	}
	public void refreshVisuals(){
		InputProviderFigure figure = (InputProviderFigure)getFigure();
		figure.repaint();
		refreshSourceConnections();
		refreshTargetConnections();
		super.refreshVisuals();
	}


	

	

}
