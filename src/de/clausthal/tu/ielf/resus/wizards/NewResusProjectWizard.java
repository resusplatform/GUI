package de.clausthal.tu.ielf.resus.wizards;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.ProgressMonitor;

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

public class NewResusProjectWizard extends Wizard implements INewWizard  {
	
	private IStructuredSelection selection;
	private IWorkbench workbench;

	private NewResusProjectPageOneWizard pageone;
	private NewResusProjectPageTwoWizard pagetwo;
	@Override
	  public void addPages() {
		setWindowTitle("New ReSUS Project Wizard");
		pageone = new NewResusProjectPageOneWizard();
	    pagetwo = new NewResusProjectPageTwoWizard();
	    addPage(pageone);
	    addPage(pagetwo);
	    
	  }


	public void init(IWorkbench aWorkbench, IStructuredSelection currentSelection) {
		workbench = aWorkbench;
		selection = currentSelection;
		
	}

	
	public boolean performFinish() {
		try{
			
			IWorkspace workspace = ResourcesPlugin.getWorkspace();

			IProject project = workspace.getRoot().getProject(pageone.getProjectName());
			

			IProjectDescription projectDesc = workspace.newProjectDescription(pageone.getProjectName());

			projectDesc.setLocation(new Path(pageone.getProjectAddress())
			.append(pageone.getProjectName()));

//			IFile file=project.getFile(new Path(pageone.getProjectAddress())
//			.append(pageone.getProjectName()).append(pageone.getProjectName()+".xml"));

			
		  
		 
			
			
			// TODO add any natures, builders, ... required to the project description

			
			project.create(projectDesc, null);

			project.open(null);

//			Shell shell = window.getShell();
//			String name = new FileDialog(shell, SWT.OPEN).open();
//			if (name == null)
//			    return;
			Path filePath=new Path(projectDesc.getLocation().toString());
			Path filefolderpath=(Path)filePath.append(pageone.getProjectName()+".xml");
			//filePath.append(pageone.getProjectName()).append(pageone.getProjectName()+".xml");
			File f=filefolderpath.toFile();
			boolean r=f.createNewFile();
			System.out.println(f.getAbsolutePath());
			System.out.println(r);
			project.refreshLocal(2, null);
			project.open(null);
			

			
			try {
                
				IPath ipath = new Path(f.getAbsolutePath());
				IFile ff=project.getFile(f.getName());
				IFileStore fileLocation = EFS.getLocalFileSystem().getStore(ipath);
				 org.eclipse.ui.IFileEditorInput fileStoreEditorInput = new  FileEditorInput(ff);
				IWorkbenchPage page2 = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				                            .getActivePage();
				page2.openEditor(fileStoreEditorInput,"org.eclipse.ui.examples.views.properties.tabbed.logic.TabbedPropertiesLogicEditor");
				
				
			} catch (PartInitException e) {
				String msg =  NLS.bind(IDEWorkbenchMessages.OpenLocalFileAction_message_errorOnOpen, f.getName());
				IDEWorkbenchPlugin.log(msg,e.getStatus());
				MessageDialog.open(MessageDialog.ERROR,getShell(), IDEWorkbenchMessages.OpenLocalFileAction_title, msg, SWT.SHEET);
			}
			
//			
//			IWorkbenchPage page = window.getActivePage();
//			if (page != null)
//			    page.openEditor(file);

			
		
			
			
			
			
			
			
		}
		catch(Exception x){
			System.err.println(x.getMessage());
			return false;
		}
		
		return true;
	}
	
	public boolean canFinish()
	 {
	 if(getContainer().getCurrentPage() ==pageone)
	 return false;
	 else 
	 return true;
	 }
	 


}