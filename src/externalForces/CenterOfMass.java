package externalForces;

import jboxGlue.PhysicalObject;
import jboxGlue.WorldManager;

import org.jbox2d.common.Vec2;

public class CenterOfMass extends Force{
	private float myMagnitude;
	public CenterOfMass(float magnitude, float exponent) {
		super(new Vec2(), exponent);
		myMagnitude = magnitude;
	}

	@Override
	public void applyForceToObject(PhysicalObject obj) {
		Vec2 center = WorldManager.getWorld().getCenterOfMass();
		Vec2 position = obj.getPosition();
		float distance = distance(center, position);
		
		Vec2 unitDirection = center.sub(position);
		unitDirection.normalize();
		
		Vec2 forceToApply = getForceToApply(unitDirection, distance);
		
		obj.applyForce(forceToApply);
	}
	
	
	public Vec2 getForceToApply(Vec2 unit, float distance){
		Vec2 force = unit.mul(myMagnitude);
		force = force.mul((float)(1.0/(Math.pow(distance, myExponent))));
		return force;
	}
	
	private float distance(Vec2 one, Vec2 two){
		float dx = one.x - two.x;
		float dy = one.y - two.y;
		return (float) Math.sqrt(dx*dx+dy*dy);
	}
	
	

}
