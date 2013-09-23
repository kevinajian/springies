package jboxGlue;

import objects.Mass;

import org.jbox2d.collision.ShapeDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.JointDef;

import springies.Springies;


import jgame.JGColor;
import jgame.JGObject;
import jgame.impl.JGEngineInterface;

/**
 * JBox Glue for our Springs
 * @author tylernisonoff
 *
 */
public abstract class PhysicalSpring extends JGObject {
	protected Springies myEngine;
	protected JGColor myColor;
	public Mass myBody1;
	public Mass myBody2;
	
	/**
	 * 
	 * @param name - JGame id for finding objects 
	 * @param collisionId - JGame CID to check collisions
	 * @param color - Color of the Spring
	 * @param body1 - first Body for the muscle
	 * @param body2 - second Body for the muscle
	 */
	protected PhysicalSpring( String name, int collisionId, JGColor color, Mass body1, Mass body2)
	{
		super( name, true, 0, 0, collisionId, null );
		myColor = color;	

		init(body1, body2);
	}
	
	/**
	 * Initializes the spring by setting up masses and the engine
	 * @param body1 Mass on one end
	 * @param body2 Mass on the other end
	 */
	private void init( Mass body1, Mass body2)
	{
		myEngine = (Springies)eng;
		myBody1 = body1;
		myBody2 = body2;
	}
	
	
	/**
	 * 
	 * @return to color of the spring
	 */
	public JGColor getColor( )
	{
		return myColor;
	}
	
	/**
	 * 
	 * @param val Color to be set
	 */
	public void setColor( JGColor val )
	{
		myColor = val;
	}
	
	/**
	 * Applies the correct forces to its masses on each iteration
	 */
	@Override
	public void move( )
	{
		// if the JGame object was deleted, remove the physical object too
		if( myBody1.getBody().m_world != WorldManager.getWorld() )
		{
			remove();
			return;
		}
		
		float distance = myBody1.distance(myBody2);
		Vec2 directionToApplyForce = directionToApplyForce();
		float force = calculateForce(distance);

		myBody1.applyForce(directionToApplyForce.mul(-1*force));
		myBody2.applyForce(directionToApplyForce.mul(force));
	}
	
	/**
	 * 
	 * @return Unit direction of force to be applied
	 */
	public Vec2 directionToApplyForce(){
		Vec2 directionToApply = myBody2.getBody().getPosition().sub(myBody1.getBody().getPosition());
		directionToApply.normalize();
		return directionToApply;
	}

	/**
	 * Implemented by subclasses to calculate the force to be applied to its masses
	 * @param distance - distance between the two masses
	 * @return force to be applied
	 */
	public abstract float calculateForce(float distance);
	
	@Override
	public void paint( )
	{
		paintJoint();
	}
	
	/**
	 * Paints the spring by drawing a line between its two masses
	 */
	protected void paintJoint() {
		myEngine.setColor( myColor );
		Vec2 position1 = myBody1.getBody().getPosition();
		Vec2 position2 = myBody2.getBody().getPosition();
		myEngine.drawLine(position1.x, position1.y, position2.x, position2.y);
	}
	
}
