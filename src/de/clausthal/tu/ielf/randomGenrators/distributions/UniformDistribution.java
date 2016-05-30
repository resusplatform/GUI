package de.clausthal.tu.ielf.randomGenrators.distributions;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class UniformDistribution extends Distribution {

	
	public UniformDistribution(double min, double max){
		super(min, max);
		this.name="Uniform";
		
		
	}

	
	@Override
	public double next() {
		
		double min=getPropertyValue(MIN_VALUE);
		double max=getPropertyValue(MAX_VALUE);
		double v=PrimitiveValue;
		
		if(isLogaritmic){
			if(min<=0) return 0;
		if(max-min<=0) return 0;
			double d=Math.pow(10,(Math.log10(min)+(v*(Math.log10(max)-Math.log10(min)))));
			System.out.println(d);
			return d;
		}
		return min+(v*(max-min));
		
		//return getUniform(getPropertyValue(MIN_VALUE), getPropertyValue(MAX_VALUE));
	}
	
	public Element getXML(Document d){
		Element distribution=super.getXML(d);
		
		
		
		return distribution;
		
	}


}
