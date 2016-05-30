package org.eclipse.ui.views.properties.tabbed.resus.properties.ResultConverter;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import org.eclipse.jface.util.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.resus.properties.AbstractSection;
import org.eclipse.ui.views.properties.tabbed.resus.properties.TextChangeHelper;

import de.clausthal.tu.ielf.resusdesigner.ResusMessages;
import de.clausthal.tu.ielf.resusdesigner.model.IndexPairs;
import de.clausthal.tu.ielf.resusdesigner.model.OutputPair;
import de.clausthal.tu.ielf.resusdesigner.model.ResultConverter;
import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResultConverter.SetResultConverterExecutorCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResultConverter.SetResultConverterExecutorOutputFileNameCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResultConverter.SetResultConverterOutputFileCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResultConverter.SetResultConverterParameterCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResultConverter.SetResultConverterWorkingDirectoryCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResultConverter.SetResusResultConverterNumberOfOutputRowsCommand;

public class ModelResultConverterExecutor extends AbstractSection {

	public ModelResultConverterExecutor() {
		// TODO Auto-generated constructor stub
	}
	private Text txtWorkingDirectory;
	private Text txtExecutor;
	private Text txtParamtere;
	private Text txtOutputFileName;
	
	
	
	
	private TextChangeHelper txtWorkingDirectoryChangelistener = new TextChangeHelper() {

		public void textChanged(Control control) {
			String WorkingDirectory = txtWorkingDirectory.getText().trim();
			
			SetResultConverterWorkingDirectoryCommand rnc=new  SetResultConverterWorkingDirectoryCommand();
			rnc.setPart((ResultConverter)getElement());				
			rnc.setWorkingDirectory(WorkingDirectory);
			runCommand(rnc);
		}
	};
	 
	private TextChangeHelper txtExecutorChangelistener = new TextChangeHelper() {

		public void textChanged(Control control) {
			String Executor = txtExecutor.getText().trim();
			
			SetResultConverterExecutorCommand rnc=new  SetResultConverterExecutorCommand();
			rnc.setPart((ResultConverter)getElement());				
			rnc.setExecutor(Executor);
			runCommand(rnc);
		}
	};
	
	
	private TextChangeHelper txtParamtereChangelistener = new TextChangeHelper() {

		public void textChanged(Control control) {
			String Paramtere = txtParamtere.getText().trim();
			
			SetResultConverterParameterCommand rnc=new  SetResultConverterParameterCommand();
			rnc.setPart((ResultConverter)getElement());				
			rnc.setParameter(Paramtere);
			runCommand(rnc);
		}
	};
	
	
	private TextChangeHelper txtOutputFileNameChangelistener = new TextChangeHelper() {

		public void textChanged(Control control) {
			String OutputFileName = txtOutputFileName.getText().trim();
			
			SetResultConverterExecutorOutputFileNameCommand rnc=new  SetResultConverterExecutorOutputFileNameCommand();
			rnc.setPart((ResultConverter)getElement());				
			rnc.setOutputFileName(OutputFileName);
			runCommand(rnc);
		}
	};
		
	
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		
		
		final Composite composite = getWidgetFactory().createFlatFormComposite(parent);


		composite.setLayout(null);
		composite.setBounds(38, 10, 900, 252);
		
		Label lblWorkingDirectory = new Label(composite, SWT.NONE);
		lblWorkingDirectory.setText("Working Directory");
		lblWorkingDirectory.setBounds(5, 10, 96, 15);

		txtWorkingDirectory= new Text(composite, SWT.BORDER);
		txtWorkingDirectory.setBounds(115, 10, 450, 21);



		Button btnBrowsexecutor = new Button(composite, SWT.NONE);
		btnBrowsexecutor.setText("brows");
		btnBrowsexecutor.setBounds(580, 7, 75, 21);
		btnBrowsexecutor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
				{
					
				DirectoryDialog dirDialog = new DirectoryDialog(composite.getShell());
			    // Set the text
				dirDialog.setText("Select Directory");
			    // Set filter on .txt files
				//dirDialog.setFilterExtensions(new String[] { "*.exe|*.bat|*.com" });
			    // Put in a readable name for the filter
				//dirDialog.setFilterNames(new String[] { "executable Files(*.exe *.bat *.com)" });
			    // Open Dialog and save result of selection
			    
			    String selected = dirDialog.open();
			    if(selected!=null){
			    	txtWorkingDirectory.setText(selected);
			    	txtWorkingDirectoryChangelistener.textChanged(txtWorkingDirectory);
				}
			}
		});
		
		

		Label lblExecutor = new Label(composite, SWT.NONE);
		lblExecutor.setText("Executor");
		lblExecutor.setBounds(5, 35, 96, 15);

		txtExecutor= new Text(composite, SWT.BORDER);
		txtExecutor.setBounds(115, 35, 300, 21);
		
		
		Label lblParameter= new Label(composite, SWT.NONE);
		lblParameter.setText("Parameter");
		lblParameter.setBounds(425, 35,75, 15);
		
		txtParamtere=new Text(composite, SWT.BORDER);
		txtParamtere.setBounds(500, 35, 100, 21);
		
		
		Label lblExecutorOutput = new Label(composite, SWT.NONE);
		lblExecutorOutput.setText("Output File");
		lblExecutorOutput.setBounds(5, 60, 100, 15);

		txtOutputFileName= new Text(composite, SWT.BORDER);
		txtOutputFileName.setBounds(115, 60, 200,21);
		

		Label lblHelpTip = new Label(composite, SWT.WRAP | SWT.BORDER);
		lblHelpTip.setText(ResusMessages.ResultConverter_OutputManipulation_information);
		lblHelpTip.setBounds(composite.getBounds().width+composite.getLocation().x, 10, 500, 200);
		
		txtWorkingDirectoryChangelistener.startListeningTo(txtWorkingDirectory);
		txtExecutorChangelistener.startListeningTo(txtExecutor);
		txtParamtereChangelistener.startListeningTo(txtParamtere);
		txtOutputFileNameChangelistener.startListeningTo(txtOutputFileName);
			
		
		
	}
	public void refresh() {
		Assert.isTrue(getElement() instanceof ResultConverter);
		
		txtWorkingDirectoryChangelistener.startNonUserChange();
		txtExecutorChangelistener.startNonUserChange();
		txtParamtereChangelistener.startNonUserChange();
		txtOutputFileNameChangelistener.startNonUserChange();
		
		try {
			ResultConverter rc=((ResultConverter) getElement());			
			
			
			//txtOutputFile.setText(rc.getOutputFileName());			
			txtWorkingDirectory.setText(rc.getExecutorWorkingDirectory());
			txtExecutor.setText(rc.getExecutor());
			txtParamtere.setText(rc.getExecutorParameter());
			txtOutputFileName.setText(rc.getExecutorOutput());
			
			
			
			
			
		} finally {
			
			
			txtWorkingDirectoryChangelistener.finishNonUserChange();
			txtExecutorChangelistener.finishNonUserChange();
			txtParamtereChangelistener.finishNonUserChange();
			txtOutputFileNameChangelistener.finishNonUserChange();
		}
	}
}
