package resus.hanlders;




import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JOptionPane;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;

public class selectThreadsDialog   extends MessageDialog {

	  
	

	private String numberOfThreads;
	  
	  Composite container;
	  private Text txtNumOfThreads;
	  public selectThreadsDialog(Shell parentShell,String[] btns) {
	     super (parentShell, "Enter level of the Parallelization ",null, "",  CONFIRM,btns, OK);
	  }

	  @Override
	  public void create() {
	    super.create();
	    // Set the title
	    

	  }

	  @Override
	  protected Control createDialogArea(Composite parent) {
		  
			container=new Composite(parent,SWT.FILL);
			
			txtNumOfThreads = new Text(container, SWT.BORDER);
			txtNumOfThreads.setBounds(9, 21, 199, 21);
			
			Label lblNewLabel = new Label(container, SWT.NONE);
			lblNewLabel.setBounds(-1, 0, 236, 15);
			lblNewLabel.setText("Number Of Threads (between 1 and 100)");
			
		
//		    
			
	    
	    return container;
	  }

	  private boolean isValidInput() {
		  try{
		  int number = Integer.parseInt(txtNumOfThreads.getText().trim());
		   if (number > 100 || number < 0) 
			   MessageDialog.openError(null, "Error", "Please enter a valid numbert");
		  }
		  catch(NumberFormatException x){
			  MessageDialog.openError(null, "Error", "Please Enter a valid number");
			  return false;
		  }
		  numberOfThreads=txtNumOfThreads.getText();
		  return true;
	  }
	     
		  
	   
	  
	  
	  @Override
	  protected boolean isResizable() {
	    return false;
	  }

	  
	  
	  
	  @Override
	protected void buttonPressed(int buttonId) {
		// TODO Auto-generated method stub
		if(buttonId==org.eclipse.jface.window.Window.OK){
			 if(!isValidInput()){
			    	
			    	return;
			    }
				  
			    super.okPressed();
		}
		else super.cancelPressed();
		 
	}

	@Override
	  protected void okPressed() {
	    if(!isValidInput()){
	    
	    	return;
	    }
		  
	    super.okPressed();
	  }

	  public String getNumbreOfThreads() {
	    return numberOfThreads;
	  }
}


