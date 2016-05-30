package org.eclipse.ui.examples.views.properties.tabbed.logic.properties.ResultConverter;

import java.util.ArrayList;




import org.eclipse.jface.util.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.ui.examples.views.properties.tabbed.logic.properties.AbstractSection;
import org.eclipse.ui.examples.views.properties.tabbed.logic.properties.TextChangeHelper;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;


import de.clausthal.tu.ielf.resusdesigner.ResusMessages;
import de.clausthal.tu.ielf.resusdesigner.model.IndexPairs;
import de.clausthal.tu.ielf.resusdesigner.model.OutputPair;
import de.clausthal.tu.ielf.resusdesigner.model.ResultConverter;
import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;

import de.clausthal.tu.ielf.resusdesigner.model.commands.ResultConverter.SetResultConverterInputFileCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResultConverter.SetResultConverterNameCommand;


public class ModelResultConverter extends AbstractSection {

	public ModelResultConverter() {
		
	}
	private Text txtName;
	//private Text txtExecutor;
	private Combo cmbInputFile;
	
	
	
	
	
//	private TextChangeHelper executorFieldChangelistener = new TextChangeHelper() {
//
//		public void textChanged(Control control) {
//			String executor = txtExecutor.getText().trim();
//			
//			SetResultConverterExecutorCommand rnc=new SetResultConverterExecutorCommand();
//			rnc.setPart((ResultConverter)getElement());				
//			rnc.setExecutor(executor);						
//			runCommand(rnc);
//			
//
//		}
//	};
	
	private TextChangeHelper nameChangelistener = new TextChangeHelper() {

		public void textChanged(Control control) {
			String name = txtName.getText().trim();
			//((ResultConverter) getElement()).setExecutor(executor);
			 
			SetResultConverterNameCommand rnc=new SetResultConverterNameCommand();
			rnc.setPart((ResultConverter)getElement());				
			rnc.setName(name);						
			runCommand(rnc);
			

		}
	};
	
	
	private TextChangeHelper inputfileFieldChangelistener = new TextChangeHelper() {

		public void textChanged(Control control) {
			String inputfilename = cmbInputFile.getText().trim();
			//((ResultConverter) getElement()).setInputFileName(inputfilename);
			SetResultConverterInputFileCommand rnc=new SetResultConverterInputFileCommand();
			rnc.setPart((ResultConverter)getElement());				
			rnc.setInputFile(inputfilename);
			runCommand(rnc);

		}
	};
	
	
		
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		
		
		final Composite composite = getWidgetFactory().createFlatFormComposite(parent);


		composite.setLayout(null);
		composite.setBounds(38, 10, 900, 252);

		Label lblName = new Label(composite, SWT.NONE);
		lblName.setText("Name");
		lblName.setBounds(5, 8, 55, 15);

	
		txtName= new Text(composite, SWT.BORDER);
		txtName.setBounds(112, 3, 300, 21);

		
		
	
//		Label lblExecutor = new Label(composite, SWT.NONE);
//		lblExecutor.setText("Executor");
//		lblExecutor.setBounds(5, 28, 55, 15);
//
//	
//		txtExecutor = new Text(composite, SWT.BORDER);
//		txtExecutor.setBounds(112, 25, 362, 21);

	//		lblColumnDelimiter.setBounds(5, 29, 123, 15);

//		
		
		

		Label lblInputFile = new Label(composite, SWT.NONE);
		lblInputFile.setText("Input File ");
		lblInputFile.setBounds(5, 48, 76, 15);

		
		
		
		

		
		
        
		cmbInputFile = new Combo(composite, SWT.READ_ONLY);
		cmbInputFile.setBounds(112, 48, 362, 21);
		
		cmbInputFile.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				String inputfilename = cmbInputFile.getText().trim();
				//((ResultConverter) getElement()).setInputFileName(inputfilename);
				SetResultConverterInputFileCommand rnc=new SetResultConverterInputFileCommand();
				rnc.setPart((ResultConverter)getElement());				
				rnc.setInputFile(inputfilename);
				runCommand(rnc);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
            
		
		Label lblHelpTip = new Label(composite, SWT.WRAP | SWT.BORDER);
		lblHelpTip.setText(ResusMessages.ResultConverter_ResultConverter_information);
		lblHelpTip.setBounds(composite.getBounds().width+composite.getLocation().x, 10, 500, 200);
       
//		nameChangelistener.startListeningTo(txtName);
//        executorFieldChangelistener.startListeningTo(txtExecutor);
//        inputfileFieldChangelistener.startListeningTo(cmbInputFile);
       
//        
//        nameChangelistener.startListeningForEnter(txtName);
//        executorFieldChangelistener.startListeningForEnter(txtExecutor);
//        inputfileFieldChangelistener.startListeningForEnter(cmbInputFile);
        
//				
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		Assert.isTrue(getElement() instanceof ResultConverter);
		nameChangelistener.startNonUserChange();
		//executorFieldChangelistener.startNonUserChange();
		inputfileFieldChangelistener.startNonUserChange();
		
		try {
			ResultConverter rc=((ResultConverter) getElement());
			
			txtName.setText(rc.getName());
			
			
			//txtExecutor.setText(rc.getExecutor());
			
			 
			ArrayList<IndexPairs> pairs= ((ResultConverter)getElement()).getColumnsIndexes();
	    	String names[]=new String[pairs.size()+1];
	    	names[0]="";
	    	for(int i=1;i<pairs.size()+1;i++)
	    		names[i]=pairs.get(i-1).tag;
			
			
			cmbInputFile.removeAll();			
			ResusModel m=((ResultConverter)getElement()).getConnectedModel();
			if(m!=null){
				
				ArrayList<OutputPair> files=m.getOutputFileNames();
				for(OutputPair p:files)
				cmbInputFile.add(m.getWorkingDirectory()+"\\"+p.getFileName());
				
			}
			
			
			cmbInputFile.setText(rc.getInputFileName());
			
		}catch(Exception x){
			System.out.println(x.getMessage());
		}
		finally {
			
			nameChangelistener.finishNonUserChange();
			//executorFieldChangelistener.finishNonUserChange();
			
			inputfileFieldChangelistener.finishNonUserChange();
			
		}
	}
}
