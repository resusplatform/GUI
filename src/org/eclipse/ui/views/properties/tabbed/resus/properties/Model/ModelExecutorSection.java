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
package org.eclipse.ui.views.properties.tabbed.resus.properties.Model;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;











import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.resus.properties.AbstractSection;
import org.eclipse.ui.views.properties.tabbed.resus.properties.TextChangeHelper;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import de.clausthal.tu.ielf.resusdesigner.ResusMessages;
import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.SetResusModelExecutorBreakIfExitCodeNotZeroTimeoutCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.SetResusModelExecutorCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.SetResusModelExecutorParametersCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.SetResusModelExecutorTimeoutCommand;






/**

 * The location section on the location tab.
 * 
 * @author Javad Ghofrani
 */
public class ModelExecutorSection extends AbstractSection {
	
	
	
	
	
	private Text txtExecParams;
	
	
	private Text txtExecutor;
	
	private Text txtTimeout; // in seconds
	private Button chkExitCode;
	
	 
	List<String> inputFileNames;// =new ArrayList<String>();
	Map<Object, Button> removeButtons ;//= new HashMap<Object, Button>();
	Map<Object, Button> editButtons ;//= new HashMap<Object, Button>();
		
	 
	
	 
	private TextChangeHelper ExecutorFieldChangelistener = new TextChangeHelper() {
	public void textChanged(Control control) {
		String workingDir=((ResusModel) getElement()).getWorkingDirectory();		
		Path path=Paths.get(workingDir).resolve(Paths.get(txtExecutor.getText().trim()));
		   if(validateExecutor(path,workingDir)==false){
			   ((Text)control).setText(((ResusModel)getElement()).getExecutor());
				return;
		   }
				SetResusModelExecutorCommand rnc=new SetResusModelExecutorCommand();
				rnc.setPart((ResusModel)getElement());				
				rnc.setExecutor(txtExecutor.getText().trim());
				
				runCommand(rnc);
			}
	};	
		
	private TextChangeHelper TimeoutFieldChangelistener = new TextChangeHelper() {
		public void textChanged(Control control) {
				
					try{
						Long.parseLong(txtTimeout.getText().trim());
					}
					catch(NumberFormatException x){
						MessageDialog.openError(null,"invalid Format","The inserted value for timeout is not valid. just long values are allowed");
						txtTimeout.setText(String.valueOf(((ResusModel) getElement()).getTimeout()));
						return;
					}
			
					
					SetResusModelExecutorTimeoutCommand rnc=new SetResusModelExecutorTimeoutCommand();
					rnc.setPart((ResusModel)getElement());				
					rnc.setTimeout(txtTimeout.getText().trim());
					
					runCommand(rnc);
				}
		};	
	
	private TextChangeHelper ChkIfExitCodeIsZeorChangelistener = new TextChangeHelper() {
		public void textChanged(Control control) {
					
					SetResusModelExecutorBreakIfExitCodeNotZeroTimeoutCommand rnc=new SetResusModelExecutorBreakIfExitCodeNotZeroTimeoutCommand();
					rnc.setPart((ResusModel)getElement());				
					rnc.setBreakIfExitCodeIsNotZero(chkExitCode.getSelection());
					
					runCommand(rnc);
				}
		};	
	
	private TextChangeHelper ExecParamsFieldChangelistener = new TextChangeHelper() {
		public void textChanged(Control control) {
			//if(((Text)control).getText().trim().equals(""))
//					((ResusModel) getElement()).setExecutionParameters(txtExecParams.getText().trim());
			SetResusModelExecutorParametersCommand rnc=new SetResusModelExecutorParametersCommand();
			rnc.setPart((ResusModel)getElement());				
			rnc.setExecutorParameters(txtExecParams.getText().trim());
			
			runCommand(rnc);
			
			}
		};	
	 /**
	

	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		
		inputFileNames =new ArrayList<String>();
		removeButtons = new HashMap<Object, Button>();
		editButtons = new HashMap<Object, Button>();
	
		
		
		final Composite composite = getWidgetFactory().createFlatFormComposite(parent);
		composite.setBounds(28, 10, 900, 252);
		composite.setLayout(null);
		
		
		
		
		
		
		///----------------------- executor field------------------
		Label lblExecutor= new Label(composite, SWT.NONE);
		lblExecutor.setBounds(5, 15,95, 15);
		lblExecutor.setText("Executor");
		
		
		txtExecutor = new Text(composite, SWT.BORDER);
		txtExecutor .setBounds(105, 15, 500, 21);
		
		Button btnBrowsExecutor = new Button(composite, SWT.NONE);
		btnBrowsExecutor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
				{
				
								
				FileDialog fileDialog = new FileDialog(composite.getShell(),SWT.OPEN);
			    // Set the text
			    fileDialog.setText("Select File");
			    String workingDir=((ResusModel) getElement()).getWorkingDirectory();
			    
			    if(workingDir==null|| workingDir.equals("")){
			    	MessageDialog.openError(null, "Invalid Working Directory", "Please set a working Directory ");
			    	return;
			    }
			    
			    
			    
			    if(!workingDir.equals(""))
			    	fileDialog.setFilterPath(workingDir);
			    // Set filter on .exe and .bat files
			    fileDialog.setFilterExtensions(new String[] { "*.exe;*.bat"});
			    // Put in a readable name for the filter
			    fileDialog.setFilterNames(new String[] { "All Executoable  Files(*.exe|*.bat)" });
			    
			    
			    //Open Dialog and save result of selection
			    String selected = fileDialog.open();
			    
			    if(selected==null)
			    	return;
			    
			   Path path=Paths.get(selected);
			   if(validateExecutor(path,workingDir)==true){
			    
			    
			    
			    	txtExecutor.setText(path.getFileName().toString());
			    	ExecutorFieldChangelistener.textChanged(txtExecutor);
			   }
			    
			    	//((ResusModel) getElement()).setExecutor(txtExecutor.getText());
				}
		});
		btnBrowsExecutor.setBounds(610, 15, 75, 25);
		btnBrowsExecutor.setText("Browse");
		
		
		
		Label lblExecParams = new Label(composite, SWT.NONE);
		lblExecParams.setBounds(5, 41, 85, 15);
		lblExecParams.setText("Exe Parameters");
		
		
		txtExecParams = new Text(composite, SWT.BORDER);
		txtExecParams .setBounds(105, 41, 500, 21);
		
		
		// ----------- timeout field
		Label lblTimeOut = new Label(composite, SWT.NONE);
		lblTimeOut.setBounds(5, 71, 85, 15);
		lblTimeOut.setText("Timeout (s)");
		
		
		txtTimeout = new Text(composite, SWT.BORDER);
		txtTimeout.setBounds(105,71, 100, 21);
		
		
		
		
		// ----------- break if exitcode not zero
		 chkExitCode = new Button(composite, SWT.CHECK);
		 chkExitCode.setBounds(300, 71, 200, 15);
		chkExitCode.setText("Break if Exit Code is not 0");
		
		
		
		
		
		
		
       
		
        
            
       

		
		
        Label lblHelpTip = new Label(composite, SWT.WRAP | SWT.BORDER);
		lblHelpTip.setText(ResusMessages.Model_Executor_information);
		lblHelpTip.setBounds(composite.getBounds().width+composite.getLocation().x, 10, 500, 200);

			
		
		
		
		
		ExecParamsFieldChangelistener.startListeningForEnter(txtExecParams);
		ExecParamsFieldChangelistener.startListeningTo(txtExecParams);
				
		ExecutorFieldChangelistener.startListeningForEnter(txtExecutor);
		//ExecutorFieldChangelistener.startListeningTo(txtExecutor);
		
		TimeoutFieldChangelistener.startListeningForEnter(txtTimeout);
		//TimeoutFieldChangelistener.startListeningTo(txtTimeout);
		
		ChkIfExitCodeIsZeorChangelistener.startListeningTo(chkExitCode);
	
		
	}
	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if(!(getElement() instanceof ResusModel)) throw new AssertionError("Object is not a Model");
		
		
		ExecParamsFieldChangelistener.startNonUserChange();	
		TimeoutFieldChangelistener.startNonUserChange();
		ExecutorFieldChangelistener.startNonUserChange();		
		
		
		
		try {
			
			
			
			txtExecutor.setText(((ResusModel) getElement()).getExecutor());
			
			txtExecParams.setText(((ResusModel) getElement()).getExecutionParameters());
			txtTimeout.setText(String.valueOf(((ResusModel) getElement()).getTimeout()));
			chkExitCode.setSelection(((ResusModel) getElement()).getBreakIfExitCodeNotZero());
			
//			if(((ResusModel) getElement()).getInputFileNames()!=null)
//				inputFileNames=((ResusModel) getElement()).getInputFileNames();
//			else inputFileNames=new ArrayList<String>();
//			

			
			
		} finally {
			
			ExecParamsFieldChangelistener.finishNonUserChange();	
			TimeoutFieldChangelistener.finishNonUserChange();
			ExecutorFieldChangelistener.finishNonUserChange();
			
		}
	}
	
	
	private boolean validateExecutor(Path path, String workingdir){
		 //Path path=Paths.get(selected);
		
		//check the existence of the working directory
		
		if(workingdir=="" || workingdir.equals("")||workingdir==null || workingdir.equals(null)){
			MessageDialog.openError(null,"invalid Working Directory", "please select a valid path as working directory file");	    	
			return false;
		}
		
		    //check the file existence
		    if(path.toFile().exists()==false){
		    	MessageDialog.openError(null,"invalid executor file", "please select a valid executable file");
		    	return false;
		    }
		    
//		    //check the file execution allowed
//		    if(path.toFile().canExecute()==false ){
//		    	MessageDialog.openError(null,"invalid executor file", "please select a valid executable file");
//		    	return false;
//		    }
		    
		    
		    //check the extension 
		    if(path.getFileName().toString().endsWith("exe")==false &&  path.getFileName().toString().endsWith("bat")==false){
		    	MessageDialog.openError(null,"invalid executor file", "please select a valid executable (*.exe) or (*.bat) file");
		    	return false;
		    }
		    
		    //check to be file and not a directory
		    if(path.toFile().isDirectory()==true){
		    	MessageDialog.openError(null,"invalid executor file", "please select a valid executable file");
		    	return false;
		    }
		  
		    // check to be located in working directory
		    if(path.getParent().compareTo(Paths.get(workingdir))!=0){
		    	MessageDialog.openError(null,"invalid executor file", "please select a executable file in the defined working directory for model frame");
		    	return false;
		    }
		    return true;
	}
	
}
