package org.eclipse.ui.views.properties.tabbed.resus.properties.ResultConverter;



import org.eclipse.jface.util.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.resus.properties.AbstractSection;
import org.eclipse.ui.views.properties.tabbed.resus.properties.TextChangeHelper;

import de.clausthal.tu.ielf.resusdesigner.ResusMessages;
import de.clausthal.tu.ielf.resusdesigner.model.ResultConverter;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResultConverter.SetResultConverterOutputFileCommand;

public class ModelResultConverterOutput extends AbstractSection {

	
	public ModelResultConverterOutput() {
		// TODO Auto-generated constructor stub
	}
	private Text txtOutputFile;	
	private Button btnBrowse;
	
	
	
	
	
	private TextChangeHelper outputfileFieldChangelistener = new TextChangeHelper() {

		public void textChanged(Control control) {
			String outputfilename = txtOutputFile.getText().trim();
			
			SetResultConverterOutputFileCommand rnc=new  SetResultConverterOutputFileCommand();
			rnc.setPart((ResultConverter)getElement());				
			rnc.setOutputFile(outputfilename);
			runCommand(rnc);
		}
	};
	 
	
	
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		
		
		final Composite composite = getWidgetFactory().createFlatFormComposite(parent);


		composite.setLayout(null);
		composite.setBounds(38, 10, 900, 252);
		
		Label lblOutputFile = new Label(composite, SWT.NONE);
		lblOutputFile.setText("Output File");
		lblOutputFile.setBounds(5, 10, 86, 15);

		txtOutputFile= new Text(composite, SWT.BORDER);
		txtOutputFile.setBounds(112, 10, 450, 21);
		
		btnBrowse=new Button(composite, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
				{
					
				FileDialog fileDialog = new FileDialog(composite.getShell());
			    // Set the text
			    fileDialog.setText("Select File");
			    
			    // Set filter on .* files
			    fileDialog.setFilterExtensions(new String[] { "*.*"});
			    // Put in a readable name for the filter
			    fileDialog.setFilterNames(new String[] { "All  Files(*.*)" });
			    // Open Dialog and save result of selection
			    String selected = fileDialog.open();
			    
			    if(selected!=null){
			    	txtOutputFile.setText(selected);
			    	outputfileFieldChangelistener.textChanged(txtOutputFile);
			    }
			    	//((ResusModel) getElement()).setExecutor(txtExecutor.getText());
				}
		});
		btnBrowse.setBounds(610, 10, 75, 25);
		btnBrowse.setText("Browse");
		


//
//		Label lblNumberOfoutputs = new Label(composite, SWT.NONE);
//		lblNumberOfoutputs.setText("Number Of Output Rows");
//		lblNumberOfoutputs.setBounds(5, 45, 180, 15);
//
//		txtNumberofOutput= new Text(composite, SWT.BORDER);
//		txtNumberofOutput.setBounds(190, 45, 50, 21);
//		
//		
//		Label lblOutputSelectedCol = new Label(composite, SWT.NONE);
//		lblOutputSelectedCol.setText("Selected Column");
//		lblOutputSelectedCol.setBounds(5, 70, 180, 15);
//		cmbSelecetdCol=new Combo(composite, SWT.READ_ONLY);
//		cmbSelecetdCol.setBounds(190, 70, 180, 15);
//		
//		cmbSelecetdCol.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				changeAffectedIndex();
//			}
//			
//			
//		});

		Label lblHelpTip = new Label(composite, SWT.WRAP | SWT.BORDER);
		lblHelpTip.setText(ResusMessages.ResultConverter_OutputManipulation_information);
		lblHelpTip.setBounds(composite.getBounds().width+composite.getLocation().x, 10, 500, 200);
		
		
		
		
		
		
		 outputfileFieldChangelistener.startListeningTo(txtOutputFile);
	     outputfileFieldChangelistener.startListeningForEnter(txtOutputFile);
	        
		
		
	}
	public void refresh() {
		Assert.isTrue(getElement() instanceof ResultConverter);
		outputfileFieldChangelistener.startNonUserChange();
		//outputRowsFieldChangelistener.startNonUserChange();
		try {
			ResultConverter rc=((ResultConverter) getElement());			
			
			txtOutputFile.setText(rc.getOutputFileName());			
			
//			txtNumberofOutput.setText(String.valueOf(rc.getNumberOfOutputRows())); 
//			ArrayList<IndexPairs> pairs= ((ResultConverter)getElement()).getColumnsIndexes();
//	    	String names[]=new String[pairs.size()+1];
//	    	names[0]="";
//	    	for(int i=1;i<pairs.size()+1;i++)
//	    		names[i]=pairs.get(i-1).tag;
//			cmbSelecetdCol.setItems(names);
//			cmbSelecetdCol.select(rc.getOutputAffectedIndex()+1);
//			
//			
			
			
			
			
		} finally {
			
			
			outputfileFieldChangelistener.finishNonUserChange();
			//outputRowsFieldChangelistener.finishNonUserChange();
		}
	}
}
