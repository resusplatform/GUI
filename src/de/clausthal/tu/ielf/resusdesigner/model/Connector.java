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

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.Bendpoint;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Connector extends ResusElement {

	static final long serialVersionUID = 1;
	protected boolean value;
	protected ResusSubpart source, target;
	protected String sourceTerminal, targetTerminal;
	protected List bendpoints = new ArrayList();

	public void attachSource() {
		if (getSource() == null
				|| getSource().getSourceConnections().contains(this))
			return;
		getSource().connectOutput(this);
	}

	public void attachTarget() {
		if (getTarget() == null
				|| getTarget().getTargetConnections().contains(this))
			return;
		getTarget().connectInput(this);
	}

	public void detachSource() {
		if (getSource() == null)
			return;
		getSource().disconnectOutput(this);
	}

	public void detachTarget() {
		if (getTarget() == null)
			return;
		getTarget().disconnectInput(this);
	}

	public List getBendpoints() {
		return bendpoints;
	}

	public ResusSubpart getSource() {
		return source;
	}

	public String getSourceTerminal() {
		return sourceTerminal;
	}

	public ResusSubpart getTarget() {
		return target;
	}

	public String getTargetTerminal() {
		return targetTerminal;
	}

	public boolean getValue() {
		return value;
	}

	public void insertBendpoint(int index, Bendpoint point) {
		getBendpoints().add(index, point);
		firePropertyChange("bendpoint", null, null);//$NON-NLS-1$
	}

	public void removeBendpoint(int index) {
		getBendpoints().remove(index);
		firePropertyChange("bendpoint", null, null);//$NON-NLS-1$
	}

	public void setBendpoint(int index, Bendpoint point) {
		getBendpoints().set(index, point);
		firePropertyChange("bendpoint", null, null);//$NON-NLS-1$
	}

	public void setBendpoints(Vector points) {
		bendpoints = points;
		firePropertyChange("bendpoint", null, null);//$NON-NLS-1$
	}

	public void setSource(ResusSubpart e) {
		Object old = source;
		source = e;
		firePropertyChange("source", old, source);//$NON-NLS-1$
	}

	public void setSourceTerminal(String s) {
		Object old = sourceTerminal;
		sourceTerminal = s;
		firePropertyChange("sourceTerminal", old, sourceTerminal);//$NON-NLS-1$
	}

	public void setTarget(ResusSubpart e) {
		Object old = target;
		target = e;
		firePropertyChange("target", old, target);//$NON-NLS-1$
	}

	public void setTargetTerminal(String s) {
		Object old = targetTerminal;
		targetTerminal = s;
		firePropertyChange("targetTerminal", old, targetTerminal);//$NON-NLS-1$
	}

	public void setValue(boolean value) {
		if (value == this.value)
			return;
		this.value = value;
		if (target != null)
			target.update();
		firePropertyChange("value", null, null);//$NON-NLS-1$
	}

	public String toString() {
		return "Wire(" + getSource() + "," + getSourceTerminal() + "->" + getTarget() + "," + getTargetTerminal() + ")";//$NON-NLS-5$//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
	}

	public Node getXML(Document doc){
		try{
			
	 
			// connection
			
			Element modelRootElement =(Element)super.getXML(doc);
			
				
	 
			Element id = doc.createElement("id");
			id.appendChild(doc.createTextNode(this.getId()));
			modelRootElement.appendChild(id);
			
//			Element numberOfInputPins = doc.createElement("numberOfInputPins");
//			numberOfInputPins.appendChild(doc.createTextNode(String.valueOf(this.getNumberOfInputs())));
//			modelRootElement.appendChild(numberOfInputPins);
//			
			
			
			
			
			
		
			
			
			
			
			return modelRootElement;
		
		}
		catch(Exception x){
			System.err.println("error ...");
		}
		return null;

	}
	protected String getPartType(){
		return "connection";
	}
}
