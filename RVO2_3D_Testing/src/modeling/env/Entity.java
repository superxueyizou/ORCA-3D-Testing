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
package modeling.env;
import modeling.env.Constants.EntityType;
import sim.engine.*;
import sim.util.*;

/**
 * The class from which every object used in simulations is created, contains only
 * the required methods for identifying more about the object.
 * 
 * @author Robert Lee
 */
public class Entity implements Steppable
{
	private static final long serialVersionUID = 1L;
	
	protected final int ID;
	public final EntityType type;	
	protected Double3D location = new Double3D(0,0,0);
	protected boolean isSchedulable;
	
	
	public Entity(int idNo, EntityType type)
	{
		ID = idNo;
		this.type = type;
	}
	
	
	public void step(SimState state)
	{
		//do nothing - this will be overwritten in the child classes - if required
	}
	
	/**
	 * A method which sets the stored location of the object to a provided coordinate
	 * 
	 * @param location the provided location to be stored in the Entity
	 */

	public Double3D getLocation() {
		return location;
	}


	public void setLocation(Double3D location) {
		this.location = location;
	}


	public boolean isSchedulable() {
		return isSchedulable;
	}
	
	public void setSchedulable(boolean isSchedulable) {
		this.isSchedulable = isSchedulable;
	}
	
	public EntityType getType() {return type;}
	public int getID() {return ID;}

}
