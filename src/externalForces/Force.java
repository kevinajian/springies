package externalForces;

import jboxGlue.PhysicalObject;

import org.jbox2d.common.Vec2;

public abstract class Force {
	protected Vec2 myForce;
	protected float myExponent;
	
	public Force(Vec2 force, float exponent){
		myForce = force;
		myExponent = exponent;
	}
	
	public Vec2 getForceToApply(float proportional) {
		float proportionalMagnitude = (float)((Math.pow(proportional, myExponent)));
		return myForce.mul((float)(1.0/proportionalMagnitude));
	}
	
	protected Vec2 getForce(){
		return myForce;
	}
	

	
}
