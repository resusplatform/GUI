package de.clausthal.tu.ielf.resus.wizards;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
public class NewResusSimulationPageTwoWizard extends WizardPage {
	  private Composite container;

	  public NewResusSimulationPageTwoWizard() {
	    super("New Resus Simulation Wizard");
	    setTitle("final step ");
	    setDescription("Press Finish to Create and open ReSUS Simulation file");
	    //setControl(text1);
	  }

	  @Override
	  public void createControl(Composite parent) {
	    container = new Composite(parent, SWT.NONE);
	    GridLayout layout = new GridLayout();
	    container.setLayout(layout);
	    Label label1 = new Label(container, SWT.NONE);
	    label1.setText("Your ReSUS Simulation File is ready to create");
	    Label labelCheck = new Label(container, SWT.NONE);
	    labelCheck.setText("press Finish to complete the process or click back to change the New Resus Simulation Configuration");
	    // required to avoid an error in the system
	    setControl(container);
	    setPageComplete(true);
	  }

	  
	}
	 