package modeling.env;

import modeling.env.Constants.EntityType;
import sim.util.Double3D;

public class Target extends Entity
{
	private static final long serialVersionUID = 1L;
	
	public Target(int id, Double3D loc)
	{
		super(id, EntityType.TTarget);
		this.location=loc;
	}
	
	public String toString()
	{
		return this.location.toString();
	}

}
