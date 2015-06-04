package modeling.observer;

import modeling.SAAModel;
import modeling.env.Constants;
import modeling.env.Entity;
import modeling.uas.Proximity;
import modeling.uas.UAS;
import sim.engine.SimState;
import sim.util.Double3D;


public class ProximityMeasurer extends Entity
{
	private static final long serialVersionUID = 1L;
	public Proximity[] minProximities;
	private SAAModel state;
	
	public ProximityMeasurer(SimState simState, int idNo, Double3D location)
	{
		super(idNo, Constants.EntityType.TObserver);
		state = (SAAModel)simState;
		this.location=location;	
		minProximities=new Proximity[state.uasBag.size()-1];		
		for(int i=0; i<state.uasBag.size()-1; ++i)
		{
			minProximities[i]=new Proximity(Double.MAX_VALUE,Double.MAX_VALUE);
		}
	}
	
	@Override
	public void step(SimState simState) 
	{
		SAAModel state = (SAAModel)simState;	
		
		for(int i=0; i<state.uasBag.size(); ++i)
		{
			UAS uas1=(UAS) state.uasBag.get(i);
			if(uas1.activeState!=0)
			{
				continue;
			}
			Double3D uas1Loc=uas1.getLocation();
			
			Proximity tempP=new Proximity(Double.MAX_VALUE,Double.MAX_VALUE);
			for(int j=0; j<state.uasBag.size(); ++j)//loop all the intruders
			{		    	
				UAS uas2= (UAS)state.uasBag.get(j);
				if(uas2.activeState!=0||uas1==uas2)
				{
					continue;
				}
				Double3D uas2Loc=uas2.getLocation();			
				Proximity p= new Proximity(uas1Loc,uas2Loc);
		    	if(p.lessThan(tempP))
		    	{
		    		tempP=p;	    		
		    	}	    
			}
		    uas1.setTempProximity(tempP);
			if (tempP.lessThan(uas1.getMinProximity()))
			{
				uas1.setMinProximity(tempP);	
			}		
		}		
		
		
		UAS ownship=(UAS) state.uasBag.get(0);
		Double3D ownshipLoc=ownship.getLocation();	

		for(int j=1; j<state.uasBag.size(); ++j)//loop all the intruders
		{		    	
			UAS intruder= (UAS)state.uasBag.get(j);
			if(intruder.activeState!=0)
			{
				continue;
			}
			Double3D intruderLoc=intruder.getLocation();			
			Proximity p= new Proximity(ownshipLoc,intruderLoc);
	    	if(p.lessThan(minProximities[j-1]))
	    	{
	    		p.minTimeStep=(int)state.schedule.getSteps();
	    		minProximities[j-1]=p;	    
	    	}	    
		}

	}	
	
//	@Override
//	public void step(SimState simState) 
//	{
//		SAAModel state = (SAAModel)simState;	
//		for(int i=0; i<state.uasBag.size(); ++i)
//		{
//			UAS uas1=(UAS) state.uasBag.get(i);
//			Double3D uas1Loc=uas1.getLocation();
//			
//			Proximity tempP=new Proximity(Double.MAX_VALUE,Double.MAX_VALUE);
//			for(int j=0; j<state.uasBag.size(); ++j)//loop all the intruders
//			{		    	
//				UAS uas2= (UAS)state.uasBag.get(j);
//				if(uas2.activeState!=0||uas1==uas2)
//				{
//					continue;
//				}
//				Double3D uas2Loc=uas2.getLocation();			
//				Proximity p= new Proximity(uas1Loc,uas2Loc);
//		    	if(p.lessThan(tempP))
//		    	{
//		    		tempP=p;	    		
//		    	}	    
//			}
//		    uas1.setTempProximity(tempP);
//			if (tempP.lessThan(uas1.getMinProximity()))
//			{
//				uas1.setMinProximity(tempP);	
//			}		
//		}		
//	}	

}
