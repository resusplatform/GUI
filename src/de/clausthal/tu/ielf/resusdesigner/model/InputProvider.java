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
import java.util.Iterator;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import de.clausthal.tu.ielf.resusdesigner.ResusMessages;

public class InputProvider extends ResusDiagram {

	
	static final long serialVersionUID = 1;	
	
	public String[] InputPins={"1"};
	public String outputPin="OUT";
	private String logFileName="";
	private String index="";
	private ArrayList<String> fileNames=new ArrayList<String>();
	
		
	
	public int getNumberOfInputs() {
		return numberOfInputs;
	}


	public void setNumberOfInputs(int numberOfInputs) {
		if(numberOfInputs>=0){
			this.numberOfInputs = numberOfInputs;
			InputPins=new String[numberOfInputs];
			for(int i=1;i<=numberOfInputs;i++){
				InputPins[i-1]=String.valueOf(i);
			}
			//System.out.println("property changed");
			firePropertyChange(ID_NumberOfInputs, null, null); //$NON-NLS-1$
			
			
		}
	
		
	}
	
	public String getIndex(){
		return index;
	}


	public void setIndex(String idx){
		index=idx;
	}
	public String getLogFileName() {
		return logFileName;
	}


	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}


	public ArrayList<String> getFileNames() {
		ArrayList<String> t=new ArrayList<String>(fileNames);
		return t;
	}

	public void setFileNames(ArrayList<String> fileNames) {
		this.fileNames = new ArrayList<String>( fileNames);
	}

	

	public String toString() {
		return ResusMessages.ResusIOProvider_LabelText+ " #" + getId(); //$NON-NLS-1$
	}
	private static Image IOProvider_ICON = createImage(InputProvider.class,
			"icons/ResusIOProvider16.gif"); //$NON-NLS-1$

	public Image getIconImage() {
		return IOProvider_ICON;
	}

	
	public Node getXML(Document doc){
		try{
			
	 
			// Model elements
			
			Element modelRootElement =(Element)super.getXML(doc);
			
			if(doc==null) 
				return null;
			if (modelRootElement==null) 
				return null;
		
	 
			Element id = doc.createElement("id");
			id.appendChild(doc.createTextNode(this.getId()));
			modelRootElement.appendChild(id);
			
			Element numberOfInputPins = doc.createElement("numberOfInputPins");
			numberOfInputPins.appendChild(doc.createTextNode(String.valueOf(this.getNumberOfInputs())));
			modelRootElement.appendChild(numberOfInputPins);
			
			Element logfile = doc.createElement("logFile");
			logfile.appendChild(doc.createTextNode(this.getLogFileName()));
			modelRootElement.appendChild(logfile);
			
			
			Element index = doc.createElement("index");
			index.appendChild(doc.createTextNode(String.valueOf(this.getIndex())));
			modelRootElement.appendChild(index);
			
			
			int l=0;
			
			if(fileNames!=null) l=fileNames.size();
			for (int i=0;i<l;i++){
				Element parametersFileName = doc.createElement("parametersFileName");
				parametersFileName.appendChild(doc.createTextNode(fileNames.get(i)));
				modelRootElement.appendChild(parametersFileName);	
			}			
			
			
			return modelRootElement;
		
		}
		catch(Exception x){
			System.err.println("error generating xml node for IOProvider Id "+this.id);
		}
		return null;

	}
	protected String getPartType(){
		return "IOProvider";
	}

}