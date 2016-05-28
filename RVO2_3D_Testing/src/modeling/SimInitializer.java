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
package modeling;

import java.util.HashMap;

import modeling.encountergenerator.EncounterGenerationFactory;
import modeling.encountergenerator.OwnshipGenerator;
import modeling.observer.CollisionDetector;
import modeling.observer.ProximityMeasurer;
import modeling.uas.UAS;
import search.random.EncounterGeneRandom;
import sim.util.Double3D;
import configuration.Configuration;
import configuration.EncounterConfig;
import ec.vector.Gene;
/**
 *
 * @author Xueyi Zou
 * This class is used to build/initialize the simulation.
 * Called for by SAAModelWithUI class
 */
public class SimInitializer
{	
	public static Configuration config= Configuration.getInstance();
	
	public static void generateSimulation(SAAModel state)
	{	
		UAS ownship = new OwnshipGenerator(state,"ownship",config.ownshipConfig.ownshipVy, config.ownshipConfig.ownshipGs).execute();
		state.uasBag.add(ownship);
		state.allEntities.add(ownship);
		
		for(String intruderAlias: config.encountersConfig.keySet())
		{
			UAS intruder=EncounterGenerationFactory.generateIntruder(state, ownship, intruderAlias,config.encountersConfig.get(intruderAlias));
			state.uasBag.add(intruder);
			state.allEntities.add(intruder);
		}		

	    ProximityMeasurer pMeasurer= new ProximityMeasurer(state, state.getNewID(), new Double3D());
	    CollisionDetector aDetector= new CollisionDetector(state.getNewID(), new Double3D());
	    state.observerBag.add(pMeasurer);
	    state.observerBag.add(aDetector);// index is 1
	    
	    state.allEntities.add(pMeasurer);
	    state.allEntities.add(aDetector);
			
	}
	
	
	public static void generateSimulation(SAAModel state, Gene[] genes)
	{	
		Genes2Config(genes);
		generateSimulation(state);
	}
	
	public static void Genes2Config(Gene[] genes)
	{
		HashMap<String,EncounterConfig> encountersConfig = config.encountersConfig;
		encountersConfig.clear();
		for (int i=0; i<genes.length; ++i) 
    	{
			EncounterConfig encounterConfig = new EncounterConfig((EncounterGeneRandom)genes[i]);
			encountersConfig.put("intruder"+i, encounterConfig);
    	}
	}
	
}
