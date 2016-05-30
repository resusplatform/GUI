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
import org.eclipse.gef.editparts.ViewportAutoexposeHelper;
import org.eclipse.gef.editparts.ViewportExposeHelper;
import org.eclipse.gef.editparts.ViewportMouseWheelHelper;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import de.clausthal.tu.ielf.resusdesigner.figures.FigureFactory;
import de.clausthal.tu.ielf.resusdesigner.figures.ResultConverterFigure;
import de.clausthal.tu.ielf.resusdesigner.model.ResultConverter;
import de.clausthal.tu.ielf.resusdesigner.model.ResusSubpart;

/**
 * Holds a circuit, which is a container capable of holding other
 * LogicEditParts.
 */
public class ResultConverterEditPart extends ResusContainerEditPart 
		 {

	

	private ResultConverter source;
	public ResultConverterEditPart(ResultConverter src){
		source=src;
	}
	protected void createEditPolicies() {
		super.createEditPolicies();
//		installEditPolicy(EditPolicy.LAYOUT_ROLE, new LogicXYLayoutEditPolicy(
//				(XYLayout) getContentPane().getLayoutManager()));
//		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE,
//				new ContainerHighlightEditPolicy());
//		installEditPolicy(SCROLLABLE_SELECTION_FEEDBACK,
//				new ScrollableSelectionFeedbackEditPolicy());
	}

	/**
	 * Creates a new Circuit Figure and returns it.
	 * 
	 * @return Figure representing the circuit.
	 */
	protected IFigure createFigure() {
		return FigureFactory.createNewResultConverter(source);
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
	 * Returns the Figure of this as a ResultConverterFigure.
	 * 
	 * @return ResultConverterFigure of this.
	 */
	protected ResultConverterFigure getResultConverterBoardFigure() {
		return (ResultConverterFigure) getFigure();
	}
	
	public void propertyChange(PropertyChangeEvent ev){
		 if (ev.getPropertyName().equals(ResusSubpart.ID_NumberOfInputs))			 			
			refreshVisuals();//refreshVisuals();
		 else super.propertyChange(ev);
	}
	public void refreshVisuals(){
		ResultConverterFigure figure = (ResultConverterFigure)getFigure();
		figure.repaint();
		refreshSourceConnections();
		refreshTargetConnections();
		super.refreshVisuals();
	}


	

	

}
