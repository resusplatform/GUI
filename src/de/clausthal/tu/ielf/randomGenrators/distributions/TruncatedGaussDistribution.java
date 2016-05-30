package de.clausthal.tu.ielf.randomGenrators.distributions;

public class TruncatedGaussDistribution extends GaussDistribution {

	public TruncatedGaussDistribution(double min, double max, double mean,
			double standardDeviation) {
		super(min, max, mean, standardDeviation);
		name="Truncated Gauss";
		// TODO Auto-generated constructor stub
	}
	@Override


public double next() {
		double v=PrimitiveValue;// [0,1]
		double lower=CDFtest.CDF(getPropertyValue(MIN_VALUE),getPropertyValue(MEAN_VALUE),getPropertyValue(STANDARD_DEVIATION_VALUE));
		double upper=CDFtest.CDF(getPropertyValue(MAX_VALUE),getPropertyValue(MEAN_VALUE),getPropertyValue(STANDARD_DEVIATION_VALUE));
		double newValue=lower+v*(upper-lower);
		if(isLogaritmic){
			lower=CDFtest.CDF(Math.log(getPropertyValue(MIN_VALUE)),Math.log(getPropertyValue(MEAN_VALUE)),Math.sqrt(getPropertyValue(STANDARD_DEVIATION_VALUE)));
			upper=CDFtest.CDF(Math.log(getPropertyValue(MAX_VALUE)),Math.log(getPropertyValue(MEAN_VALUE)),Math.sqrt(getPropertyValue(STANDARD_DEVIATION_VALUE)));
			newValue=lower+v*(upper-lower);
			double d= CDFtest.INVCDF(newValue,Math.log(getPropertyValue(MEAN_VALUE)),Math.sqrt(getPropertyValue(STANDARD_DEVIATION_VALUE)));
			return Math.exp(d);
		}
		
		return CDFtest.INVCDF(newValue,getPropertyValue(MEAN_VALUE),getPropertyValue(STANDARD_DEVIATION_VALUE));
	}
}