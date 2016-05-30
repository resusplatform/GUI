package de.clausthal.tu.ielf.resus.wizards;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import de.clausthal.tu.ielf.randomGenrators.distributions.Distribution;
import de.clausthal.tu.ielf.randomGenrators.distributions.TriangleDistribution;
import de.clausthal.tu.ielf.randomGenrators.distributions.TruncatedGaussDistribution;
import de.clausthal.tu.ielf.randomGenrators.distributions.TruncatedGaussDistribution2;
import de.clausthal.tu.ielf.randomGenrators.distributions.UniformDistribution;
import de.clausthal.tu.ielf.resusdesigner.model.IndexPairs;
import de.clausthal.tu.ielf.resusdesigner.model.ResultConverter;

public class ParameterNewDialog extends MessageDialog {

	public ParameterNewDialog(Shell parentShell, String dialogTitle,
			Image dialogTitleImage, String dialogMessage, int dialogImageType,
			String[] dialogButtonLabels, int defaultIndex) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage,
				dialogImageType, dialogButtonLabels, defaultIndex);
		// TODO Auto-generated constructor stub
	}

	
	

	public Parameter getParameter() {
		return this.parameter;
	}

	@Override
	public void create() {
		super.create();
		// Set the title
		// setTitle("This is my first own dialog");
		// Set the message
		// setMessage("This is a TitleAreaDialog",
		// IMessageProvider.INFORMATION);

	}
private Parameter parameter;
	
	private Text txtName;
	private Text txtMinValue;
	private Text txtMaxValue;
	private Text txtDescription;
	private Text txtIndex;

	private Combo cmbUnits;
	private Combo cmbDistributions;
	
	private Text txtExParam1;
	private Text txtExParam2;
	private Text txtExParam3;
	private Label lblExParam3;
	private Label lblExParam1;
	private Label lblExParam2;
	
	private ArrayList<Parameter> listofParameters;
	void setParameterList(ArrayList<Parameter> prms){
		listofParameters=prms;
	}
	
	final String[] items=new String[] {"Uniform","Truncated Gauss","Truncated Gauss2", "Triangle"};
	 Button chkLog;
	 private Button chkShowInOutput;
	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setBounds(10, 10, 1008, 370);

		Group grpParameter = new Group(composite, SWT.NONE);
		grpParameter.setText("Parameter Configuration");
		grpParameter.setBounds(10, 12, 391, 307);
		
		
		Label lblIndex = new Label(grpParameter, SWT.NONE);
		lblIndex.setText("Index");
		lblIndex.setBounds(10, 23, 55, 15);
		
		txtIndex = new Text(grpParameter, SWT.BORDER);
		txtIndex.setBounds(81, 19, 148, 21);
		
		
		
		Label lblName = new Label(grpParameter, SWT.NONE);
		lblName.setText("Name");
		lblName.setBounds(10, 50, 55, 15);
		
				
		txtName = new Text(grpParameter, SWT.BORDER);
		txtName.setBounds(81, 45, 148, 21);
		
		
		Label lblUnit = new Label(grpParameter, SWT.NONE);
		lblUnit.setText("Unit");
		lblUnit.setBounds(10, 74, 55, 15);
		
		cmbUnits = new Combo(grpParameter, SWT.None);
		cmbUnits.setBounds(81, 71, 101, 23);
		//cmbUnits.setItems(new String[] {"Kg", "s", "m"});
		
		Label lblDistribution = new Label(grpParameter, SWT.NONE);
		lblDistribution.setText("Distribution");
		lblDistribution.setBounds(10, 107, 70, 15);
		
		
		
		cmbDistributions = new Combo(grpParameter, SWT.READ_ONLY);
		cmbDistributions.setBounds(81, 100, 148, 23);
		cmbDistributions.setItems(items);
		
		cmbDistributions.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
		    	DistributionChanged();
		      }
		      });
		
		
		chkLog = new Button(grpParameter, SWT.CHECK);
		chkLog.setBounds(235, 106, 41, 16);
		chkLog.setText("Log");
		
		
		Label lblMinValue = new Label(grpParameter, SWT.NONE);
		lblMinValue.setText("Min Value");
		lblMinValue.setBounds(10, 132, 55, 15);
		
		
		txtMinValue = new Text(grpParameter, SWT.BORDER);
		txtMinValue.setBounds(81, 129, 76, 21);
		
		
		Label lblMaxValue = new Label(grpParameter, SWT.NONE);
		lblMaxValue.setText("Max value");
		lblMaxValue.setBounds(10, 156, 55, 15);
		
		txtMaxValue = new Text(grpParameter, SWT.BORDER);
		txtMaxValue.setBounds(81, 156, 76, 21);
		
		
		
		
		lblExParam1 = new Label(grpParameter, SWT.NONE);
		
		lblExParam1.setBounds(184, 132, 83, 15);
		lblExParam1.setVisible(false);
		
		txtExParam1 = new Text(grpParameter, SWT.BORDER);
		txtExParam1.setBounds(295, 129, 76, 21);
		txtExParam1.setVisible(false);
		
		lblExParam2 = new Label(grpParameter, SWT.NONE);
		
		lblExParam2.setBounds(194, 156, 83, 15);
		lblExParam2.setVisible(false);
		
		txtExParam2 = new Text(grpParameter, SWT.BORDER);
		txtExParam2.setBounds(295, 153, 76, 21);
		txtExParam2.setVisible(false);
		
		lblExParam3 = new Label(grpParameter, SWT.NONE);
		
		lblExParam3.setBounds(184, 159, 98, 15);
		lblExParam3.setVisible(false);
		
		txtExParam3 = new Text(grpParameter, SWT.BORDER);
		txtExParam3.setBounds(295, 156, 76, 21);
		txtExParam3.setVisible(false);
		
		
		chkShowInOutput = new Button(grpParameter, SWT.CHECK);
		chkShowInOutput.setBounds(81, 184, 115, 16);
		chkShowInOutput.setText("Show in Output");
		
		Label lblDescrition= new Label(grpParameter, SWT.NONE);
		lblDescrition.setText("Description");
		lblDescrition.setBounds(10, 199, 70, 15);
		
		txtDescription = new Text(grpParameter, SWT.BORDER);
		txtDescription.setBounds(81, 202, 300, 64);
		
		
		
		
		
		
		
		
		
		
		
		
		return parent;
	}
	
	
	private void DistributionChanged()
	{
		lblExParam1.setVisible(false);		  		
  		txtExParam1.setVisible(false);
  		lblExParam2.setVisible(false);		  		
  		txtExParam2.setVisible(false);
  		lblExParam3.setVisible(false);		  		
  		txtExParam3.setVisible(false);
  		
    	  
    	  
    	  if (cmbDistributions.getText().equals(items[0])) {// uniform
            
          }else if (cmbDistributions.getText().equals(items[1])) {// gaus
        	  
	        	  
	        	  lblExParam1.setText("Mean Value");
	        	  lblExParam1.setVisible(true);
	        	  txtExParam1.setVisible(true);
	        	  
	        	  lblExParam2.setText("Std.  Deviation");
	        	  lblExParam2.setVisible(true);
	        	  txtExParam2.setVisible(true);
	        	  
	        	  
          }else if (cmbDistributions.getText().equals(items[2])) {// gaus
        	  
        	  
        	  lblExParam1.setText("Mean Value");
        	  lblExParam1.setVisible(true);
        	  txtExParam1.setVisible(true);
        	  
        	  lblExParam2.setText("Std.  Deviation");
        	  lblExParam2.setVisible(true);
        	  txtExParam2.setVisible(true);
        	  
        	  
      } else if (cmbDistributions.getText().equals(items[3])) {// triangle
        	 
        	  
	        	  lblExParam1.setText("Mode Value");
	        	  lblExParam1.setVisible(true);
	        	  txtExParam1.setVisible(true);
	        	  
          }
        }
	

	/*
	 * checks the values inserted into the TextBoxes 
	 * 
	 * 
	 */
	private boolean isValidInput() {
		
		boolean valid = true;
		int indx=-1;
		
		//check if all neccessary fields are filled with valid values
		if(txtIndex.getText().isEmpty()){
			MessageDialog.openError(null, "Empty value Error", "the Index value should not be empty");
			return false;
		}		
		
		try{
			Integer.valueOf(txtIndex.getText().trim());			
		}
		catch(NumberFormatException NoFrmtExc)
		{
			MessageDialog.openError(null, "Number Format Error", "the Index value must be an positive integer number");
			return false;
		}
		

		indx=Integer.valueOf(txtIndex.getText().trim());
		if(indx<1){
			
			MessageDialog.openError(null, "Number Format Error", "the Index value must be an positive integer number");
			return false;
		}
		
		
		if(txtName.getText().isEmpty()){
			MessageDialog.openError(null, "Empty value Error", "the Parameter Name should not be empty");
			return false;
		}
		if(txtName.getText().indexOf('\\')>=0 || txtName.getText().indexOf('/')>=0)// string contains backslash
		{
			MessageDialog.openError(null, "Invalid Char", "the Parameter Name should not contain slash or backslash Character");
			return false;
			
		}
		
		if(cmbDistributions.getSelectionIndex()<0){
			MessageDialog.openError(null, "Empty value Error", "the Distribution should be selected");
			return false;
		}
		
		// min and max is common between all distribution and are also the mandatory fields
		if(txtMinValue.getText().isEmpty()){
			MessageDialog.openError(null, "Invalid Value Error", "The Min value should not be emtpy ");
			return false;
		}
		
		
		try{ // check min value in double fromat
			Double.valueOf(txtMinValue.getText().trim());
		}
		catch(NumberFormatException nrFrmtExc){
			MessageDialog.openError(null, "Invalid Value Error", "The Min value should be in Double format ");
			return false;
		}
		if(txtMaxValue.getText().isEmpty()){
			MessageDialog.openError(null, "Invalid Value Error", "The Max value should not be emtpy ");
			return false;
		}
		try{// check max value in double format
			Double.valueOf(txtMaxValue.getText().trim());
		}
		catch(NumberFormatException nrFrmtExc){
			MessageDialog.openError(null, "Invalid Value Error", "The Max value should be in Double format ");
			return false;
		}
		
		
		// check that min<max
		Double min=Double.valueOf(txtMinValue.getText().trim());
		Double max=Double.valueOf(txtMaxValue.getText().trim());
		if(min>=max){
			MessageDialog.openError(null, "Logical Error", "the value of Min should be smaller than Max");
			return false;
		}
		
		
		if (cmbDistributions.getText().equals(items[0])) {//uniform		
		
		
		}else if (cmbDistributions.getText().equals(items[1])) {// gaus
			//Check not to be empty 
			if(txtExParam1.getText().isEmpty()){
				MessageDialog.openError(null, "Empty value Error", "The value of Mean should not be emtpy");
				return false;
			}
			
			
			// check the format
			try{
				Double.valueOf(txtExParam1.getText().trim());
			}catch(NumberFormatException nrFrmExc){
				MessageDialog.openError(null, "number format Error", "the value of Mean should be a real value");
				return false;
			}
			
			
			try{
				Double.valueOf(txtExParam2.getText().trim());
			}catch(NumberFormatException nrFrmExc){
				MessageDialog.openError(null, "number format Error", "the value of Std. Deviation should be a real value");
				return false;
			}
			//check not to be emtpy
			if(txtExParam2.getText().isEmpty()){
				MessageDialog.openError(null, "Empty value Error", "The value of Std. Deviation should not be emtpy");
				return false;
			}
			
			Double mean=Double.valueOf(txtExParam1.getText().trim());
			Double stdev=Double.valueOf(txtExParam2.getText().trim());			
			
			
			if(stdev>max || stdev<min){
				MessageDialog.openError(null, "Logical Error", "the value of Std. Deviation should be smaller than Max and greater than Min");
				return false;
			}	
			

			if(mean>max || mean<min){
				MessageDialog.openError(null, "Logical Error", "the value of Mean should be smaller than Max and greater than Min");
				return false;
			}	
			
			
		}else if (cmbDistributions.getText().equals(items[2])) { //trianlge
			//Check not to be empty 
			if(txtExParam1.getText().isEmpty()){
				MessageDialog.openError(null, "Empty value Error", "The value of Mode should not be emtpy");
				return false;
			}
		
			
			// check the format
			try{
				Double.valueOf(txtExParam1.getText().trim());
			}catch(NumberFormatException nrFrmExc){
				MessageDialog.openError(null, "number format Error", "the value of Mean should be a real value");
				return false;
			}			
			
			Double mean=Double.valueOf(txtExParam1.getText().trim());
			
			
			
			if(mean>max || mean<min){
				MessageDialog.openError(null, "Logical Error", "the value of Std. Deviation should be smaller than Max and greater than Min");
				return false;
			}	
		}
		
		
		
		// at the end chek if the same parameter is already in the list
		for(int i=0;i<listofParameters.size();i++){
			
			Parameter tmpmP=listofParameters.get(i);
			if(txtName.getText().trim().equals(tmpmP.name)){
				MessageDialog.openWarning(null,"New Parameter ","The selected Name is already exist, please select another Name for parameter");
				return false;
			}
			if(indx==tmpmP.index.intValue()){
				MessageDialog.openWarning(null,"New Parameter","The selected Index is already exist, please select another index for parameter");
				return false;
			}
			
			
		}
		
		
		return valid;
	}

	@Override
	protected boolean isResizable() {
		return false;
	}

	// We need to have the textFields into Strings because the UI gets disposed
	// and the Text Fields are not accessible any more.
	private void saveInput() {
		
		Parameter p = new Parameter();
		p.index=Integer.valueOf(txtIndex.getText().trim());
		p.name = txtName.getText().trim();
		if(!cmbUnits.getText().isEmpty())		p.unit =cmbUnits.getText().trim();
		else p.unit="";
		
		if (cmbDistributions.getText().equals(items[0])) {//uniform
			 
			p.distribution=new UniformDistribution(Double.parseDouble(txtMinValue.getText().trim()), Double.parseDouble(txtMaxValue.getText().trim())); 
			p.distribution.setLogaritmic(chkLog.getSelection());
           
         }else if (cmbDistributions.getText().equals(items[1])) {// gaus
       	  p.distribution=new TruncatedGaussDistribution(Double.parseDouble(txtMinValue.getText().trim()), Double.parseDouble(txtMaxValue.getText().trim()),Double.parseDouble( txtExParam1.getText().trim()), Double.parseDouble(txtExParam2.getText().trim())); 
       	  p.distribution.setLogaritmic(chkLog.getSelection());
       	  
         } else if (cmbDistributions.getText().equals(items[2])) {// gaus2
          	  p.distribution=new TruncatedGaussDistribution2(Double.parseDouble(txtMinValue.getText().trim()), Double.parseDouble(txtMaxValue.getText().trim()),Double.parseDouble( txtExParam1.getText().trim()), Double.parseDouble(txtExParam2.getText().trim())); 
           	  p.distribution.setLogaritmic(chkLog.getSelection());
           	  
             } 
         else if (cmbDistributions.getText().equals(items[3])) { //trianlge
       	  p.distribution=new TriangleDistribution(Double.parseDouble(txtMinValue.getText().trim()), Double.parseDouble(txtMaxValue.getText().trim()),Double.parseDouble( txtExParam1.getText().trim()));
       	  p.distribution.setLogaritmic(chkLog.getSelection());		        	 
       	  
         }
		
		p.description = txtDescription.getText().trim();
		
		p.showInOutput=chkShowInOutput.getSelection();
		parameter=(p);
		System.out.println("saved");
	}

	@Override
	protected void okPressed() {
		if(!isValidInput())return;
		saveInput();
		super.okPressed();
	}
	@Override
	protected void buttonPressed(int buttonId){
		if(buttonId==0) okPressed();
		else super.cancelPressed();
	}

}