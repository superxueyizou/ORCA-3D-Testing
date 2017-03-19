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

import modeling.SAAModel;
import modeling.env.Constants;
import modeling.env.Entity;
import modeling.env.Target;
import modeling.env.Waypoint;
import saa.AutoPilot;
import saa.selfseparation.SelfSeparationAlgorithm;
import sim.engine.SimState;
import sim.util.Double2D;
import sim.util.Double3D;

/**
 *
 * @author Xueyi Zou
 */
public class UAS extends Entity
{
	private static final long serialVersionUID = 1L;
	
	private String alias;
	
	//parameters for subsystems	
	private SelfSeparationAlgorithm ssa;
	private AutoPilot ap;

	//parameters for UAS movement	
	private Double3D oldLocation;
	private Double3D location;
	private UASVelocity oldUASVelocity;		
	private UASVelocity UASVelocity;		

	//the set performance for the uas.
	private UASPerformance uasPerformance;	
	
	//parameters for navigation
	private Waypoint nextWp;
	private Waypoint apTargetWp = null;//for auto-pilot
	private Target target;
	
/*************************************************************************************************/
	//parameters for recording information about simulation
	private Proximity tempProximity = new Proximity(Double.MAX_VALUE,Double.MAX_VALUE); //records the closest distance to danger in each step
	private Proximity minProximity = new Proximity(Double.MAX_VALUE,Double.MAX_VALUE);//records the closest distance to danger experienced by the uas
	
/*************************************************************************************************/

	public int activeState;	//-1:collision, 0:active, 1:arrived target

	private SAAModel state;	


	public UAS(int idNo, Double3D location, UASVelocity uasVelocity, Target target, UASPerformance uasPerformance)
	{
		super(idNo, Constants.EntityType.TUAS);

		this.location=location;
		this.uasPerformance = uasPerformance;
		this.UASVelocity = uasVelocity; 
		this.target=target;
		
		this.oldUASVelocity= uasVelocity;
		this.oldLocation= location;
		
		nextWp=null;
		this.activeState=0;
	}
	
	public void init(AutoPilot ap,SelfSeparationAlgorithm ssa)
	{
		this.ap = ap;
		this.ssa = ssa;
		
	}
	

	@Override
	public void step(SimState simState)
	{
		state = (SAAModel) simState;		
		if(this.activeState == 0)
		{				
			if(apTargetWp != null)
			{
				Double3D diff = apTargetWp.getLocation().subtract(this.location);
				Double2D velH=new Double2D(diff.x, diff.z);
				double gs= Math.sqrt(diff.x*diff.x + diff.z*diff.z);
				double velV = diff.y;
	
				if(gs>this.uasPerformance.getMaxGS())
				{
					velH=velH.resize(this.uasPerformance.getMaxGS());
				}
				if(velV>this.uasPerformance.getMaxVS())
				{
					velV=this.uasPerformance.getMaxVS();
				}
				if(velV<this.uasPerformance.getMinVS())
				{
					velV=this.uasPerformance.getMinVS();
				}
				nextWp = apTargetWp;
				nextWp.setLocation(this.location.add(new Double3D(velH.x, velV, velH.y)));
				state.environment3D.setObjectLocation(nextWp, nextWp.getLocation());
		
				this.setOldLocation(this.location);
				this.setLocation(nextWp.getLocation());
				state.environment3D.setObjectLocation(this, this.location);		
			}			
			else
			{
				System.out.println("approaching the destination (impossible)!");
			}
			
			if(location.distanceSq(target.getLocation())<=5*5)
			{
				this.activeState=1;
			}
			
		}	
		
    }


//**************************************************************************
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	
	public AutoPilot getAp() {
		return ap;
	}

	public void setAp(AutoPilot ap) {
		this.ap = ap;
	}
	
	public SelfSeparationAlgorithm getSsa() {
		return ssa;
	}

	public void setSsa(SelfSeparationAlgorithm ss) {
		this.ssa = ss;
	}
	
	public UASPerformance getUasPerformance() {
		return uasPerformance;
	}

	public void setUasPerformance(UASPerformance performance) {
		this.uasPerformance = performance;
	}
	
	public Double3D getOldVelocity() {
		return oldUASVelocity.getVelocity();
	}
	public void setOldVelocity(Double3D velocity) {
		oldUASVelocity.setVelocity(velocity);
	}

	public Double3D getVelocity() {
		return UASVelocity.getVelocity();
	}
	public void setVelocity(Double3D velocity) {
		UASVelocity.setVelocity(velocity);
	}

	public Double3D getOldLocation() {
		return oldLocation;
	}

	public void setOldLocation(Double3D oldLocation) {
		this.oldLocation = oldLocation;
	}
	
	public Double3D getLocation() {
		return location;
	}

	public void setLocation(Double3D location) {
		this.location = location;
	}

	public Proximity getTempProximity() {
		return tempProximity;
	}

	public void setTempProximity(Proximity tempProximity) {
		this.tempProximity = tempProximity;
	}

	public Proximity getMinProximity() {
		return minProximity;
	}

	public void setMinProximity(Proximity minProximity) {
		this.minProximity = minProximity;
	}

	public void setApTargetWp(Waypoint apWp) {
		this.apTargetWp = apWp;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

}
