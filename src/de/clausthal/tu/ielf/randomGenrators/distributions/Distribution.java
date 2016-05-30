package de.clausthal.tu.ielf.randomGenrators.distributions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class Distribution implements Serializable {
	protected String name;
	protected double PrimitiveValue;
	


	public static final String MIN_VALUE="min_value";
	public static final String MAX_VALUE="max_value";
	protected Map<String, Double> parameters;
	protected Random fRandom = new Random();
	public Distribution(double min,double max){
		parameters=new HashMap<String, Double>();
		parameters.put(MIN_VALUE, min);
		parameters.put(MAX_VALUE, max);
	}	
	public double getPrimitiveValue() {
		return PrimitiveValue;
	}

	
	public void setPrimitiveValue(double primitiveValue) {
		PrimitiveValue = primitiveValue;
	}
	protected enum propertis{MIN,MAX};
	

	public void setName(String name) {
		this.name = name;
	}
	public String getName(){
		return name;
	}

	public boolean isLogaritmic() {
		return isLogaritmic;
	}

	public Double getPropertyValue(String propertyName){
		if(parameters.get(propertyName)!=null)
			return parameters.get(propertyName);
		return null; 
	}
	public void setPropertyValue(String propertyName,double PropertyValue){
		if(parameters.containsKey(propertyName))
			parameters.put(propertyName, PropertyValue);
	}
	public void setLogaritmic(boolean isLogaritmic) {
		this.isLogaritmic = isLogaritmic;
	}

	
	protected boolean isLogaritmic;
	@Override 
	public String toString()
	{
		if(isLogaritmic)return "logaritmic "+name;
		return name;
	}
	
	protected double getUniform(double minimum,double maximum){
		
		if(isLogaritmic){
		
			isLogaritmic=false;
			double m=(getUniform(Math.log(minimum),Math.log(maximum)));
			 double r=Math.exp(m);
			 
			 
			 while(r>maximum || r<minimum){
				  m=(getUniform(Math.log10(minimum),Math.log10(maximum)));
				  r=Math.exp(m);
			  }
			 
			 isLogaritmic=true;
			 
			 return r;		
		}
		
		  double m=fRandom.nextDouble();
		  double r=minimum +  m* (maximum-minimum);
		  while(r>maximum || r<minimum){
			  m=fRandom.nextDouble();
			  r=minimum +  m* (maximum-minimum);
		  }
	      return r;
	  }
	
	public abstract double next();
	public  Element getXML(Document d){
		Element distribution=d.createElement("distribution");		
		
		Element distName=d.createElement("name");
		distName.setTextContent(name);
		distribution.appendChild(distName);
		
		Element distMin=d.createElement("minValue");
		distMin.setTextContent(String.valueOf(getPropertyValue(MIN_VALUE)));
		distribution.appendChild(distMin);
		
		
		Element distMax=d.createElement("maxValue");
		distMax.setTextContent(String.valueOf(getPropertyValue(MAX_VALUE)));
		distribution.appendChild(distMax);
		
		return distribution;
	}
	
}
