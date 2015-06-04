/**
 * 
 */
package saa.selfseparation;

import configuration.Configuration;
import modeling.SAAModel;
import modeling.observer.CollisionDetector;
import modeling.uas.UAS;
import rvo2.RVO2;
import sim.engine.SimState;
import sim.util.Double3D;


/**
 * @author Xueyi
 *
 */
public class RVO2_3D extends SelfSeparationAlgorithm
{
	private static final long serialVersionUID = 1L;
	
	private static double guassianStd = 0.05;
	private static double safetyBuffer =1.05;
	
	private SAAModel state; 
	private UAS hostUAS;
	private RVO2 rvo2Sim;
	
	public RVO2_3D(SimState simstate, UAS uas) 
	{
		state = (SAAModel) simstate;
		hostUAS = uas;
		rvo2Sim = new RVO2(1.0);
		//radius,neighborDist, maxNeighbors, timeHorizon, maxSpeed
		rvo2Sim.setOwnshipDefaults(CollisionDetector.thresholdV*safetyBuffer, 200, 10, Configuration.getInstance().globalConfig.alertTime, 
				Math.sqrt(Math.pow(hostUAS.getUasPerformance().getMaxGS(),2) + Math.pow(hostUAS.getUasPerformance().getMaxVS(),2)));
	}
	
/******************************************************************************************************************************************/
	
	@Override
	public void step(SimState simState)
	{
		if(hostUAS.activeState ==0 && Configuration.getInstance().globalConfig.selfSeparationEnabler)
		{	
			execute();			
		}		 
	}	
	
	public void execute()
	{
		rvo2Sim.reset();		
		Double3D ownshipprefVelocity= hostUAS.getTarget().getLocation().subtract(hostUAS.getLocation()).resize(Configuration.getInstance().ownshipConfig.ownshipGs);
		rvo2Sim.addOwnship(hostUAS.getLocation(), hostUAS.getVelocity(),ownshipprefVelocity);
	
		/* Add agents, specifying their start position, and store their goals on the opposite side of the environment. */
		for (int i = 0; i < state.uasBag.size(); ++i)
		{
			UAS uas=(UAS) state.uasBag.get(i);
			if(hostUAS.getID()==uas.getID())
			{
				continue;
			}
			double locX = uas.getLocation().x*(1+state.random.nextGaussian()*guassianStd);
			double locY = uas.getLocation().y*(1+state.random.nextGaussian()*guassianStd);
			double locZ = uas.getLocation().z*(1+state.random.nextGaussian()*guassianStd);
			
			double velX = uas.getVelocity().x*(1+state.random.nextGaussian()*guassianStd);
			double velY = uas.getVelocity().y*(1+state.random.nextGaussian()*guassianStd);
			double velZ = uas.getVelocity().z*(1+state.random.nextGaussian()*guassianStd);
						
			rvo2Sim.addIntruder(new Double3D(locX,locY,locZ), new Double3D(velX,velY,velZ), CollisionDetector.thresholdV*safetyBuffer);	
			
//			rvo2Sim.addIntruder(uas.getLocation(), uas.getVelocity(), CollisionDetector.thresholdV*safetyBuffer);
		}
		Double3D newVelocity=rvo2Sim.resolve();					
		hostUAS.getAp().setSSVelocity(newVelocity);;	
		
	}
	
}
