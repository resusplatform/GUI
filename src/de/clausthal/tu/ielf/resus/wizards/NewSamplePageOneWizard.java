package de.clausthal.tu.ielf.resus.wizards;


import java.awt.Container;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Policy.Parameters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ncsa.hdf.hdf5lib.H5;
import ncsa.hdf.hdf5lib.HDF5Constants;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.views.navigator.ResourceNavigator;
import org.eclipse.wb.swt.SWTResourceManager;

import de.clausthal.tu.ielf.randomGenrators.Random;
import de.clausthal.tu.ielf.randomGenrators.SimpleRandomGenerator;
import de.clausthal.tu.ielf.randomGenrators.SobolRandomGenerator;
import de.clausthal.tu.ielf.randomGenrators.distributions.Distribution;
import de.clausthal.tu.ielf.randomGenrators.distributions.GaussDistribution;
import de.clausthal.tu.ielf.randomGenrators.distributions.TriangleDistribution;
import de.clausthal.tu.ielf.randomGenrators.distributions.TruncatedGaussDistribution;
import de.clausthal.tu.ielf.randomGenrators.distributions.UniformDistribution;
import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;

public class NewSamplePageOneWizard extends WizardPage {
 
	
	private ArrayList<Parameter> parameteres=new ArrayList<Parameter>();

	final String[] items=new String[] {"Uniform","Gauss", "Triangle"};
	
	
	private int rowCounter=0;
	private Text txtTemplateName;
	private Table table;
	private TableViewer viewer;
	Map<Object, Button> Removebuttons = new HashMap<Object, Button>();
	Map<Object, Button> editButtons = new HashMap<Object, Button>();
	private Text txtExParam3;
	private boolean isTemplateChanged=false;
	
	Composite container;
	
  public NewSamplePageOneWizard() {
    super("New Sample Wizard");
    setTitle("Configure The Parameters");
    setDescription("This Wizard Helps you to make a new Sample");
    
  }

  @Override
  public void createControl(Composite parent) {
	  container = new Composite(parent, SWT.NONE);  
	  
	  
	  container.setBounds(10, 10, 1008, 370);
			
			rowCounter=0;
			
			
			Label lblFileName = new Label(container, SWT.NONE);
			lblFileName.setText("Parameters Template Name");
			lblFileName.setBounds(10, 15, 147, 15);
			
			txtTemplateName = new Text(container, SWT.BORDER);
			txtTemplateName.setBounds(165, 15, 236, 21);
			
			txtTemplateName.addKeyListener(new KeyListener() {
				
			      @Override
			      public void keyPressed(KeyEvent e) {
			      }
			
			      @Override
			      public void keyReleased(KeyEvent e) {
			    	  if (!txtTemplateName.getText().isEmpty() && !parameteres.isEmpty()) {
			          setPageComplete(true);
			
			        }
			    	  else setPageComplete(false);
			      }
			
			    });
			
			
			//---------------table--------------------------

			viewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
			final Table table_1 = viewer.getTable();
			table_1.setBounds(10, 55, 750, 287);
			
			
	        viewer.getTable().setHeaderVisible(true);
	        viewer.getTable().setLinesVisible(true);
	        viewer.setContentProvider(new ArrayContentProvider());

	        TableColumn column = new TableColumn(viewer.getTable(), SWT.NONE);
	        column.setText(" Index ");
	        column.setWidth(50);
	        TableViewerColumn cntCol = new TableViewerColumn(viewer, column);
	        cntCol.setLabelProvider(new ColumnLabelProvider(){

	            @Override
	            public String getText(Object element) {
	                Parameter p=(Parameter)element;
	            	

	                return String.valueOf(p.index);
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
	            	

	                return p.name;
	            }

	        });
	        
	        
	        
	        column = new TableColumn(viewer.getTable(), SWT.NONE);
	        column.setText(" Unit ");
	        column.setWidth(100);
	        TableViewerColumn UnitCol = new TableViewerColumn(viewer, column);
	       UnitCol .setLabelProvider(new ColumnLabelProvider(){

	            @Override
	            public String getText(Object element) {
	            	 Parameter ip=(Parameter)element;
	             	

	                 return ip.unit;
	            }

	        });

	       column = new TableColumn(viewer.getTable(), SWT.NONE);
	       column.setText(" Distribution ");
	       column.setWidth(100);
	       TableViewerColumn DistributionCol = new TableViewerColumn(viewer, column);
	       DistributionCol .setLabelProvider(new ColumnLabelProvider(){

	           @Override
	           public String getText(Object element) {
	           	 Parameter ip=(Parameter)element;
	            	
	           	 	String disPlusLog=ip.distribution.getName();
	           	 	if(ip.distribution.isLogaritmic()) 
	           	 		disPlusLog="Log-"+disPlusLog;

	                return disPlusLog;
	           }

	       });
	      
//	      column = new TableColumn(viewer.getTable(), SWT.NONE);
//	      column.setText(" Min ");
//	      column.setWidth(100);
//	      TableViewerColumn param1Col = new TableViewerColumn(viewer, column);
//	      param1Col .setLabelProvider(new ColumnLabelProvider(){
	//
//	          @Override
//	          public String getText(Object element) {
//	          	 Parameter ip=(Parameter)element;
//	           	
//	          	return String.valueOf(ip.distribution.getPropertyValue(Distribution.MIN_VALUE));
//	          }
	//
//	      });
//	     column = new TableColumn(viewer.getTable(), SWT.NONE);
//	     column.setText(" Max ");
//	     column.setWidth(100);
//	     TableViewerColumn param2Col = new TableViewerColumn(viewer, column);
//	     param2Col .setLabelProvider(new ColumnLabelProvider(){
	//
//	         @Override
//	         public String getText(Object element) {
//	         	 Parameter ip=(Parameter)element;
//	          	
//	         	return String.valueOf(ip.distribution.getPropertyValue(Distribution.MAX_VALUE));
//	         }
	//
//	     });
	    
	    

	     column = new TableColumn(viewer.getTable(), SWT.NONE);
	     column.setText(" Output ");
	     column.setWidth(80);
	     TableViewerColumn showinoutputCol = new TableViewerColumn(viewer, column);
	     showinoutputCol.setLabelProvider(new ColumnLabelProvider(){

	         @Override
	         public String getText(Object element) {
	             Parameter p=(Parameter)element;
	         	

	             return p.showInOutput.toString();
	         }

	     });

	     
	     
	     


	        column = new TableColumn(viewer.getTable(), SWT.NONE);
	        column.setText("Remove");
	        column.setWidth(90);
	        TableViewerColumn actionsNameCol = new TableViewerColumn(viewer, column);
	        
	    	Removebuttons = new HashMap<Object, Button>();
	        actionsNameCol.setLabelProvider(new ColumnLabelProvider(){
	            //make sure you dispose these buttons when viewer input changes
	            //final Map<Object, Button> 
	        	


	            @Override
	            public void update(ViewerCell cell) {
	            	
	            	TableItem item = (TableItem) cell.getItem();
	                Button button;
	                if(Removebuttons.containsKey(cell.getElement()))
	                {
	                    button = Removebuttons.get(cell.getElement());
	                }
	                else
	                {
	                    button = new Button((Composite) cell.getViewerRow().getControl(),SWT.NONE);
	                    button.setText("Remove");
	                    final Parameter par=(Parameter)cell.getElement();
	                    button.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) 
							{
								parameteres.remove(par);
								isTemplateChanged=true;
								((Button)e.getSource()).dispose();
								Removebuttons.remove(par);
								
								editButtons.get(par).dispose();
								editButtons.remove(par);
								
								
								rowCounter=0;
								viewer.setInput(parameteres);
								//mainWizard.setParameterConfigurations(parameteres);
								
								viewer.refresh();
							}
	                    });
	                    Removebuttons.put(cell.getElement(), button);
	                }
	                TableEditor editor = new TableEditor(item.getParent());
	                editor.grabHorizontal  = true;
	                editor.grabVertical = true;
	                editor.setEditor(button , item, cell.getColumnIndex());
	                editor.layout();
	            }

	        });
	        
	       
			
			

	        column = new TableColumn(viewer.getTable(), SWT.NONE);
	        column.setText("Edit");
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
	                    button.setText("Edit");
	                    final Parameter parameter=(Parameter)cell.getElement();
	                    button.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent ex) 
							{				
								String[] btns={"ok","Cancel"};
								ParameterEditDialog d= new ParameterEditDialog(container.getShell(), "Edit Parameter Properties", null, "", 1, btns, 0);
								
								d.setParameter(parameter);
								
								d.create();
								d.setListofParameters(parameteres);
								//System.out.println(d.open());
								if (d.open() == 0) {
									Parameter p=d.getParameter();

									int indx=parameteres.indexOf(parameter);
									
									
									
									parameteres.set(indx,p);
									isTemplateChanged=true;
									editButtons.get(parameter).dispose();
									Removebuttons.get(parameter).dispose();
									
																	
									
									
								}
								rowCounter=0;
								viewer.setInput(parameteres);
								//mainWizard.setParameterConfigurations(parameteres);
								viewer.refresh();
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
	        rowCounter=0;
	        viewer.setInput(parameteres);
	        
	        
	        
	     // sort according to due date
	        viewer.setComparator(new ViewerComparator() {
	          public int compare(Viewer viewer, Object e1, Object e2) {
	            Parameter t1 = (Parameter) e1;
	            Parameter t2 = (Parameter) e2;
	            return t1.getIndex().compareTo(t2.getIndex());
	          };
	        }); 
	        
	        
	        
	        //mainWizard.setParameterConfigurations(parameteres);
			
	        
	        
			Button btnSaveAsTemplate = new Button(container, SWT.NONE);
			btnSaveAsTemplate.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {

					if(txtTemplateName.getText().isEmpty())
					{
						MessageDialog.openWarning(getShell(),"Input Error","Please enter a name for Sample Template");
						return;
					}
					
					// get the file in workspace
					try{
					
						IEditorPart editor = PlatformUI.getWorkbench()
			    	        .getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			    	    IFileEditorInput input = (IFileEditorInput)editor.getEditorInput() ;
			    	    IFile file = input.getFile();
			    	    IProject activeProject = file.getProject();
			    	    IResource res = (IResource) activeProject;
					
			    	     
			    	// chek if the file exist make warning			    	     
			    	    File checkFileExistence=res.getLocation().append(txtTemplateName.getText()+".ptml").toFile();
			    	    if(checkFileExistence.exists()){
			    	    	boolean confResult=MessageDialog.openConfirm(getShell(), "File Exist", "There is already a sample template file with the entered name, woud you like to rewrite it?");
			    	    	if(confResult==false) return;
			    	    }
			    	    	
			    	     
					System.out.println(res.getLocation().toString());
					
			        FileOutputStream fout = new FileOutputStream(res.getLocation().append(txtTemplateName.getText()+".ptml").toString());
			        ObjectOutputStream oos = new ObjectOutputStream(fout);
			        oos.writeObject(parameteres);
			        oos.close();
			        
			        activeProject.refreshLocal(1, null);
			        
			        MessageDialog.openInformation(getShell(),"Saved Successfully","Sample Template saved successfully under the current project folder in workspace");
			        
			        } catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						MessageDialog.openError(getShell(),"File Not Found Exception",e1.getStackTrace().toString());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (CoreException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				        }
				    
					
				
			});
			btnSaveAsTemplate.setBounds(606, 13, 123, 25);
			btnSaveAsTemplate.setText("Save as Template");
			
			Button btnLoadTemplate = new Button(container, SWT.NONE);
			btnLoadTemplate.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
				
					
					
			    	
					String[] buttons={"OK","Cancel"};
			    	SampleTemplateSelectDialog smppage=new SampleTemplateSelectDialog(getShell(),buttons );
			    	
			    	smppage.create();
			    	int dialogResult=smppage.open();
			    	if(dialogResult==0){
			    		// get the list of ptml files in current project
					    IEditorPart editor = PlatformUI.getWorkbench()
				    	        .getActiveWorkbenchWindow().getActivePage().getActiveEditor();
				    	IFileEditorInput input = (IFileEditorInput)editor.getEditorInput() ;
				    	IFile file = input.getFile();
				    	IProject activeProject = file.getProject();
				    	IResource res = (IResource) activeProject;
				    	
				    	// chek if the current template has not changed 
				    	if( isTemplateChanged==true){
				    		  	
				    	
					    	// if changed confirm for save or discard current template 
					    	boolean changeConfrim=MessageDialog.openConfirm(getShell(), "Template Changed", "The current changes are not saved. Press Ok to save the changes or Cancell to discard Changes and load the selected Sample Template file");
					    	// if save { save the template } 
					    	if(changeConfrim==true){
					    		
					    		if(txtTemplateName.getText().isEmpty())
								{
									MessageDialog.openWarning(getShell(),"Input Error","Please enter a name for Sample Template");
									return;
								}								
								
								
						    	  
					    		try{
						    	// dont chek if the file existance. overwrite it			    	     
						    	    File checkFileExistence=res.getLocation().append(txtTemplateName.getText()+".ptml").toFile();
						    	    
						    	    	
						    	     
								System.out.println(res.getLocation().toString());
								
						        FileOutputStream fout = new FileOutputStream(res.getLocation().append(txtTemplateName.getText()+".ptml").toString());
						        ObjectOutputStream oos = new ObjectOutputStream(fout);
						        oos.writeObject(parameteres);
						        oos.close();
					    		}

						    	  catch (FileNotFoundException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								  
						      
													    		
					    	}
				    	}
				    	// load
				    	try{
				    	
				    	String loadTemplateFile=smppage.getTemplateName();
				    	if(loadTemplateFile.equals(null) || loadTemplateFile.trim().equals(""))return;
				    	txtTemplateName.setText(loadTemplateFile.substring(0,loadTemplateFile.length()-5));
				        	FileInputStream fin = new FileInputStream(res.getLocation().append(loadTemplateFile).toString());
				        ObjectInputStream ois = new ObjectInputStream(fin);
				        
				        for (Map.Entry<Object, Button> entry : Removebuttons.entrySet())
				        {
				            entry.getValue().dispose();
				        }
				        
				        for (Map.Entry<Object, Button> entry : editButtons.entrySet())
				        {
				            entry.getValue().dispose();
				        }
				        
				        
				        
				        parameteres = (ArrayList<Parameter>) ois.readObject();
				        ois.close();
						fin.close();
						
						viewer.setInput(parameteres);
						//mainWizard.setParameterConfigurations(parameteres);
						viewer.refresh();
				    	isTemplateChanged=false;
				    	
				    	if (!txtTemplateName.getText().isEmpty() && !parameteres.isEmpty()) {
					          setPageComplete(true);
					
					        }
					    	  else setPageComplete(false);
				    	
				    	
				    	}

				    	  catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				    	
	 
			    	}
					
//			    	FileDialog fd=new FileDialog(container.getShell());
					
					
//			        fd.setText("Load Sample Template ");
//			        
//			        String[] filterExt = { "*.ptml"};
//			        fd.setFilterExtensions(filterExt);
//			        String selected = fd.open();
//			        
//			        if(selected.equals(null)||selected.trim().equals(""))return;
//			        //System.out.println(selected);
			        
					
				}
			       
			        	
			        
				
					
				
			        
			});
			
			btnLoadTemplate.setBounds(493, 13, 107, 25);
			btnLoadTemplate.setText("Load Template");
			
			setControl(container);
			
			Button btnAddNewParameter = new Button(container, SWT.NONE);
			btnAddNewParameter.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					
					String[] btns={"ok","Cancel"};
					ParameterNewDialog d= new ParameterNewDialog(container.getShell(), "Add New Parameter", null, "", 1, btns, 0 );
					
					
					d.create();
					d.setParameterList(parameteres);
					//System.out.println(d.open());
					if (d.open() == 0) {
						Parameter p=d.getParameter();
								
						parameteres.add(p);
						isTemplateChanged=true;
						viewer.setInput(parameteres);
						//mainWizard.setParameterConfigurations(parameteres);
						
						if (!txtTemplateName.getText().isEmpty() && !parameteres.isEmpty()) {
					          setPageComplete(true);
					
					        }
					    	  else setPageComplete(false);
					
					
				}
				}
			});
			btnAddNewParameter.setBounds(10, 348, 205, 25);
			btnAddNewParameter.setText("Add New Parameter to the List");
			
			Label lblItIsRecommended = new Label(container, SWT.NONE);
			lblItIsRecommended.setBounds(294, 385, 460, 20);
			lblItIsRecommended.setText("It is recommended to save your Template before you go to the next step");
			
        
        setPageComplete(false);

  }
  
  public String getParametersTemplateFilename(){
	  return txtTemplateName.getText().trim();
  }
  public ArrayList<Parameter> getParametersList(){
	  return this.parameteres;
  }
}
 