package externalForces;

import org.jbox2d.common.Vec2;

public abstract class Force {
	private Vec2 myForce;
	
	public Force(float magnitude, Vec2 unitDirection){
		this(unitDirection.mul(magnitude));
	}
	
	public Force(Vec2 force){
		myForce = force; 
	}
	
	public Vec2 getForceToApply(){
		return myForce;
	}
}
