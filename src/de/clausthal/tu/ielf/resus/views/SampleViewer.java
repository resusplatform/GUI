/****
 * 
 * this sample viewer is depricated and will not be used in resus package
 */


package de.clausthal.tu.ielf.resus.views;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.Policy.Parameters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.clausthal.tu.ielf.randomGenrators.*;
import de.clausthal.tu.ielf.resus.RowNumberLabelProvider;
import de.clausthal.tu.ielf.resusdesigner.model.IndexPairs;
import de.clausthal.tu.ielf.resusdesigner.model.ResultConverter;


public class SampleViewer extends MessageDialog {
private String fileName;
NodeList realizations; 
private TableViewer viewer;
private Tree tree;

  public SampleViewer(Shell parentShell, String dialogTitle,
			Image dialogTitleImage, String dialogMessage, int dialogImageType,
			String[] dialogButtonLabels, int defaultIndex, String filename) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage,
				dialogImageType, dialogButtonLabels, defaultIndex);
		this.fileName=filename;
		// TODO Auto-generated constructor stub
	}


  @Override
  public void create() {
    super.create();
    refresh();

  }
  
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
	        
	        //populateList(ti.getText());
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
  protected Control createDialogArea(Composite parent) {
	  final Composite composite = new Composite(parent, SWT.NONE);
	  

		viewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		final Table table_1 = viewer.getTable();
		table_1.setBounds(217, 10, 750, 400);
		
		
      viewer.getTable().setHeaderVisible(true);
      viewer.getTable().setLinesVisible(true);
      viewer.setContentProvider(new ArrayContentProvider());

      //---------------------table---------------
      TableColumn column = new TableColumn(viewer.getTable(), SWT.NONE);
      column.setText(" Row ");
      column.setWidth(50);
      TableViewerColumn rowCol = new TableViewerColumn(viewer, column);
      rowCol.setLabelProvider(new RowNumberLabelProvider());
      
      
      column = new TableColumn(viewer.getTable(), SWT.NONE);
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
    
//    column = new TableColumn(viewer.getTable(), SWT.NONE);
//    column.setText(" Min ");
//    column.setWidth(100);
//    TableViewerColumn param1Col = new TableViewerColumn(viewer, column);
//    param1Col .setLabelProvider(new ColumnLabelProvider(){
//
//        @Override
//        public String getText(Object element) {
//      	   Element p=(Element)element;
//            	String minvalue=((Element)p.getElementsByTagName("distribution").item(0)).getElementsByTagName("minValue").item(0).getTextContent();
//             	
//
//                 return minvalue;
//            }
//        
//
//    });
//   column = new TableColumn(viewer.getTable(), SWT.NONE);
//   column.setText(" Max ");
//   column.setWidth(100);
//   TableViewerColumn param2Col = new TableViewerColumn(viewer, column);
//   param2Col .setLabelProvider(new ColumnLabelProvider(){
//
//       @Override
//       public String getText(Object element) {
//      	 Element p=(Element)element;
//      	 String maxvalue=((Element)p.getElementsByTagName("distribution").item(0)).getElementsByTagName("maxValue").item(0).getTextContent();
//      	 return maxvalue;
//         }
//
//   });
//  
//  

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
     
      
      
      
      return parent;
      
    


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
  protected boolean isResizable() {
    return false;
  }

  
public String getFileName(){
	return fileName;
}
  
} 