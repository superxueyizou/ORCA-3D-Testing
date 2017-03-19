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
