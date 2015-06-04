package saa;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double2D;
import sim.util.Double3D;
import modeling.SAAModel;
import modeling.env.Waypoint;
import modeling.uas.UAS;
import modeling.uas.UASPerformance;

public class AutoPilot implements Steppable
{
	private static final long serialVersionUID = 1L;
	
	private SAAModel state; 
	private UAS hostUAS;
	
	private UASPerformance uasPerformance;
	private double SDX=0;
	private double SDY=0;
	private double SDZ=0;
	
	private String normativeMode=null;//normative manoeuvre normativeMode
	private Double3D ssVelocity=null; // new Velocity from self separation algorithm
	

	public AutoPilot(SimState simstate, UAS uas, Boolean enableWhiteNoise, String mode) 
	{
		state = (SAAModel)simstate;
		hostUAS = uas;	

		uasPerformance=hostUAS.getUasPerformance();
		if(enableWhiteNoise)
		{
			this.SDX=uasPerformance.getStdDevX();
			this.SDY=uasPerformance.getStdDevY();
			this.SDZ=uasPerformance.getStdDevZ();
		}		
		
		normativeMode=mode;
		ssVelocity=null;
	}


	public void step(SimState simState) 
	{
		if(hostUAS.activeState==0)
		{	
			if (ssVelocity!=null)
			{
				hostUAS.setApTargetWp(executeVelocity(ssVelocity));
			}
			else if(normativeMode=="WhiteNoise")
			{
				hostUAS.setApTargetWp(executeWhiteNoise());
			}
			else if(normativeMode=="Specific")
			{
				double ay=SDY * state.random.nextGaussian()*(state.random.nextBoolean()?1:-1);
				hostUAS.setApTargetWp(executeSpecific(ay));
			}	
			else
			{
				System.err.println("Something wrong with public class AutoPilot implements Steppable/public void step(SimState simState) ");
			}
			
		}		
	}

	public Waypoint executeVelocity(Double3D ssVelocity)
	{		
		Waypoint wp = new Waypoint(state.getNewID());		
		hostUAS.setOldVelocity(hostUAS.getVelocity());
		hostUAS.setVelocity(ssVelocity);
		wp.setLocation(hostUAS.getLocation().add(ssVelocity));		
		return wp;
	}
	
	public Waypoint executeWhiteNoise()
	{		
		Waypoint wp = new Waypoint(state.getNewID());
		double vx=hostUAS.getVelocity().x;
		double vy=hostUAS.getVelocity().y;
		double vz=hostUAS.getVelocity().z;
		double ax = SDX * state.random.nextGaussian();
		double ay = SDY * state.random.nextGaussian();
		double az = SDZ * state.random.nextGaussian();
		Double2D groundVelocity = new Double2D(vx+ax,vz+az);
		if(groundVelocity.length()>uasPerformance.getMaxGS())
		{
			groundVelocity= groundVelocity.resize(uasPerformance.getMaxGS());
		}
		else if(groundVelocity.length()<uasPerformance.getMinGS())
		{
			groundVelocity= groundVelocity.resize(uasPerformance.getMinGS());
		}
		
		double newVy=vy+ay;
		if(newVy>uasPerformance.getMaxVS())
		{
			newVy=uasPerformance.getMaxVS();
		}
		else if(newVy<uasPerformance.getMinVS())
		{
			newVy=uasPerformance.getMinVS();
		}
		
		double x= hostUAS.getLocation().x + 0.5*(vx+groundVelocity.x);				
		double y= hostUAS.getLocation().y + 0.5*(vy + newVy);
		double z= hostUAS.getLocation().z + 0.5*(vz+groundVelocity.y);
		hostUAS.setOldVelocity(new Double3D(vx,vy,vz));
		hostUAS.setVelocity(new Double3D(groundVelocity.x, newVy,groundVelocity.y));
		wp.setLocation(new Double3D(x , y,z));		
		return wp;
	}

	public Waypoint executeSpecific(double ay)
	{
		Waypoint wp = new Waypoint(state.getNewID());
		double vx=hostUAS.getVelocity().x;
		double vy=hostUAS.getVelocity().y;	
		double vz=hostUAS.getVelocity().z;	
		if(vy+ay>uasPerformance.getMaxVS())
		{
			ay=uasPerformance.getMaxVS()-vy;
		}
		else if(vy+ay<uasPerformance.getMinVS())
		{
			ay=uasPerformance.getMinVS()-vy;
		}
		
		double x = hostUAS.getLocation().x + vx;				
		double y= hostUAS.getLocation().y + vy + 0.5*ay;
		double z= hostUAS.getLocation().z;
		hostUAS.setOldVelocity(new Double3D(vx,vy,vz));
		hostUAS.setVelocity(new Double3D(vx, vy+ay, vz));
		wp.setLocation(new Double3D(x , y, z));		
		return wp;
	}
	
	public String getNormativeMode() {
		return normativeMode;
	}


	public void setNormativeMode(String normativeMode) {
		this.normativeMode = normativeMode;
	}
	
	public Double3D getSSVelocity() {
		return ssVelocity;
	}


	public void setSSVelocity(Double3D ssVelocity) {
		this.ssVelocity = ssVelocity;
	}
}
