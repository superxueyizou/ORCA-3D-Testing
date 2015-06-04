package configuration;

import search.random.EncounterGeneRandom;

public class EncounterConfig 
{
	public double CAPY= 0;//[-20,20]
	public double CAPR= 0;//[0,20]
	public double CAPTheta= 90;//[-180,180]
	public double CAPVy =0;//[-2,2]
	public double CAPGs =5;//[2,10]
	public double CAPBearing =180;//[-180,180]
	public double CAPT=20;//[10,30]
	
	public EncounterConfig()	
	{
	}
	
	public EncounterConfig(EncounterGeneRandom encounterGene)	
	{
		CAPY=encounterGene.CAPY;
		CAPR=encounterGene.CAPR;
		CAPTheta=encounterGene.CAPTheta;
		CAPVy=encounterGene.CAPVy;
		CAPGs=encounterGene.CAPGs;
		CAPBearing=encounterGene.CAPBearing;
		CAPT=encounterGene.CAPT;
	}
}
