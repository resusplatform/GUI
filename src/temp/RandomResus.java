package temp;
import java.util.Random;

import de.clausthal.tu.ielf.randomGenrators.SimpleRandomGenerator;
import de.clausthal.tu.ielf.randomGenrators.SobolRandomGenerator;
import de.clausthal.tu.ielf.randomGenrators.distributions.TriangleDistribution;
import de.clausthal.tu.ielf.randomGenrators.distributions.TruncatedGaussDistribution;
import de.clausthal.tu.ielf.randomGenrators.distributions.UniformDistribution;

/** 
Generate pseudo-random floating point values, with an 
approximately Gaussian (normal) distribution.

Many physical measurements have an approximately Gaussian 
distribution; this provides a way of simulating such values. 
*/
public final class RandomResus {
  
  
  public double[] generateGausian(double mean,double variance,double interMin,double interMax){
	  SimpleRandomGenerator generator= new SimpleRandomGenerator(10000,1);
	  //SobolRandomGenerator generator= new SobolRandomGenerator(10000,1);
	    double[] result=new double[10000];
	    
	    for (int idx = 0; idx < 10000; idx++){
	      TruncatedGaussDistribution d= new TruncatedGaussDistribution(interMin, interMax, mean, variance);
	     
	      d.setPrimitiveValue(generator.getItem(idx, 0)); 
	      d.setLogaritmic(true);
	    	result[idx]=d.next();
//	      log("Generated : " +String.valueOf(idx)+" "+ );
	    }
	    return result;
	  }
	  
    
  public double[] generateUniform(double interMin,double interMax){
	   SimpleRandomGenerator generator= new SimpleRandomGenerator(10000,1);
	  //SobolRandomGenerator generator= new SobolRandomGenerator(10000,1);
	    double[] result=new double[10000];
	    
	    for (int idx = 0; idx < 10000; idx++){
	      UniformDistribution d= new UniformDistribution(interMin, interMax);
	      d.setPrimitiveValue(generator.getItem(idx, 0));
	    	result[idx]=d.next();
	    	System.out.println(result[idx]);
//	      log("Generated : " +String.valueOf(idx)+" "+ );
	    }
	    return result;
	  }
  
  
  public double[] generateLogUniform(double interMin,double interMax){
	  SimpleRandomGenerator generator= new SimpleRandomGenerator(10000,1);
	  //SobolRandomGenerator generator= new SobolRandomGenerator(10000,1);
	    double[] result=new double[10000];
	    
	    for (int idx = 0; idx < 10000; idx++){
	      UniformDistribution d= new UniformDistribution(interMin, interMax);
	      d.setPrimitiveValue(generator.getItem(idx, 0));
	      d.setLogaritmic(true);
	    	result[idx]=d.next();
	    	System.out.println(result[idx]);
//	      log("Generated : " +String.valueOf(idx)+" "+ );
	    }
	    return result;
	  }
  
  
  public double[] generateTriangle(double a,double b,double c){
	  SimpleRandomGenerator generator= new SimpleRandomGenerator(10000,1);
	  //SobolRandomGenerator generator= new SobolRandomGenerator(10000,1);
	    double[] result=new double[10000];
	    
	    for (int idx = 0; idx < 10000; idx++){
	      TriangleDistribution d= new TriangleDistribution(a, b, c);
	      d.setPrimitiveValue(generator.getItem(idx, 0));
	    	result[idx]=d.next();
//	      log("Generated : " +String.valueOf(idx)+" "+ );
	    }
	    return result;
	  }

 // private Random fRandom = new Random();
  
  //private double getGaussian(double aMean, double aVariance,double minimum,double maximum){
	  
//	  SimpleRandomGenerator generator= new SimpleRandomGenerator(10000,1);
//	  //SobolRandomGenerator generator= new SobolRandomGenerator(10000,1);
//	    double[] result=new double[10000];
//	    
//	    for (int idx = 0; idx < 10000; idx++){
//	      UniformDistribution d= new UniformDistribution(interMin, interMax);
//	      d.setPrimitiveValue(generator.getItem(idx, 0));
//	    	result[idx]=d.next();
////	      log("Generated : " +String.valueOf(idx)+" "+ );
//	    }
//	    return result;
//	  }
 // }
// private double getTrianle(double min, double max,double c){
//	  
//	 
//	 double u=getUniform(0, 1);
//	 if(u<(c-min)/(max-min))
//		 return min+Math.sqrt(u*(max-min)*(c-min));
//	 else return max-Math.sqrt((1-u)*(max-min)*(max-c));
//     
//  }
//  
//  
//  
//private double getUniform(double minimum,double maximum){
//	  
//	  double m=fRandom.nextDouble();
//	  double r=minimum +  m* (maximum-minimum);
//	  
//	  
//	  
//      return r;
//  }
//
//
//private double getLogUniform(double minimum , double maximum){
//	double m=(getUniform(Math.log10(minimum),Math.log10(maximum)));
//
//	 System.out.println(m);
//	double r=Math.exp(m);
//	 System.out.println(r);
//	return r;
//}

  private static void log(Object aMsg){
    System.out.println(String.valueOf(aMsg));
  }
} 
