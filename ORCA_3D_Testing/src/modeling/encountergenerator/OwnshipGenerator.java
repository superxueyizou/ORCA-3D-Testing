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
import sim.util.Double3D;
import configuration.Configuration;
import configuration.GlobalConfig;

/**
 * @author Xueyi
 *
 */
public class OwnshipGenerator
{
	private SAAModel state;
	private String ownshipAlias;
	GlobalConfig globalConfig = Configuration.getInstance().globalConfig;
	private double uasX=-0.1*globalConfig.worldX;
	private double uasY=0;
	private double uasZ=0;
	private double ownshipVy;
	private double ownshipGs;
		
	public OwnshipGenerator(SAAModel state, String ownshipAlias, double ownshipVy, double ownshipGs) 
	{		
		this.state=state;
		this.ownshipAlias=ownshipAlias;		
		this.ownshipVy=ownshipVy;
		this.ownshipGs=ownshipGs;
	}
	
	public UAS execute()
	{		
		Double3D location = new Double3D(uasX,uasY,uasZ);
		UASVelocity uasVelocity = new UASVelocity(new Double3D(ownshipGs,ownshipVy,0));
		Double3D targetLoc = location.add(uasVelocity.getVelocity().multiply(6*globalConfig.alertTime));
		Target target= new Target(state.getNewID(),targetLoc);
		UASPerformance uasPerformance = new UASPerformance(globalConfig.stdDevX, globalConfig.stdDevY,globalConfig.stdDevZ,
				globalConfig.maxGS, globalConfig.minGS, globalConfig.maxVS, globalConfig.minVS);
		
		UAS ownship = new UAS(state.getNewID(),location, uasVelocity,target,uasPerformance);
		ownship.setAlias(ownshipAlias);
		
		AutoPilot ap= new AutoPilot(state, ownship,Configuration.getInstance().globalConfig.whiteNoiseEnabler, "WhiteNoise");
		
		
		SelfSeparationAlgorithm ssa;
		ssa= new RVO2_3D(state, ownship);		
		
		ownship.init(ap,ssa);	
		ownship.setSchedulable(true);
		
		return ownship;
		
	}
}
