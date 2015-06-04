package modeling.env;
/**
 *
 * @author Xueyi Zou
 */
public interface Constants
{
	//Entity Types
	public static enum EntityType
	{			
		TUAS,//the type constant of a uas
		TWAYPOINT,//the type constant of a waypoint
		TObserver,//the type constant of an observer
		TTarget,//the type constant of a target
		TOTHER,//a placeholder for entities which aren't mentioned elsewhere	
				
	}
}
