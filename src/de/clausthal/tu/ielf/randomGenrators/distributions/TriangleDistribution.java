package de.clausthal.tu.ielf.randomGenrators.distributions;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TriangleDistribution extends Distribution {

	public static final String C_VALUE="c_value";
	public TriangleDistribution(double min, double max, double c) {
		super(min, max);
		name="Triangle";
		
		
		parameters.put(C_VALUE, c);
	}
	
	
	private double getTriangle(double min, double max,double c){
		  
		 
		 double u=PrimitiveValue;
		 if(u<(c-min)/(max-min))
			 return min+Math.sqrt(u*(max-min)*(c-min));
		 else return max-Math.sqrt((1-u)*(max-min)*(max-c));
	     
	  }
	
//	@Override
//	public ArrayList<Number> generate(int amount) {
//		ArrayList<Number> result=new ArrayList<Number>(); 
//		for(int i=0;i<amount ;i++){		
//				    //  double d=getTrianle(getPropertyValue(MIN_VALUE), getPropertyValue(MAX_VALUE), getPropertyValue(C_VALUE));
//				      result.add(next());
//		}
//		return result;
//
//	}


	@Override
	public double next() {
		  return getTriangle(getPropertyValue(MIN_VALUE), getPropertyValue(MAX_VALUE), getPropertyValue(C_VALUE));
	}
	
	public Element getXML(Document d){
		Element distribution=super.getXML(d);
		
		Element distC=d.createElement("cValue");
		distC.setTextContent(String.valueOf(getPropertyValue(C_VALUE)));
		distribution.appendChild(distC);
		
		
		return distribution;
		
	}


	

}
