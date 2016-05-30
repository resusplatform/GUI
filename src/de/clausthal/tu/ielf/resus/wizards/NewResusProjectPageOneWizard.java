package de.clausthal.tu.ielf.resus.wizards;



import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;

import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;

public class NewResusProjectPageOneWizard extends WizardPage {
  private Text txtProjectName;
  private Text txtProjectAddress;
  private Label lblMessage;
  private Composite container;
  private Button btnBrowse;

  public NewResusProjectPageOneWizard() {
    super("New ReSUS Projcet Wizard");
   
    setTitle("Configure Project Name");
    setDescription("This Wizard Helps you to make a new ReSUS project ");
  }

  @Override
  public void createControl(Composite parent) {
		    container = new Composite(parent, SWT.NONE);
		    GridLayout layout = new GridLayout();
		    container.setLayout(layout);
		    layout.numColumns = 3;
		    Label label1 = new Label(container, SWT.NONE);
		    label1.setText("Project Name");
		
		    txtProjectName = new Text(container, SWT.BORDER | SWT.SINGLE);
		    txtProjectName.setText("");
		    txtProjectName.addKeyListener(new KeyListener() {
		
		      @Override
		      public void keyPressed(KeyEvent e) {
		      }
		
		      @Override
		      public void keyReleased(KeyEvent e) {
		    	  if (!txtProjectName.getText().isEmpty() && !txtProjectAddress.getText().isEmpty()) {
		          setPageComplete(true);
		
		        }
		    	  else setPageComplete(false);
		      }
		
		    });
		    GridData gd_txtProjectName = new GridData(GridData.FILL_HORIZONTAL);
		    gd_txtProjectName.widthHint = 372;
		    txtProjectName.setLayoutData(gd_txtProjectName);
		    //txtProjectAddress.setLayoutData(gd);
		    // required to avoid an error in the system
		    setControl(container);
            new Label(container, SWT.NONE);
            Label label2 = new Label(container, SWT.NONE);
            label2.setText("Project Adress");
        
            txtProjectAddress = new Text(container, SWT.BORDER | SWT.SINGLE);
            GridData gd_txtProjectAddress = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
            gd_txtProjectAddress.widthHint = 369;
            txtProjectAddress.setLayoutData(gd_txtProjectAddress);
            txtProjectAddress.setText("");
            txtProjectAddress.addKeyListener(new KeyListener() { //TODO: check the validity of the typed path

              @Override
              public void keyPressed(KeyEvent e) {
              }

              @Override
              public void keyReleased(KeyEvent e) {
                if (!txtProjectName.getText().isEmpty() && !txtProjectAddress.getText().isEmpty()) {
                  setPageComplete(true);


                }
                else setPageComplete(false);
                
              }

            });
        
        btnBrowse = new Button(container, SWT.NONE);
        btnBrowse.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		DirectoryDialog directoryDialog = new DirectoryDialog(container.getShell());
			    // Set the text
				directoryDialog.setText("Select Project directory");
			    
			    // Open Dialog and save result of selection
			    String selected = directoryDialog.open();
			   
			    if(selected!=null)
			    	txtProjectAddress.setText(selected);
			    if (!txtProjectName.getText().isEmpty() && !txtProjectAddress.getText().isEmpty()) {
			          setPageComplete(true);
			    }
                else setPageComplete(false);
			    

        	}
        });
        btnBrowse.setText("Browse...");
        new Label(container, SWT.NONE);
      
        lblMessage= new Label(container, SWT.NONE);
        lblMessage.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
        new Label(container, SWT.NONE);
        
        
        setPageComplete(false);

  }

  public String getProjectName() {
	  return txtProjectName.getText();
  }
  public String getProjectAddress(){
	  return txtProjectAddress.getText();
  }
}
 