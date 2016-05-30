package de.clausthal.tu.ielf.resus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Tree;

public class InputSampleEditor extends ViewPart {

	
	Tree tree ;
	
	private String fileName;
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	HashMap<Object, Button> Removebuttons = new HashMap<Object, Button>();
	HashMap<Object, Button> editButtons = new HashMap<Object, Button>();
	
	private int RealizationCounter=0;
	NodeList realizations;
	
	public InputSampleEditor() {
		
		
		
	}
	 
	private TableViewer viewer;
	public void refresh(){
		init();
		tree.removeAll();
		viewer.setInput(null);
        TreeItem sample=new TreeItem(tree, SWT.NONE, 0);
        sample.setText("Sample");
        for(int i=0;i<realizations.getLength();i++)
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
		
		Element realization=(Element)realizations.item(realizationIndex);
		ArrayList<Node> list=new ArrayList<Node>();
		NodeList parameters=realization.getElementsByTagName("parameter");
		for(int i=0;i<parameters.getLength();i++)
			list.add(parameters.item(i));
		
		viewer.setInput(list);
		viewer.refresh();

		
	}
	
	
	@Override
	public void createPartControl(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);

		viewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		final Table table_1 = viewer.getTable();
		table_1.setBounds(217, 10, 750, 266);
		
		
        viewer.getTable().setHeaderVisible(true);
        viewer.getTable().setLinesVisible(true);
        viewer.setContentProvider(new ArrayContentProvider());

        //---------------------table---------------
        TableColumn column = new TableColumn(viewer.getTable(), SWT.NONE);
        column.setText(" Name ");
        column.setWidth(100);
        TableViewerColumn nameCol = new TableViewerColumn(viewer, column);
        nameCol.setLabelProvider(new ColumnLabelProvider(){

            @Override
            public String getText(Object element) {
                Element p=(Element)element;
            	String name=p.getElementsByTagName("name").item(0).getTextContent();

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
            	Element p=(Element)element;
            	String unit=p.getElementsByTagName("unit").item(0).getTextContent();
             	

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
        	   Element p=(Element)element;
           	String dist=((Element)p.getElementsByTagName("distribution").item(0)).getElementsByTagName("name").item(0).getTextContent();
            	

                return dist;
           }

       });
      
      column = new TableColumn(viewer.getTable(), SWT.NONE);
      column.setText(" Min ");
      column.setWidth(100);
      TableViewerColumn param1Col = new TableViewerColumn(viewer, column);
      param1Col .setLabelProvider(new ColumnLabelProvider(){

          @Override
          public String getText(Object element) {
        	   Element p=(Element)element;
              	String minvalue=((Element)p.getElementsByTagName("distribution").item(0)).getElementsByTagName("minValue").item(0).getTextContent();
               	

                   return minvalue;
              }
          

      });
     column = new TableColumn(viewer.getTable(), SWT.NONE);
     column.setText(" Max ");
     column.setWidth(100);
     TableViewerColumn param2Col = new TableViewerColumn(viewer, column);
     param2Col .setLabelProvider(new ColumnLabelProvider(){

         @Override
         public String getText(Object element) {
        	 Element p=(Element)element;
        	 String maxvalue=((Element)p.getElementsByTagName("distribution").item(0)).getElementsByTagName("maxValue").item(0).getTextContent();
        	 return maxvalue;
           }

     });
    
    

     column = new TableColumn(viewer.getTable(), SWT.NONE);
     column.setText(" Output ");
     column.setWidth(80);
     TableViewerColumn showinoutputCol = new TableViewerColumn(viewer, column);
     showinoutputCol.setLabelProvider(new ColumnLabelProvider(){

         @Override
         public String getText(Object element) {
        	 
              	Element p=(Element)element;
              	String showInOutput=p.getElementsByTagName("showInOutput").item(0).getTextContent();
               	

                   return showInOutput;
              }
         

     });

     column = new TableColumn(viewer.getTable(), SWT.NONE);
     column.setText(" Value ");
     column.setWidth(100);
     TableViewerColumn valueCol = new TableViewerColumn(viewer, column);
     valueCol .setLabelProvider(new ColumnLabelProvider(){

         @Override
         public String getText(Object element) {
       	   Element p=(Element)element;
             	String value=p.getElementsByTagName("value").item(0).getTextContent();
              	

                  return value;
             }
         

     });
     
     

		
		

        		
		
		//--------------------- end of table---------------
		
		
		
		
		
		
		
		
		
		
		
		
		
		
        viewer.getTable().setHeaderVisible(true);
        viewer.getTable().setLinesVisible(true);
        
        tree = new Tree(composite, SWT.BORDER);
        tree.setBounds(10, 10, 201, 266);
       
        
        
        
        
        
      


	}
	
	public void init(){
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		

			Document doc;
			doc = dBuilder.parse(fileName);
			realizations = doc.getElementsByTagName("realization");
		
		}
		catch(Exception x){
			System.out.println("error ");
		}
		
	}

	@Override
	public void setFocus() {
		

	}
}
