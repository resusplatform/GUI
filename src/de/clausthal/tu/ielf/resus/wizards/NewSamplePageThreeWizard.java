
package de.clausthal.tu.ielf.resus.wizards;
 


import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.clausthal.tu.ielf.randomGenrators.distributions.Distribution;
import de.clausthal.tu.ielf.randomGenrators.distributions.GaussDistribution;
import de.clausthal.tu.ielf.randomGenrators.distributions.TriangleDistribution;
import de.clausthal.tu.ielf.randomGenrators.distributions.TruncatedGaussDistribution;
import de.clausthal.tu.ielf.randomGenrators.distributions.TruncatedGaussDistribution2;
import de.clausthal.tu.ielf.randomGenrators.distributions.UniformDistribution;
import de.clausthal.tu.ielf.resus.wizards.Parameter;

import ncsa.hdf.hdf5lib.H5;
import ncsa.hdf.hdf5lib.HDF5Constants;
import ncsa.hdf.hdf5lib.callbacks.H5L_iterate_cb;
import ncsa.hdf.hdf5lib.callbacks.H5L_iterate_t;
import ncsa.hdf.hdf5lib.callbacks.H5O_iterate_cb;
import ncsa.hdf.hdf5lib.callbacks.H5O_iterate_t;
import ncsa.hdf.hdf5lib.exceptions.HDF5Exception;
import ncsa.hdf.hdf5lib.exceptions.HDF5LibraryException;
import ncsa.hdf.hdf5lib.structs.H5L_info_t;
import ncsa.hdf.hdf5lib.structs.H5O_info_t;
import ncsa.hdf.object.*;

import org.eclipse.swt.widgets.Button;



public class NewSamplePageThreeWizard extends WizardPage{

	 Composite composite;
	
	private String fileName;
	ArrayList<double[] > realizations; 
	private TableViewer viewer;
	private Tree tree;
	
	ArrayList<Parameter> attributes;;
	
	
	
	protected NewSamplePageThreeWizard() {	
		
		super("New Sample Wizard");
	    setTitle("Sample Viewer ");
	    setDescription("show the generated values and add them to the list Of Input Files");
	
		
		
	}

	@Override
	public void createControl(Composite parent) {
		 composite = new Composite(parent, SWT.NULL);
		 setControl(composite);
		  viewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		  final Table table_1 = viewer.getTable();
		  table_1.setBounds(217, 10, 750, 400);
		
		
		  viewer.getTable().setHeaderVisible(true);
		  viewer.getTable().setLinesVisible(true);
		  viewer.setContentProvider(new ArrayContentProvider());
		// sort according to due date
	        viewer.setComparator(new ViewerComparator() {
	          public int compare(Viewer viewer, Object e1, Object e2) {
	            Parameter t1 = (Parameter) e1;
	            Parameter t2 = (Parameter) e2;
	            return t1.getIndex().compareTo(t2.getIndex());
	          };
	        }); 
	        
		  
		  

		  //---------------------table---------------
		  TableColumn column = new TableColumn(viewer.getTable(), SWT.NONE);
		  column.setText(" Index ");
		  column.setWidth(50);
		  TableViewerColumn rowCol = new TableViewerColumn(viewer, column);
		  rowCol.setLabelProvider(new ColumnLabelProvider(){

			  @Override
			  public String getText(Object element) {
	            Parameter p=(Parameter)element;
	          		String name=p.getIndex().toString();
	
	            return name;
			  }

		  });
    
    
		  column = new TableColumn(viewer.getTable(), SWT.NONE);
		  column.setText(" Name ");
		  column.setWidth(100);
		  TableViewerColumn nameCol = new TableViewerColumn(viewer, column);
	    
		  nameCol.setLabelProvider(new ColumnLabelProvider(){

			  @Override
			  public String getText(Object element) {
				  Parameter p=(Parameter)element;
	          		String name=p.getName();
	
	            return name;
			  }

		  });
    
    
    
		  column = new TableColumn(viewer.getTable(), SWT.NONE);
		  column.setText(" Unit ");
		  column.setWidth(100);
		  TableViewerColumn UnitCol = new TableViewerColumn(viewer, column);
		  UnitCol .setLabelProvider(new ColumnLabelProvider(){

	    	 @Override
	    	 public String getText(Object element) {
	    		 Parameter p=(Parameter)element;
	          		String unit=p.getUnit();
        	
	    		 return unit;
	    	 }	    	 
	     });

	     column = new TableColumn(viewer.getTable(), SWT.NONE);
	     column.setText(" Distribution ");
	     column.setWidth(100);
	     TableViewerColumn DistributionCol = new TableViewerColumn(viewer, column);
	     DistributionCol .setLabelProvider(new ColumnLabelProvider(){

	         @Override
	         public String getText(Object element) {
	   
	        	 Parameter p=(Parameter)element;
	          		String dist=p.getDistribution().getName();
	          		boolean isLog=p.getDistribution().isLogaritmic();
	          		if(isLog)
	          			dist="log-"+dist;
	        	 return dist;
	         }

	     });
  

	   column = new TableColumn(viewer.getTable(), SWT.NONE);
	   column.setText(" Output ");
	   column.setWidth(80);
	   TableViewerColumn showinoutputCol = new TableViewerColumn(viewer, column);
	   showinoutputCol.setLabelProvider(new ColumnLabelProvider(){

		   @Override
		   public String getText(Object element) {
    	 
	    	   Parameter p=(Parameter)element;
	    	   boolean b=p.getShowInOutput();
	    	   	return Boolean.toString(b);
		   }
     

	   });
	
	   column = new TableColumn(viewer.getTable(), SWT.NONE);
	   column.setText(" Value ");
	   column.setWidth(100);
	   TableViewerColumn valueCol = new TableViewerColumn(viewer, column);
	   valueCol .setLabelProvider(new ColumnLabelProvider(){
	
	       @Override
	       public String getText(Object element) {
	     	   
	    	   Parameter p=(Parameter)element;
	    	   return p.getValue().toString();
	           }
	       
	
	   });
 
 //--------------------- end of table---------------
		
	   viewer.getTable().setHeaderVisible(true);
	   viewer.getTable().setLinesVisible(true);
	      
	   tree = new Tree(composite, SWT.BORDER);
	   tree.setBounds(10, 10, 201, 266);
	   
	  
	   
	   
	   setPageComplete(true);
	   }
		
	  
	  // open the file and read the data from file into 
		@SuppressWarnings("deprecation")
		public void init() throws NullPointerException, HDF5Exception{
			int file_id = -1;
			
			 try {
		            //Open file
		          file_id = H5.H5Fopen(fileName, HDF5Constants.H5F_ACC_RDONLY, HDF5Constants.H5P_DEFAULT);

		          //Begin iteration using H5Ovisit
		          System.out.println("Objects in the file:");
		          H5O_iterate_t iter_data = new H5O_iter_data();
		          H5O_iterate_cb iter_cb = new H5O_iter_callback();
		          H5.H5Ovisit(file_id, HDF5Constants.H5_INDEX_NAME, HDF5Constants.H5_ITER_NATIVE, iter_cb, iter_data);
		           
		          if(file_id>=0){
		        	  H5.H5Fclose (file_id);
		        	  file_id=-1;
		          }
		       
				 //the names of datasets are retrieved and stored in the Datasetnames arraylist. 
				 // initalize the number of arraylist<Parameter> according to the datasetnames length
				 
				
				
						 
				 // attributes of each dataset must be read and written in attributes arraylist
				 // for each dataset read the attributes 
				 for(int i=0;i<attributes.size();i++){
					 	 
					 
					 String attName=ReadAttribute(attributes.get(i).getName(),"name");
					 
			         String attUnit=ReadAttribute(attributes.get(i).getName(),"unit");
			         attributes.get(i).setUnit(attUnit);
			         
			         
			         String attShowinOutput=ReadAttribute(attributes.get(i).getName(),"show_in_output");
			         attributes.get(i).setShowInOutput(Boolean.valueOf(attShowinOutput));
			         
			         String attIndex=ReadAttribute(attributes.get(i).getName(),"index");
			         attributes.get(i).setIndex(Integer.valueOf(attIndex));
			         
			         
			         // retrieve Distribution 
			         String attDist=ReadAttribute(attributes.get(i).getName(),"distribution");
			         String attDistMin=ReadAttribute(attributes.get(i).getName(),"min_value");
			         String attDistMax=ReadAttribute(attributes.get(i).getName(),"max_value");
			         String attDistLog=ReadAttribute(attributes.get(i).getName(),"log");
			         double minvalue=Double.parseDouble(attDistMin);
			         double maxvalue=Double.parseDouble(attDistMax);
			         boolean islog=Boolean.parseBoolean(attDistLog);
			         Distribution dist=null;
			         UniformDistribution udis=new UniformDistribution(minvalue,maxvalue);
			         TruncatedGaussDistribution tgdis=new TruncatedGaussDistribution(minvalue,maxvalue, 0, 0);
			         TruncatedGaussDistribution2 tgdis2=new TruncatedGaussDistribution2(minvalue,maxvalue, 0, 0);
			         TriangleDistribution trdis=new TriangleDistribution(minvalue, maxvalue, 0);
			         
			         if(attDist.equals(udis.getName())) 
			        	 dist=udis;
			         else if(attDist.equals(tgdis.getName())){		        	 
			        	 dist=tgdis;
			         }
			         else if(attDist.equals(tgdis2.getName())){		        	 
			        	 dist=tgdis2;
			         }
			         else if(attDist.equals(trdis.getName())){
			        	 dist= trdis;
			         }
			         
			         dist.setLogaritmic(islog);
			         
			         attributes.get(i).setDistribution(dist);
			         
			        
			         
			         
				 }
				 
				 
				 
				
				 // in this step , the lenght of each dataset must be retrieved
				 for(int i=0;i<attributes.size();i++){
					 double[] l=ReadDatasetLenght(attributes.get(i).getName());
					 
					 realizations.add(i,l);
					 
				 }
			 }
			 
		     finally {
		            //Close and release resources.
		            if(file_id >= 0){ 
		                H5.H5Fclose (file_id);
		                file_id=-1;
		            }
		     }
			 
			 
		}
		
		  
		
		
  
	
		
	
	public void refresh() throws NullPointerException, HDF5Exception{
		  
			  realizations=new ArrayList<double[]>();
			  attributes=new ArrayList<Parameter>();
	            
	            NewSamplePageTwoWizard prepage=(NewSamplePageTwoWizard)this.getPreviousPage();
	            fileName=prepage.filename;	
	            init();
	        
	        
		  
		 
		  tree.removeAll();
		  viewer.setInput(null);
	      TreeItem sample=new TreeItem(tree, SWT.NONE, 0);
	      sample.setText("Sample");
	      long  minlen=Long.MAX_VALUE;
	      for(int i=0;i<realizations.size();i++)
	      {
	    	  long lenOfRealizationi=realizations.get(i).length;
	    	  if(lenOfRealizationi<minlen)
	    		  minlen=lenOfRealizationi;
	      }
	      
	      
	      for(int i=0;i<minlen;i++)
	      {
	      	TreeItem t=new TreeItem(sample, SWT.NONE,i);
	      	t.setText("realization "+ i);
	      	t.setData(i);
	      	
	      }
	      tree.addSelectionListener(new SelectionAdapter() {
		
	      public void widgetSelected(SelectionEvent e) {
	        TreeItem ti = (TreeItem) e.item;
	        if(ti.getData()==null || ti.getData().equals(null))return;
	        fillTable( ((Integer)ti.getData()));
	        
	        
	      }
	    });
	      
	  }
	
	 private void fillTable(int realizationIndex){
		 
			
			//refresh();
		 
			//	Element realization=(Element)realizations.(realizationIndex);
				ArrayList<Parameter> list=new ArrayList<Parameter>();
			//	NodeList parameters=realization.getElementsByTagName("parameter");
				
				  for(int i=0;i<attributes.size();i++){
					Parameter p=new Parameter();
					p.setIndex(attributes.get(i).getIndex());
					p.setName(attributes.get(i).getName());
					p.setUnit(attributes.get(i).getUnit());
					p.setShowInOutput(attributes.get(i).getShowInOutput());
					p.setValue(realizations.get(i)[realizationIndex]);
					p.setDistribution(attributes.get(i).getDistribution());
					list.add(p);
				  }
				
				viewer.setInput(list);
				
				viewer.refresh();
				
			  }
	
	
	 
	  
	 double[] ReadDatasetLenght(String datasetname) throws NullPointerException, HDF5Exception{
		
		  
		int file_id = -1;
		int dataset_id = -1;
		double[][] dset_data =new double[1][1];
		try {
			// Open file using the default properties.
		
			file_id = H5.H5Fopen(fileName, HDF5Constants.H5F_ACC_RDWR, HDF5Constants.H5P_DEFAULT);
		
			// Open dataset using the default properties.
			if (file_id >= 0)
					dataset_id = H5.H5Dopen(file_id, datasetname, HDF5Constants.H5P_DEFAULT);
			

			// Read the dimensions of the dataset
					
			
			int dspace=H5.H5Dget_space(dataset_id);
			int ndims = H5.H5Sget_simple_extent_ndims(dspace);
			long[] dims=new long[ndims];
			H5.H5Sget_simple_extent_dims(dspace, dims, null);
			
			dset_data =new double[(int)dims[0]][(int)dims[1]];
			if (dataset_id >= 0)
				H5.H5Dread(dataset_id, HDF5Constants.H5T_NATIVE_DOUBLE,HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,HDF5Constants.H5P_DEFAULT, dset_data);
			

//			// Output the data to the screen.
//			long DIM_X=dset_data.length;
//			long DIM_Y=dset_data[0].length;
//			
//			for (int indx = 0; indx < DIM_X; indx++) {
//				System.out.print(" [ ");
//				for (int jndx = 0; jndx < DIM_Y; jndx++)
//					System.out.print(dset_data[indx][jndx] + " ");
//				System.out.println("]");
//			}
//			System.out.println();
			
			}
			// Close the dataset.
			finally {
				if (dataset_id >= 0)
					H5.H5Dclose(dataset_id);
			
			// Close the file.
				if (file_id >= 0)
					H5.H5Fclose(file_id);
			
			} 
		  
		  return dset_data[0];
	  }
	  
	  String ReadAttribute(String datasetname,String attName) {
		  String attValue;
		  int file_id = -1;
			int filetype_id = -1;
			int memtype_id = -1;
			int dataspace_id = -1;
			int dataset_id = -1;
			int attribute_id = -1;
			int sdim = 0;
			long[] dims = { 1 };
			byte[][] dset_data;
			StringBuffer[] str_data;

			// Open an existing file.
			try {
				file_id = H5.H5Fopen(fileName, HDF5Constants.H5F_ACC_RDONLY, HDF5Constants.H5P_DEFAULT);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			// Open an existing dataset.
			try {
				if (file_id >= 0)
					dataset_id = H5.H5Dopen(file_id, datasetname, HDF5Constants.H5P_DEFAULT);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (dataset_id >= 0)
					attribute_id = H5.H5Aopen_by_name(dataset_id, ".", attName, 
					        HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			// Get the datatype and its size.
			try {
				if (attribute_id >= 0)
					filetype_id = H5.H5Aget_type(attribute_id);
				if (filetype_id >= 0) {
					sdim = H5.H5Tget_size(filetype_id);
					sdim++; // Make room for null terminator
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			// Get dataspace and allocate memory for read buffer.
			try {
				if (attribute_id >= 0)
					dataspace_id = H5.H5Aget_space(attribute_id);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (dataspace_id >= 0)
					H5.H5Sget_simple_extent_dims(dataspace_id, dims, null);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			dims[0]=1;
			// Allocate space for data.
			dset_data = new byte[(int) dims[0]][sdim];
			str_data = new StringBuffer[(int) dims[0]];

			// Create the memory datatype.
			try {
				memtype_id = H5.H5Tcopy(HDF5Constants.H5T_C_S1);
				if (memtype_id >= 0)
					H5.H5Tset_size(memtype_id, sdim);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			// Read data.
			try {
				if ((attribute_id >= 0) && (memtype_id >= 0))
					H5.H5Aread(attribute_id, memtype_id, dset_data);
				byte[] tempbuf = new byte[sdim];
				for (int indx = 0; indx < (int) dims[0]; indx++) {
					for (int jndx = 0; jndx < sdim; jndx++) {
						tempbuf[jndx] = dset_data[indx][jndx];
					}
					str_data[indx] = new StringBuffer(new String(tempbuf).trim());
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			// Output the data to the screen.
			//for (int indx = 0; indx < dims[0]; indx++) {
				//System.out.println(datasetname + " [" + indx + "]: " + str_data[indx]);
			//}
			//System.out.println();

			attValue=str_data[0].toString();
			
			
			// End access to the dataset and release resources used by it.
			try {
				if (attribute_id >= 0)
					H5.H5Aclose(attribute_id);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (dataset_id >= 0)
					H5.H5Dclose(dataset_id);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			// Terminate access to the data space.
			try {
				if (dataspace_id >= 0)
					H5.H5Sclose(dataspace_id);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			// Terminate access to the file type.
			try {
				if (filetype_id >= 0)
					H5.H5Tclose(filetype_id);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			// Terminate access to the mem type.
			try {
				if (memtype_id >= 0)
					H5.H5Tclose(memtype_id);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			// Close the file.
			try {
				if (file_id >= 0)
					H5.H5Fclose(file_id);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			return attValue;
		}
	  
	  
	  
	  /////-------------------------------------------------------------------------


	    /************************************************************
	     * Operator function for H5Lvisit.  This function simply
	     * retrieves the info for the object the current link points
	     * to, and calls the operator function for H5Ovisit.
	     ************************************************************/

	    class idata {
	        public String link_name = null;
	        public int link_type = -1;
	        idata(String name, int type) {
	            this.link_name = name;
	            this.link_type = type;
	        }
	    }

	     class H5L_iter_data implements H5L_iterate_t {
	        public ArrayList<idata> iterdata = new ArrayList<idata>();
	    }

	     class H5L_iter_callback implements H5L_iterate_cb {
	        public int callback(int group, String name, H5L_info_t info, H5L_iterate_t op_data) {

	            idata id = new idata(name, info.type);
	            ((H5L_iter_data)op_data).iterdata.add(id);

	            H5O_info_t infobuf;
	            int ret = 0;
	            try {
	                //Get type of the object and display its name and type. The name of the object is passed to this function by the Library.
	                infobuf = H5.H5Oget_info_by_name (group, name, HDF5Constants.H5P_DEFAULT);
	                H5O_iterate_cb iter_cbO = new H5O_iter_callback();
	                H5O_iterate_t iter_dataO = new H5O_iter_data();
	                ret=iter_cbO.callback(group, name, infobuf, iter_dataO);
	            }
	            catch (Exception e) {
	                e.printStackTrace();
	            }

	            return ret;
	        }
	    }

	     class H5O_iter_data implements H5O_iterate_t {
	        public ArrayList<idata> iterdata = new ArrayList<idata>();
	    }


	     class H5O_iter_callback implements H5O_iterate_cb {
	        public int callback(int group, String name, H5O_info_t info, H5O_iterate_t op_data) {
	            idata id = new idata(name, info.type);
	            ((H5O_iter_data)op_data).iterdata.add(id);

	            System.out.print("/"); /* Print root group in object path */

	            //Check if the current object is the root group, and if not print the full path name and type.

	           
	            if(info.type == HDF5Constants.H5O_TYPE_DATASET){
	                //System.out.println(name + "  (Dataset)");
	            	Parameter p=new Parameter();
	            	p.setName(name);
	            	attributes.add(p);
	            }

	            return 0;
	        }
	    }


	  
	  
	  
	  
	  //_----------------------------------------------------------------------------
	  
	  
	  
	  
	     public boolean isPageFinished(){
	    	return isPageComplete();	
	     }
	}	

