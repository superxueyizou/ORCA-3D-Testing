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

public class GlobalConfig 
{
	public double worldX = 1000; 
	public double worldY = 300; 
	public double worldZ = 1000; 
	
	public int alertTime=10;
	public static double PROPERDISTANCE=20;

	public boolean collisionAvoidanceEnabler=false;
	public boolean selfSeparationEnabler=true;
	public boolean accidentDetectorEnabler=true;
	public boolean sensorNoiseEnabler=false;
	public boolean sensorValueUncertainty=false;
	public boolean encounterGeneratorEnabler=true;
	public boolean whiteNoiseEnabler=false;	
	
	public static final double DEFAULT_OWNSHIPGS = 5;
	public static final double DEFAULT_OWNSHIPVY = 0;
	
	public double stdDevX =1;//[0,3]
	public double stdDevY =0.5;//[0,3]
	public double stdDevZ =1;//[0,3]
	
	public double maxGS =10;
	public double minGS =0;
	public double maxVS =2;
	public double minVS =-10;
}
