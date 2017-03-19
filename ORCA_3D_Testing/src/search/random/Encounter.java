/*******************************************************************************
 * /* *************************************************************************************
 *  * Copyright (C) Xueyi Zou - All Rights Reserved
 *  * Written by Xueyi Zou <xz972@york.ac.uk>, 2015
 *  * You are free to use/modify/distribute this file for whatever purpose!
 *  -----------------------------------------------------------------------
 *  |THIS FILE IS DISTRIBUTED "AS IS", WITHOUT ANY EXPRESS OR IMPLIED
 *  |WARRANTY. THE USER WILL USE IT AT HIS/HER OWN RISK. THE ORIGINAL
 *  |AUTHORS AND COPPELIA ROBOTICS GMBH WILL NOT BE LIABLE FOR DATA LOSS,
 *  |DAMAGES, LOSS OF PROFITS OR ANY OTHER KIND OF LOSS WHILE USING OR
 *  |MISUSING THIS SOFTWARE.
 *  ------------------------------------------------------------------------
 *  **************************************************************************************/
 /*******************************************************************************/
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
