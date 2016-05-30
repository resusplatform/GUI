package de.clausthal.tu.ielf.resusdesigner.model;

public class OutputPair {
	private String numberOfLines;
	
	private String fileName;
	private  String MinFileSize;
	private boolean breakIfHappend;
	
	
	
	public String getNumberOfLines() {
		return numberOfLines;
	}
	public void setNumberOfLines(String numberOfLines) {
		this.numberOfLines = numberOfLines;
	}
	public String getFileName() {
		if(fileName==null) return "";
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getMinFileSize() {
		if(MinFileSize==null) return "";
		return MinFileSize;
	}
	public void setMinFileSize(String minFileSize) {
		MinFileSize = minFileSize;
	}
	public boolean isBreakIfHappend() {
		return breakIfHappend;
	}
	public void setBreakIfHappend(boolean breakIfHappend) {
		this.breakIfHappend = breakIfHappend;
	}
	
	
	
}
