package search.random;

import java.io.IOException;

import modeling.SAAModel;
import modeling.SimInitializer;
import modeling.observer.CollisionDetector;
import modeling.uas.Proximity;
import modeling.uas.UAS;
import configuration.GlobalConfig;
import ec.util.MersenneTwisterFast;

public class RandomSearch {	
	
	public static void evaluate(Encounter encounter)
	{		
		SAAModel simState= new SAAModel(785945568, false); 	
		simState.reset();//reset the simulation. Very important!
    	SimInitializer.generateSimulation(simState, encounter.genes);   
//    	System.out.println(encounter);
    	if(!isProper(simState))
        {
        	 encounter.objectives[0]= 0;
        	 encounter.objectives[1]= 0;
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
		CollisionDetector collisionDetector = (CollisionDetector)simState.observerBag.get(1); 		
		Proximity proximity = ownship.getMinProximity();
//*****************************************
		int x=Encounter.maxNumGenes;
		encounter.objectives[0]= 1.0-(encounter.genes.length-1)*1.0/(x-1);   
		if(collisionDetector.hasAccident())
		{
			encounter.objectives[1]=1.0;
			System.out.println("accident"+"  "+proximity);
		}
    	else
    	{	
    		encounter.objectives[1] = 1.0/(1+ proximity.toValue());
    	}
		
		simState.finish();
	  
	}
	

	public static boolean isProper(SAAModel simState)
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

	public static void main(String[] args) throws IOException {	
		
		long seed = System.currentTimeMillis();//97846789,194679667,249719121, 567971664, 946163716
		MersenneTwisterFast rdn = new MersenneTwisterFast(seed);
    		
		int targetNumEncounters=10;
		int numSamplePoints=100000;
		
		int requiredEncounterCount=0;
		int sampleCount=0;
		long startTime = System.currentTimeMillis();		
		do
		{			
			Encounter encounter=new Encounter(rdn);
			evaluate(encounter);
			if(encounter.objectives[1]==1.0)
			{
				++requiredEncounterCount;
				System.out.println(encounter.genes.length+"  "+encounter);	
				System.out.println("Objective0: "+encounter.objectives[0]+"  objective1: "+encounter.objectives[1]+"\n");
			}
			++sampleCount;
		} while(sampleCount<numSamplePoints);//while(requiredEncounterCount<targetNumEncounters);
		long endTime = System.currentTimeMillis();
		System.out.println("Total search time: "+ (endTime-startTime)/1000+"s");
		System.out.println(requiredEncounterCount+" required encounters were found.");
	}

}
