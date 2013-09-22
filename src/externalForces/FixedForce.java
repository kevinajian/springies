package externalForces;

import jboxGlue.PhysicalObject;

import org.jbox2d.common.Vec2;

public abstract class FixedForce extends Force {
	public FixedForce(float magnitude, Vec2 unitDirection){
		this(unitDirection.mul(magnitude));
	}
	
	public FixedForce(Vec2 force){
		super(force, (float)0.0);
	}
	
	public void applyForceToObject(PhysicalObject obj){
		obj.applyForce(getForceToApply());
	}
	
	public Vec2 getForceToApply(){
		return getForce();
	}

}
