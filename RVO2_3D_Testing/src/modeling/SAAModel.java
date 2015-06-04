package modeling;

import modeling.env.Entity;
import modeling.uas.UAS;
import sim.engine.SimState;
import sim.field.continuous.Continuous3D;
import sim.util.Bag;
import configuration.Configuration;

public class SAAModel extends SimState
{
	private static final long serialVersionUID = 1L;
	
	public static Configuration config= Configuration.getInstance();

	public boolean runningWithUI = false; 
	public Continuous3D environment3D=null;
	public Bag allEntities = null; // entities to load into the environment3D, important
	public Bag uasBag = null;	
	public Bag observerBag = null;	
    public String information="no information now"; 

    private int newID = 0;		

    /**
	 * @param seed for random number generator
	 * @param config configuration for the simulation
	 * @param UI pass true if the simulation is being run with a UI false if it is not.
	 */
	public SAAModel(long seed,boolean UI)
    {
		super(seed);		
		runningWithUI = UI;		
		environment3D = new Continuous3D(1.0, config.globalConfig.worldX, config.globalConfig.worldY, config.globalConfig.worldZ);
		allEntities=new Bag();
		uasBag=new Bag();
		observerBag=new Bag();        
	}    
		
	
	public void start()
	{
		super.start();	
		loadEnvironment();
		loadSchedule();				
	}
	
	public void finish()
	{
		super.finish();			

	}		

	/**
	 * A method which resets the variables for the SAAModel and also clears
	 * the schedule and environment3D of any entities, to be called between simulations.	 * 
	 * This method resets the newID counter so should NOT be called during a run.
	 * This method is called by SAAModelWithUI.start()
	 */
	public void reset()
	{
		newID = 0;
		uasBag.clear();
		observerBag.clear();
		allEntities.clear();
		schedule.reset();
		environment3D.clear(); //clear the environment3D
	}
		
	
	/**
	 * A method which provides a different number each time it is called, this is
	 * used to ensure that different entities are given different IDs
	 * 
	 * @return a unique ID number
	 */
	public int getNewID()
	{
		int t = newID;
		newID++;
		return t;
	}
	
	
	/**
	 * A method which adds all of the entities to the simulations environment3D.
	 */
	public void loadEnvironment()
	{
		for(int i = 0; i < allEntities.size(); i++)
		{
			Entity e =(Entity) allEntities.get(i);
			environment3D.setObjectLocation(e, e.getLocation());		
		}
	}
	
	
	/**
	 * A method which adds all the entities marked as requiring scheduling to the
	 * schedule for the simulation
	 */
	public void loadSchedule()
	{
		//loop across all items in toSchedule and add them all to the schedule
		int count = 0;	
		
		if (config.globalConfig.selfSeparationEnabler)
		{
			for(int i = 0; i < uasBag.size(); ++i, ++count)
			{
				schedule.scheduleRepeating(((UAS)uasBag.get(i)).getSsa(), count, 1.0);
			}	
			
		}		
		for(int i=0; i < uasBag.size(); ++i, ++count)
		{
			schedule.scheduleRepeating(((UAS)uasBag.get(i)).getAp(), count, 1.0);			
		}
		
		for(int i=0; i < uasBag.size(); ++i, ++count)
		{
			schedule.scheduleRepeating((Entity) uasBag.get(i), count, 1.0);
		}	
		
		for(int i=0; i < observerBag.size(); ++i, ++count)
		{
			schedule.scheduleRepeating((Entity) observerBag.get(i), count, 1.0);
		}	
			
	}


	public String getInformation() 
	{
		return information;
	} 

}
