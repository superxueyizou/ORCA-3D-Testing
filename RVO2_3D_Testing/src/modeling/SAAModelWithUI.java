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

import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.swing.JFrame;
import javax.vecmath.Color3f;

import modeling.env.Waypoint;
import modeling.uas.UAS;
import sim.display.Console;
import sim.display.Controller;
import sim.display.GUIState;
import sim.display3d.Display3D;
import sim.engine.SimState;
import sim.portrayal.Inspector;
import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import sim.portrayal3d.simple.SpherePortrayal3D;
import sim.portrayal3d.simple.WireFrameBoxPortrayal3D;
import configuration.Configuration;
import configuration.SAAConfigurator;
import ec.Individual;
import ec.vector.Gene;
import ec.vector.GeneVectorIndividual;

/**
 * A class for running a simulation with a UI, run to see a simulation with a UI
 * showing it running.
 * 
 * @author Xueyi Zou
 */
public class SAAModelWithUI extends GUIState
{	
	public static Configuration config= Configuration.getInstance();
	
	private static Gene[] genes=null;	
	private static Individual[] inds=null;
	private static int currentInd=0;
	
	public Display3D display3D;
	public JFrame display3DFrame;	
	ContinuousPortrayal3D environment3DPortrayal = new ContinuousPortrayal3D();
	WireFrameBoxPortrayal3D wireFrameP = new WireFrameBoxPortrayal3D(-0.4*config.globalConfig.worldX,-0.5*config.globalConfig.worldY,-0.5*config.globalConfig.worldZ,  0.6*config.globalConfig.worldX,0.5*config.globalConfig.worldY,0.5*config.globalConfig.worldZ);
   
	SpherePortrayal3D uasPortrayal=null;
	SpherePortrayal3D wpPortrayal=null;
	
    public SAAModelWithUI() 
    {   
        super(new SAAModel(785945568,true)); 	
    }
  
    
    public void init(Controller c)
    {
        super.init(c);     
        // make the 3D display
        display3D = new Display3D(config.globalConfig.worldX,config.globalConfig.worldY,this);           
        display3D.scale(1.2 / config.globalConfig.worldX);        
        
        display3DFrame = display3D.createFrame();
        display3DFrame.setTitle("SAA Simulation");
        c.registerFrame(display3DFrame);   // register the frame so it appears in the "Display" list        
        display3DFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        display3DFrame.setVisible(true);
        
		//adding the different layers to the display3D
        display3D.attach(environment3DPortrayal, "Environment3D" );
        display3D.attach(wireFrameP, "WireFrame");
        
        uasPortrayal = new SpherePortrayal3D(Color.CYAN, 10, 10)
        {
			public javax.media.j3d.TransformGroup getModel(java.lang.Object obj, javax.media.j3d.TransformGroup j3dModel)
			{
				Color3f col;
				UAS uas = (UAS)obj;
								
				if(uas.activeState==-1)
				{
					col=new Color3f(255,0,0);		
				}
				else if(uas.activeState==1)
				{
					col=new Color3f(96,96,96);	
					if(uas.getAlias()=="ownship")
					{
						col=new Color3f(255,255,0);		
					}
					
				}
				else
				{
					col=new Color3f(0,255,255);	
					if(uas.getAlias()=="ownship")
					{
						col=new Color3f(255,255,0);		
					}
				}					

				//Create the coloring attributes
				ColoringAttributes ca = new ColoringAttributes(col, ColoringAttributes.NICEST);
				//Add the attributes to the appearance
				Appearance ap = new Appearance();
				ap.setColoringAttributes(ca);			
				//setTransform(j3dModel,javax.media.j3d.Transform3D transform);
				setAppearance(j3dModel, ap);		
				return  super.getModel(obj, j3dModel);
			}
		};		
		
		wpPortrayal = new SpherePortrayal3D(new Color(0, 255, 0), 3, 10)
		{
			public javax.media.j3d.TransformGroup getModel(java.lang.Object obj, javax.media.j3d.TransformGroup j3dModel)
			{
				Color3f col=new Color3f(255, 255,255);
				double scale=3;		
			

				//Create the coloring attributes
				ColoringAttributes ca = new ColoringAttributes(col, ColoringAttributes.NICEST);
				//Add the attributes to the appearance
				Appearance ap = new Appearance();
				ap.setColoringAttributes(ca);			
				//setTransform(j3dModel,javax.media.j3d.Transform3D transform);
				setAppearance(j3dModel, ap);				
				setScale(j3dModel,scale);
				return  super.getModel(obj, j3dModel);
			}
		};

    }


	public void start()
	{
		((SAAModel)state).reset();		
		if(inds!=null)
		{		
			genes=((GeneVectorIndividual)inds[currentInd]).genome;
			System.out.println(inds[currentInd].genotypeToStringForHumans());
			currentInd++;
			SimInitializer.generateSimulation((SAAModel)state, genes);	
		}
		else
		{
			SimInitializer.generateSimulation((SAAModel)state);	
		}
				
		super.start();
		setupPortrayals();			
	}

	public void load(SimState state)
	{
//		((SAAModel)state).reset();
//		SimInitializer.generateSimulation((SAAModel)state, config);		
//		super.load(state);
//		setupPortrayals();
	}

	
	
	/**
	 * A method which sets up the portrayals of the different layers in the UI,
	 * this is where details of the simulation are coloured and set to different
	 * parts of the UI
	 */
	public void setupPortrayals()
	{		
		SAAModel simulation = (SAAModel) state;	
		
		// tell the portrayals what to portray and how to portray them
		environment3DPortrayal.setField( simulation.environment3D );		
		
		environment3DPortrayal.setPortrayalForClass(UAS.class, uasPortrayal);
		
		environment3DPortrayal.setPortrayalForClass(Waypoint.class,wpPortrayal);				

		// reschedule the displayer
		display3D.reset();
		
	}
	
	

    public void quit()
    {
        super.quit();  
        
        if (display3DFrame!=null) display3DFrame.dispose();
        display3DFrame = null;
        display3D = null;
    }
    
    
    public static String getName() { return "UAS-SAA-Sim"; }

	public Object getSimulationInspectedObject(){return state;}
    
    public Inspector getInspector()
    {
    	Inspector i = super.getInspector();
    	i.setVolatile(true);
    	return i;
    }   
    
    public static void main(String[] args)
    {
    	SAAModelWithUI saaModelWithUI = new SAAModelWithUI();
    	    	    	
    	Console c = new Console(saaModelWithUI); 
    	c.setBounds(0, 50, 340, 300); 
    	c.setAlwaysOnTop(true);
		c.setVisible(true);		
//		c.setWhenShouldEnd(100);
		
		SAAConfigurator configurator = new SAAConfigurator(saaModelWithUI.state, saaModelWithUI);
		configurator.setBounds(0, 380, 340,650); 
		configurator.setAlwaysOnTop(true);
		configurator.setVisible(true);
		configurator.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);				
    }
	
	public static void recur(Individual[] inds)
	{
//		System.out.println(inds[0].genotypeToStringForHumans());
		SAAModelWithUI.inds=inds;
		SAAModelWithUI saaModelWithUI = new SAAModelWithUI();
    	
    	Console c = new Console(saaModelWithUI); 
    	c.setBounds(0, 50, 340, 300); 
    	c.setAlwaysOnTop(true);
		c.setVisible(true);		
//		c.setWhenShouldEnd(100);
		
		SAAConfigurator configurator = new SAAConfigurator(saaModelWithUI.state, saaModelWithUI);
		configurator.setBounds(0, 380, 340,650); 
		configurator.setAlwaysOnTop(true);
		configurator.setVisible(true);
		configurator.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);				
	}

}
