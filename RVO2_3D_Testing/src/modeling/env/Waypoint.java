package modeling.env;

/**
 *
 * @author rl576
 */
public class Waypoint extends Entity
{
	private static final long serialVersionUID = 1L;

	public Waypoint(int ID)
	{
		super(ID, Constants.EntityType.TWAYPOINT);		
	}
	
	public String toString()
	{
		return this.location.toString();
	}
}
