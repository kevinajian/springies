package jboxGlue;

import org.jbox2d.collision.ShapeDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.JointDef;

import Objects.Mass;

import jgame.JGColor;
import jgame.JGObject;
import jgame.impl.JGEngineInterface;

public abstract class PhysicalSpring extends JGObject {
	protected JGEngineInterface myEngine;
	protected JGColor myColor;
	protected Joint myJoint;
	protected float myRotation;
	
	protected PhysicalSpring( String name, int collisionId, JGColor color, Mass body1, Mass body2 )
	{
		super( name, true, 0, 0, collisionId, null );
		
		myColor = color;		
		init(body1.getBody(), body2.getBody());
	}
	
	private void init( Body body1, Body body2)
	{
		// init defaults
		myEngine = eng;
		createJoint(body1, body2);
	}
	
	protected void createJoint( Body body1, Body body2 )
	{
		DistanceJointDef definition = new DistanceJointDef();
		definition.initialize(body1, body2, new Vec2(0,0), new Vec2(0,0));
		definition.dampingRatio=(float)0.0;
		World world = WorldManager.getWorld();
		myJoint = world.createJoint( definition);
		myJoint.setUserData( this ); // for following body back to JGObject
		myJoint.getBody1().m_world = WorldManager.getWorld();
	}
	
	public Joint getJoint( )
	{
		return myJoint;
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
		if( myJoint.getBody1().m_world != WorldManager.getWorld() )
		{
			remove();
			return;
		}
	
	}	
	
	@Override
	public void destroy( )
	{
		// body may not be in actual world. If not, do not call destroyBody.
		if( myJoint.getBody1().m_world == WorldManager.getWorld() )
		{
			// also destroys associated joints
			WorldManager.getWorld().destroyJoint( myJoint );
		}
	}
	
	@Override
	public void paint( )
	{
		paintJoint();
	}
	protected abstract void paintJoint( );

	
}
