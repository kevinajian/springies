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
	
	public abstract void applyForceToObject(PhysicalObject obj);
	
	protected Vec2 getForce(){
		return myForce;
	}
	

	
}
