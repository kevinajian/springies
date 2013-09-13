package externalForces;

import org.jbox2d.common.Vec2;

public abstract class ForceField extends Force {
	private float myExponent;
	public ForceField(float magnitude, Vec2 unitDirection, float exponent){
		this(unitDirection.mul(magnitude), exponent);
	}
	
	public ForceField(Vec2 force, float exponent){
		super(force);
		myExponent = exponent;
	}
	
	public Vec2 getForceToApply(float proportional) {
		return super.getForceToApply().mul((float) (1.0/Math.pow(proportional, myExponent)));
	}
}
