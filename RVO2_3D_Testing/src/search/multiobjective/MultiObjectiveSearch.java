/**
 * 
 */
package search.multiobjective;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JOptionPane;

import configuration.Configuration;
import configuration.EncounterConfig;
import modeling.SAAModelWithUI;
import ec.EvolutionState;
import ec.Evolve;
import ec.Individual;
import ec.multiobjective.MultiObjectiveFitness;
import ec.util.Output;
import ec.util.ParameterDatabase;

/**
 * @author xueyi
 * simulation with GA as harness
 *
 */
public class MultiObjectiveSearch
{	
	static String problemName="KillOwnship";
	static File parameterFile= new File("src/search/multiobjective/"+problemName+".params");	
	
	public static void go() throws Exception
	{	
		ParameterDatabase dBase= new ParameterDatabase(parameterFile, new String[]{"-file", parameterFile.getCanonicalPath()});	
		ParameterDatabase child = new ParameterDatabase();
		child.addParent(dBase);		
		long startTime = System.currentTimeMillis();
		EvolutionState evaluatedState=GASearch(child);	
		long endTime = System.currentTimeMillis();
		System.out.println("Total search time: "+ (endTime-startTime)/1000+"s");					
		recur(evaluatedState);		
	}
		
	
	public static EvolutionState GASearch(ParameterDatabase child)
	{
		EvolutionState evaluatedState=null;
		Output out = Evolve.buildOutput();
					
		out.getLog(0).silent=false;//stdout
		out.getLog(1).silent=false;//stderr
		
		evaluatedState= Evolve.initialize(child, 0, out);
		evaluatedState.startFresh();
		int result=EvolutionState.R_NOTDONE;			
		int i=0;		
		while(result == EvolutionState.R_NOTDONE)
		{
			result=evaluatedState.evolve();
			evaluatedState.output.println("Generation "+i +" finished :)", 0);
			++i;
		}
		return evaluatedState;
	}
	
	public static void recur(EvolutionState evaluatedState)
	{
		Object[] options= new Object[]{"Recurrence","Close"};
		int confirmationResult = JOptionPane.showOptionDialog(null, "choose the next step", "What's next", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options, 0);
		
		if (confirmationResult == 0 )
		{
			Individual[] inds = getIndsInParetoFront(evaluatedState,1, false);
	
		    evaluatedState.output.println("\nRecurrenceWithGUI. There are "+inds.length + " individuals",0);	    
		    Evolve.cleanup(evaluatedState);	
			SAAModelWithUI.recur(inds);
		}		
		else
		{
			Evolve.cleanup(evaluatedState);	
		}
	}
	
	public static Individual[] getIndsInParetoFront(final EvolutionState state, final int sortBy, final boolean ascending)
	{
        // build front
        ArrayList<?> front = MultiObjectiveFitness.partitionIntoParetoFront(state.population.subpops[0].individuals, null, null);
        
        Collections.sort(front, new Comparator<Object>()
        {
            public boolean lt(Object a, Object b)
            {
                return (((MultiObjectiveFitness) (((Individual) a).fitness)).getObjective(sortBy) < 
                    (((MultiObjectiveFitness) ((Individual) b).fitness)).getObjective(sortBy));
            }
            
            public boolean gt(Object a, Object b)
            {
                return (((MultiObjectiveFitness) (((Individual) a).fitness)).getObjective(sortBy) > 
                    ((MultiObjectiveFitness) (((Individual) b).fitness)).getObjective(sortBy));
            }

			@Override
			public int compare(Object a, Object b) {
				if(lt(a,b)) return -1;
				else if(gt(a,b)) return +1;
				else return 0;
			}
        });
        
        if(!ascending)
        {
        	 Collections.reverse(front);
        }
        
        int logID=0;
        String frontIndFile = String.format("frontIndividuals%d.log", 679463479);
        try {
			logID=state.output.addLog(new File(frontIndFile), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
        Individual[] indsInParetoFront = new Individual[front.size()];
        state.output.println("Individuals in the Pareto Front: "+front.size(), logID);
        for(int i=0; i<front.size(); i++)
        {
        	indsInParetoFront[i] = (Individual) front.get(i);
        	state.output.println("number of intruders: "+indsInParetoFront[i].size(), logID);
        	indsInParetoFront[i].printIndividualForHumans(state, logID);
        	state.output.println("", logID);
        }
        
        return indsInParetoFront;
	                
	}
	
	public static void genomeString2Config(String genomeString)
	{
		genomeString = genomeString.trim();
		genomeString=genomeString.replaceAll("\\>", "");
		System.out.println(genomeString);
		String[] pArr= genomeString.split("\\s+");
		
		Configuration config = Configuration.getInstance();
 
		int numEncounters=pArr.length/7;
		for(int i=0; i<numEncounters; ++i)
		{
			EncounterConfig encounterConfig=new EncounterConfig();
			encounterConfig.CAPY=Double.parseDouble(pArr[i*7+0]);
			encounterConfig.CAPR=Double.parseDouble(pArr[i*7+1]);
			encounterConfig.CAPTheta=Double.parseDouble(pArr[i*7+2]);			
			encounterConfig.CAPVy=Double.parseDouble(pArr[i*7+3]);
			encounterConfig.CAPGs=Double.parseDouble(pArr[i*7+4]);
			encounterConfig.CAPBearing=Double.parseDouble(pArr[i*7+5]);
			encounterConfig.CAPT=Double.parseDouble(pArr[i*7+6]);
			config.encountersConfig.put("intruder"+i, encounterConfig);
		}		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		go();
	}
	
}
