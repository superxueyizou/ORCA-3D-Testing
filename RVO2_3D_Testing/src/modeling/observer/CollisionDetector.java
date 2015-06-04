package modeling.observer;

import modeling.SAAModel;
import modeling.env.Constants;
import modeling.env.Entity;
import modeling.uas.UAS;
import sim.engine.SimState;
import sim.util.Double3D;
import configuration.Configuration;

/**
 * @author xueyi
 *
 */
public class CollisionDetector extends Entity
{
	private static final long serialVersionUID = 1L;
	
	public static final double thresholdH=10;
	public static final double thresholdV=10;
	
	private int numCollisions=0;	
	
	public CollisionDetector(int idNo, Double3D location)
	{
		super(idNo, Constants.EntityType.TObserver);
		this.location=location;
	
	}

	@Override
	public void step(SimState simState)
	{
		if(!Configuration.getInstance().globalConfig.accidentDetectorEnabler)
		{
			return;
		}
		SAAModel state = (SAAModel)simState;		
		
		UAS ownship=(UAS) state.uasBag.get(0);
		if(ownship.activeState!=0)
		{
			return;
		}

		for (int k = 1; k<state.uasBag.size(); ++k)
		{
			UAS intruder=(UAS)state.uasBag.get(k);
			if(intruder.activeState!=0)
			{
				continue;
			}
			if (detectCollisionBetweenUAS(ownship, intruder))
			{
				numCollisions++;
				ownship.activeState=-1;
				intruder.activeState=-1;
				break;
			}
		}

		dealWithTermination(state);		
	}

	
	private boolean detectCollisionBetweenUAS(UAS uas1, UAS uas2)
	{	
		double deltaHori=Math.pow((uas1.getLocation().x-uas2.getLocation().x),2)+Math.pow((uas1.getLocation().z-uas2.getLocation().z),2);
		double deltaVert=Math.abs(uas1.getLocation().y-uas2.getLocation().y);	
//		System.out.println(deltaHori+"  "+deltaVert);
		return (deltaHori<=thresholdH*thresholdH)&&(deltaVert<=thresholdV);		
	}
	
	
    public void dealWithTermination(SAAModel state)
	{
    	int numActiveAgents =0;
    	for(Object o: state.uasBag)
    	{
    		if(((UAS)o).activeState==0)
    		{
    			numActiveAgents++;
    		}
    		
    	}
    	
		if(numActiveAgents < 1)
		{
			state.schedule.clear();
			state.kill();
		}
	 }

	public int getNumCollisions() 
	{
		return numCollisions;
	}
	
	public boolean hasAccident()
	{
		return (numCollisions>0);
	}

}
