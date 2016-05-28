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
package modeling.uas;

import sim.util.Double3D;

public class UASVelocity 
{
	private Double3D velocity = new Double3D(0,0,0);
	
	public UASVelocity(Double3D velocity) 
	{
		super();
		this.velocity = velocity;
	}

	public UASVelocity(double vx, double vy, double vz) 
	{
		super();
		this.velocity = new Double3D(vx,vy,vz);

	}
	
	public Double3D getVelocity() 
	{
		return velocity;
	}

	public void setVelocity(Double3D velocity) 
	{
		this.velocity = velocity;	
	}
	
	public void setVelocity(double vx, double vy, double vz) 
	{
		this.velocity = new Double3D(vx,vy,vz);
	}
	
	

}
