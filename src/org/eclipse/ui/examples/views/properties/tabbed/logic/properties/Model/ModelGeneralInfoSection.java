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
import java.util.List;
import java.util.Map;


import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.TableViewer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.examples.views.properties.tabbed.logic.properties.AbstractSection;
import org.eclipse.ui.examples.views.properties.tabbed.logic.properties.TextChangeHelper;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;


import de.clausthal.tu.ielf.resusdesigner.ResusMessages;

import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;

import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.SetResusModelDescriptionCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.SetResusModelModelTypeCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.SetResusModelNameCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.SetResusModelNumberOfInputPinsCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.SetResusModelNumberOfOutputPinsCommand;


/**
 * The Model General Information  section on the Model General Information tab.
 * 
 * @author Javad Ghofrani
 */
public class ModelGeneralInfoSection extends AbstractSection {
	
	//private Text txtID;
//	private Text txtExecutorPath;
	private Text txtName;
	private Text txtModelType;
	private Text txtDescription;
	private Text txtNumberOfInputPins;
	private Text txtNumberOfOutputPins;
	
	
	AbstractSection sec=(AbstractSection)this;
	 List<String> filenames ;
	
	private  Button btnAddToList;
	 private TableViewer viewer;
	 Map<Object, Button> buttons = new HashMap<Object, Button>();
	/**
	 * A helper to listen for events that indicate that a text
	 * field has been changed.
	 */ 
	private TextChangeHelper txtNameChangeListener = new TextChangeHelper() {
		
		public void textChanged(Control control) {
			SetResusModelNameCommand rnc=new SetResusModelNameCommand();
			rnc.setPart((ResusModel)getElement());			
			rnc.setName(txtName.getText().trim());			
			runCommand(rnc);
			
		}
	};
	
	
	
	
	
	
	private TextChangeHelper txtDescriptionChangeListener = new TextChangeHelper() {

		public void textChanged(Control control) {			
			SetResusModelDescriptionCommand rnc=new SetResusModelDescriptionCommand();
			rnc.setPart((ResusModel)getElement());			
			rnc.setDescription(txtDescription.getText().trim());			
			runCommand(rnc);
						
		}
	};
	private TextChangeHelper txtModelTypeChangeListener = new TextChangeHelper() {

		public void textChanged(Control control) {			
			SetResusModelModelTypeCommand rnc=new SetResusModelModelTypeCommand();
			rnc.setPart((ResusModel)getElement());			
			rnc.setModelType(txtModelType.getText().trim());			
			runCommand(rnc);
			//((ResusModel)getElement()).setModelType(txtModelType.getText().trim()) ;			
		}
	};

	private TextChangeHelper txtNumberOfInputPinsFieldChangelistener = new TextChangeHelper() {

		public void textChanged(Control control) {
			int num= Integer.parseInt(txtNumberOfInputPins.getText());
			
			
			SetResusModelNumberOfInputPinsCommand rnc=new SetResusModelNumberOfInputPinsCommand();
			rnc.setPart((ResusModel)getElement());
			rnc.setLabel("change Model Description");
			rnc.setInputPins(num);
			
			runCommand(rnc);
			
			
			
			
			
		}
	};
	private TextChangeHelper txtNumberOfOutputPinsFieldChangelistener = new TextChangeHelper() {

		public void textChanged(Control control) {
			int num= Integer.parseInt(txtNumberOfOutputPins.getText());
			
			
			SetResusModelNumberOfOutputPinsCommand rnc=new SetResusModelNumberOfOutputPinsCommand();
			rnc.setPart((ResusModel)getElement());
			rnc.setLabel("change Model Description");
			rnc.setOutputPins(num);
			
			runCommand(rnc);
			
		}
	};


	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		
		
		
		filenames=new ArrayList<String>();
		
		final Composite composite= getWidgetFactory().createFlatFormComposite(parent);
		composite.setLayout(null);
		composite.setBounds(28, 268, 900, 252);
		
	
		txtName = new Text(composite, SWT.BORDER);
		txtName.setBounds(84, 4, 176, 21);
		
		Label lblName = new Label(composite, SWT.NONE);
		lblName.setText("Name");
		lblName.setBounds(5, 4, 55, 15);
		
		txtModelType = new Text(composite, SWT.BORDER);
		txtModelType.setBounds(84, 28, 176, 21);
		
		Label lblModelType = new Label(composite, SWT.NONE);
		lblModelType.setText("Model Type");
		lblModelType.setBounds(5, 28, 76, 15);
		
		
		Label lblNumberOfInput = new Label(composite, SWT.NONE);
		lblNumberOfInput.setBounds(287, 4, 125, 15);
		lblNumberOfInput.setText("Number Of Input Pins");
		
		Label label = new Label(composite, SWT.NONE);
		label.setText("Number Of Output Pins");
		label.setBounds(287, 31, 125, 15);
		
		txtNumberOfInputPins = new Text(composite, SWT.BORDER);
		txtNumberOfInputPins.setBounds(414, 4, 176, 21);
		
		txtNumberOfOutputPins= new Text(composite, SWT.BORDER);
		txtNumberOfOutputPins.setBounds(414, 28, 176, 21);
		
		
		
		
		
		
		
		
		

		txtDescription =new Text(composite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		txtDescription.setBounds(84, 65, 500, 97);
		
		Label lblDescription = new Label(composite, SWT.NONE);
		lblDescription.setText("Description");
		lblDescription.setBounds(5, 65, 60, 15);;
		
		
		Label lblHelpTip = new Label(composite, SWT.WRAP | SWT.BORDER);
		lblHelpTip.setText(ResusMessages.Model_GeneralInformation_information);
		lblHelpTip.setBounds(composite.getBounds().width+composite.getLocation().x, 10, 500, 200);
		
		
		
		

		txtNameChangeListener.startListeningForEnter(txtName);
		txtNameChangeListener.startListeningTo(txtName);
		txtDescriptionChangeListener.startListeningForEnter(txtDescription);
		txtDescriptionChangeListener.startListeningTo(txtDescription);
		txtModelTypeChangeListener.startListeningForEnter(txtModelType);
		
		
		
		
		txtModelTypeChangeListener.startListeningTo(txtModelType);

		txtNumberOfInputPinsFieldChangelistener.startListeningForEnter(txtNumberOfInputPins);
		txtNumberOfInputPinsFieldChangelistener.startListeningTo(txtNumberOfInputPins);
		txtNumberOfOutputPinsFieldChangelistener.startListeningForEnter(txtNumberOfOutputPins);
		txtNumberOfOutputPinsFieldChangelistener.startListeningTo(txtNumberOfOutputPins);
		
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		Assert.isTrue(getElement() instanceof ResusModel);
		
		
		
		//txtIDChangeListener.startNonUserChange();
		txtNameChangeListener.startNonUserChange();
		txtDescriptionChangeListener.startNonUserChange();
		txtModelTypeChangeListener.startNonUserChange();
		
		txtNumberOfInputPinsFieldChangelistener.startNonUserChange();
		txtNumberOfOutputPinsFieldChangelistener.startNonUserChange();
		
		
		try {
			

			
			txtName.setText(((ResusModel) getElement()).getName());
			txtDescription.setText(((ResusModel) getElement()).getDescription());
			txtModelType.setText(((ResusModel) getElement()).getModelType());
		
			txtNumberOfInputPins.setText(String.valueOf((((ResusModel)getElement()).getNumberOfInputs())));
			txtNumberOfOutputPins.setText(String.valueOf((((ResusModel)getElement()).getNumberOfOutputs())));
			
			
		}
		catch(Exception x){
			//MessageDialog.openError(null, "Error", "Error occured");
			System.out.println(x.getMessage());
		}
		finally {
			//txtIDChangeListener.finishNonUserChange();
			txtNameChangeListener.finishNonUserChange();
			txtDescriptionChangeListener.finishNonUserChange();
			txtModelTypeChangeListener.finishNonUserChange();			
			txtNumberOfInputPinsFieldChangelistener.finishNonUserChange();
			txtNumberOfOutputPinsFieldChangelistener.finishNonUserChange();
			
			
			//listener.finishNonUserChange();
			
		}
	}
	
	
}
