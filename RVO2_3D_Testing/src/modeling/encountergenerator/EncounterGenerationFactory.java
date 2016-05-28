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
package modeling.encountergenerator;

import modeling.SAAModel;
import modeling.env.Target;
import modeling.uas.UAS;
import modeling.uas.UASPerformance;
import modeling.uas.UASVelocity;
import saa.AutoPilot;
import saa.selfseparation.RVO2_3D;
import saa.selfseparation.SelfSeparationAlgorithm;
import search.random.EncounterGeneRandom;
import sim.util.Double3D;
import configuration.Configuration;
import configuration.EncounterConfig;
import configuration.GlobalConfig;

public class EncounterGenerationFactory {
	private static GlobalConfig globalConfig = Configuration.getInstance().globalConfig;
	
	public static UAS generateIntruder(SAAModel state, UAS ownship, String intruderAlias,EncounterConfig encounterConfig)
	{			
		UASVelocity intruderVelocity = new UASVelocity(new Double3D(encounterConfig.CAPGs*Math.cos(Math.toRadians(encounterConfig.CAPBearing)), encounterConfig.CAPVy, encounterConfig.CAPGs*Math.sin(Math.toRadians(encounterConfig.CAPBearing))));
		Double3D ownshipCAP = ownship.getLocation().add(ownship.getVelocity().multiply(encounterConfig.CAPT));
		Double3D intruderCAP =ownshipCAP.add(new Double3D(encounterConfig.CAPR*Math.cos(Math.toRadians(encounterConfig.CAPTheta)), encounterConfig.CAPY, encounterConfig.CAPR*Math.sin(Math.toRadians(encounterConfig.CAPTheta))));
		Double3D location=intruderCAP.add(intruderVelocity.getVelocity().negate().multiply(encounterConfig.CAPT));			
		Double3D intruderTargetLoc = location.add(intruderVelocity.getVelocity().multiply(3*encounterConfig.CAPT));
		Target intruderTarget =  new Target(state.getNewID(),intruderTargetLoc);
		UASPerformance intruderPerformance = new UASPerformance(globalConfig.stdDevX, globalConfig.stdDevY,globalConfig.stdDevZ,
				globalConfig.maxGS, globalConfig.minGS, globalConfig.maxVS, globalConfig.minVS);
		
		UAS intruder = new UAS(state.getNewID(),location, intruderVelocity,intruderTarget, intruderPerformance);
		intruder.setAlias(intruderAlias);		
		
		
		AutoPilot ap= new AutoPilot(state, intruder,Configuration.getInstance().globalConfig.whiteNoiseEnabler,"WhiteNoise");
		SelfSeparationAlgorithm ssa;
		ssa= new RVO2_3D(state, intruder);
		
		intruder.init(ap,ssa);
		intruder.setSchedulable(true);
		
		return intruder;	
	}
	
//	public static UAS generateIntruder(SAAModel state, UAS ownship, String intruderAlias,EncounterGeneRandom encounterGene)
//	{	
//		UASVelocity intruderVelocity = new UASVelocity(new Double3D(encounterGene.CAPGs*Math.cos(Math.toRadians(encounterGene.CAPBearing)), encounterGene.CAPVy, encounterGene.CAPGs*Math.sin(Math.toRadians(encounterGene.CAPBearing))));
//		Double3D ownshipCAP = ownship.getLocation().add(ownship.getVelocity().multiply(encounterGene.CAPT));
//		Double3D intruderCAP =ownshipCAP.add(new Double3D(encounterGene.CAPR*Math.cos(Math.toRadians(encounterGene.CAPTheta)), encounterGene.CAPY, encounterGene.CAPR*Math.sin(Math.toRadians(encounterGene.CAPTheta))));
//		Double3D location=intruderCAP.add(intruderVelocity.getVelocity().negate().multiply(encounterGene.CAPT));			
//		Double3D intruderTargetLoc = location.add(intruderVelocity.getVelocity().multiply(3*encounterGene.CAPT));
//		Target intruderTarget =  new Target(state.getNewID(),intruderTargetLoc);
//		UASPerformance intruderPerformance = new UASPerformance(globalConfig.stdDevX, globalConfig.stdDevY,globalConfig.stdDevZ,
//				globalConfig.maxGS, globalConfig.minGS, globalConfig.maxVS, globalConfig.minVS);
//		
//		UAS intruder = new UAS(state.getNewID(),location, intruderVelocity,intruderTarget, intruderPerformance);
//		intruder.setAlias(intruderAlias);		
//		
//		
//		AutoPilot ap= new AutoPilot(state, intruder,Configuration.getInstance().globalConfig.whiteNoiseEnabler,"WhiteNoise");
//		SelfSeparationAlgorithm ssa;
//		ssa= new RVO2_3D(state, intruder);
//		
//		intruder.init(ap,ssa);
//		intruder.setSchedulable(true);
//		
//		return intruder;	
//	}

}
