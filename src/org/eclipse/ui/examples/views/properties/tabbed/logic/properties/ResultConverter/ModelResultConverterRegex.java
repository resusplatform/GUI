package org.eclipse.ui.examples.views.properties.tabbed.logic.properties.ResultConverter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;









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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.examples.views.properties.tabbed.logic.properties.AbstractSection;
import org.eclipse.ui.examples.views.properties.tabbed.logic.properties.TextChangeHelper;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.views.IViewDescriptor;
import org.eclipse.ui.views.IViewRegistry;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import de.clausthal.tu.ielf.resusdesigner.ResusMessages;
import de.clausthal.tu.ielf.resusdesigner.model.IndexPairs;
import de.clausthal.tu.ielf.resusdesigner.model.ResultConverter;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResultConverter.SetResultConverterColDelimiterCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResultConverter.SetResultConverterColumnIndexesCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResultConverter.SetResultConverterRegexCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResultConverter.SetResusResultConverterNumberOfOutputRowsCommand;


public class ModelResultConverterRegex extends AbstractSection {

	public ModelResultConverterRegex() {
		// TODO Auto-generated constructor stub
	}
	private Text txtRegex;
	
	private Text txtColumndelimiter;
	
	
	
	Composite composite;
	
	private Text txtIndexId;
	private Text txtIndexTag;
	private Text txtUnitTag;
	private Text txtIndexCoefficient;

	ArrayList<IndexPairs> indexPairs;
	
	
	 private TableViewer viewer;
	 Map<Object, Button> buttons ;//= new HashMap<Object, Button>();
	/**
	 * A helper to listen for events that indicate that a text
	 * field has been changed.
	 */
	 private TextChangeHelper regexFieldChangelistener = new TextChangeHelper() {

			public void textChanged(Control control) {				
				changeRegex();

			}
		};
	private void changeRegex()
	{
		String regex= txtRegex.getText().trim();			
		//((ResultConverter)getElement()).setRegex(regex) ;
		SetResultConverterRegexCommand cmd=new SetResultConverterRegexCommand();
		cmd.setRegex(regex);
		cmd.setPart((ResultConverter)getElement());				
		runCommand(cmd);
	}
	
	private TextChangeHelper columndelimiterFieldChangelistener = new TextChangeHelper() {

			public void textChanged(Control control) {
				changedelimiter();
			}
		};
	
		private void changedelimiter()
		{
			String columndelemiter= txtColumndelimiter.getText().trim();			
			//((ResultConverter)getElement()).setColumnDelimiter(columndelemiter) ;
			
			SetResultConverterColDelimiterCommand cmd=new SetResultConverterColDelimiterCommand();
			cmd.setColumnDelimiter(columndelemiter);
			cmd.setPart((ResultConverter)getElement());				
			runCommand(cmd);
			
		}
		
	 private void setIndexes(ArrayList<IndexPairs> p)
	 {
		 	SetResultConverterColumnIndexesCommand cmd=new SetResultConverterColumnIndexesCommand();
			cmd.setColumnIndexes(p);
			cmd.setPart((ResultConverter)getElement());				
			runCommand(cmd);
	 }
		
		
	

	

	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		
		
		
		
		indexPairs=new ArrayList<IndexPairs>();
		
		composite = getWidgetFactory().createFlatFormComposite(parent);


		composite.setLayout(null);
		composite.setBounds(38, 10, 900, 252);

		Label lblRegex = new Label(composite, SWT.NONE);
		lblRegex.setText("regex");
		lblRegex.setBounds(5, 5, 55, 15);

		

		txtRegex = new Text(composite, SWT.BORDER);
		txtRegex.setBounds(112, 5, 455, 21);
		
		
		
		Button btnAddDouble=new Button(composite,SWT.NONE);
		btnAddDouble.setBounds(580, 5, 55, 21);
		btnAddDouble.setText("Double");
		btnAddDouble.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String string=txtRegex.getText();
				string=string+"([-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?)";
				txtRegex.setText(string);
				changeRegex();
			}
		});

		
		Button btnAddInteger=new Button(composite,SWT.NONE);
		btnAddInteger.setBounds(640, 5, 55, 21);
		btnAddInteger.setText("Integer");
		btnAddInteger.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String string=txtRegex.getText();
				string=string+"([-+]?[0-9]+)";
				txtRegex.setText(string);
				changeRegex();
			}
		});

		
		
		Button btnAddSpace=new Button(composite,SWT.NONE);
		btnAddSpace.setBounds(710, 5, 55, 21);
		btnAddSpace.setText("Space");
		btnAddSpace.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String string=txtRegex.getText();
				string=string+"(\\s+)";
				txtRegex.setText(string);
				changeRegex();
			}
		});

		

		

		txtColumndelimiter = new Text(composite, SWT.BORDER);
		txtColumndelimiter.setBounds(112, 29, 176, 21);

		
		
		Label lblColumnDelimiter = new Label(composite, SWT.NONE);
		lblColumnDelimiter.setText("Column Delimiter");
		lblColumnDelimiter.setBounds(5, 29, 123, 15);

		Button btnAddspaceDelimiter = new Button(composite, SWT.NONE);
		btnAddspaceDelimiter.setText("Space");
		btnAddspaceDelimiter.setBounds(300, 29, 56, 21);

		btnAddspaceDelimiter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
				{		
				txtColumndelimiter.setText(txtColumndelimiter.getText()+"[[:space:]]");
				changedelimiter();
				}
		});

		
		Label lblIndexId = new Label(composite, SWT.NONE);
		lblIndexId.setText("Index");
		lblIndexId.setBounds(5, 55, 55, 15);//(400, 133, 75, 21);


		txtIndexId = new Text(composite, SWT.BORDER);
		txtIndexId.setBounds(5,80, 75, 21);
		
		
		Label lblTag= new Label(composite, SWT.NONE);
		lblTag.setText("Tag");
		lblTag.setBounds(90, 55, 75, 21);
		
		txtIndexTag = new Text(composite, SWT.BORDER);
		txtIndexTag.setBounds(90, 80, 75, 21);
		
		Label lblUnit= new Label(composite, SWT.NONE);
		lblUnit.setText("Unit");
		lblUnit.setBounds(180, 55, 75, 21);
		
		txtUnitTag = new Text(composite, SWT.BORDER);
		txtUnitTag.setBounds(180, 80, 75, 21);

		
		Label lblCoefficint= new Label(composite, SWT.NONE);
		lblCoefficint.setText("Coefficient");
		lblCoefficint.setBounds(270, 55, 75, 21);
		
		txtIndexCoefficient = new Text(composite, SWT.BORDER);
		txtIndexCoefficient .setBounds(270, 80, 75, 21);
		
		
		
		final Button chkForward= new Button(composite, SWT.CHECK);
		chkForward.setBounds(360, 80, 75, 21);
		chkForward.setText("Forward");
		
		
//		TableViewer tableViewer = new TableViewer(composite, SWT.BORDER
//				| SWT.FULL_SELECTION);
//		table = tableViewer.getTable();
//		table.setBounds(112, 133, 134, 85);

		Button btnAddToList = new Button(composite, SWT.NONE);
		btnAddToList.setBounds(450, 80, 75, 25);//(333, 133, 75, 25);
		btnAddToList.setText("Add To List");
		btnAddToList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
				{
					if(txtIndexId.getText().trim().equals(""))
					{
						MessageDialog.openWarning(composite.getShell(),"Input Error","Please Insert Index Value");
						return;
					}
				
					if(txtIndexTag.getText().trim().equals(""))
					{
						MessageDialog.openWarning(composite.getShell(),"Input Error","Please Insert Tag Value");
						return;
					}
					IndexPairs p=new IndexPairs();
					p.id=Integer.parseInt(txtIndexId.getText().trim());
					p.tag=txtIndexTag.getText().trim();
					p.unit=txtUnitTag.getText().trim();
					p.forward=chkForward.getSelection();
					p.coefficient=Double.parseDouble(txtIndexCoefficient.getText().trim());
					if(!indexPairs.contains(p))
						{					
							indexPairs.add(p);
							setIndexes(indexPairs);						
						}
					else{
							MessageBox messageBox = new MessageBox(composite.getShell(), SWT.ICON_ERROR| SWT.OK );
					        messageBox.setText("Error");
					        messageBox.setMessage("Your selected index is already in list");
					        
	
					        return;
					}
						
				//ListChanged();
				viewer.setInput(indexPairs);
				viewer.refresh();
					 
				}
		});
		
		
		
		
		
		
		
		
		
		viewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);

		final Table table_1 = viewer.getTable();
		table_1.setBounds(5, 110, 545, 132);
		
        viewer.getTable().setHeaderVisible(true);
        viewer.getTable().setLinesVisible(true);
        viewer.setContentProvider(new ArrayContentProvider());

        TableColumn column = new TableColumn(viewer.getTable(), SWT.NONE);
        column.setText(" Index ");
        column.setWidth(100);
        TableViewerColumn firstNameCol = new TableViewerColumn(viewer, column);
        firstNameCol.setLabelProvider(new ColumnLabelProvider(){

            @Override
            public String getText(Object element) {
                IndexPairs ip=(IndexPairs)element;
            	Integer id = ip.id;

                return String.valueOf(id);
            }

        });
        
        
        column = new TableColumn(viewer.getTable(), SWT.NONE);
        column.setText(" Tag ");
        column.setWidth(100);
        TableViewerColumn tagNameCol = new TableViewerColumn(viewer, column);
       tagNameCol .setLabelProvider(new ColumnLabelProvider(){

            @Override
            public String getText(Object element) {
            	 IndexPairs ip=(IndexPairs)element;
             	String tag=ip.tag;

                 return tag;
            }

        });

       
       column = new TableColumn(viewer.getTable(), SWT.NONE);
       column.setText(" Unit ");
       column.setWidth(90);
       TableViewerColumn UnitNameCol = new TableViewerColumn(viewer, column);
       UnitNameCol .setLabelProvider(new ColumnLabelProvider(){

           @Override
           public String getText(Object element) {
           	 IndexPairs ip=(IndexPairs)element;
            	String unit=ip.unit;

                return unit;
           }

       });
       
       column = new TableColumn(viewer.getTable(), SWT.NONE);
       column.setText(" Coefficient ");
       column.setWidth(90);
       TableViewerColumn coefficientNameCol = new TableViewerColumn(viewer, column);
       coefficientNameCol .setLabelProvider(new ColumnLabelProvider(){

           @Override
           public String getText(Object element) {
           	 IndexPairs ip=(IndexPairs)element;
            	double coefficient=ip.coefficient;

                return String.valueOf(coefficient);
           }

       });
       
       column = new TableColumn(viewer.getTable(), SWT.NONE);
       column.setText(" Forward ");
       column.setWidth(70);
       TableViewerColumn forwardColName = new TableViewerColumn(viewer, column);
       forwardColName .setLabelProvider(new ColumnLabelProvider(){

           @Override
           public String getText(Object element) {
           	 IndexPairs ip=(IndexPairs)element;
            	boolean forward=ip.forward;

                return String.valueOf(forward);
           }

       });
        
       



        column = new TableColumn(viewer.getTable(), SWT.NONE);
        column.setText("Actions");
        column.setWidth(90);
        TableViewerColumn actionsNameCol = new TableViewerColumn(viewer, column);
        
    	buttons = new HashMap<Object, Button>();
        actionsNameCol.setLabelProvider(new ColumnLabelProvider(){
            //make sure you dispose these buttons when viewer input changes
            //final Map<Object, Button> 
        	


            @Override
            public void update(ViewerCell cell) {
            	
            	TableItem item = (TableItem) cell.getItem();
                Button button;
                if(buttons.containsKey(cell.getElement()))
                {
                    button = buttons.get(cell.getElement());
                }
                else
                {
                    button = new Button((Composite) cell.getViewerRow().getControl(),SWT.NONE);
                    button.setText("Remove");
                    final IndexPairs pair=(IndexPairs)cell.getElement();
                    button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) 
						{
							indexPairs.remove(pair);
							
							((Button)e.getSource()).dispose();
							buttons.remove(pair);
							
							
							
							setIndexes(indexPairs);
							//((ResultConverter)getElement()).setColumnsIndexes(indexPairs);
							
							viewer.setInput(indexPairs);
							viewer.refresh();
						}
                    });
                    buttons.put(cell.getElement(), button);
                }
                TableEditor editor = new TableEditor(item.getParent());
                editor.grabHorizontal  = true;
                editor.grabVertical = true;
                editor.setEditor(button , item, cell.getColumnIndex());
                editor.layout();
            }

        });
        
        viewer.setInput(indexPairs);

        composite.getShell().open();
        
    

        Label lblHelpTip = new Label(composite, SWT.WRAP | SWT.BORDER);
		lblHelpTip.setText(ResusMessages.ResultConverter_Regex_information);
		lblHelpTip.setBounds(composite.getBounds().width+composite.getLocation().x, 10, 500, 200);
		
		
		
		
        
            
       
        regexFieldChangelistener.startListeningTo(txtRegex);
       
        columndelimiterFieldChangelistener.startListeningTo(txtColumndelimiter);
       
        regexFieldChangelistener.startListeningForEnter(txtRegex);
        columndelimiterFieldChangelistener.startListeningForEnter(txtColumndelimiter);
       
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if(!(getElement() instanceof ResultConverter)){
			MessageDialog.openError(composite.getShell(), "Error","Element is not an ResultConverter Instance");
			return;
		}
		
		regexFieldChangelistener.startNonUserChange();
		columndelimiterFieldChangelistener.startNonUserChange();
		try {
			ResultConverter rc=((ResultConverter) getElement());
			
			Iterator<Object> itr=buttons.keySet().iterator();
			while (itr.hasNext())
				buttons.get(itr.next()).dispose();

			
			buttons=new Hashtable<Object, Button>();
			
			
			
			txtRegex.setText(rc.getRegex());
			txtColumndelimiter.setText(rc.getColumnDelimiter());
			
			if(rc.getColumnsIndexes()!=null)
				indexPairs=rc.getColumnsIndexes();
			else indexPairs=new ArrayList<IndexPairs>();
			
			
			
			viewer.setInput(indexPairs);
			viewer.refresh();
//			System.out.println(buttons);
//			System.out.println("------------------");
//			//heightText.setText(Integer.toString(dimension.height));
		} finally {
			regexFieldChangelistener.finishNonUserChange();
			columndelimiterFieldChangelistener.finishNonUserChange();
		}
	}
}
