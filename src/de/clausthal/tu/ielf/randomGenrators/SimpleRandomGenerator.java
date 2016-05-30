package de.clausthal.tu.ielf.randomGenrators;



public class SimpleRandomGenerator extends Random {

	public SimpleRandomGenerator(int realizaions, int parameters) {
		super(realizaions, parameters);
		createLookupTable();
		
	}
	
	@Override
	public void createLookupTable() {
		
		
		super.createLookupTable();
		java.util.Random fRandom=new java.util.Random();
		
		for(int i=0;i<realizations;i++)
			for(int j=0;j<parameters;j++){
				table[i][j]=fRandom.nextDouble();
			}
	}

}
