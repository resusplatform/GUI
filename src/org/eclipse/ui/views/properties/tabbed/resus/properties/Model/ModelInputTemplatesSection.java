 
package org.eclipse.ui.views.properties.tabbed.resus.properties.Model;


import java.awt.Dialog;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFileChooser;









import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.resus.properties.AbstractSection;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import de.clausthal.tu.ielf.resus.RowNumberLabelProvider;
import de.clausthal.tu.ielf.resusdesigner.ResusMessages;
import de.clausthal.tu.ielf.resusdesigner.TemplateEditor;
import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.SetResusModelInputFilesCommand;






/**

 * The location section on the location tab.
 * 
 * @author Javad Ghofrani
 */
public class ModelInputTemplatesSection extends AbstractSection {
	
	
	
	
	private Text txtInputFilename;
	
	
	
	
	
	
	private  Button btnAddToList;
	 private TableViewer viewer;
	 
	List<String> inputFileNames;// =new ArrayList<String>();
	Map<Object, Button> removeButtons ;//= new HashMap<Object, Button>();
	Map<Object, Button> editButtons ;//= new HashMap<Object, Button>();
		
	 
	
	 
	
	

	
	/**
	 * 
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
		
		
		
		///------------- input file field
		Label lblInputfile = new Label(composite, SWT.NONE);
		lblInputfile.setBounds(5, 10, 155, 15);
		lblInputfile.setText("Input Template File name");
		
		
		txtInputFilename = new Text(composite, SWT.BORDER);
		txtInputFilename .setBounds(175, 10, 200, 21);
		
		//-----------------------------------------------------------------------		
		
		//----------------------add to list button--------------------------------
		btnAddToList = new Button(composite, SWT.NONE);
		 
		btnAddToList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
				{
					
				if(txtInputFilename.getText().trim().equals("")) {
					MessageDialog.openWarning(composite.getShell(),"Input Error","Please select an input file ");
					return;
				}
				if(!inputFileNames.contains(txtInputFilename .getText()))
					{
						
					String workingDir=((ResusModel) getElement()).getWorkingDirectory();		
					Path path=Paths.get(workingDir).resolve(Paths.get(txtInputFilename.getText().trim()));
					   if(validateTempalteFile(path,workingDir)==false){
						   txtInputFilename.setText("");
							return;
					   }
					
					
											
						addTolist(txtInputFilename .getText().trim());
					}
				else{
					MessageDialog.openError(null, "duplicate Input Error", "the selected file exists already in the list");
					return;			      
				}
						
				//ListChanged();
				viewer.setInput(inputFileNames);
				viewer.refresh();
					 
				}

			
		});
		btnAddToList.setBounds(390, 10, 75, 25);
		btnAddToList.setText("Add To List");
		//----------------------------------------------------------------------
		
		
		//-----------------btn brows (add existing template file------------------
		
		Button btnBrows = new Button(composite, SWT.NONE);
		btnBrows.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
				{
					
				FileDialog fileDialog = new FileDialog(composite.getShell(),SWT.OPEN);
			    // Set the text
			    fileDialog.setText("Select File");
			    // Set filter on .txt files
			    fileDialog.setFilterExtensions(new String[] { "*.tmp" });
			    // Put in a readable name for the filter
			    fileDialog.setFilterNames(new String[] { "All ReSUS Tempalte Files(*.tmp)" });
			    // Open Dialog and save result of selection
			    String workingDir=((ResusModel) getElement()).getWorkingDirectory();
			    if(workingDir!=null && workingDir.equals("")==false)
			    	fileDialog.setFilterPath(workingDir);			    
			    
			    String selected = fileDialog.open();
			    
			    if(selected==null) 
			    	return;
			    
			    Path path=Paths.get(selected);
			    if(validateTempalteFile(path, workingDir)==false)
			    	return;
			    
			    addTolist(path.toAbsolutePath().getFileName().toString());
			    
			    

					 
				}
		});
		btnBrows.setBounds(390, 40, 175, 25);
		btnBrows.setText("Add Existing Template File");
		//-----------------------------------------------------------------------------
		
		
		
		
		//-----------------------------------------table-------------------
		viewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);

		final Table table_1 = viewer.getTable();
		table_1.setBounds(5, 80, 685, 132);
		
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
        column.setWidth(430);
        TableViewerColumn firstNameCol = new TableViewerColumn(viewer, column);
        firstNameCol.setLabelProvider(new ColumnLabelProvider(){

            @Override
            public String getText(Object element) {
                String p = (String)element;
                return p;
            }

        });
        
        
        column = new TableColumn(viewer.getTable(), SWT.NONE);
        column.setText("Remove");
        column.setWidth(100);
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
                   
                    final String filename=(String)cell.getElement();
                    button.setData(filename);
                    
                    button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) 
						{
							
						String fname=((Button)e.getSource()).getData().toString();
							
							
						inputFileNames.remove(fname);
							((Button)e.getSource()).dispose();
							removeButtons.remove(fname);
							editButtons.get(fname).dispose();
							editButtons.remove(fname);
							
							
							
							setInputFiles(inputFileNames);
							
							viewer.setInput(inputFileNames);
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
        
        //------------------edit------------------------
        column = new TableColumn(viewer.getTable(), SWT.NONE);
        column.setText("Template");
        column.setWidth(100);
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
                    button.setText("Edit Template");
                    final String filename=(String)cell.getElement();
                    button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent ex) 
						{
									try {
						                
										String workingDir=((ResusModel) getElement()).getWorkingDirectory();
										Path ipath = Paths.get(workingDir).resolve(filename+".tmp");
										
										String[] btns={"ok","Cancel"};										
										TemplateEditor te=new TemplateEditor(composite.getShell(),  null);
										
										te.init();
										te.run(composite.getDisplay(),ipath.toString());
										
									} //catch (PartInitException e) {
									catch (Exception e) {
										
										MessageDialog.openError(composite.getShell(),"Error",e.getMessage());
									}

						
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
        
        
        
        
        
        
        viewer.setInput(inputFileNames);

        
        
    

		
		
		
       
		
        
            
       

		
		
        Label lblHelpTip = new Label(composite, SWT.WRAP | SWT.BORDER);
		lblHelpTip.setText(ResusMessages.Model_Executor_information);
		lblHelpTip.setBounds(composite.getBounds().width+composite.getLocation().x, 10, 500, 200);

			
	
		
	}
	
	private void addTolist(String filename){
		
		
		
		if(filename.endsWith(".tmp")){
			filename=filename.substring(0, filename.length()-4);
		}
		
		if(inputFileNames.contains(filename)){
			MessageDialog.openError(null, "duplicate Input Error", "the selected file exists already in the list");
			return;
		}
		
		inputFileNames.add(filename);
		
		setInputFiles(inputFileNames);
		txtInputFilename.setText("");
	}
	
	
	 private void setInputFiles(List<String> inputFileNames) {
			// TODO Auto-generated method stub
			SetResusModelInputFilesCommand rnc=new SetResusModelInputFilesCommand();
			rnc.setPart((ResusModel)getElement());				
			rnc.setInputFiles((ArrayList<String>)inputFileNames);						
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
			
			
			if(((ResusModel) getElement()).getInputFileNames()!=null)
				inputFileNames=((ResusModel) getElement()).getInputFileNames();
			else inputFileNames=new ArrayList<String>();
			

			viewer.setInput(inputFileNames);
			viewer.refresh();
			
		}
		catch(Exception x){
			MessageDialog.openError(null, "Error", x.getMessage());
		}
		finally {
			
			
			
		}
	}
	
	private boolean validateTempalteFile(Path path,String workingDir){
		
    	if(path.isAbsolute()==false){
    		MessageDialog.openError(null, "Erro Path", "The path to the input Template should be absolute");
    		return false;
    	}
    	
    	if(path.toFile().exists()==false){
    		MessageDialog.openError(null, "Erro Path", "Please insert an existing template file");
    		return false;
    	}
    	
    	if(path.toFile().isDirectory()==true){
    		MessageDialog.openError(null, "Erro Path", "The Input Template File should not be a directory");
    		return false;
    	}
    	 // check to be located in working directory
	    if(path.getParent().compareTo(Paths.get(workingDir))!=0){
	    	MessageDialog.openError(null,"invalid Tempalte file", "please select a Template which exists in the defined working directory for model frame");
	    	return false;
	    }
    	
    	
    	return true;
    	
		
	}
	
	
	
}
