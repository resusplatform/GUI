package de.clausthal.tu.ielf.randomGenrators.distributions;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.special.Erf;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GaussDistribution extends Distribution {

	public GaussDistribution(double min, double max,double mean,double standardDeviation) {
		super(min, max);
		name="Gauss";
		
		parameters.put(MEAN_VALUE, mean);
		parameters.put(STANDARD_DEVIATION_VALUE,standardDeviation);

		 
	}

	public static final String MEAN_VALUE="mean_value";
	public static final String STANDARD_DEVIATION_VALUE="standard_deviation_value";
	
	
	
	private double getGaussian(double aMean, double aStandardDeviation,double minimum,double maximum){
		  
		  double n=fRandom.nextGaussian();
		  
		  if(isLogaritmic){
			  double r=aMean +  n* aStandardDeviation;
			  while(r>maximum || r<minimum){
				  System.out.println("---> übersehen : "+r); 
				  n=fRandom.nextGaussian();
			      
				  r=aMean +  n* aStandardDeviation;
			  }
		      return Math.exp(r);
		  }
		  
		  
		  double r=aMean +  n* aStandardDeviation;
		  while(r>maximum || r<minimum){
			  System.out.println("---> übersehen : "+r); 
			  n=fRandom.nextGaussian();
		      
			  r=aMean +  n* aStandardDeviation;
		  }
	      return r;
	  }

	@Override
	public double next() {
		
		double value= -Math.sqrt(2.0)*Erf.erfcInv(1-(2*PrimitiveValue));
		value=getPropertyValue(MEAN_VALUE)+value*( getPropertyValue(STANDARD_DEVIATION_VALUE));
		return value;
		//return getGaussian(getPropertyValue(MEAN_VALUE), getPropertyValue(STANDARD_DEVIATION_VALUE), getPropertyValue(MIN_VALUE), getPropertyValue(MAX_VALUE));
	}
	
//	public  double INVCDF(double u,double mu,double sigma){
//		
//		return mu-(Math.sqrt(2.0)*Erf.erfInv(1-(2*u))*sigma);
//	}
//	public  double CDF(double x,double mu,double sigma){
//		double v=Erf.erf((x-mu)/(Math.sqrt(2.0)*sigma));
//		return(1+v)/2;
//	}
	public Element getXML(Document d){
		Element distribution=super.getXML(d);
		
		Element distMin=d.createElement("meanValue");
		distMin.setTextContent(String.valueOf(getPropertyValue(MEAN_VALUE)));
		distribution.appendChild(distMin);
		
		
		Element distMax=d.createElement("standardDeviation");
		distMax.setTextContent(String.valueOf(getPropertyValue(STANDARD_DEVIATION_VALUE)));
		distribution.appendChild(distMax);
		
		return distribution;
		
	}

}
