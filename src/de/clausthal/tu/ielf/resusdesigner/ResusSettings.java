package de.clausthal.tu.ielf.resusdesigner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public  class ResusSettings {

	public ResusSettings(){
		
		String resusHomeSystemVariable="RESUS_HOME";
		String resusHomePath="";
	   
		resusHomePath= System.getenv(resusHomeSystemVariable);
	    if(resusHomePath==null || resusHomePath.equals(null) || resusHomePath.equals(""))
	    {
	    	//show error message
	    	

	    	MessageDialog.openError(null,"RESUS_HOME Error","system variable %RESUS_HOME% is not set");

	    	System.err.println("invalid %RESUS_HOME% variable");
	    }
	    this.core=resusHomePath+"\\core\\resusCore.exe";
	    this.chart=resusHomePath+"\\chart\\resusCharts.exe";
	    this.export=resusHomePath+"\\export\\resusExport.exe";

	    // check if the resus core and tools exist in the defined path
	    
	    File coref = new File(core);
	    File chartf=new File(chart);
	    File exportf=new File(export);
	    
	    if(!coref.exists() || coref.isDirectory()) {
	    	System.err.println("resusCore.exe is not available");
	    }
	    if(!chartf.exists() || chartf.isDirectory()) {
	    	System.err.println("resusCharts.exe is not available");
	    }
	    if(!exportf.exists() || exportf.isDirectory()) {
		    System.err.println("resusExport.exe is not available");
	    }


	    
	
		
	}
	
	
	public String getCore() {
		return core;
	}
	
	public String getExport() {
		return export;
	}
	
	public String getChart() {
		return chart;
	}
	

	String core="";//D:\\resusParts\\resusCore\\build-resusCore-Desktop_Qt_5_2_1_MSVC2012_32bit-Debug\\debug\\resusCore.exe";
	String chart="";//"D:\\resusParts\\resusCharts\\build-resusCharts-Desktop_Qt_5_2_1_MSVC2012_32bit-Debug\\debug\\resusCharts.exe";
	String export="";// "D:\\resusParts\\resusExport\\build-ResusExport-Desktop_Qt_5_2_1_MSVC2012_32bit-Debug\\debug\\resusExport.exe";
	public static  String RESUS_HOME="RESUS_HOME";
	public static Path checkReSUS_HOME(){
		
		final String resusHomePath=System.getenv(RESUS_HOME);
		if(resusHomePath==null||resusHomePath.equals(null)){
			MessageDialog.openError(null, "RESUS_HOME system Variable error", "The System Variable RESUS_HOME is not set. Can not start chart tool");
			return null;
		}
		//  check if the value of path is valid
		Path p=Paths.get(resusHomePath);
		if(!p.isAbsolute()){
			MessageDialog.openError(null, "Path Error", "The inserted Path for RESUS_HOME System Variable is invalid");
			return null;
		}
		
		//check if it is a file or directory. if not directory then error
		if(p.toFile().isDirectory()==false){
			MessageDialog.openError(null, "Path Error", "The RESUS_HOME should define a Directory");
			return null;
		}
		//if it is file, chek the directory exists 
		if(p.toFile().exists()==false){
			MessageDialog.openError(null, "Path Error", "The path to the RESUS_HOME is not valid ");
			return null;
		}
		return p;
	}
	
	
	public static Path chekReSUS_HOME_core(){
		Path resushome=checkReSUS_HOME();
		if(resushome==null|| resushome.equals(null)){
			return null;
		}
		//----------------------
		//check core folder exists
		Path core=resushome.resolve("core");
		
		if(!core.isAbsolute()){
			MessageDialog.openError(null, "Path Error", "The RESUS_HOME\\core folder path should be Absolute");
			return null;
		}
		
		//check if it is a file or directory. if not directory then error
		if(core.toFile().isDirectory()==false){
			MessageDialog.openError(null, "Path Error", "The RESUS_HOME\\core path should define a Directory");
			return null;
		}
		//if it is file, chek the directory exists 
		if(core.toFile().exists()==false){
			MessageDialog.openError(null, "Path Error", "The path to the RESUS_HOME\\core is not valid ");
			return null;
		}
		//---------------------------
		
		//check the resusCore.exe
		Path coreexe=core.resolve("resusCore.exe");
		if(!coreexe.isAbsolute()){
			MessageDialog.openError(null, "Path Error", "The RESUS_HOME\\core\\resusCore.exe path should be Absolute");
			return null;
		}
		
		//check if it is a file or directory. if not directory then error
		if(coreexe.toFile().isDirectory()==true){
			MessageDialog.openError(null, "ReSUS Core Path Error", "The RESUS_HOME\\core\\resusCore.exe path is not valid");
			return null;
		}
		//if it is file, chek the directory exists 
		if(coreexe.toFile().exists()==false){
			MessageDialog.openError(null, "ReSUS core Path Error", "The path to the RESUS_HOME\\core\\resusCore.exe does not exist");
			return null;
		}
		
		
		return coreexe;
		
	}

	public static Path chekReSUS_HOME_chart(){
		Path resushome=checkReSUS_HOME();
		if(resushome==null|| resushome.equals(null)){
			return null;
		}
		//----------------------
		//check core folder exists
		Path chart=resushome.resolve("chart");
		
		if(!chart.isAbsolute()){
			MessageDialog.openError(null, "Path Error", "The RESUS_HOME\\chart folder path should be Absolute");
			return null;
		}
		
		//check if it is a file or directory. if not directory then error
		if(chart.toFile().isDirectory()==false){
			MessageDialog.openError(null, "Path Error", "The RESUS_HOME\\chart path should define a Directory");
			return null;
		}
		//if it is file, chek the directory exists 
		if(chart.toFile().exists()==false){
			MessageDialog.openError(null, "Path Error", "The path to the RESUS_HOME\\chart is not valid ");
			return null;
		}
		//---------------------------
		
		//check the resusCore.exe
		Path chartexe=chart.resolve("resusCharts.exe");
		if(!chartexe.isAbsolute()){
			MessageDialog.openError(null, "Path Error", "The RESUS_HOME\\chart\\resusCharts.exe path should be Absolute");
			return null;
		}
		
		//check if it is a file or directory. if not directory then error
		if(chartexe.toFile().isDirectory()==true){
			MessageDialog.openError(null, "ReSUS Core Path Error", "The RESUS_HOME\\chart\\resusCharts.exe path is not valid");
			return null;
		}
		//if it is file, chek the directory exists 
		if(chartexe.toFile().exists()==false){
			MessageDialog.openError(null, "ReSUS core Path Error", "The path to the RESUS_HOME\\chart\\resusCharts.exe does not exist");
			return null;
		}
		
		
		return chartexe;
		
	}

	public static Path chekReSUS_HOME_regex(){
		Path resushome=checkReSUS_HOME();
		if(resushome==null|| resushome.equals(null)){
			return null;
		}
		//----------------------
		//check core folder exists
		Path regex=resushome.resolve("regex");
		
		if(!regex.isAbsolute()){
			MessageDialog.openError(null, "Path Error", "The RESUS_HOME\\regex folder path should be Absolute");
			return null;
		}
		
		//check if it is a file or directory. if not directory then error
		if(regex.toFile().isDirectory()==false){
			MessageDialog.openError(null, "Path Error", "The RESUS_HOME\\regex path should define a Directory");
			return null;
		}
		//if it is file, chek the directory exists 
		if(regex.toFile().exists()==false){
			MessageDialog.openError(null, "Path Error", "The path to the RESUS_HOME\\regex is not valid ");
			return null;
		}
		//---------------------------
		
		//check the resusCore.exe
		Path regextexe=regex.resolve("RegexChecker.exe");
		if(!regextexe.isAbsolute()){
			MessageDialog.openError(null, "Path Error", "The RESUS_HOME\\regex\\RegexChecker.exe path should be Absolute");
			return null;
		}
		
		//check if it is a file or directory. if not directory then error
		if(regextexe.toFile().isDirectory()==true){
			MessageDialog.openError(null, "ReSUS Core Path Error", "The RESUS_HOME\\regex\\RegexChecker.exe path is not valid");
			return null;
		}
		//if it is file, chek the directory exists 
		if(regextexe.toFile().exists()==false){
			MessageDialog.openError(null, "ReSUS core Path Error", "The path to the RESUS_HOME\\regex\\RegexChecker.exe does not exist");
			return null;
		}
		
		
		return regextexe;
		
	}
	
	
	public static Path chekReSUS_HOME_export(){
		Path resushome=checkReSUS_HOME();
		if(resushome==null|| resushome.equals(null)){
			return null;
		}
		//----------------------
		//check core folder exists
		Path export=resushome.resolve("export");
		
		if(!export.isAbsolute()){
			MessageDialog.openError(null, "Path Error", "The RESUS_HOME\\export folder path should be Absolute");
			return null;
		}
		
		//check if it is a file or directory. if not directory then error
		if(export.toFile().isDirectory()==false){
			MessageDialog.openError(null, "Path Error", "The RESUS_HOME\\export path should define a Directory");
			return null;
		}
		//if it is file, chek the directory exists 
		if(export.toFile().exists()==false){
			MessageDialog.openError(null, "Path Error", "The path to the RESUS_HOME\\export is not valid ");
			return null;
		}
		//---------------------------
		
		//check the resusCore.exe
		Path exportexe=export.resolve("resusExport.exe");
		if(!exportexe.isAbsolute()){
			MessageDialog.openError(null, "Path Error", "The RESUS_HOME\\core\\resusExport.exe path should be Absolute");
			return null;
		}
		
		//check if it is a file or directory. if not directory then error
		if(exportexe.toFile().isDirectory()==true){
			MessageDialog.openError(null, "ReSUS Core Path Error", "The RESUS_HOME\\core\\resusExport.exe path is not valid");
			return null;
		}
		//if it is file, chek the directory exists 
		if(exportexe.toFile().exists()==false){
			MessageDialog.openError(null, "ReSUS core Path Error", "The path to the RESUS_HOME\\core\\resusExport.exe does not exist");
			return null;
		}
		
		
		return exportexe;
		
	}
	
	
	  
}