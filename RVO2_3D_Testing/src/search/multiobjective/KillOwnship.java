/**
 * 
 */
package search.multiobjective;

import modeling.SAAModel;
import modeling.SimInitializer;
import modeling.observer.CollisionDetector;
import modeling.observer.ProximityMeasurer;
import modeling.uas.Proximity;
import modeling.uas.UAS;
import configuration.GlobalConfig;
import ec.EvolutionState;
import ec.Individual;
import ec.Problem;
import ec.multiobjective.MultiObjectiveFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;
import ec.vector.Gene;
import ec.vector.GeneVectorIndividual;

/**
 * @author Xueyi Zou
 *
 */
public class KillOwnship extends Problem implements SimpleProblemForm 
{
	private static final long serialVersionUID = 1L;	

	/* (non-Javadoc)
	 * @see ec.simple.SimpleProblemForm#evaluate(ec.EvolutionState, ec.Individual, int, int)
	 */
	@Override
	public void evaluate(EvolutionState state, Individual ind, int subpopulation, int threadnum) 
	{

		if (ind.evaluated) return;

        if (!(ind instanceof GeneVectorIndividual))
            state.output.fatal("Whoa!  It's not a GeneVectorIndividual!!!",null);        
      
        GeneVectorIndividual ind2 = (GeneVectorIndividual)ind;
        Gene[] genes = ind2.genome;
        
        if (!(ind2.fitness instanceof MultiObjectiveFitness))
            state.output.fatal("Whoa!  It's not a MultiObjectiveFitness!!!",null);
        
        double[] objectives = ((MultiObjectiveFitness)ind.fitness).getObjectives();
 
//*****************************************        
		SAAModel simState= new SAAModel(785945568, false); 	
		simState.reset();//reset the simulation. Very important!
    	SimInitializer.generateSimulation(simState, genes);   

    	if(!isProper(simState))
        {
        	 objectives[0]= 0;
             objectives[1]= 0;
             ((MultiObjectiveFitness)ind.fitness).setObjectives(state, objectives);
        	 ind2.evaluated = true;
        	 return;
        }
		
		simState.start();	
		do
		{
			if (!simState.schedule.step(simState))
			{
				break;
			}
		} while(simState.schedule.getSteps()< 100);	

		UAS ownship = (UAS)simState.uasBag.get(0);
	
		
		ProximityMeasurer proximityMeasurer = (ProximityMeasurer)simState.observerBag.get(0);
		CollisionDetector collisionDetector = (CollisionDetector)simState.observerBag.get(1);
		
		Proximity[] minProximities=proximityMeasurer.minProximities;
		Proximity p=minProximities[0];
		for(int i=1; i<minProximities.length;++i)
		{
			if(minProximities[i].lessThan(p))
			{
				p=minProximities[i];
			}
		}	
 
		simState.finish();
//*****************************************
		int x=state.parameters.getInt(new Parameter("pop.subpop.0.species.max-initial-size"), null);
        objectives[0]= 1.0-(genes.length-1)*1.0/(x-1);
       
    	if(collisionDetector.hasAccident())
		{
    		objectives[1]=1.0;
		}
    	else
    	{
    		Proximity proximity = ownship.getMinProximity();	
//    		System.out.println(p);
//    		System.out.println(proximity);
//    		System.out.println();
    		objectives[1] = 1.0/(1+ proximity.toValue());
    	}
    	
      
        ((MultiObjectiveFitness)ind.fitness).setObjectives(state, objectives);
        ind2.evaluated = true;
	}
	
//****************************************************************************************  	

	public boolean isProper(SAAModel simState)
	{	
		UAS ownship = (UAS)simState.uasBag.get(0);
		UAS uas;
	    for(int i=1; i<simState.uasBag.size(); ++i)// i=0 to exclude ownship
		{		    	
			uas= (UAS)simState.uasBag.get(i);							
			if(ownship.getLocation().distanceSq(uas.getLocation())<GlobalConfig.PROPERDISTANCE*GlobalConfig.PROPERDISTANCE)
			{
				return false;
			}		
		}	    
	       
		return true;
	}

}
