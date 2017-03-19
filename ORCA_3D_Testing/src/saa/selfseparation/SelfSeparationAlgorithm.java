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
/**
 * 
 */
package saa.selfseparation;

import sim.engine.SimState;
import sim.engine.Steppable;

/**
 * @author Xueyi
 *
 */
public abstract class SelfSeparationAlgorithm implements Steppable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SelfSeparationAlgorithm() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public abstract void execute();
	
	@Override
	public abstract void step(SimState simState);

}
