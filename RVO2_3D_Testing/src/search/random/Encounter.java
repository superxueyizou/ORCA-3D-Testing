package search.random;

import ec.util.MersenneTwisterFast;

public class Encounter {
	
	public static int maxNumGenes =10;
	
	public EncounterGeneRandom[] genes;
	public double[] objectives;
	
	public Encounter(MersenneTwisterFast rdn)
	{
		int numGenes = 1+rdn.nextInt(maxNumGenes);
		genes = new EncounterGeneRandom[numGenes];
		for(int i=0; i<numGenes; ++i)
		{
			genes[i]=new EncounterGeneRandom().initialize(rdn);;
		}
		objectives=new double[]{0,0};
	}
	
	public String toString()
	{
		StringBuilder strBuilder=new StringBuilder();
		for(int i=0; i<genes.length; ++i)
		{
			strBuilder.append(genes[i].printGeneToStringForHumans());
		}
		return strBuilder.toString();
	}
	

}
