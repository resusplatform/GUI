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



import java.nio.file.Path;
import java.nio.file.Paths;





import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.examples.views.properties.tabbed.logic.properties.AbstractSection;
import org.eclipse.ui.examples.views.properties.tabbed.logic.properties.TextChangeHelper;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import de.clausthal.tu.ielf.resusdesigner.ResusMessages;
import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.setResusModelExecutorWorkingDirectoryCommand;

/**
 * The Model General Information  section on the Model General Information tab.
 * 
 * @author Javad Ghofrani
 */
public class ModelWorkingDirSection extends AbstractSection {
	
	//private Text txtID;
//	private Text txtExecutorPath;
	
	private Text txtWorkingDirectory;
	
	AbstractSection sec=(AbstractSection)this;
	
	
	
	private TextChangeHelper txtWorkingDirectoryChangelistener = new TextChangeHelper() {
		public void textChanged(Control control) {
					
			if(validateWorkingDirectory(txtWorkingDirectory.getText().trim())==false){
				txtWorkingDirectory.setText(((ResusModel)getElement()).getWorkingDirectory());
				return;
			}
			
					setResusModelExecutorWorkingDirectoryCommand rnc=new setResusModelExecutorWorkingDirectoryCommand();
					rnc.setPart((ResusModel)getElement());				
					rnc.setWorkingDirectory(txtWorkingDirectory.getText().trim());
					
					runCommand(rnc);
				}
		};	
	 
	
	
	
	
	
	
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		
		
		
		
		
		final Composite composite= getWidgetFactory().createFlatFormComposite(parent);
		composite.setLayout(null);
		composite.setBounds(28, 268, 900, 252);
		
	
		
		
		
		
		
		
		
		//-------------working directory field--------------------
				Label lblWorkingDirectory= new Label(composite, SWT.NONE);
				lblWorkingDirectory.setBounds(5, 15,95, 15);
				lblWorkingDirectory.setText("Working Directory");
				
				
				txtWorkingDirectory = new Text(composite, SWT.BORDER);
				txtWorkingDirectory .setBounds(105, 15, 450, 21);
				
				Button btnBrowsWorkingDirectory = new Button(composite, SWT.NONE);
				btnBrowsWorkingDirectory.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) 
						{
							
							DirectoryDialog directoryDialog = new DirectoryDialog(composite.getShell(),SWT.OPEN);
						    // Set the text
							directoryDialog.setText("Select Wrking Directory");   			
						   
						
						    // Open Dialog and save result of selection
						    String selected = directoryDialog.open();
						   
						    if(selected!=null){
						    	if(validateWorkingDirectory(selected)==false) 
						    		return;		    	
						    	
						    	txtWorkingDirectory.setText(selected);
						    	txtWorkingDirectoryChangelistener.textChanged(txtWorkingDirectory);
						    }
					   
					    					 
						}
				});
				btnBrowsWorkingDirectory.setBounds(580, 15, 75, 25);
				btnBrowsWorkingDirectory.setText("Browse");
				
				//----------------------
		
		
		
		
		
		

		
		
		//TODO: add new text description for this section
		Label lblHelpTip = new Label(composite, SWT.WRAP | SWT.BORDER);
		lblHelpTip.setText(ResusMessages.Model_GeneralInformation_information);
		lblHelpTip.setBounds(composite.getBounds().width+composite.getLocation().x, 10, 500, 200);
		
		
		
		

		
		txtWorkingDirectoryChangelistener.startListeningForEnter(txtWorkingDirectory);
		
		
	
		
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if(!(getElement() instanceof ResusModel)) throw new AssertionError("Object is not a Model");
		
		
		
		//txtIDChangeListener.startNonUserChange();
		
		txtWorkingDirectoryChangelistener.startNonUserChange();
		
		try {
			

			
			
			txtWorkingDirectory.setText(((ResusModel)getElement()).getWorkingDirectory());
			
		}
		catch(Exception x){
			//MessageDialog.openError(null, "Error", "Error occured");
			System.out.println(x.getMessage());
		}
		finally {
			//txtIDChangeListener.finishNonUserChange();
			
			txtWorkingDirectoryChangelistener.finishNonUserChange();
			
			//listener.finishNonUserChange();
			
		}
	}
	
	private boolean validateWorkingDirectory(String dir){
		boolean valid=true;
		Path directory=Paths.get(dir);
		if(directory.isAbsolute()==false){
			MessageDialog.openError(null, "Working Directory Error", "The path to th Working Directoy should be absolute. please select an absolute path");
			return false;
		}
		if(directory.toFile().exists()==false){
			MessageDialog.openError(null, "Working Directory Error", "The path to th Working Directoy should exist. please select an existing directory");
			return false;
		}
		if(directory.toFile().isDirectory()==false || directory.toFile().isFile()==true)
		{
			MessageDialog.openError(null, "Working Directory Error", "Please select a Directory");
			return false;
		}
		
		return valid;
	}
	
}
