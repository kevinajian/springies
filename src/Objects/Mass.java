package objects;

import jboxGlue.PhysicalObjectCircle;
import jboxGlue.WorldManager;

import org.jbox2d.common.Vec2;

import jgame.JGColor;
import jgame.JGObject;

public class Mass extends PhysicalObjectCircle{
	private static final int RADIUS = 5;
	private static final double DEFAULT_MASS = 5;
	public static final float INITIAL_X_VELOCITY = 0;
	public static final float INITIAL_Y_VELOCITY = 0;
	public static final JGColor COLOR = JGColor.blue;

	public Mass(String id, int collisionId, double x, double y){
		this(id, collisionId, x, y, DEFAULT_MASS);
	}
	
	public Mass(String id, int collisionId, double x, double y, double mass){
		this(id, collisionId, x, y, mass, INITIAL_X_VELOCITY, INITIAL_Y_VELOCITY);
		setPos(x, y);
	}
	
	public Mass(String id, int collisionId, double x, double y, double mass, float xVel, float yVel){
		super(id, collisionId, COLOR, RADIUS, mass);
		setPos(x, y);
		myBody.setLinearVelocity(new Vec2(xVel, yVel));
	}
	
	public void hit( JGObject other )
	{
		//if it is another mass, return
		if(other.colid==1) return;
		// we hit something! bounce off it!
		Vec2 velocity = myBody.getLinearVelocity();
		
		// is it a tall wall?
		final double DAMPING_FACTOR = 0.8;
		boolean isSide = other.getBBox().height > other.getBBox().width;
		if( isSide )
		{
			velocity.x *= -DAMPING_FACTOR;
		}
		else
		{
			velocity.y *= -DAMPING_FACTOR;
		}
		
		
		// apply the change
		myBody.setLinearVelocity( velocity );
	}
}
