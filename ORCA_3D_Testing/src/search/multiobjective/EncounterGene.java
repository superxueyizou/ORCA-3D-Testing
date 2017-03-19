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
package search.multiobjective;


import search.random.EncounterGeneRandom;
import ec.EvolutionState;
import ec.util.MersenneTwisterFast;
import ec.util.Parameter;
import ec.vector.VectorDefaults;

public class EncounterGene extends EncounterGeneRandom 
{	
	
	private static final long serialVersionUID = 1L;
		
	public static final String P_OWNSHIPVY ="ownshipVy";	
	public static final String P_OWNSHIPGS ="ownshipGs";
	
	public static final String P_MIN_CAPY ="min_CAPY";	
	public static final String P_MAX_CAPY ="max_CAPY";	
	
	public static final String P_MIN_CAPR ="min_CAPR";	
	public static final String P_MAX_CAPR ="max_CAPR";	
	
	public static final String P_MIN_CAPTheta ="min_CAPTheta";	
	public static final String P_MAX_CAPTheta="max_CAPTheta";		
	
	public static final String P_MIN_CAPVy ="min_CAPVy";	
	public static final String P_MAX_CAPVy ="max_CAPVy";	
	
	public static final String P_MIN_CAPGs ="min_CAPGs";	
	public static final String P_MAX_CAPGs ="max_CAPGs";	
	
	public static final String P_MIN_CAPBearing ="min_CAPBearing";	
	public static final String P_MAX_CAPBearing ="max_CAPBearing";	
	
	public static final String P_MIN_CAPT ="min_CAPT";	
	public static final String P_MAX_CAPT ="max_CAPT";	
	
	public static final String P_GAUSSIANSTD ="gaussianStd";		
	
	public double ownshipGs;
	public double ownshipVy;
	public double minCAPY;
	public double maxCAPY;
	public double minCAPR;
	public double maxCAPR;
	public double minCAPTheta;
	public double maxCAPTheta;
	public double minCAPVy;
	public double maxCAPVy;
	public double minCAPGs;
	public double maxCAPGs;
	public double minCAPBearing;
	public double maxCAPBearing;
	public double minCAPT;
	public double maxCAPT;
	public double gaussianStd;	

	
	public static final String P_ENCOUNTERGENE = "encountergene";
       
	public Parameter defaultBase()
	{
		return VectorDefaults.base().push(P_ENCOUNTERGENE);
	}

	public void setup(final EvolutionState state, final Parameter base)
	{
		super.setup(state,base);
		Parameter def = defaultBase();
		ownshipGs = state.parameters.getDoubleWithDefault(base.push(P_OWNSHIPGS), def.push(P_OWNSHIPGS), 5);
		ownshipVy = state.parameters.getDoubleWithDefault(base.push(P_OWNSHIPVY), def.push(P_OWNSHIPVY), 0);
		
		minCAPY = state.parameters.getDoubleWithDefault(base.push(P_MIN_CAPY), def.push(P_MIN_CAPY), -20);
		maxCAPY = state.parameters.getDoubleWithDefault(base.push(P_MAX_CAPY), def.push(P_MAX_CAPY),  20);
		
		minCAPR = state.parameters.getDoubleWithDefault(base.push(P_MIN_CAPR), def.push(P_MIN_CAPR), 0);
		maxCAPR = state.parameters.getDoubleWithDefault(base.push(P_MAX_CAPR), def.push(P_MAX_CAPR), 20);
		
		minCAPTheta = state.parameters.getDoubleWithDefault(base.push(P_MIN_CAPTheta), def.push(P_MIN_CAPTheta), -180);
		maxCAPTheta = state.parameters.getDoubleWithDefault(base.push(P_MAX_CAPTheta), def.push(P_MAX_CAPTheta),  180);
		
		minCAPVy = state.parameters.getDoubleWithDefault(base.push(P_MIN_CAPVy), def.push(P_MIN_CAPVy), -2);
		maxCAPVy = state.parameters.getDoubleWithDefault(base.push(P_MAX_CAPVy), def.push(P_MAX_CAPVy),  2);
		
		minCAPGs = state.parameters.getDoubleWithDefault(base.push(P_MIN_CAPGs), def.push(P_MIN_CAPGs), 2);
		maxCAPGs = state.parameters.getDoubleWithDefault(base.push(P_MAX_CAPGs), def.push(P_MAX_CAPGs), 10);
		
		minCAPBearing = state.parameters.getDoubleWithDefault(base.push(P_MIN_CAPBearing), def.push(P_MIN_CAPBearing), -180);
		maxCAPBearing = state.parameters.getDoubleWithDefault(base.push(P_MAX_CAPBearing), def.push(P_MAX_CAPBearing),  180);
		
		minCAPT = state.parameters.getDoubleWithDefault(base.push(P_MIN_CAPT), def.push(P_MIN_CAPT), 10);
		maxCAPT = state.parameters.getDoubleWithDefault(base.push(P_MAX_CAPT), def.push(P_MAX_CAPT),  30);
		
		gaussianStd = state.parameters.getDoubleWithDefault(base.push(P_GAUSSIANSTD), def.push(P_GAUSSIANSTD), 0.05);
	
	}

	public void reset(EvolutionState state, int thread) 
	{
		MersenneTwisterFast rdn = state.random[thread];
		CAPY=minCAPY+rdn.nextDouble(true, true) * (maxCAPY-minCAPY);
		CAPR=minCAPR+rdn.nextDouble(true, true) * (maxCAPR-minCAPR);
		CAPTheta=minCAPTheta+rdn.nextDouble(true, true) * (maxCAPTheta-minCAPTheta);
		CAPVy=minCAPVy+rdn.nextDouble(true, true) * (maxCAPVy-minCAPVy);
		CAPGs=minCAPGs+rdn.nextDouble(true, true) * (maxCAPGs-minCAPGs);
		CAPBearing=minCAPBearing+rdn.nextDouble(true, true) * (maxCAPBearing-minCAPBearing);
		CAPT=minCAPT+rdn.nextDouble(true, true) * (maxCAPT-minCAPT);		
	}
	
	public void mutate(EvolutionState state, int thread)
	{
		MersenneTwisterFast rdn = state.random[thread];
		CAPY=(1+rdn.nextGaussian()*gaussianStd)*CAPY;
		CAPR=(1+rdn.nextGaussian()*gaussianStd)*CAPR;
		CAPTheta=(1+rdn.nextGaussian()*gaussianStd)*CAPTheta;
		CAPVy=(1+rdn.nextGaussian()*gaussianStd)*CAPVy;
		CAPGs=(1+rdn.nextGaussian()*gaussianStd)*CAPGs;
		CAPBearing=(1+rdn.nextGaussian()*gaussianStd)*CAPBearing;
		CAPT=(1+rdn.nextGaussian()*gaussianStd)*CAPT;		
	}	
	
}