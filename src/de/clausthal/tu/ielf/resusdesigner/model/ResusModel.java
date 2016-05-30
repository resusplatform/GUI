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
import java.util.Vector;

import org.eclipse.swt.graphics.Image;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import de.clausthal.tu.ielf.resusdesigner.ResusMessages;







public class ResusModel extends ResusDiagram {

	static final long serialVersionUID = 1;

	
	public String[] inputPins={"1"};
	public String [] outputPins={"O1"};

	int numberOfInputs=1;
	int numberOfOutputs=1;
	
	private String name="";
	private String modelType="";
	private String workingDirectory="";
	
	private String executionParameters="";


	private String description="";
	private String executor="";
	private ArrayList<String> inputFileNames=new ArrayList<String>();;//some of executors just accept the exact name as input file. in this case we need to save our input in such a file and name it with this attribute
	private ArrayList<OutputPair> outputFileNames=new ArrayList<OutputPair>();;
	
	private ResusModelLogFile resusModelLogFile=new ResusModelLogFile();
	
	public ResusModelLogFile getResusModelLogFile() {
		return resusModelLogFile;
	}
	public void setResusModelLogFile(ResusModelLogFile resusModelLogFile) {
		this.resusModelLogFile = resusModelLogFile;
	}

	private long timeout;
	private boolean breakIfExitCodeNotZero;
	
		
	
	

	public void setNumberOfInputs(int numberOfInputs) {
		int tmp=this.numberOfInputs;
		if(numberOfInputs>=0){
			this.numberOfInputs = numberOfInputs;
			inputPins=new String[numberOfInputs];
			for(int i=1;i<=numberOfInputs;i++){
				inputPins[i-1]=String.valueOf(i);
			}
			
			firePropertyChange("numberOfInputs", null, numberOfInputs); //$NON-NLS-1$
			
		}
	
		
	}
	public void setNumberOfOutputs(int numberOfOutputs) {
		if(numberOfOutputs>=0){
			this.numberOfOutputs = numberOfOutputs;
			outputPins=new String[numberOfOutputs];
			for(int i=1;i<=numberOfOutputs;i++){
				outputPins[i-1]="O"+String.valueOf(i);
			}
		
			firePropertyChange("numberOfOutputs", null, numberOfOutputs); //$NON-NLS-1$
			
		}
	
		
	}
	
	
	public int getNumberOfInputs() {
		return numberOfInputs;
	}

	

	public int getNumberOfOutputs() {
		
			return numberOfOutputs;
	}

	
	
	
	 
	public String getWorkingDirectory() {
		return workingDirectory;
	}
	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}
	
	
	
	public String getExecutionParameters() {
		return executionParameters;
	}
	public void setExecutionParameters(String executionParameters) {
		this.executionParameters = executionParameters;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public ArrayList<String> getInputFileNames() {
		ArrayList<String> t=new ArrayList<String>(inputFileNames);
		return t;
	}
	public ArrayList<OutputPair> getOutputFileNames() {
		ArrayList<OutputPair> t=new ArrayList<OutputPair>(outputFileNames);
		return t;
	}
	public void setInputFileNames(ArrayList<String> inputFileNames) {
		this.inputFileNames = new  ArrayList<String>(inputFileNames);
	}

	
	public void setOutputFileNames(ArrayList<OutputPair> outputFileNames) {
		this.outputFileNames = new  ArrayList<OutputPair>(outputFileNames);
	}
	
	

	public String toString() {
		return ResusMessages.ResusModel_LabelText + " ( " +getName()+"[#"+getId()+"] )"; //$NON-NLS-1$
	}

	private static Image ResusModel_ICON = createImage(ResusModel.class,
			"icons/resusModel16.gif"); //$NON-NLS-1$

	public Image getIconImage() {
		return ResusModel_ICON;
	}
	
	public Node getXML(Document doc){
		try{
			
	 
			// Model elements
			
			Element modelRootElement =(Element)super.getXML(doc);
			
			if(doc==null) 
				return null;
			if (modelRootElement==null) 
				return null;
			//doc.createElement("model");
			//doc.appendChild(modelRootElement);
	 
			Element id = doc.createElement("id");
			id.appendChild(doc.createTextNode(this.getId()));
			modelRootElement.appendChild(id);
			
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(this.getName()));
			modelRootElement.appendChild(name);
			
			Element modelType = doc.createElement("modelType");
			modelType.appendChild(doc.createTextNode(this.getModelType()));
			modelRootElement.appendChild(modelType);
			
			Element description = doc.createElement("description");
			description.appendChild(doc.createTextNode(this.getDescription()));
			modelRootElement.appendChild(description);
			
			Element executor = doc.createElement("executor");
			executor.appendChild(doc.createTextNode(this.getExecutor()));
			modelRootElement.appendChild(executor);
			
			
			Element timeout = doc.createElement("timeout");
			timeout.appendChild(doc.createTextNode(String.valueOf(this.getTimeout())));
			modelRootElement.appendChild(timeout);
			
			Element breakIfExitCodeNotZero = doc.createElement("breakIfNotZero");
			breakIfExitCodeNotZero.appendChild(doc.createTextNode(String.valueOf(this.getBreakIfExitCodeNotZero())));
			modelRootElement.appendChild(breakIfExitCodeNotZero);
			
			
			Element workingDirectory = doc.createElement("workingDirectory");
			workingDirectory.appendChild(doc.createTextNode(this.getWorkingDirectory()));
			modelRootElement.appendChild(workingDirectory);
			
			
			Element executionParamteres = doc.createElement("executionParameters");
			executionParamteres.appendChild(doc.createTextNode(String.valueOf(this.getExecutionParameters())));
			modelRootElement.appendChild(executionParamteres);
			
			Element numberOfInputPins = doc.createElement("numberOfInputPins");
			numberOfInputPins.appendChild(doc.createTextNode(String.valueOf(this.getNumberOfInputs())));
			modelRootElement.appendChild(numberOfInputPins);
			
			
			Element numberOfOutputPins = doc.createElement("numberOfOutputPins");
			numberOfOutputPins.appendChild(doc.createTextNode(String.valueOf(this.getNumberOfOutputs())));
			modelRootElement.appendChild(numberOfOutputPins);
			
			
			int l=0;
			
			if(inputFileNames!=null) l=inputFileNames.size();
			for (int i=0;i<l;i++){
				Element inputFileName = doc.createElement("inputFileName");
				inputFileName.appendChild(doc.createTextNode(inputFileNames.get(i)));
				modelRootElement.appendChild(inputFileName);	
			}
			
			l=0;
			if(outputFileNames!=null) l=outputFileNames.size();
			
			
			for (int i=0;i<l;i++){
				Element outputFileName = doc.createElement("outputFile");
				Element FileName = doc.createElement("fileName");
				Element noLine = doc.createElement("numberOfLines");
				Element minFileSize = doc.createElement("minFileSize");
				Element breakIfHappend = doc.createElement("breakIfHappend");
				
				FileName.appendChild(doc.createTextNode(outputFileNames.get(i).getFileName()));
				noLine.appendChild(doc.createTextNode(outputFileNames.get(i).getNumberOfLines()));
				System.out.println(outputFileNames.get(i).getNumberOfLines());
				
				minFileSize.appendChild(doc.createTextNode(outputFileNames.get(i).getMinFileSize()));
				System.out.println(outputFileNames.get(i).getMinFileSize());
				
				breakIfHappend.appendChild(doc.createTextNode(Boolean.toString(outputFileNames.get(i).isBreakIfHappend())));
				System.out.println(outputFileNames.get(i).isBreakIfHappend());
				
				outputFileName.appendChild(FileName);
				outputFileName.appendChild(noLine);
				outputFileName.appendChild(minFileSize);
				outputFileName.appendChild(breakIfHappend);
				
				
				modelRootElement.appendChild(outputFileName);	
			}
			
			if(resusModelLogFile!=null){
				Element logFile = doc.createElement("logFile");
				
				Element logFileName = doc.createElement("fileName");
				logFileName.appendChild(doc.createTextNode(resusModelLogFile.getFileName()));
				
				Element logFileCriticalWords= doc.createElement("numberOfLines");
				logFileCriticalWords.appendChild(doc.createTextNode(resusModelLogFile.getCriticalWords()));
				
				Element breakIfHappend = doc.createElement("breakIfHappend");
				
				
				breakIfHappend.appendChild(doc.createTextNode(String.valueOf(resusModelLogFile.isBreakIfHappend())));
				
				
				logFile.appendChild(logFileName);
				logFile.appendChild(logFileCriticalWords);
				logFile.appendChild(breakIfHappend);
				
				modelRootElement.appendChild(logFile);	
			}
			
			return modelRootElement;
			
			
			
			
			
			
			
		}
		catch(Exception x){
			System.err.println("error ...");
		}
		return null;

	}
	protected String getPartType(){
		return "model";
	}
	
	public long getTimeout(){
	     return timeout ;
	}

	public void setTimeout(long _timeout){
	     timeout= _timeout;
	}

	public boolean getBreakIfExitCodeNotZero(){
	     return breakIfExitCodeNotZero ;
	}

	public void setBreakIfExitCodeNotZero(boolean _breakIfExitCodeNotZero){
	     breakIfExitCodeNotZero= _breakIfExitCodeNotZero;
	}


	
	
	
}
