package de.clausthal.tu.ielf.resus.wizards;

import java.io.Serializable;

import de.clausthal.tu.ielf.randomGenrators.distributions.Distribution;

public class Parameter	implements Serializable{
	
	protected String name;
	protected String unit;
	protected Distribution distribution;
	protected String firstParam;
	protected String secondParam;
	protected String thirdParam;
	protected String description;
	protected Boolean showInOutput;
	protected Integer index;
	protected Double value;
	
	public Double getValue(){
		return value;
	}
	public void setValue(double v){
		value=Double.valueOf(v);
	}
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Distribution getDistribution() {
		return distribution;
	}

	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
	}

	public String getFirstParam() {
		return firstParam;
	}

	public void setFirstParam(String firstParam) {
		this.firstParam = firstParam;
	}

	public String getSecondParam() {
		return secondParam;
	}

	public void setSecondParam(String secondParam) {
		this.secondParam = secondParam;
	}

	public String getThirdParam() {
		return thirdParam;
	}

	public void setThirdParam(String thirdParam) {
		this.thirdParam = thirdParam;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getShowInOutput() {
		return showInOutput;
	}

	public void setShowInOutput(Boolean showInOutput) {
		this.showInOutput = showInOutput;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	
	
	public void setName(String s){
		name=s;
	}
	
	
	
	}

