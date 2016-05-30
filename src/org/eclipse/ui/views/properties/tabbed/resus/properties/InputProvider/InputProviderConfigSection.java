 
package org.eclipse.ui.views.properties.tabbed.resus.properties.InputProvider;




import org.eclipse.swt.widgets.Text;







import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;


import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.resus.properties.AbstractSection;
import org.eclipse.ui.views.properties.tabbed.resus.properties.TextChangeHelper;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import de.clausthal.tu.ielf.resus.RowNumberLabelProvider;
import de.clausthal.tu.ielf.resus.SampleViewer;
import de.clausthal.tu.ielf.resus.wizards.NewSampleWizard;
import de.clausthal.tu.ielf.resusdesigner.ResusMessages;
import de.clausthal.tu.ielf.resusdesigner.model.InputProvider;
import de.clausthal.tu.ielf.resusdesigner.model.commands.InputProvider.SetInputProviderIdCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.InputProvider.SetInputProviderIndexCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.InputProvider.SetInputProviderInputFilesCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.InputProvider.SetInputProviderLogFileCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.InputProvider.SetInputProviderNumberOfInputPinsCommand;


/**
 * The location section on the location tab.
 * 
 * @author Javad Ghofrani
 */
public class InputProviderConfigSection extends AbstractSection {
	
	private Text txtId;
	//private Text txtInputFileName;
	private Text txtNumberOfInputPins;
	private Text txtLogFileName;
	private Text txtIndex;

	private Composite composite; 
	private String filename;
	 List<String> filenames ;
	
	private  Button btnAddToList;
	private  Button btnGenarateInput;
	 private TableViewer viewer;
	 Map<Object, Button> buttons;//=new HashMap<Object, Button>();
	 Map<Object, Button> editButtons;// = new HashMap<Object, Button>();
	

	
	 /**
		 * A helper to listen for events that indicate that a text
		 * field has been changed.
		 */
		private TextChangeHelper IdChangelistener = new TextChangeHelper() {

			public void textChanged(Control control) {
				
				String id= txtId.getText();

				SetInputProviderIdCommand rnc=new SetInputProviderIdCommand();
				rnc.setPart((InputProvider)getElement());				
				rnc.setId(id);			
				
				runCommand(rnc);			
			}
		};
	 
	
	private TextChangeHelper NumberOfInputPinsFieldChangelistener = new TextChangeHelper() {

		public void textChanged(Control control) {
			
			int num= Integer.parseInt(txtNumberOfInputPins.getText());
			//
			Dimension d=new Dimension();
			d.height=((InputProvider)getElement()).getSize().height;
			d.width=((InputProvider)getElement()).getSize().width;
			if(num>4) 
				d.height=(25*num)+25;
			
				
			
			SetInputProviderNumberOfInputPinsCommand rnc=new SetInputProviderNumberOfInputPinsCommand();
			rnc.setPart((InputProvider)getElement());
			
			rnc.setNumberOfInputs(num,d);			
			runCommand(rnc);
			
			
		}
	};
	
		
	private TextChangeHelper LogFileFieldChangelistener = new TextChangeHelper() {

		public void textChanged(Control control) {
			
			
			ChangeLogFileText();		
			
		}
	};	
	
	
	private void ChangeLogFileText(){
		SetInputProviderLogFileCommand rnc=new SetInputProviderLogFileCommand();
		rnc.setPart((InputProvider)getElement());			
		rnc.setLogFile(txtLogFileName.getText().trim());			
		runCommand(rnc);
	}
	
	
	private TextChangeHelper IndexFieldChangelistener = new TextChangeHelper() {

		public void textChanged(Control control) {
			
			
			ChangeIndexText();		
			
		}
	};
	private void ChangeIndexText(){
		SetInputProviderIndexCommand rnc=new SetInputProviderIndexCommand();
		rnc.setPart((InputProvider)getElement());			
		rnc.setIndex(txtIndex.getText().trim());			
		runCommand(rnc);
	}
	
	
	private void ChangeInputFilesList(){
		SetInputProviderInputFilesCommand rnc=new SetInputProviderInputFilesCommand();
		rnc.setPart((InputProvider)getElement());			
		rnc.setFileNames((ArrayList<String>)filenames);			
		runCommand(rnc);
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		
		
		buttons=new HashMap<Object, Button>();
		editButtons= new HashMap<Object, Button>();
		
		
		filenames=new ArrayList<String>();
		
		composite = getWidgetFactory().createFlatFormComposite(parent);
		composite.setLayout(null);
		composite.setBounds(28, 10, 900, 252);
		
		//----------ID------------------
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBounds(5, 5, 55, 15);
		lblNewLabel.setText("ID");
		
		txtId = new Text(composite, SWT.BORDER);
		txtId.setBounds(66, 2, 76, 21);
		
		//------------NumberOfPinputPins-----------		
		Label lblNumberOfinputs = new Label(composite, SWT.NONE);
		lblNumberOfinputs.setBounds(170, 5, 120, 15);
		lblNumberOfinputs.setText("Number Of Input Pins");
		
		txtNumberOfInputPins = new Text(composite, SWT.BORDER);
		txtNumberOfInputPins.setBounds(300, 2, 55, 21);
		
		
		//------------Index---------------------
		Label lblIndex = new Label(composite, SWT.NONE);
		lblIndex.setBounds(375, 5,60, 15);
		lblIndex.setText("Index ");
		
		txtIndex = new Text(composite, SWT.BORDER);
		txtIndex.setBounds(440, 2, 76, 21);
		
		
		
		//------------Logfile--------------		
		Label lblLogFile = new Label(composite, SWT.NONE);
		lblLogFile.setBounds(5, 29, 55, 15);
		lblLogFile.setText("Log File");
		
		txtLogFileName= new Text(composite, SWT.BORDER);
		txtLogFileName.setBounds(66, 24, 362, 21);
		
		Button btnLogFileBrows = new Button(composite, SWT.NONE);
		btnLogFileBrows.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
				{
					
				FileDialog fileDialog = new FileDialog(composite.getShell());
			    // Set the text
			    fileDialog.setText("Select File");
			    // Set filter on .txt files
			    fileDialog.setFilterExtensions(new String[] { "*.xml" });
			    // Put in a readable name for the filter
			    fileDialog.setFilterNames(new String[] { "XML files(*.xml)" });
			    // Open Dialog and save result of selection
			    String selected = fileDialog.open();
			    txtLogFileName.setText(selected);
			    ChangeLogFileText();
					 
				}
		});
		btnLogFileBrows.setBounds(434, 24, 75, 25);
		btnLogFileBrows.setText("Browse");
		
		//-----------inputFile-----------------	

		

		Button btnBrows = new Button(composite, SWT.NONE);
		btnBrows.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
				{
					
				FileDialog fileDialog = new FileDialog(composite.getShell(),SWT.OPEN);
			    // Set the text
			    fileDialog.setText("Select File");
			    // Set filter on .txt files
			    fileDialog.setFilterExtensions(new String[] { "*.h5" });
			    // Put in a readable name for the filter
			    fileDialog.setFilterNames(new String[] { "HDF5 (*.h5)" });
			    // Open Dialog and save result of selection
			    String selected = fileDialog.open();
			    //txtInputFileName.setText(selected);
			    if(selected!=null)
			    	addtolist(selected);
					 
				}
		});
		btnBrows.setBounds(10, 50, 300, 25);
		btnBrows.setText("Add existing Sample File to the list");
		


		
		btnGenarateInput = new Button(composite, SWT.NONE);
		
		btnGenarateInput.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
				{
				
				NewSampleWizard newsamplewizard=new NewSampleWizard();
				WizardDialog wizardDialog = new WizardDialog(null,newsamplewizard);
					    if (wizardDialog.open() == Window.OK) {
					      //txtInputFileName.setText(newsamplewizard.getSampleFilename());
					     addtolist(newsamplewizard.getSampleFilename());
					     //txtInputFileName.setText("");
					    } else {
					      System.out.println("Cancel pressed");
					    }
					    } 
				
				
	
		});
		
		btnGenarateInput.setBounds(350, 50, 320, 25);
		btnGenarateInput.setText("Generate new Sample File");

		
		
		
		
		viewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);

		final Table table_1 = viewer.getTable();
		table_1.setBounds(5, 80, 664, 132);
		
        viewer.getTable().setHeaderVisible(true);
        viewer.getTable().setLinesVisible(true);
        viewer.setContentProvider(new ArrayContentProvider());

        
        
        TableColumn column = new TableColumn(viewer.getTable(), SWT.NONE);
        column.setText("row");
        column.setWidth(50);
        TableViewerColumn rowcntrCol = new TableViewerColumn(viewer, column);
        rowcntrCol.setLabelProvider(new RowNumberLabelProvider());

        
        column = new TableColumn(viewer.getTable(), SWT.NONE);
        column.setText("File Name");
        column.setWidth(450);
        TableViewerColumn firstNameCol = new TableViewerColumn(viewer, column);
        firstNameCol.setLabelProvider(new ColumnLabelProvider(){

            @Override
            public String getText(Object element) {
                String p = (String)element;

                return p;
            }

        });
        
       
       

        column = new TableColumn(viewer.getTable(), SWT.NONE);
        column.setText("Actions");
        column.setWidth(80);
        TableViewerColumn actionsNameCol = new TableViewerColumn(viewer, column);
        
        
        
    	
    	Iterator<Object> itr=buttons.keySet().iterator();
		while (itr.hasNext())
			buttons.get(itr.next()).dispose();

		itr=editButtons.keySet().iterator();
		while (itr.hasNext())
			editButtons.get(itr.next()).dispose();
		buttons = new HashMap<Object, Button>();
    	editButtons=new Hashtable<Object, Button>();
		

    	
    	
    	actionsNameCol.setLabelProvider(new ColumnLabelProvider(){
            //make sure you dispose these buttons when viewer input changes
            //final Map<Object, Button> 
        	


            @Override
            public void update(ViewerCell cell) {
            	
            	
            	TableItem item = (TableItem) cell.getItem();
            	
                Button button;
                if(buttons.containsKey(cell.getElement()))
                {
                    button = buttons.get(cell.getElement());
                }
                else
                {
                    button = new Button((Composite) cell.getViewerRow().getControl(),SWT.NONE);
                    button.setText("Remove");
                     String filename=cell.getElement().toString();
                     button.setData(filename);
                    button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) 
						{
						
						String fname=((Button)e.getSource()).getData().toString();	
						System.out.println(fname);
						filenames.remove(fname);
							((Button)e.getSource()).dispose();
							buttons.get(fname).dispose();
							buttons.remove(fname);
							
							editButtons.get(fname).dispose();
							editButtons.remove(fname);
							ChangeInputFilesList();
							//							viewer.setInput(filenames);
//							viewer.refresh();
							refresh();
							
							 
				               
							
						}
                    });
                    buttons.put(cell.getElement(), button);
                }
                TableEditor editor = new TableEditor(item.getParent());
                editor.grabHorizontal  = true;
                editor.grabVertical = true;
                editor.setEditor(button , item, cell.getColumnIndex());
                editor.layout();
            }

        });
        
        
        
        
        //---------------------------------View column
        
        column = new TableColumn(viewer.getTable(), SWT.NONE);
        column.setText("View");
        column.setWidth(80);
        TableViewerColumn editNameCol = new TableViewerColumn(viewer, column);
        
    	
        editNameCol.setLabelProvider(new ColumnLabelProvider(){
            //make sure you dispose these buttons when viewer input changes
            //final Map<Object, Button> 
        	


            @Override
            public void update(ViewerCell cell) {
            	
            	TableItem item = (TableItem) cell.getItem();
                Button button;
                if(editButtons.containsKey(cell.getElement()))
                {
                    button = editButtons.get(cell.getElement());
                }
                else
                {
                    button = new Button((Composite) cell.getViewerRow().getControl(),SWT.NONE);
                    button.setText("View");
                    filename=cell.getElement().toString();
                    button.setData(filename);
                    button.addSelectionListener(new SelectionAdapter() 
                    {
						@Override
						public void widgetSelected(SelectionEvent ex) 
						{
							//-------
//							File f = new File(filename);
//						    System.out.println(filename);
//							IPath ipath = new Path(f.getPath());
//							IFileStore fileLocation = EFS.getLocalFileSystem().getStore(ipath);
//							FileStoreEditorInput fileStoreEditorInput = new FileStoreEditorInput(fileLocation);
							String fname=((Button)ex.getSource()).getData().toString();
							String[] btns={};
							SampleViewer sv=new SampleViewer(composite.getShell(), "Sample", null, "", 0, btns, 0, fname);
							sv.create();
							sv.open();
							//-----------
						}
                    });
                    editButtons.put(cell.getElement(), button);
                }
                TableEditor editor = new TableEditor(item.getParent());
                editor.grabHorizontal  = true;
                editor.grabVertical = true;
                editor.setEditor(button , item, cell.getColumnIndex());
                editor.layout();
            }

        });
        
        
     
            
        Label lblHelpTip = new Label(composite, SWT.WRAP | SWT.BORDER);
		lblHelpTip.setText(ResusMessages.InputProvider_Configuration_information);
		lblHelpTip.setBounds(composite.getBounds().width+composite.getLocation().x, 10, 500, 200);

		
		
		
		
		
		IdChangelistener.startListeningTo(txtId);
		IdChangelistener.startListeningForEnter(txtId);
		
		
		NumberOfInputPinsFieldChangelistener.startListeningForEnter(txtNumberOfInputPins);
		NumberOfInputPinsFieldChangelistener.startListeningTo(txtNumberOfInputPins);
		
		LogFileFieldChangelistener.startListeningForEnter(txtLogFileName);
		LogFileFieldChangelistener.startListeningTo(txtLogFileName);

		IndexFieldChangelistener.startListeningForEnter(txtIndex);
		IndexFieldChangelistener.startListeningTo(txtIndex);
		
	}
	
	
	 private void addtolist(String filePath){
		 if(filePath==null || filePath.equals("")) {
				MessageDialog.openWarning(composite.getShell(),"Input Error","Please select an input file ");
				return;
			}
			
			if(!filenames.contains(filePath))
				{
					filenames.add(filePath);
					//((InputProvider)getElement()).setFileNames((ArrayList<String>)filenames);
					ChangeInputFilesList();
					MessageDialog.openInformation(composite.getShell(), "Successful", "Added Successfully to the list Of Sample Files");
				}
			else{
				MessageDialog.openWarning(composite.getShell(),"Input Error","Your selected file is already in list ");
			        //messageBox.setMessage("Your selected file is already in list");
			        

			        return;
			}
					

			refresh();
	 }

	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
	
		
		if(!(getElement() instanceof InputProvider)){
			MessageDialog.openError(composite.getShell(), "Error","Element is not an InputProvider Instance");
			return;
		}
		
		IdChangelistener.startNonUserChange();
		LogFileFieldChangelistener.startNonUserChange();
		NumberOfInputPinsFieldChangelistener.startNonUserChange();
		IndexFieldChangelistener.startNonUserChange();
		try {
			
			Iterator<Object> itr=buttons.keySet().iterator();
			while (itr.hasNext())
				buttons.get(itr.next()).dispose();

			itr=editButtons.keySet().iterator();
			while (itr.hasNext())
				editButtons.get(itr.next()).dispose();
			buttons=new Hashtable<Object, Button>();
			editButtons=new Hashtable<Object, Button>();
			
			String id = (String) ((InputProvider) getElement()).getId();
			
			txtId.setText(id);
			
			txtIndex.setText((String) ((InputProvider) getElement()).getIndex());
			txtNumberOfInputPins.setText(String.valueOf(((InputProvider)getElement()).getNumberOfInputs()));
			txtLogFileName.setText(((InputProvider)getElement()).getLogFileName());
			
			if(((InputProvider) getElement()).getFileNames()!=null)
			filenames=((InputProvider) getElement()).getFileNames();
			else filenames=new ArrayList<String>();
			
			
			viewer.setInput(filenames);
			
			
			

		}catch(Exception x){
			System.out.println(x.getMessage());
		}
		finally {
			IdChangelistener.finishNonUserChange();
			LogFileFieldChangelistener.finishNonUserChange();
			NumberOfInputPinsFieldChangelistener.finishNonUserChange();
			IndexFieldChangelistener.finishNonUserChange();
		}
	
	}

	
	 

	
	
	 
	
}