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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.xml.sax.Attributes;  

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.swt.graphics.Image;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.clausthal.tu.ielf.resusdesigner.ResusMessages;



public class ResultConverter extends ResusDiagram {

	static final long serialVersionUID = 1;

	private static int count;

	public String[] InputPins = { "1" };
	public String outputPin = "OUT";
	private String name = "";
	private String executor = "";
	private String executorWorkingDirectory="";
	private String executorParameter="";
	private String executorOutput="";
	

	private String inputFileName = "";
	private String outputFileName = "";
	private String regex = "";
	private String columnDelimiter = "";
	private ArrayList<IndexPairs> columnsIndexes = new ArrayList<IndexPairs>();

	private int numberOfOutputRows = 0;// number of outputrows
	private int outputAffectedIndex = -1; // index of seleceted column

	public void setOutputFileName(String outputfileName) {
		this.outputFileName = outputfileName;
	}

	public int getNumberOfOutputRows() {
		return numberOfOutputRows;
	}

	public void setNumberOfOutputRows(int numberOfOutputRows) {
		this.numberOfOutputRows = numberOfOutputRows;
	}

	public int getOutputAffectedIndex() {
		return outputAffectedIndex;
	}

	public void setOutputAffectedIndex(int outputAffectedIndex) {
		this.outputAffectedIndex = outputAffectedIndex;
	}

	public ArrayList<IndexPairs> getColumnsIndexes() {
		ArrayList<IndexPairs> t = new ArrayList<IndexPairs>(columnsIndexes);
		return t;
	}

	public void setColumnsIndexes(ArrayList<IndexPairs> columnsIndexes) {
		this.columnsIndexes = new ArrayList<IndexPairs>(columnsIndexes);
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getName() {
		if(name.equals(""))
			return "RC"+id.toString();
		return name;
	}

	public void setName(String nm) {
		this.name = nm;
	}

	public String getColumnDelimiter() {
		return columnDelimiter;
	}

	public void setColumnDelimiter(String columnDelimiter) {
		this.columnDelimiter = columnDelimiter;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getInputFileName() {

		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public int getNumberOfInputs() {
		return numberOfInputs;
	}

	public void setNumberOfInputs(int numberOfInputs) {
		if (numberOfInputs >= 0) {
			this.numberOfInputs = numberOfInputs;
			InputPins = new String[numberOfInputs];
			for (int i = 1; i <= numberOfInputs; i++) {
				InputPins[i - 1] = String.valueOf(i);
			}
			System.out.println("property changed");
			firePropertyChange("size", null, null); //$NON-NLS-1$

		}

	}

	public String toString() {
		return ResusMessages.ResusResultConverter_LabelText + " ( " +getName()+"[#"+getId()+"] )";
	}

	private static Image ResultConverter_ICON = createImage(ResultConverter.class,
			"icons/ResultConverter16.gif"); //$NON-NLS-1$

	public Image getIconImage() {
		return ResultConverter_ICON;
	}

	
	public String getExecutorWorkingDirectory() {
		return executorWorkingDirectory;
	}

	public void setExecutorWorkingDirectory(String executorWorkingDirectory) {
		this.executorWorkingDirectory = executorWorkingDirectory;
	}

	public String getExecutorParameter() {
		return executorParameter;
	}

	public void setExecutorParameter(String executorParameter) {
		this.executorParameter = executorParameter;
	}

	public String getExecutorOutput() {
		return executorOutput;
	}

	public void setExecutorOutput(String executorOutput) {
		this.executorOutput = executorOutput;
	}
	

	public Node getXML(Document doc) {
		try {

			// Model elements

			Element modelRootElement = (Element) super.getXML(doc);

			if (doc == null)
				return null;
			if (modelRootElement == null)
				return null;

			Element id = doc.createElement("id");
			id.appendChild(doc.createTextNode(this.getId()));
			modelRootElement.appendChild(id);

			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(this.getName()));
			modelRootElement.appendChild(name);

			Element numberOfInputPins = doc.createElement("numberOfInputPins");
			numberOfInputPins.appendChild(doc.createTextNode(String
					.valueOf(this.getNumberOfInputs())));
			modelRootElement.appendChild(numberOfInputPins);

			Element regex = doc.createElement("regex");
			regex.appendChild(doc.createTextNode(this.getRegex()));
			modelRootElement.appendChild(regex);

			Element columnDelimiter = doc.createElement("columnDelimiter");
			columnDelimiter.appendChild(doc.createTextNode(String.valueOf(this
					.getColumnDelimiter())));
			modelRootElement.appendChild(columnDelimiter);

			Element executor = doc.createElement("executor");
			Element executorFileName=doc.createElement("fileName");
			executorFileName.appendChild(doc.createTextNode(this.executor));
			
			Element executorWorkingDirecotry=doc.createElement("workingDirecotry");
			executorWorkingDirecotry.appendChild(doc.createTextNode(this.executorWorkingDirectory));
			
			Element executorExecutorParameter=doc.createElement("executorParameter");
			executorExecutorParameter.appendChild(doc.createTextNode(this.executorParameter));
			
			Element executorOutputFileName=doc.createElement("executorOutputFileName");
			executorOutputFileName.appendChild(doc.createTextNode(this.executorOutput));
			
			
			
			
			executor.appendChild(executorFileName);
			executor.appendChild(executorWorkingDirecotry);
			executor.appendChild(executorExecutorParameter);
			executor.appendChild(executorOutputFileName);
			
			
			modelRootElement.appendChild(executor);

			Element inputFileName = doc.createElement("inputFileName");
			inputFileName.appendChild(doc.createTextNode(String.valueOf(this
					.getInputFileName())));
			modelRootElement.appendChild(inputFileName);

			Element outputFileName = doc.createElement("outputFileName");
			outputFileName.appendChild(doc.createTextNode(String.valueOf(this
					.getOutputFileName())));
			modelRootElement.appendChild(outputFileName);

			Element outputrows = doc.createElement("numberOfOutputRows");
			outputrows.appendChild(doc.createTextNode(String.valueOf(this
					.getNumberOfOutputRows())));
			modelRootElement.appendChild(outputrows);

			Element outputAffectedIndex = doc
					.createElement("outputAffectedIndex");
			outputAffectedIndex.appendChild(doc.createTextNode(String
					.valueOf(this.getOutputAffectedIndex())));
			modelRootElement.appendChild(outputAffectedIndex);

			Iterator<IndexPairs> itr = columnsIndexes.iterator();
			if (itr != null && columnsIndexes != null)
				while (itr.hasNext()) {

					Element index = doc.createElement("index");
					Element indexId = doc.createElement("id");
					Element indexTag = doc.createElement("tag");
					Element indexUnit = doc.createElement("unit");
					Element indexCoefficient = doc.createElement("coefficient");
					Element indexForward = doc.createElement("forward");
					IndexPairs key = itr.next();
					indexId.appendChild(doc.createTextNode(String
							.valueOf(key.id)));
					indexTag.appendChild(doc.createTextNode(String
							.valueOf(key.tag)));
					indexUnit.appendChild(doc.createTextNode(String
							.valueOf(key.unit)));
					indexForward.appendChild(doc.createTextNode(String
							.valueOf(key.forward)));
					indexCoefficient.appendChild(doc.createTextNode(String
							.valueOf(key.coefficient)));
					index.appendChild(indexId);
					index.appendChild(indexTag);
					index.appendChild(indexUnit);
					index.appendChild(indexForward);
					index.appendChild(indexCoefficient);
					modelRootElement.appendChild(index);
				}

			return modelRootElement;

		} catch (Exception x) {
			System.err.println("error ...");
		}
		return null;

	}

	protected String getPartType() {
		return "resultConverter";
	}

	

	

	
		
	
	public ResusModel getConnectedModel(){
		ResusModel m=null;
		
		if(this.inputs.size()>0)
		{
			Connector w=(Connector)inputs.values().iterator().next();
			m=(ResusModel)w.getSource();			
		}
		return m;
	}
	
}
