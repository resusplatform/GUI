package de.clausthal.tu.ielf.resus.views;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


import org.eclipse.swt.widgets.Tree;

import de.clausthal.tu.ielf.resusdesigner.ResusEditor;
import de.clausthal.tu.ielf.resusdesigner.ResusSettings;
import de.clausthal.tu.ielf.resusdesigner.model.ResultConverter;

public class OutputView extends ViewPart {

	public OutputView() {
		// TODO Auto-generated constructor stub
	}
	
	
	Tree tree ;
	ResusEditor logicEditor;
	
	@Override
	public void createPartControl(Composite parent) {		
				
		
		Composite cmp=new Composite(parent, 0);
		
		final Composite composite = new Composite(cmp, SWT.NONE);
		composite.setBounds(26, 55, 217, 235);
		
		tree = new Tree(composite, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		tree.setBounds(10, 10, 196, 217);
		
		Button btnCharts = new Button(cmp, SWT.NONE);
		btnCharts.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// make path to the  xmlfile
				String xmlFile="chxmlFile.xml";				
				
				Path resusHomechartexe=ResusSettings.chekReSUS_HOME_chart();
				if(resusHomechartexe==null)
					return;
				
				Path pathToChartsfolder=resusHomechartexe.getParent();
				if(pathToChartsfolder==null)
					return;
				
				
				
				
				// generate xml File according to selected result convertes
				ArrayList<ResultConverter> selectedRCs= getSelectedResultConvertors();
				StringBuilder sb=new StringBuilder();
				sb.append("<?xml version=\"1.0\" encoding= \"UTF-8\" standalone=\"no\"?>");
				sb.append("<chart>"	);
				for(int i=0;i<selectedRCs.size();i++){
					sb.append("<chartItem>");
					sb.append("<fileName>"+selectedRCs.get(i).getOutputFileName()+"</fileName>");
					sb.append("</chartItem>");
				}
				sb.append("</chart>"	);
				
				// save xml file 
				File xfile = pathToChartsfolder.resolve(Paths.get(xmlFile)).toFile();
				BufferedWriter writer;
				try {
					writer = new BufferedWriter(new FileWriter(xfile));
					writer.write(sb.toString());
					writer.close();

				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
		            

				
				//---
				
				ProcessBuilder builder = new ProcessBuilder(
						logicEditor.getSettings().getChart(),						
						xmlFile);
				builder.directory(pathToChartsfolder.toFile());

				try {
					builder.start();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCharts.setBounds(26, 23, 100, 25);
		btnCharts.setText("Chart Tool");
		
		Button btnExportTool = new Button(cmp, SWT.NONE);
		btnExportTool.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// make path to xml file
				String xmlFile="expxmlFile.xml";				
				
				Path resusHomeExportexe=ResusSettings.chekReSUS_HOME_export();
				if(resusHomeExportexe==null)
					return;
				
				Path pathToExportfolder=resusHomeExportexe.getParent();
				if(pathToExportfolder==null)
					return;
				
				
				
				// make xmlfile
				
				
				//String pathToFile="D:\\resusParts\\resusExport\\build-ResusExport-Desktop_Qt_5_2_1_MSVC2012_32bit-Debug\\debug\\";//+xmlFile;
				
				// generate xml File according to selected result convertes
				ArrayList<ResultConverter> selectedRCs= getSelectedResultConvertors();
				StringBuilder sb=new StringBuilder();
				sb.append("<?xml version=\"1.0\" encoding= \"UTF-8\" standalone=\"no\"?>");
				sb.append("<exportItems>"	);
				for(int i=0;i<selectedRCs.size();i++){
					sb.append("<exportItem>");
					sb.append("<fileName>"+selectedRCs.get(i).getOutputFileName()+"</fileName>");
					sb.append("<title>"+selectedRCs.get(i).getName()+"</title>");
					sb.append("</exportItem>");
				}
				sb.append("</exportItems>"	);
				
				// save xml file 
				File xfile = pathToExportfolder.resolve(Paths.get(xmlFile)).toFile();
				
				BufferedWriter writer;
				try {
					writer = new BufferedWriter(new FileWriter(xfile));
					writer.write(sb.toString());
					writer.close();

				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
		            

				//---				
				ProcessBuilder builder = new ProcessBuilder(						
						
						logicEditor.getSettings().getExport(),
						xmlFile);
				builder.directory(pathToExportfolder.toFile());

				try {
					builder.start();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnExportTool.setText("Export Tool");
		btnExportTool.setBounds(132, 23, 100, 25);
		ArrayList<ResultConverter> rcs=getResultConvertors();
		tree.removeAll();
		if(rcs!=null)
		for(int i=0;i<rcs.size();i++){
			TreeItem t=new TreeItem(tree, SWT.CHECK);
			t.setText(rcs.get(i).getName());
			t.setChecked(true);
			t.setData(rcs.get(i));
		}
		
		

	
		
	}
	
	
	public void refresh()
	{
		
			ArrayList<ResultConverter>  rcs= getResultConvertors();
			if(rcs==null) return ;
			
			tree.removeAll();
			String[] ResultConverterNames=new String[rcs.size()];
			for(int j=0;j<rcs.size();j++){
				String s="";
				if(rcs.get(j).getName().equals("") || rcs.get(j).getName().equals(null))
					s="RC";
				else 
					s=rcs.get(j).getName();
				ResultConverterNames[j]=s+"("+rcs.get(j).getId()+")";
				
				TreeItem t=new TreeItem(tree, SWT.CHECK);
				t.setText(ResultConverterNames[j]);
				t.setChecked(true);
				t.setData(rcs.get(j));
				
				
			}
		
	}
	
	private ArrayList<ResultConverter> getResultConvertors(){
	IEditorPart editor= PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		
		if (editor instanceof ResusEditor){
			logicEditor = (ResusEditor) editor;
			ArrayList<ResultConverter>  rcs= logicEditor.getResultConverters();
			return rcs;
		}
		return null;
		
	}
	private ArrayList<ResultConverter> getSelectedResultConvertors(){
	
		ArrayList<ResultConverter> selection=new ArrayList<ResultConverter>();
		TreeItem[] tItems=tree.getItems();
		for(int i=0;i<tItems.length;i++)
			if(tItems[i].getChecked())
				selection.add((ResultConverter)tItems[i].getData());
		return selection;
	}
		
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		refresh();
	
	}
}
