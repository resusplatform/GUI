package de.clausthal.tu.ielf.resus.wizards;




import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.ProgressMonitor;

import ncsa.hdf.hdf5lib.H5;
import ncsa.hdf.hdf5lib.HDF5Constants;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.tabbed.resus.Activator;

import de.clausthal.tu.ielf.randomGenrators.*;
import de.clausthal.tu.ielf.randomGenrators.distributions.Distribution;
import de.clausthal.tu.ielf.randomGenrators.distributions.GaussDistribution;
import de.clausthal.tu.ielf.randomGenrators.distributions.TriangleDistribution;

public class NewSampleWizard extends Wizard implements INewWizard  {
	
	private IStructuredSelection selection;
	private IWorkbench workbench;
	private String sampleFilename;
	private NewSamplePageOneWizard pageone;
	private NewSamplePageTwoWizard pageTwo;
	private NewSamplePageThreeWizard pageThree;
	
	@Override
	  public void addPages() {
		setWindowTitle("Sample Generator Wizard");
		pageone = new NewSamplePageOneWizard();
		
	    pageTwo=new NewSamplePageTwoWizard();
	    pageThree=new NewSamplePageThreeWizard();
	    addPage(pageone);
	    addPage(pageTwo);	    
	    addPage(pageThree);
	    
	  }


	public void init(IWorkbench aWorkbench,
			IStructuredSelection currentSelection) {
		workbench = aWorkbench;
		selection = currentSelection;
		
	}

	
	public boolean performFinish() {
		
		
		
		
		
		
		return true;
	}
	private String filename;// address of HDF5
	private ArrayList<Parameter> parameterConfigurations; // list of Configurations of parameteres for sampling
	private int samplingMethodIndex;
	


	public ArrayList<Parameter> getParameterConfigurations() {
		return parameterConfigurations;
	}


	public void setParameterConfigurations(
			ArrayList<Parameter> parameterConfigurations) {
		this.parameterConfigurations = parameterConfigurations;
	}


	public void setFileName(String filename){
		this.filename=filename;		
	}
	public String getFileName(){
		return filename;
	}
	
	
	
	
	public boolean canFinish()
	 {
	 if(getContainer().getCurrentPage() ==pageone)
		 return false;
	 if(getContainer().getCurrentPage() ==pageTwo)
		 return false;
//	 if(getContainer().getCurrentPage() == pageThree)
//		 return false;
	 else 
	 return true;
	 }
	 public String getSampleFilename(){
		 sampleFilename=pageTwo.filename;
		 return sampleFilename;
	 }


}