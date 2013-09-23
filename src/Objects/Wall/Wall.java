package objects.wall;

import objects.*;

import org.jbox2d.common.Vec2;

import externalForces.Force;
import externalForces.WallRepulsion;
import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectRect;
import jboxGlue.WorldManager;
import jgame.JGColor;

/**
 * Abstract wall class
 * Contains a force and a unit direction to repel the object in
 * @author tylernisonoff
 *
 */
public abstract class Wall extends PhysicalObjectRect {
	protected Force myForce;
	protected  Vec2 myUnitDirectionToRepel;
	
	/**
	 * 
	 * @param id JGame id 
	 * @param collisionId Jgame collision id for collisions
	 * @param color Color for the wall
	 * @param width Width of the wall
	 * @param height Height of the wall
	 */
	public Wall(String id, int collisionId, JGColor color, double width, double height){
		super(id, collisionId, color, width, height);
		initWall();	
	}
	
	/**
	 * sets up the wall
	 * Normalizes the unit direction
	 * sets default force to nothing
	 */
	public void initWall( ){
		setUnitVector();
		myUnitDirectionToRepel.normalize();
		myForce = new WallRepulsion((float)0.0, myUnitDirectionToRepel, (float)0.0, this);
	}
		
	/**
	 * 
	 * @param force - force to set
	 */
	public void setRepulsionForce(WallRepulsion force){
		myForce = force;
	}
	
	/**
	 * 
	 * @param magnitude - magnitude of the force
	 * @param exponent - exponent of the relationship between the force and some parameter
	 */
	public void setRepulsionForce(float magnitude, float exponent){
		WallRepulsion rep =  new WallRepulsion(magnitude, myUnitDirectionToRepel, exponent, this);
		myForce = rep;
	}
	
	/**
	 * 
	 * @return returns the repulsion force
	 */
	public WallRepulsion getRepulsionForce(){
		return (WallRepulsion) myForce;
	}
	
	
	@Override
	public void move( )
	{
		super.move();
		
		Mass[] masses = WorldManager.getWorld().getMasses();
		for(Mass mass : masses){
			myForce.applyForceToObject(mass);
		}
	}
	
	/**
	 * gets the position of the wall
	 */
	public Vec2 getPosition(){
		return myBody.getPosition();
	}
	
	/**
	 * Used to calculate the distance from an object to the wall 
	 * @param object - whose distance we want
	 * @return - the distance
	 */
	public abstract float calculateDistance(PhysicalObject object);
	
	/**
	 * used to set the unit vector
	 */
	protected abstract void setUnitVector();

}
