package de.clausthal.tu.ielf.resusdesigner.model;

public class ResusModelLogFile {
	String fileName="";
	String criticalWords="";
	boolean breakIfHappend=false;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCriticalWords() {
		return criticalWords;
	}
	public void setCriticalWords(String criticalWords) {
		this.criticalWords = criticalWords;
	}
	public boolean isBreakIfHappend() {
		return breakIfHappend;
	}
	public void setBreakIfHappend(boolean breakIfHappend) {
		this.breakIfHappend = breakIfHappend;
	}
	
}
