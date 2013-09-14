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


import jgame.JGColor;
import jgame.JGObject;
import jgame.impl.JGEngineInterface;

public abstract class PhysicalSpring extends JGObject {
	protected JGEngineInterface myEngine;
	protected JGColor myColor;
	public Mass myBody1;
	public Mass myBody2;
	public float myRestLength;
	public float myK;
	public float amplitude = 0;
	public float frequency = 1;
	protected PhysicalSpring( String name, int collisionId, JGColor color, Mass body1, Mass body2, float restLength,float k )
	{
		super( name, true, 0, 0, collisionId, null );
		myColor = color;	
		myRestLength = restLength;
		myK = k;
		init(body1, body2);
	}
	
	private void init( Mass body1, Mass body2)
	{
		// init defaults
		myEngine = eng;
		myBody1 = body1;
		myBody2 = body2;
	}
	
	protected void createJoint( Body body1, Body body2 )
	{
//		DistanceJointDef definition = new DistanceJointDef();
//		definition.initialize(body1, body2, new Vec2(0,0), new Vec2(0,0));
//		definition.dampingRatio=(float).0;
//		definition.collideConnected=false;
//		definition.length = (float)(3.0);
//		World world = WorldManager.getWorld();
//		myJoint = (DistanceJoint)world.createJoint( definition);
//		myJoint.setUserData( this ); // for following body back to JGObject
//		myJoint.getBody1().m_world = WorldManager.getWorld();
//	}
//	
//	public Joint getJoint( )
//	{
//		return myJoint;
//
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
		
//		Vec2 vel1 = myBody1.getBody().getLinearVelocity();
//		Vec2 vel2 = myBody2.getBody().getLinearVelocity();
//		Vec2 dv = vel1.sub(vel2);
		float distance = myBody1.distance(myBody2);
		Vec2 directionToApply = myBody2.getBody().getPosition().sub(myBody1.getBody().getPosition());
//		System.out.println("distance : "+distance);
//		System.out.println("direction: "+directionToApply.toString());
		directionToApply.normalize();
//		System.out.println("normalize:"+directionToApply.toString());

		float force = (myK)*(myRestLength-distance);

		myBody1.applyForce(directionToApply.mul(-1*force));
		myBody2.applyForce(directionToApply.mul(force));
		
	}
	
	//will return a normalized vector with from the body2's position
	// vector - body1's
	public Vec2 directionToApplyForce(Body body1, Body body2){
		Vec2 directionToApply = body1.getPosition().sub(body2.getPosition());
		directionToApply.normalize();
		return directionToApply;
	}
	
//	@Override
//	public void destroy( )
//	{
//		// body may not be in actual world. If not, do not call destroyBody.
//		if( myBody1.getBody()..m_world == WorldManager.getWorld() )
//		{
//			// also destroys associated joints
//			WorldManager.getWorld().destroyJoint( myJoint );
//		}
//	}
	
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
