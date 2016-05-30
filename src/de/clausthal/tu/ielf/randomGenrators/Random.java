package de.clausthal.tu.ielf.randomGenrators;

import de.clausthal.tu.ielf.resus.wizards.Parameter;

public class Random {

	protected double[][] table;
	protected int realizations;
	protected int parameters;
	public  Random(int realizaions,int parameters){
		this.realizations=realizaions;
		this.parameters=parameters;
	}
	
	public  void createLookupTable(){
		table=new double[realizations][parameters];
	}
	public double getItem(int realizationNo,int parameterNo){
		double value=table[realizationNo][parameterNo];
		
		
		return value; 
	}
	
}
