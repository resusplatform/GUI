package de.clausthal.tu.ielf.randomGenrators.distributions;

import org.apache.commons.math3.special.Erf;

public class CDFtest {

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		double u=0.145;
//		double x=INVCDF(u, 200, 100);
//		System.out.println("invcdf --> "+ x);
//		System.out.println("cdf jhkjh--> "+ CDF(x, 200, 100));
//		
//		
//	}
	public static double INVCDF(double u,double mu,double sigma){
		
		return mu-(Math.sqrt(2.0)*Erf.erfInv(1-(2*u))*sigma);
	}
	public static double CDF(double x,double mu,double sigma){
		double v=Erf.erf((x-mu)/(Math.sqrt(2.0)*sigma));
		return(1+v)/2;
	}
}
