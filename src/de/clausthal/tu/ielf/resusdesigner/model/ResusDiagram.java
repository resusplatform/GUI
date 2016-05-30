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
package de.clausthal.tu.ielf.resusdesigner.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.draw2d.PositionConstants;

import de.clausthal.tu.ielf.resusdesigner.ResusMessages;

public class ResusDiagram extends ResusSubpart {
	static final long serialVersionUID = 1;

	
	private static Image RESUS_ICON = createImage(ResusDiagram.class,
			"icons/can.gif"); //$NON-NLS-1$

	protected List children = new ArrayList();
	
	

	protected LogicRuler leftRuler, topRuler;
	protected Integer connectionRouter = null;
	private boolean rulersVisibility = false;
	private boolean snapToGeometry = false;
	private boolean gridEnabled = false;
	private double zoom = 1.0;

	public ResusDiagram() {
		size.width = 100;
		size.height = 100;
		location.x = 20;
		location.y = 20;
		createRulers();
	}

	public void addChild(ResusElement child) {
		addChild(child, -1);
	}
	

	
	public void addChild(ResusElement child, int index) {
		if (index >= 0)
			children.add(index, child);
		else
			children.add(child);
		fireChildAdded(CHILDREN, child, new Integer(index));
	}

	protected void createRulers() {
		leftRuler = new LogicRuler(false);
		topRuler = new LogicRuler(true);
	}

	public List getChildren() {
		return children;
	}

	
	public Image getIconImage() {
		return RESUS_ICON;
	}



	public double getZoom() {
		return zoom;
	}

	/**
	 * Returns <code>null</code> for this model. Returns normal descriptors for
	 * all subclasses.
	 * 
	 * @return Array of property descriptors.
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		
		return super.getPropertyDescriptors();
	}

	public Object getPropertyValue(Object propName) {
		
		return super.getPropertyValue(propName);
	}

	public LogicRuler getRuler(int orientation) {
		LogicRuler result = null;
		switch (orientation) {
		case PositionConstants.NORTH:
			result = topRuler;
			break;
		case PositionConstants.WEST:
			result = leftRuler;
			break;
		}
		return result;
	}

	public boolean getRulerVisibility() {
		return rulersVisibility;
	}

	public boolean isGridEnabled() {
		return gridEnabled;
	}

	public boolean isSnapToGeometryEnabled() {
		return snapToGeometry;
	}

	private void readObject(java.io.ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		s.defaultReadObject();
	}

	public void removeChild(ResusElement child) {
		children.remove(child);
		fireChildRemoved(CHILDREN, child);
	}

	

	public void setPropertyValue(Object id, Object value) {
	
		
			super.setPropertyValue(id, value);
	}

	public void setRulerVisibility(boolean newValue) {
		rulersVisibility = newValue;
	}

	public void setGridEnabled(boolean isEnabled) {
		gridEnabled = isEnabled;
	}

	public void setSnapToGeometry(boolean isEnabled) {
		snapToGeometry = isEnabled;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

	public String toString() {
		return ResusMessages.LogicDiagram_LabelText;
	}



}
