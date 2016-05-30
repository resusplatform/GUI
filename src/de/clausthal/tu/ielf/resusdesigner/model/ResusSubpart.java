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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import de.clausthal.tu.ielf.resusdesigner.ResusMessages;

abstract public class ResusSubpart extends ResusElement {

	
	private LogicGuide verticalGuide, horizontalGuide;
	protected Hashtable inputs = new Hashtable(7);
	protected Point location = new Point(0, 0);
	protected Vector outputs = new Vector(4, 4);
	
	
	static final long serialVersionUID = 1;
	protected Dimension size = new Dimension(-1, -1);
	protected int numberOfInputs=1;
	protected int numberOfOutputs=1;
	protected static IPropertyDescriptor[] descriptors = null;
	public static String ID_SIZE = "size"; //$NON-NLS-1$
	public static String ID_LOCATION = "location"; //$NON-NLS-1$
	public static String ID_NumberOfInputs="numberOfInputs";
	public static String ID_NumberOfOutputs="numberOfOutputs";
	static {
		descriptors = new IPropertyDescriptor[] {
				new PropertyDescriptor(ID_SIZE,
						ResusMessages.PropertyDescriptor_LogicSubPart_Size),
				new PropertyDescriptor(ID_LOCATION,
						ResusMessages.PropertyDescriptor_LogicSubPart_Location),
				new PropertyDescriptor(ID_NumberOfInputs, 
						"number OF INPUTS"),
				new PropertyDescriptor(ID_NumberOfOutputs, 
						"number OF OUTPUTS")
		};
	}

	protected static Image createImage(Class rsrcClass, String name) {
		InputStream stream = rsrcClass.getResourceAsStream(name);
		Image image = new Image(null, stream);
		try {
			stream.close();
		} catch (IOException ioe) {
		}
		return image;
	}

	public ResusSubpart() {
		setId(getNewID());
	}

	public void connectInput(Connector w) {
		inputs.put(w.getTargetTerminal(), w);
		update();
		fireStructureChange(INPUTS, w);
	}

	public void connectOutput(Connector w) {
		outputs.addElement(w);
		update();
		fireStructureChange(OUTPUTS, w);
	}

	public void disconnectInput(Connector w) {
		inputs.remove(w.getTargetTerminal());
		update();
		fireStructureChange(INPUTS, w);
	}

	public void disconnectOutput(Connector w) {
		outputs.removeElement(w);
		update();
		fireStructureChange(OUTPUTS, w);
	}

	public Vector getConnections() {
		Vector v = (Vector) outputs.clone();
		Enumeration ins = inputs.elements();
		while (ins.hasMoreElements())
			v.addElement(ins.nextElement());
		return v;
	}

	public LogicGuide getHorizontalGuide() {
		return horizontalGuide;
	}

	public Image getIcon() {
		return getIconImage();
	}

	abstract public Image getIconImage();

	

	protected boolean getInput(String terminal) {
		if (inputs.isEmpty()) {
			return false;
		}
		Connector w = (Connector) inputs.get(terminal);
		return (w == null) ? false : w.getValue();

	}

	public Point getLocation() {
		return location;
	}

	private static int IdGeneratoinSeed=0;
	 protected static String getNewID(){
		 return String.valueOf(IdGeneratoinSeed++);
	 }

	/**
	 * Returns useful property descriptors for the use in property sheets. this
	 * supports location and size.
	 * 
	 * @return Array of property descriptors.
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return descriptors;
	}

	/**
	 * Returns an Object which represents the appropriate value for the property
	 * name supplied.
	 * 
	 * @param propName
	 *            Name of the property for which the the values are needed.
	 * @return Object which is the value of the property.
	 */
	public Object getPropertyValue(Object propName) {
		if (ID_SIZE.equals(propName))
			return new DimensionPropertySource(getSize());
		else if (ID_LOCATION.equals(propName))
			return new LocationPropertySource(getLocation());
		else if (ID_NumberOfInputs.equals(propName))
			return numberOfInputs;
		else if (ID_NumberOfOutputs.equals(propName))
			return numberOfOutputs;
		
		return null;
	}

	public Dimension getSize() {
		return size;
	}

	public Vector getSourceConnections() {
		return (Vector) outputs.clone();
	}

	public Vector getTargetConnections() {
		Enumeration elements = inputs.elements();
		Vector v = new Vector(inputs.size());
		while (elements.hasMoreElements())
			v.addElement(elements.nextElement());
		return v;
	}

	public LogicGuide getVerticalGuide() {
		return verticalGuide;
	}

	/**
 * 
 */
	public boolean isPropertySet() {
		return true;
	}

	public void setHorizontalGuide(LogicGuide hGuide) {
		horizontalGuide = hGuide;
		/*
		 * @TODO:Pratik firePropertyChanged?
		 */
	}

	/*
	 * Does nothing for the present, but could be used to reset the properties
	 * of this to whatever values are desired.
	 * 
	 * @param id Parameter which is to be reset.
	 * 
	 * public void resetPropertyValue(Object id){ if(ID_SIZE.equals(id)){;}
	 * if(ID_LOCATION.equals(id)){;} }
	 */

	

	public void setLocation(Point p) {
		if (location.equals(p))
			return;
		location = p;
		firePropertyChange("location", null, p); //$NON-NLS-1$
	}

	protected void setOutput(String terminal, boolean val) {
		Enumeration elements = outputs.elements();
		Connector w;
		while (elements.hasMoreElements()) {
			w = (Connector) elements.nextElement();
			if (w.getSourceTerminal().equals(terminal)
					&& this.equals(w.getSource()))
				w.setValue(val);
		}
	}

	/**
	 * Sets the value of a given property with the value supplied. Also fires a
	 * property change if necessary.
	 * 
	 * @param id
	 *            Name of the parameter to be changed.
	 * @param value
	 *            Value to be set to the given parameter.
	 */
	public void setPropertyValue(Object id, Object value) {
		if (ID_SIZE.equals(id))
			setSize((Dimension) value);
		else if (ID_LOCATION.equals(id))
			setLocation((Point) value);
//		else if (ID_NumberOfInputs.equals(id))
//			this.numberOfInputs=(Integer)value;
	}

	public void setSize(Dimension d) {
		if (size.equals(d))
			return;
		size = d;
		firePropertyChange("size", null, size); //$NON-NLS-1$
	}

	public void setVerticalGuide(LogicGuide vGuide) {
		verticalGuide = vGuide;
	}
	
	public Node getXML(Document doc){
		try{		
			
			Element modelRootElement =doc.createElement(getPartType());
			doc.appendChild(modelRootElement);
			
			
			
			Element size = doc.createElement("size");
			
			Element heigth= doc.createElement("heigth");
			int a=this.size.height;
			heigth.appendChild(doc.createTextNode(String.valueOf(a)));

			
			
			Element width= doc.createElement("width");
			width.appendChild(doc.createTextNode(String.valueOf(this.size.width)));
			size.appendChild(width);
			size.appendChild(heigth);
			modelRootElement.appendChild(size);
			
			
			Element location= doc.createElement("location");
			Element x= doc.createElement("x");
			x.appendChild(doc.createTextNode((String.valueOf(this.location.x))));
			Element y= doc.createElement("y");
			y.appendChild(doc.createTextNode(String.valueOf(this.location.y)));
			location.appendChild(x);
			location.appendChild(y);
			modelRootElement.appendChild(location);
			
			
			
			
			
			
			
			
			
			return modelRootElement;
		}
		catch(Exception x){
		}
		return null;
	}
	
	
	public void getConnections(Document doc,Element modelRootElement){
		ArrayList<Node> resutl=new ArrayList<Node>();
		
		
		Iterator i = this.getTargetConnections().iterator();
		while (i.hasNext()) {
			Connector wire= (Connector) i.next();
			Element connection = doc.createElement("connection");
			
			// descrioption of source of connection
			Element connectionSource= doc.createElement("source");
			Element sourceId= doc.createElement("id");
			sourceId.appendChild(doc.createTextNode(wire.source.getId()));
			connectionSource.appendChild(sourceId);
				
			//modelRootElement.appendChild(connection);	
			
			Element connectionSourcePin= doc.createElement("pin");
			
			connectionSourcePin.appendChild(doc.createTextNode(wire.getSourceTerminal()));
			connectionSource.appendChild(connectionSourcePin);
			
			connection.appendChild(connectionSource);
				
			
			
			//description of target of connection
			Element connectionTarget= doc.createElement("target");
			Element targetId= doc.createElement("id");
			targetId.appendChild(doc.createTextNode(wire.target.getId()));
			connectionTarget.appendChild(targetId);
				
			//modelRootElement.appendChild(connection);	
			
			Element connectionTargetPin= doc.createElement("pin");
			
			connectionTargetPin.appendChild(doc.createTextNode(wire.getTargetTerminal()));
			connectionTarget.appendChild(connectionTargetPin);
			
			connection.appendChild(connectionTarget);
			
			
			
			modelRootElement.appendChild(connection);	
			
			
		
		}
		
		
		
	}
	
}
