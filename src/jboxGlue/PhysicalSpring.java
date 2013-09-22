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

public abstract class PhysicalSpring extends JGObject {
	protected Springies myEngine;
	protected JGColor myColor;
	public Mass myBody1;
	public Mass myBody2;
	protected PhysicalSpring( String name, int collisionId, JGColor color, Mass body1, Mass body2)
	{
		super( name, true, 0, 0, collisionId, null );
		myColor = color;	

		init(body1, body2);
	}
	
	private void init( Mass body1, Mass body2)
	{
		myEngine = (Springies)eng;
		myBody1 = body1;
		myBody2 = body2;
	}
	
	
	public JGColor getColor( )
	{
		return myColor;
	}
	
	public void setColor( JGColor val )
	{
		myColor = val;
	}
	
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
		Vec2 directionToApplyForce = directionToApplyForce(myBody1, myBody2);
		float force = calculateForce(distance);

		myBody1.applyForce(directionToApplyForce.mul(-1*force));
		myBody2.applyForce(directionToApplyForce.mul(force));
	}
	
	public Vec2 directionToApplyForce(Mass body1, Mass body2){
		Vec2 directionToApply = myBody2.getBody().getPosition().sub(myBody1.getBody().getPosition());
		directionToApply.normalize();
		return directionToApply;
	}

	public abstract float calculateForce(float distance);
	
	@Override
	public void paint( )
	{
		paintJoint();
	}
	
	protected void paintJoint() {
		myEngine.setColor( myColor );
		Vec2 position1 = myBody1.getBody().getPosition();
		Vec2 position2 = myBody2.getBody().getPosition();
		myEngine.drawLine(position1.x, position1.y, position2.x, position2.y);
	}
	
}
