package de.clausthal.tu.ielf.resus.wizards;
/*
 * TODO: full path to the HDF5 file must be a valid file 
 * TODO: sample size must be a postivie integer value
 * 
 */


import java.io.File;
import java.util.ArrayList;

import ncsa.hdf.hdf5lib.H5;
import ncsa.hdf.hdf5lib.HDF5Constants;
import ncsa.hdf.hdf5lib.exceptions.HDF5Exception;
import ncsa.hdf.hdf5lib.exceptions.HDF5LibraryException;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;

import de.clausthal.tu.ielf.randomGenrators.Random;
import de.clausthal.tu.ielf.randomGenrators.SimpleRandomGenerator;
import de.clausthal.tu.ielf.randomGenrators.SobolRandomGenerator;
import de.clausthal.tu.ielf.randomGenrators.distributions.Distribution;
import de.clausthal.tu.ielf.randomGenrators.distributions.GaussDistribution;
import de.clausthal.tu.ielf.randomGenrators.distributions.TriangleDistribution;

public class NewSamplePageTwoWizard extends WizardPage{
	private Text txtSampleSize;
	private Text txtSampleFile;
	Composite container;
	Combo combo;
	String filename;
	boolean isSampleGenerated;
	
	protected NewSamplePageTwoWizard() {
		  super("New Sample Wizard");
		    setTitle("Generate and save sample parameter values");
		    setDescription("This Wizard Helps you to generate and save Samples");
		    isSampleGenerated=false;
	}

	

	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		container=new Composite(parent,SWT.NULL);
		setControl(container);
		
		txtSampleSize = new Text(container, SWT.NONE);
		txtSampleSize.setBounds(142, 128, 60, 25);
		txtSampleSize.addKeyListener(new KeyListener() {			
			
		      @Override
		      public void keyPressed(KeyEvent e) {
		      }
		
		      @Override
		      public void keyReleased(KeyEvent e) {
		    	 controlComplete();
		      }
		
		    });
		
		Label label = new Label(container, SWT.NONE);
		label.setText("Sample Size");
		label.setBounds(52, 128, 80, 25);
		
		combo = new Combo(container, SWT.READ_ONLY);
		combo.setBounds(142, 80, 80, 23);
		combo.add("Simple ");
		combo.add("Sobol ");
		combo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				controlComplete();
			}
		});
		
		Label label_1 = new Label(container, SWT.NONE);
		label_1.setText("Sampling Method");
		label_1.setBounds(52, 82, 80, 25);
		
		Label lblSampleFileName = new Label(container, SWT.NONE);
		lblSampleFileName.setBounds(22, 33, 158, 23);
		lblSampleFileName.setText("full path to sample file (HDF5)");
		
		txtSampleFile = new Text(container, SWT.BORDER);
		txtSampleFile.setBounds(186, 30, 243, 21);
		txtSampleFile.addKeyListener(new KeyListener() {			
			
		      @Override
		      public void keyPressed(KeyEvent e) {
		      }
		
		      @Override
		      public void keyReleased(KeyEvent e) {
		    	 controlComplete();
		      }
		
		    });
		
		Button btnBrowse = new Button(container, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String selected=null;
				
				
				IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		    	IFileEditorInput input = (IFileEditorInput)editor.getEditorInput() ;
		    	IFile file = input.getFile();
		    	IProject activeProject = file.getProject();
		    	IResource res = (IResource) activeProject;
		    	final String startDirectory=res.getLocation().removeLastSegments(1).toString();
    		    	     

		    	    
		    	  
				
				
				
					FileDialog fileDialog = new FileDialog(container.getShell(),SWT.SAVE);
				    // Set the text
				    fileDialog.setText("Select Path to save the Sample File");
				    
				    // Set filter on .txt files
				    fileDialog.setFilterExtensions(new String[] { "*.H5"});
				    // Put in a readable name for the filter
				    fileDialog.setFilterNames(new String[] { "All HDF5 Files(*.H5)"});

				    // set the default location of the open dialog to the current project folder
				    fileDialog.setFilterPath(startDirectory);
				    
				    // Open Dialog and save result of selection
				    selected = fileDialog.open();
				    if(selected==null) return;
				   
				    
				  
				 
				
				if(selected!=null){
					 if(new File(selected).isDirectory()){
							MessageDialog.openError(null, "Path Error", "The path should define a file");
							return;
						}
					
					txtSampleFile.setText(selected);
				   	controlComplete();
				    	
				}
			    	//((ResusModel) getElement()).setExecutor(txtExecutor.getText());
			
			}
		});
		btnBrowse.setBounds(435, 28, 75, 25);
		btnBrowse.setText("Browse...");
		
		Button btnGenerateSample = new Button(container, SWT.NONE);
		btnGenerateSample.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(!isInputReadyToGenerateSample())
					return;
				try{
				generateSample();
				
				((NewSamplePageThreeWizard)getNextPage()).refresh();
				}
				catch(Exception x){
					MessageDialog.openError(null, "Error", x.getMessage());
					isSampleGenerated=false;
					controlComplete();
					return;
				}
				MessageDialog.openInformation(null, "Sample Generation", "Sample file has been generated Successfully");
				isSampleGenerated=true;
				controlComplete();
			}
		});
		btnGenerateSample.setBounds(324, 180, 186, 25);
		btnGenerateSample.setText("Generate Sample");
		setPageComplete(false);
		//TODO: set keypresslistener to txtnumberofrealizations
		//TODO: set the finish button enabled wenn all fields are full
		
	}
	
	/*
	 * checks if the path to the file is valid
	 * if the file already exist and asks to permision to rewrite the hdf5 file
	 * 
	 */
	
	private boolean isInputReadyToGenerateSample(){
		if(txtSampleFile.getText().isEmpty()){
			MessageDialog.openError(null, "Path Error", "The path to the Sample File should not be Empty");
			return false;
		}
		
		
		Path p=Paths.get(txtSampleFile.getText().trim());
		if(!p.isAbsolute()){
			MessageDialog.openError(null, "Path Error", "Please Enter an Absolute path");
			return false;
		}
		
		// if it is a file or directory. if directory then error
		if(new File(txtSampleFile.getText().trim()).isDirectory()){
			MessageDialog.openError(null, "Path Error", "The path should define a file");
			return false;
		}
		//if it is file, chek the directory exists 
		if(!new File(p.getParent().toString()).exists()){
			MessageDialog.openError(null, "Path Error", "The path to the file should exist");
			return false;
		}
		
		if(new File(txtSampleFile.getText().trim()).exists()){
	    	if(MessageDialog.openConfirm(null, "File Exists", "Would you like to overwrite the existing file?")==false)
	    		return false;    	
	    }
		
		PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**.h5");
		if (matcher.matches(p)==false){
			MessageDialog.openError(null, "File extension", "Please select a file with .h5 extension");
	    		return false;
		}

		
		
		if(txtSampleSize.getText().isEmpty()){
			MessageDialog.openError(null, "Sample Size Error", "Sample Size should be a positive integer value");
			return false;			
		}
		
		int l=-1;
		try{
			l=Integer.parseInt(txtSampleSize.getText().trim());
		}
		catch(NumberFormatException x){
			MessageDialog.openError(null, "Sample Size Error", "Sample Size should be a positive integer value");
			return false;
		}
		if(l<=0){
			MessageDialog.openError(null, "Sample Size Error", "Sample Size should be a positive integer value");
			return false;
		}
		
		
		
		return true;
	}
	
	private void generateSample() throws HDF5LibraryException, NullPointerException, HDF5Exception{
		//chek if the three fields are fuull
		
		// control file exisitance and give warinign if the file already exists
		
		
		Random randomGenerator=null;
		int sampleSize=getSampleLength();
		filename=getSampleFileName();
		
		//sampleFilename=filename;
		
		NewSamplePageOneWizard prepage=(NewSamplePageOneWizard) this.getPreviousPage();
		
		int cmbindex=getSamplingMethod();
		if(cmbindex==0)//Pseudo Simple Random
			randomGenerator=new SimpleRandomGenerator((int)sampleSize, prepage.getParametersList().size());
		else if(cmbindex==1) // Quazi montecarlo (Sobol )
			randomGenerator=new SobolRandomGenerator((int)sampleSize, prepage.getParametersList().size());
		
		
		
		//-------------------------save the generated sample---------------------
		
		

		int file_id = -1;
        int dataspace_id = -1;
		int dataset_id = -1;
		int attribute_id = -1;
        long[] dims = new long[2] ;
        dims[0]=1;
        dims[1]=sampleSize;
		double[][] parameter_data = new double[1][sampleSize];
		
		// Create a new file using default properties.
        try {
          
        
			
		  file_id = H5.H5Fcreate(filename , HDF5Constants.H5F_ACC_TRUNC,
                    HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT);
		  
		  if(file_id<0) throw new HDF5Exception("Unable to create the H5 file");
								
		for(int i=0;i<prepage.getParametersList().size();i++){
			Parameter p=prepage.getParametersList().get(i);
			
			for(int j=0;j<sampleSize;j++){
				p.distribution.setPrimitiveValue(randomGenerator.getItem(j, i));	
				parameter_data[0][j]=p.distribution.next();
			}
			
		
       
		// Create the data space for the dataset.
	    
	    	dataspace_id = H5.H5Screate_simple(2, dims, null);
	    
	     // Create the dataset.
	        
	            if ((file_id >= 0) && (dataspace_id >= 0))
	                dataset_id = H5.H5Dcreate(file_id, "/" + p.name,
	                        HDF5Constants.H5T_NATIVE_DOUBLE, dataspace_id,
	                        HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT);
	        
	        
	        
	   //-------------------write parameter configuration as attribute to the table
	        
	     // -----1st attribute : name
	        String attributename="name";			        
	        String attributevalue=p.name;
			int memtype_id=-1;
			int filetype_id = -1;
			int DIM0=1;
			int RANK=1;
			System.err.println(p.name.length());
			int SDIM=attributevalue.length()+1;
			StringBuffer[] str_data = new StringBuffer[1];
			str_data[0]=new StringBuffer(attributevalue);
			byte[][] dset_data = new byte[DIM0][SDIM];
			long[] dimsa = { DIM0 };
	        
	        
			
				memtype_id = H5.H5Tcopy(HDF5Constants.H5T_C_S1);
				if (memtype_id >= 0)
					H5.H5Tset_size(memtype_id, SDIM);
			
			
				filetype_id = H5.H5Tcopy(HDF5Constants.H5T_FORTRAN_S1);
				if (filetype_id >= 0)
					H5.H5Tset_size(filetype_id, SDIM - 1);
			
			
			
				dataspace_id = H5.H5Screate_simple(RANK, dimsa, null);
			
			
			
	     // Create the attribute.
			
				if ((dataset_id >= 0) && (dataspace_id >= 0) && (filetype_id>= 0))
					attribute_id = H5.H5Acreate(dataset_id, attributename, filetype_id,
							dataspace_id, HDF5Constants.H5P_DEFAULT);
							

			// Write the data to the dataset.
			
				for (int indx = 0; indx < DIM0; indx++) {
					for (int jndx = 0; jndx < SDIM; jndx++) {
						if (jndx < str_data[indx].length())
							dset_data[indx][jndx] = (byte) str_data[indx].charAt(jndx);
						else
							dset_data[indx][jndx] = 0;
					}
				}
				if ((attribute_id >= 0) && (memtype_id >= 0))
					H5.H5Awrite(attribute_id, memtype_id, dset_data);
				
			

			// End access to the dataset and release resources used by it.
			
				if (attribute_id >= 0)
					H5.H5Aclose(attribute_id);
			
			   
	        
			// -----2nd attribute : unit
	        attributename="unit";
	        attributevalue=p.unit;
			memtype_id=-1;
			filetype_id = -1;					
			//System.err.println(p.name.length());
			SDIM=attributevalue.length()+1;
			str_data = new StringBuffer[1];
			str_data[0]=new StringBuffer(attributevalue);
			dset_data = new byte[DIM0][SDIM];
			
	        
	        
			
				memtype_id = H5.H5Tcopy(HDF5Constants.H5T_C_S1);
				if (memtype_id >= 0)
					H5.H5Tset_size(memtype_id, SDIM);
			
				filetype_id = H5.H5Tcopy(HDF5Constants.H5T_FORTRAN_S1);
				if (filetype_id >= 0)
					H5.H5Tset_size(filetype_id, SDIM - 1);
			
				dataspace_id = H5.H5Screate_simple(RANK, dimsa, null);
			
			
			
			
	     // Create the attribute.
			
				if ((dataset_id >= 0) && (dataspace_id >= 0) && (filetype_id>= 0))
					attribute_id = H5.H5Acreate(dataset_id, attributename, filetype_id,
							dataspace_id, HDF5Constants.H5P_DEFAULT);
			
			// Write the data to the dataset.
			
				for (int indx = 0; indx < DIM0; indx++) {
					for (int jndx = 0; jndx < SDIM; jndx++) {
						if (jndx < str_data[indx].length())
							dset_data[indx][jndx] = (byte) str_data[indx].charAt(jndx);
						else
							dset_data[indx][jndx] = 0;
					}
				}
				if ((attribute_id >= 0) && (memtype_id >= 0))
					H5.H5Awrite(attribute_id, memtype_id, dset_data);
				
			

			// End access to the dataset and release resources used by it.
			
				if (attribute_id >= 0)
					H5.H5Aclose(attribute_id);
			   
			
			// -----3rd attribute : distribution
	        attributename="distribution";// 
	        attributevalue=p.distribution.getName();
			memtype_id=-1;
			filetype_id = -1;					
			//System.err.println(p.name.length());
			SDIM=attributevalue.length()+1;
			str_data = new StringBuffer[1];
			str_data[0]=new StringBuffer(attributevalue);
			dset_data = new byte[DIM0][SDIM];
			
	        
	        
			
				memtype_id = H5.H5Tcopy(HDF5Constants.H5T_C_S1);
				if (memtype_id >= 0)
					H5.H5Tset_size(memtype_id, SDIM);
			
				filetype_id = H5.H5Tcopy(HDF5Constants.H5T_FORTRAN_S1);
				if (filetype_id >= 0)
					H5.H5Tset_size(filetype_id, SDIM - 1);
			
				dataspace_id = H5.H5Screate_simple(RANK, dimsa, null);
			
			
			
			
	     // Create the attribute.
			
				if ((dataset_id >= 0) && (dataspace_id >= 0) && (filetype_id>= 0))
					attribute_id = H5.H5Acreate(dataset_id, attributename, filetype_id,
							dataspace_id, HDF5Constants.H5P_DEFAULT);
							

			// Write the data to the dataset.
			
				for (int indx = 0; indx < DIM0; indx++) {
					for (int jndx = 0; jndx < SDIM; jndx++) {
						if (jndx < str_data[indx].length())
							dset_data[indx][jndx] = (byte) str_data[indx].charAt(jndx);
						else
							dset_data[indx][jndx] = 0;
					}
				}
				if ((attribute_id >= 0) && (memtype_id >= 0))
					H5.H5Awrite(attribute_id, memtype_id, dset_data);
				
			

			// End access to the dataset and release resources used by it.
			
				if (attribute_id >= 0)
					H5.H5Aclose(attribute_id);
			
			// -----4rd attribute : distribution.min
	        attributename=Distribution.MIN_VALUE;
	        attributevalue="";
	        
	        if(p.distribution.getPropertyValue(Distribution.MIN_VALUE)!=null)
	        	attributevalue=String.valueOf(p.distribution.getPropertyValue(Distribution.MIN_VALUE));
	        
			memtype_id=-1;
			filetype_id = -1;					
			//System.err.println(p.name.length());
			SDIM=attributevalue.length()+1;
			str_data = new StringBuffer[1];
			str_data[0]=new StringBuffer(attributevalue);
			dset_data = new byte[DIM0][SDIM];
			
	        
	        
				memtype_id = H5.H5Tcopy(HDF5Constants.H5T_C_S1);
				if (memtype_id >= 0)
					H5.H5Tset_size(memtype_id, SDIM);
			
				filetype_id = H5.H5Tcopy(HDF5Constants.H5T_FORTRAN_S1);
				if (filetype_id >= 0)
					H5.H5Tset_size(filetype_id, SDIM - 1);
			
				dataspace_id = H5.H5Screate_simple(RANK, dimsa, null);
			
			
			
			
	     // Create the attribute.
			
				if ((dataset_id >= 0) && (dataspace_id >= 0) && (filetype_id>= 0))
					attribute_id = H5.H5Acreate(dataset_id, attributename, filetype_id,
							dataspace_id, HDF5Constants.H5P_DEFAULT);
							

			// Write the data to the dataset.
			
				for (int indx = 0; indx < DIM0; indx++) {
					for (int jndx = 0; jndx < SDIM; jndx++) {
						if (jndx < str_data[indx].length())
							dset_data[indx][jndx] = (byte) str_data[indx].charAt(jndx);
						else
							dset_data[indx][jndx] = 0;
					}
				}
				if ((attribute_id >= 0) && (memtype_id >= 0))
					H5.H5Awrite(attribute_id, memtype_id, dset_data);
				
			

			// End access to the dataset and release resources used by it.
			
				if (attribute_id >= 0)
					H5.H5Aclose(attribute_id);
			
			      
			
			// -----5th attribute : distribution.max
	        attributename=Distribution.MAX_VALUE;
	        attributevalue=" ";
	        
	        if(p.distribution.getPropertyValue(Distribution.MAX_VALUE)!=null)
	        	attributevalue=String.valueOf(p.distribution.getPropertyValue(Distribution.MAX_VALUE));
	        
			memtype_id=-1;
			filetype_id = -1;					
			//System.err.println(p.name.length());
			SDIM=attributevalue.length()+1;
			str_data = new StringBuffer[1];
			str_data[0]=new StringBuffer(attributevalue);
			dset_data = new byte[DIM0][SDIM];
			
	        
	        
			
				memtype_id = H5.H5Tcopy(HDF5Constants.H5T_C_S1);
				if (memtype_id >= 0)
					H5.H5Tset_size(memtype_id, SDIM);
			
		
				filetype_id = H5.H5Tcopy(HDF5Constants.H5T_FORTRAN_S1);
				if (filetype_id >= 0)
					H5.H5Tset_size(filetype_id, SDIM - 1);
			
			
			
			
				dataspace_id = H5.H5Screate_simple(RANK, dimsa, null);
			
			
			
			
	     // Create the attribute.
			
				if ((dataset_id >= 0) && (dataspace_id >= 0) && (filetype_id>= 0))
					attribute_id = H5.H5Acreate(dataset_id, attributename, filetype_id,
							dataspace_id, HDF5Constants.H5P_DEFAULT);
			
			// Write the data to the dataset.
			
				for (int indx = 0; indx < DIM0; indx++) {
					for (int jndx = 0; jndx < SDIM; jndx++) {
						if (jndx < str_data[indx].length())
							dset_data[indx][jndx] = (byte) str_data[indx].charAt(jndx);
						else
							dset_data[indx][jndx] = 0;
					}
				}
				if ((attribute_id >= 0) && (memtype_id >= 0))
					H5.H5Awrite(attribute_id, memtype_id, dset_data);
			

			// End access to the dataset and release resources used by it.
			
				if (attribute_id >= 0)
					H5.H5Aclose(attribute_id);
			
			      
			
			// -----6th attribute : distribution.log
	        attributename="log";
	        attributevalue="False";
	        
	        if(p.distribution.isLogaritmic())
	        	attributevalue="True";
	        
			memtype_id=-1;
			filetype_id = -1;					
			//System.err.println(p.name.length());
			SDIM=attributevalue.length()+1;
			str_data = new StringBuffer[1];
			str_data[0]=new StringBuffer(attributevalue);
			dset_data = new byte[DIM0][SDIM];
			
	        
	        
			
				memtype_id = H5.H5Tcopy(HDF5Constants.H5T_C_S1);
				if (memtype_id >= 0)
					H5.H5Tset_size(memtype_id, SDIM);
			
			
				filetype_id = H5.H5Tcopy(HDF5Constants.H5T_FORTRAN_S1);
				if (filetype_id >= 0)
					H5.H5Tset_size(filetype_id, SDIM - 1);
			
				dataspace_id = H5.H5Screate_simple(RANK, dimsa, null);
			
			
			
			
	     // Create the attribute.
			
				if ((dataset_id >= 0) && (dataspace_id >= 0) && (filetype_id>= 0))
					attribute_id = H5.H5Acreate(dataset_id, attributename, filetype_id,
							dataspace_id, HDF5Constants.H5P_DEFAULT);
							

			// Write the data to the dataset.
			
				for (int indx = 0; indx < DIM0; indx++) {
					for (int jndx = 0; jndx < SDIM; jndx++) {
						if (jndx < str_data[indx].length())
							dset_data[indx][jndx] = (byte) str_data[indx].charAt(jndx);
						else
							dset_data[indx][jndx] = 0;
					}
				}
				if ((attribute_id >= 0) && (memtype_id >= 0))
					H5.H5Awrite(attribute_id, memtype_id, dset_data);
				
			

			// End access to the dataset and release resources used by it.
			
				if (attribute_id >= 0)
					H5.H5Aclose(attribute_id);
			
			
			
			// -----7th attribute : distribution.mean
			if(GaussDistribution.class.isInstance(p.distribution)){
				attributename=GaussDistribution.MEAN_VALUE;
				attributevalue="";
				
				if(p.distribution.getPropertyValue(GaussDistribution.MEAN_VALUE)!=null)
					attributevalue=String.valueOf(p.distribution.getPropertyValue(GaussDistribution.MEAN_VALUE));
				
				memtype_id=-1;
				filetype_id = -1;					
				//System.err.println(p.name.length());
				SDIM=attributevalue.length()+1;
				str_data = new StringBuffer[1];
				str_data[0]=new StringBuffer(attributevalue);
				dset_data = new byte[DIM0][SDIM];
				
				
				
				
					memtype_id = H5.H5Tcopy(HDF5Constants.H5T_C_S1);
					if (memtype_id >= 0)
						H5.H5Tset_size(memtype_id, SDIM);
				
				
					filetype_id = H5.H5Tcopy(HDF5Constants.H5T_FORTRAN_S1);
					if (filetype_id >= 0)
						H5.H5Tset_size(filetype_id, SDIM - 1);
				
				
				
				
					dataspace_id = H5.H5Screate_simple(RANK, dimsa, null);
				
				
				
				
			 // Create the attribute.
				
					if ((dataset_id >= 0) && (dataspace_id >= 0) && (filetype_id>= 0))
						attribute_id = H5.H5Acreate(dataset_id, attributename, filetype_id,
								dataspace_id, HDF5Constants.H5P_DEFAULT);
								

				// Write the data to the dataset.
				
					for (int indx = 0; indx < DIM0; indx++) {
						for (int jndx = 0; jndx < SDIM; jndx++) {
							if (jndx < str_data[indx].length())
								dset_data[indx][jndx] = (byte) str_data[indx].charAt(jndx);
							else
								dset_data[indx][jndx] = 0;
						}
					}
					if ((attribute_id >= 0) && (memtype_id >= 0))
						H5.H5Awrite(attribute_id, memtype_id, dset_data);
					
				
				// End access to the dataset and release resources used by it.
				
					if (attribute_id >= 0)
						H5.H5Aclose(attribute_id);
				
				////------------ gaus standard deviation
				attributename=GaussDistribution.STANDARD_DEVIATION_VALUE;
				attributevalue="";
				
				if(p.distribution.getPropertyValue(GaussDistribution.STANDARD_DEVIATION_VALUE)!=null)
					attributevalue=String.valueOf(p.distribution.getPropertyValue(GaussDistribution.STANDARD_DEVIATION_VALUE));
				
				memtype_id=-1;
				filetype_id = -1;					
				//System.err.println(p.name.length());
				SDIM=attributevalue.length()+1;
				str_data = new StringBuffer[1];
				str_data[0]=new StringBuffer(attributevalue);
				dset_data = new byte[DIM0][SDIM];
				
				
				
				
					memtype_id = H5.H5Tcopy(HDF5Constants.H5T_C_S1);
					if (memtype_id >= 0)
						H5.H5Tset_size(memtype_id, SDIM);
				
				
					filetype_id = H5.H5Tcopy(HDF5Constants.H5T_FORTRAN_S1);
					if (filetype_id >= 0)
						H5.H5Tset_size(filetype_id, SDIM - 1);
				
					dataspace_id = H5.H5Screate_simple(RANK, dimsa, null);
				
				
				
			 // Create the attribute.
				
					if ((dataset_id >= 0) && (dataspace_id >= 0) && (filetype_id>= 0))
						attribute_id = H5.H5Acreate(dataset_id, attributename, filetype_id,
								dataspace_id, HDF5Constants.H5P_DEFAULT);
							

				// Write the data to the dataset.
				
					for (int indx = 0; indx < DIM0; indx++) {
						for (int jndx = 0; jndx < SDIM; jndx++) {
							if (jndx < str_data[indx].length())
								dset_data[indx][jndx] = (byte) str_data[indx].charAt(jndx);
							else
								dset_data[indx][jndx] = 0;
						}
					}
					if ((attribute_id >= 0) && (memtype_id >= 0))
						H5.H5Awrite(attribute_id, memtype_id, dset_data);
					
				

				// End access to the dataset and release resources used by it.
				
					if (attribute_id >= 0)
						H5.H5Aclose(attribute_id);
				
				
			}
			///---- 
			if(TriangleDistribution.class.isInstance(p.distribution)){
				attributename=TriangleDistribution.C_VALUE;
				attributevalue="";
				
				if(p.distribution.getPropertyValue(TriangleDistribution.C_VALUE)!=null)
					attributevalue=String.valueOf(p.distribution.getPropertyValue(TriangleDistribution.C_VALUE));
				
				memtype_id=-1;
				filetype_id = -1;					
				//System.err.println(p.name.length());
				SDIM=attributevalue.length()+1;
				str_data = new StringBuffer[1];
				str_data[0]=new StringBuffer(attributevalue);
				dset_data = new byte[DIM0][SDIM];
				
				
				
					memtype_id = H5.H5Tcopy(HDF5Constants.H5T_C_S1);
					if (memtype_id >= 0)
						H5.H5Tset_size(memtype_id, SDIM);
				
					filetype_id = H5.H5Tcopy(HDF5Constants.H5T_FORTRAN_S1);
					if (filetype_id >= 0)
						H5.H5Tset_size(filetype_id, SDIM - 1);
				
				
				
				
					dataspace_id = H5.H5Screate_simple(RANK, dimsa, null);
				
				
				
			 // Create the attribute.
				
					if ((dataset_id >= 0) && (dataspace_id >= 0) && (filetype_id>= 0))
						attribute_id = H5.H5Acreate(dataset_id, attributename, filetype_id,
								dataspace_id, HDF5Constants.H5P_DEFAULT);
				
				// Write the data to the dataset.
				
					for (int indx = 0; indx < DIM0; indx++) {
						for (int jndx = 0; jndx < SDIM; jndx++) {
							if (jndx < str_data[indx].length())
								dset_data[indx][jndx] = (byte) str_data[indx].charAt(jndx);
							else
								dset_data[indx][jndx] = 0;
						}
					}
					if ((attribute_id >= 0) && (memtype_id >= 0))
						H5.H5Awrite(attribute_id, memtype_id, dset_data);
					
				

				// End access to the dataset and release resources used by it.
				
					if (attribute_id >= 0)
						H5.H5Aclose(attribute_id);
				
				
				
			}
			
	      //-- attribute show in output			
			
	        attributename="show_in_output";
	        attributevalue="False";
	        
	        if(p.showInOutput==true)
	        	attributevalue="True";
	        
			memtype_id=-1;
			filetype_id = -1;					
			//System.err.println(p.name.length());
			SDIM=attributevalue.length()+1;
			str_data = new StringBuffer[1];
			str_data[0]=new StringBuffer(attributevalue);
			dset_data = new byte[DIM0][SDIM];
			
	        
	        
			
				memtype_id = H5.H5Tcopy(HDF5Constants.H5T_C_S1);
				if (memtype_id >= 0)
					H5.H5Tset_size(memtype_id, SDIM);
			
				filetype_id = H5.H5Tcopy(HDF5Constants.H5T_FORTRAN_S1);
				if (filetype_id >= 0)
					H5.H5Tset_size(filetype_id, SDIM - 1);
			
				dataspace_id = H5.H5Screate_simple(RANK, dimsa, null);
			
			
	     // Create the attribute.
			
				if ((dataset_id >= 0) && (dataspace_id >= 0) && (filetype_id>= 0))
					attribute_id = H5.H5Acreate(dataset_id, attributename, filetype_id,
							dataspace_id, HDF5Constants.H5P_DEFAULT);
					

			// Write the data to the dataset.
			
				for (int indx = 0; indx < DIM0; indx++) {
					for (int jndx = 0; jndx < SDIM; jndx++) {
						if (jndx < str_data[indx].length())
							dset_data[indx][jndx] = (byte) str_data[indx].charAt(jndx);
						else
							dset_data[indx][jndx] = 0;
					}
				}
				if ((attribute_id >= 0) && (memtype_id >= 0))
					H5.H5Awrite(attribute_id, memtype_id, dset_data);
				
			

			// End access to the dataset and release resources used by it.
			
				if (attribute_id >= 0)
					H5.H5Aclose(attribute_id);
			
			
			//-----------------description
			
			
			 attributename="description";
		     attributevalue=p.description;
		        if(attributevalue.equals(""))
		        	attributevalue=" ";
		        
				memtype_id=-1;
				filetype_id = -1;					
				//System.err.println(p.name.length());
				SDIM=attributevalue.length()+1;
				str_data = new StringBuffer[1];
				str_data[0]=new StringBuffer(attributevalue);
				dset_data = new byte[DIM0][SDIM];
				
		        
		        
				
					memtype_id = H5.H5Tcopy(HDF5Constants.H5T_C_S1);
					if (memtype_id >= 0)
						H5.H5Tset_size(memtype_id, SDIM);
				
				
					filetype_id = H5.H5Tcopy(HDF5Constants.H5T_FORTRAN_S1);
					if (filetype_id >= 0)
						H5.H5Tset_size(filetype_id, SDIM - 1);
				
				
				
				
					dataspace_id = H5.H5Screate_simple(RANK, dimsa, null);
				
				
				
				//-----------------index
				
				
				 attributename="index";
			     attributevalue=String.valueOf(p.index);
			        
			        
					memtype_id=-1;
					filetype_id = -1;					
					//System.err.println(p.name.length());
					SDIM=attributevalue.length()+1;
					str_data = new StringBuffer[1];
					str_data[0]=new StringBuffer(attributevalue);
					dset_data = new byte[DIM0][SDIM];
					
			        
			        
						memtype_id = H5.H5Tcopy(HDF5Constants.H5T_C_S1);
						if (memtype_id >= 0)
							H5.H5Tset_size(memtype_id, SDIM);
					
					
						filetype_id = H5.H5Tcopy(HDF5Constants.H5T_FORTRAN_S1);
						if (filetype_id >= 0)
							H5.H5Tset_size(filetype_id, SDIM - 1);
					
					
					
					
						dataspace_id = H5.H5Screate_simple(RANK, dimsa, null);
					
					
				
		     // Create the attribute.
				
					if ((dataset_id >= 0) && (dataspace_id >= 0) && (filetype_id>= 0))
						attribute_id = H5.H5Acreate(dataset_id, attributename, filetype_id,
								dataspace_id, HDF5Constants.H5P_DEFAULT);
							

				// Write the data to the dataset.
				
					for (int indx = 0; indx < DIM0; indx++) {
						for (int jndx = 0; jndx < SDIM; jndx++) {
							if (jndx < str_data[indx].length())
								dset_data[indx][jndx] = (byte) str_data[indx].charAt(jndx);
							else
								dset_data[indx][jndx] = 0;
						}
					}
					if ((attribute_id >= 0) && (memtype_id >= 0))
						H5.H5Awrite(attribute_id, memtype_id, dset_data);
					
				

				// End access to the dataset and release resources used by it.
				
					if (attribute_id >= 0)
						H5.H5Aclose(attribute_id);
				
	        
	     ////--------------------------------------------------------------------
	    	
	   
	        
	        

			
			

			
			

			

			// Write the dataset.
			
				if (dataset_id >= 0)
					H5.H5Dwrite(dataset_id, HDF5Constants.H5T_NATIVE_DOUBLE,
							HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
							HDF5Constants.H5P_DEFAULT, parameter_data);
			
			
				if (dataset_id >= 0)
					H5.H5Dread(dataset_id, HDF5Constants.H5T_NATIVE_DOUBLE,
							HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
							HDF5Constants.H5P_DEFAULT, parameter_data);
			

			// Close the dataset.
			
				if (dataset_id >= 0)
					H5.H5Dclose(dataset_id);
			
			
	        
	        
	        
	        
		
		}
        }
		// Close the file.
		finally {
			if (file_id >= 0)
				H5.H5Fclose(file_id);
		}
		
		//----------------------------------------------------------------------

	
	}
	
	public String getSampleFileName(){
		return this.txtSampleFile.getText().trim();
	}
	public int getSamplingMethod(){
		return combo.getSelectionIndex();
		
	}
	public int getSampleLength(){
		int sampleSize=Integer.parseInt(this.txtSampleSize.getText().trim());
		return sampleSize;
	}
	private void controlComplete(){
	 if (txtSampleFile.getText().isEmpty()==false && combo.getSelectionIndex()>-1 && txtSampleSize.getText().isEmpty()==false && isSampleGenerated==true) {
         setPageComplete(true);

       }
   	  else setPageComplete(false);
	}
}
