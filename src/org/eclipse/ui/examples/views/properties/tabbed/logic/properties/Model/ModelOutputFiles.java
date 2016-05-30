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
package org.eclipse.ui.examples.views.properties.tabbed.logic.properties.Model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;

import org.eclipse.jface.viewers.ViewerCell;



import org.eclipse.swt.SWT;

import org.eclipse.swt.custom.TableEditor;


import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;


import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;


import org.eclipse.ui.examples.views.properties.tabbed.logic.properties.AbstractSection;




import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;


import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import de.clausthal.tu.ielf.resus.RowNumberLabelProvider;

import de.clausthal.tu.ielf.resusdesigner.ResusMessages;

import de.clausthal.tu.ielf.resusdesigner.model.OutputPair;
import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.SetResusModelOutputFilesCommand;






/**

 * The location section on the location tab.
 * 
 * @author Javad Ghofrani
 */
public class ModelOutputFiles extends AbstractSection {
	
	
	
	
	private Text txtOutputFileName;
	private Text txtNumberOfLines;
	private Text txtFileSize;
	
	
	
	private  Button btnAddToList;
	private TableViewer viewer;
	 
	List<OutputPair> outputFileNames;// =new ArrayList<String>();
	Map<Object, Button> removeButtons ;//= new HashMap<Object, Button>();
	Map<Object, Button> editButtons ;//= new HashMap<Object, Button>();
		
	Label lblOutputfile ;
	Label lblNumerOfLines;
	Label lblFileSize;
	Label lblBytes;
	 Button chkBreakOperation;
	
	
	

	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		
		outputFileNames =new ArrayList<OutputPair>();
		removeButtons = new HashMap<Object, Button>();
		editButtons = new HashMap<Object, Button>();
	
		
		
		final Composite composite = getWidgetFactory().createFlatFormComposite(parent);
		composite.setBounds(28, 10, 900, 252);
		composite.setLayout(null);
		
		
		
		///------------- output file field
		lblOutputfile = new Label(composite, SWT.NONE);
		lblOutputfile.setBounds(10, 15, 55, 15);
		lblOutputfile.setText("File Name");
		
		
		
		
		
		txtOutputFileName = new Text(composite, SWT.BORDER);
		txtOutputFileName .setBounds(105, 15, 300, 21);

		lblNumerOfLines = new Label(composite, SWT.NONE);
		lblNumerOfLines.setBounds(10, 45, 55, 15);
		lblNumerOfLines.setText("lines");
		
		txtNumberOfLines = new Text(composite, SWT.BORDER);
		txtNumberOfLines .setBounds(105, 45, 50, 21);
		
		
		
		/// minimum size label and textbox
		lblFileSize = new Label(composite, SWT.NONE);
		lblFileSize.setBounds(220, 45, 85, 15);
		lblFileSize.setText("Minimum Size ");
		
		txtFileSize = new Text(composite, SWT.BORDER);
		txtFileSize.setBounds(320, 45, 50, 21);
		
		
		lblBytes= new Label(composite, SWT.NONE);
		lblBytes.setBounds(390, 45, 85, 15);
		lblBytes.setText("Bytes ");
		
		
		
		chkBreakOperation=new Button(composite, SWT.CHECK);
		chkBreakOperation.setBounds(10, 75, 255, 15);
		chkBreakOperation.setText("break the operation ");
		
		
		
		Button btnBrows = new Button(composite, SWT.NONE);
		btnBrows.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
				{
					
				FileDialog fileDialog = new FileDialog(composite.getShell());
			    // Set the text
			    fileDialog.setText("Select File");
			    // Set filter on .txt files
			    fileDialog.setFilterExtensions(new String[] { "*.*" });
			    // Put in a readable name for the filter
			    fileDialog.setFilterNames(new String[] { "All Files(*.*)" });
			    // Open Dialog and save result of selection
//			    if(!txtWorkingDirectory.getText().trim().equals(""))
//			    	fileDialog.setFilterPath(txtWorkingDirectory.getText());			    
			    
			    String selected = fileDialog.open();
			    //btnAddToList.setEnabled(true);
			    if(selected!=null)
			    	txtOutputFileName .setText(selected.substring(selected.lastIndexOf("\\")+1));
			   //else btnAddToList.setEnabled(false); 
			    

					 
				}
		});
		btnBrows.setBounds(415, 13, 75, 25);
		btnBrows.setText("brows");
		
		 btnAddToList = new Button(composite, SWT.NONE);
		 
		btnAddToList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
				{
				if(txtOutputFileName .getText().trim().equals("")) return;	
				OutputPair op=new OutputPair();
				op.setFileName(txtOutputFileName.getText().trim());
				op.setNumberOfLines(txtNumberOfLines.getText().trim());
				op.setMinFileSize(txtFileSize.getText().trim());
				
				op.setBreakIfHappend(chkBreakOperation.getSelection());
				
				if(!outputFileNames.contains(op))
					{
					outputFileNames.add(op );
						//((ResusModel)getElement()).setInputFileNames((ArrayList<String>)inputFileNames);
						setOutputFiles(outputFileNames);
						
					}
				else{
						MessageBox messageBox = new MessageBox(composite.getShell(), SWT.ICON_ERROR| SWT.OK );
				        messageBox.setText("Error");
				        messageBox.setMessage("Your selected file is already in list");
				        System.out.println("errror ");

				        return;
				}
						
				//ListChanged();
				viewer.setInput(outputFileNames);
				viewer.refresh();
					 
				}

			
		});
		btnAddToList.setBounds(15,103, 95, 25);
		btnAddToList.setText("Add To List");
		
		
		//-----------------------------------------table-------------------
		viewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);

		final Table table_1 = viewer.getTable();
		table_1.setBounds(5, 136, 685, 132);
		
        viewer.getTable().setHeaderVisible(true);
        viewer.getTable().setLinesVisible(true);
        viewer.setContentProvider(new ArrayContentProvider());

        TableColumn column = new TableColumn(viewer.getTable(), SWT.NONE);
        column.setText(" Row ");
        column.setWidth(50);
        TableViewerColumn rowCol = new TableViewerColumn(viewer, column);
        rowCol.setLabelProvider(new RowNumberLabelProvider());

        column = new TableColumn(viewer.getTable(), SWT.NONE);
        column.setText("File Name");
        column.setWidth(300);
        TableViewerColumn firstNameCol = new TableViewerColumn(viewer, column);
        firstNameCol.setLabelProvider(new ColumnLabelProvider(){

            @Override
            public String getText(Object element) {
            	OutputPair p = (OutputPair)element;
                return p.getFileName();
            }

        });
        
        column = new TableColumn(viewer.getTable(), SWT.NONE);
        column.setText("Number Of Lines");
        column.setWidth(105);
        TableViewerColumn linesCol = new TableViewerColumn(viewer, column);
        linesCol.setLabelProvider(new ColumnLabelProvider(){

            @Override
            public String getText(Object element) {
                OutputPair p = (OutputPair)element;
                return p.getNumberOfLines();
            }

        });
        
        column = new TableColumn(viewer.getTable(), SWT.NONE);
        column.setText("Min File Size");
        column.setWidth(85);
        TableViewerColumn MinFileSizeCol = new TableViewerColumn(viewer, column);
        MinFileSizeCol.setLabelProvider(new ColumnLabelProvider(){

            @Override
            public String getText(Object element) {
                OutputPair p = (OutputPair)element;
                return p.getMinFileSize();
            }

        });
        
        column = new TableColumn(viewer.getTable(), SWT.NONE);
        column.setText("Break");
        column.setWidth(50);
        TableViewerColumn breakCol = new TableViewerColumn(viewer, column);
        breakCol.setLabelProvider(new ColumnLabelProvider(){

            @Override
            public String getText(Object element) {
                OutputPair p = (OutputPair)element;
                return Boolean.toString(p.isBreakIfHappend());
            }

        });
        
        
        
        column = new TableColumn(viewer.getTable(), SWT.NONE);
        column.setText("Remove");
        column.setWidth(80);
        TableViewerColumn actionsNameCol = new TableViewerColumn(viewer, column);
        
    	
        actionsNameCol.setLabelProvider(new ColumnLabelProvider(){
            //make sure you dispose these buttons when viewer input changes
            //final Map<Object, Button> 
        	


            @Override
            public void update(ViewerCell cell) {
            	
            	TableItem item = (TableItem) cell.getItem();
                Button button;
                if(removeButtons.containsKey(cell.getElement()))
                {
                    button = removeButtons.get(cell.getElement());
                }
                else
                {
                    button = new Button((Composite) cell.getViewerRow().getControl(),SWT.NONE);
                    button.setText("Remove");
                    final OutputPair pair=(OutputPair)cell.getElement();
                    button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) 
						{
							outputFileNames.remove(pair);
							
							
							
							((Button)e.getSource()).dispose();
							removeButtons.remove(pair);
							
							
							
							
							setOutputFiles(outputFileNames);
							
							viewer.setInput(outputFileNames);
							viewer.refresh();
						}
                    });
                    removeButtons.put(cell.getElement(), button);
                }
                TableEditor editor = new TableEditor(item.getParent());
                editor.grabHorizontal  = true;
                editor.grabVertical = true;
                editor.setEditor(button , item, cell.getColumnIndex());
                editor.layout();
            }

        });
        
        
        
        
        
        
        viewer.setInput(outputFileNames);

        
        
    
        Label lblHelpTip = new Label(composite, SWT.WRAP | SWT.BORDER);
		lblHelpTip.setText(ResusMessages.Model_OutputFiles_information);
		lblHelpTip.setBounds(composite.getBounds().width+composite.getLocation().x, 10, 500, 200);
		
		
		
		
		
		
		
	}
	 private void setOutputFiles(List<OutputPair> outputFileNames) {
			
			SetResusModelOutputFilesCommand rnc=new SetResusModelOutputFilesCommand();
			rnc.setPart((ResusModel)getElement());				
			rnc.setOutputFiles((ArrayList<OutputPair>)outputFileNames);						
			runCommand(rnc);
		}
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if(!(getElement() instanceof ResusModel)) throw new AssertionError("Object is not a Model");
		
		
		
		
		
		try {
			
			Iterator<Object> itr=removeButtons.keySet().iterator();
			while (itr.hasNext())
				removeButtons.get(itr.next()).dispose();

			itr=editButtons.keySet().iterator();
			while (itr.hasNext())
				editButtons.get(itr.next()).dispose();
			removeButtons=new Hashtable<Object, Button>();
			editButtons=new Hashtable<Object, Button>();
			
			
			
			if(((ResusModel) getElement()).getOutputFileNames()!=null)
				outputFileNames=((ResusModel) getElement()).getOutputFileNames();
			else outputFileNames=new ArrayList<OutputPair>();
			

			viewer.setInput(outputFileNames);
			viewer.refresh();
			
		} finally {
			
				
		}
	}
}
