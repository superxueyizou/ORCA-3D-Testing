package configuration;

public class GlobalConfig 
{
	public double worldX = 1000; 
	public double worldY = 300; 
	public double worldZ = 1000; 
	
	public int alertTime=10;
	public static double PROPERDISTANCE=20;

	public boolean collisionAvoidanceEnabler=false;
	public boolean selfSeparationEnabler=true;
	public boolean accidentDetectorEnabler=true;
	public boolean sensorNoiseEnabler=false;
	public boolean sensorValueUncertainty=false;
	public boolean encounterGeneratorEnabler=true;
	public boolean whiteNoiseEnabler=false;	
	
	public static final double DEFAULT_OWNSHIPGS = 5;
	public static final double DEFAULT_OWNSHIPVY = 0;
	
	public double stdDevX =1;//[0,3]
	public double stdDevY =0.5;//[0,3]
	public double stdDevZ =1;//[0,3]
	
	public double maxGS =10;
	public double minGS =0;
	public double maxVS =2;
	public double minVS =-10;
}
