/*
  Copyright 2010 by Sean Luke and George Mason University
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/

package search.multiobjective;

import ec.BreedingPipeline;
import ec.EvolutionState;
import ec.Individual;
import ec.SelectionMethod;
import ec.util.Parameter;
import ec.vector.*;


public class GeneRemovalPipeline extends BreedingPipeline
{
	private static final long serialVersionUID = 1L;
	
	public static final String P_REMOVAL = "removal";
    public static final int NUM_SOURCES = 1;

    public Parameter defaultBase()
        {
        return VectorDefaults.base().push(P_REMOVAL);
        }

    public int numSources() { return NUM_SOURCES; }

    public int produce(int min, 
        int max, 
        int start, 
        int subpopulation,
        Individual[] inds, 
        EvolutionState state, 
        int thread) 
    {

        // grab individuals from our source and stick 'em right into inds.
        // we'll modify them from there
        int n = sources[0].produce(min,max,start,subpopulation,inds,state,thread);


        // should we bother?
        if (!state.random[thread].nextBoolean(likelihood))
            return reproduce(n, start, subpopulation, inds, state, thread, false);  // DON'T produce children from source -- we already did

        
        // now let's mutate 'em
        for(int q=start; q < n+start; q++)
        {
            if (sources[0] instanceof SelectionMethod)
                inds[q] = (Individual)(inds[q].clone());

            
            //duplicate from the genome between a random begin and end point,
            //and put that at the end of the new genome.
            VectorIndividual ind = (VectorIndividual)(inds[q]);
            
            int len = ind.genomeLength();

            //zero length individual, just return
            if (len <= 1)
            {
                return n;
            }
            
            int index = state.random[thread].nextInt(len);// the index of the removed gene
//            System.out.println(ind.genomeLength());
            if(index!=len-1)
            {
            	 Object[] splice = new Object[3];
                 ind.split(new int[] {index, index+1}, splice);
                 ind.join(new Object[] {splice[0], splice[1]});
            }
            else
            {
            	 Object[] splice = new Object[2];
                 ind.split(new int[] {index}, splice);
                 ind.join(new Object[] {splice[0]});
            }  
//            System.out.println(ind.genomeLength());
           
        }
        return n;  // number of individuals produced, 1 here.
    }

}
