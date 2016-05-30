package de.clausthal.tu.ielf.randomGenrators.distributions;

public class TruncatedGaussDistribution2 extends GaussDistribution {

	public TruncatedGaussDistribution2(double min, double max, double mean,
			double standardDeviation) {
		super(min, max, mean, standardDeviation);
		name="Truncated Gauss2";
		// TODO Auto-generated constructor stub
	}
	@Override
	public double next() {
		double v=PrimitiveValue;// [0,1]
		double lower=CDFtest.CDF(getPropertyValue(MIN_VALUE),getPropertyValue(MEAN_VALUE),getPropertyValue(STANDARD_DEVIATION_VALUE));
		double upper=CDFtest.CDF(getPropertyValue(MAX_VALUE),getPropertyValue(MEAN_VALUE),getPropertyValue(STANDARD_DEVIATION_VALUE));
		double newValue=lower+v*(upper-lower);
		if(isLogaritmic){
			double mu=Math.log(
					getPropertyValue(MEAN_VALUE)*getPropertyValue(MEAN_VALUE)/Math.sqrt(getPropertyValue(MEAN_VALUE)*getPropertyValue(MEAN_VALUE)+getPropertyValue(STANDARD_DEVIATION_VALUE)*getPropertyValue(STANDARD_DEVIATION_VALUE))
					);
			double sigma=Math.sqrt(
					Math.log(
							1+((getPropertyValue(STANDARD_DEVIATION_VALUE)/getPropertyValue(MEAN_VALUE))*(getPropertyValue(STANDARD_DEVIATION_VALUE)/getPropertyValue(MEAN_VALUE)))));
			
			lower=CDFtest.CDF(Math.log(getPropertyValue(MIN_VALUE)),mu,sigma);
			upper=CDFtest.CDF(Math.log(getPropertyValue(MAX_VALUE)),mu,sigma);
			newValue=lower+v*(upper-lower);
			double d= CDFtest.INVCDF(newValue,mu,sigma);
			return Math.exp(d);
		}
		
		return CDFtest.INVCDF(newValue,getPropertyValue(MEAN_VALUE),getPropertyValue(STANDARD_DEVIATION_VALUE));
	}
}
