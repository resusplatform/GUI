package de.clausthal.tu.ielf.resus.wizards;


import java.io.File;
import java.io.FilenameFilter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;

public class SampleTemplateSelectDialog  extends MessageDialog {

	  
	  
	  private String templateName;
	  
	  Composite container;
	  public SampleTemplateSelectDialog(Shell parentShell,String[] btns) {
	     super (parentShell, "Load Template ",null, "",  CONFIRM,btns, OK);
	  }

	  @Override
	  public void create() {
	    super.create();
	    // Set the title
	    

	  }

	  @Override
	  protected Control createDialogArea(Composite parent) {
		  
			container=new Composite(parent,SWT.NULL);
			
			
			final List list = new List(container, SWT.BORDER | SWT.V_SCROLL);
		    list.setBounds(0, 10, 220, 100);
		   
		    // get the list of ptml files in current project
		    IEditorPart editor = PlatformUI.getWorkbench()
	    	        .getActiveWorkbenchWindow().getActivePage().getActiveEditor();
	    	IFileEditorInput input = (IFileEditorInput)editor.getEditorInput() ;
	    	IFile file = input.getFile();
	    	IProject activeProject = file.getProject();
	    	IResource res = (IResource) activeProject;
			
	    
	    	File dir = new File(res.getLocation().toString());
	    	File[] files = dir.listFiles(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					// TODO Auto-generated method stub
					return name.toLowerCase().endsWith(".ptml");
				
				}
			});	    	
	    	
	    	//res.getLocation().append(txtTemplateName.getText()+".ptml").toFile();
		    for(int i=0;i<files.length;i++){
		    	list.add(files[i].getName());
		    }
		    
		    
		    
		    //show the ptml file names in listbox
		    
		    

		    list.addSelectionListener(new SelectionListener() {
				
		      public void widgetSelected(SelectionEvent event) {
		        
		    	
		        String [] selectedFiles=list.getSelection();
		        if(selectedFiles.length>0)
		        	templateName=selectedFiles[0];
		      }

		      public void widgetDefaultSelected(SelectionEvent event) {
		       
		      }
		    });
			list.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseUp(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseDown(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					// TODO Auto-generated method stub
					//System.out.println(templateName);
					okPressed();
				}
			});
		    
			
	    
	    return container;
	  }

	  private boolean isValidInput() {
	    
	    if (templateName==null || templateName.equals(null) || templateName.length() == 0) {	      
	    	return false;
	    }
	    return true;
	  }
	  
	  @Override
	  protected boolean isResizable() {
	    return true;
	  }

	  
	  
	  
	  @Override
	protected void buttonPressed(int buttonId) {
		// TODO Auto-generated method stub
		if(buttonId==org.eclipse.jface.window.Window.OK)
			 if(!isValidInput()){
			    	MessageDialog.openError(null, "Error", "Please select a Template File from the list");
			    	return;
			    }
				  //saveInput();
			    super.okPressed();
		 // super.buttonPressed(buttonId);
	}

	@Override
	  protected void okPressed() {
	    if(!isValidInput()){
	    	MessageDialog.openError(null, "Error", "Please select a Template File from the list");
	    	return;
	    }
		  //saveInput();
	    super.okPressed();
	  }

	  public String getTemplateName() {
	    return templateName;
	  }


	
	
	


	
	
	
	
}

//res.getLocation().append(txtTemplateName.getText()+".ptml").toFile();
