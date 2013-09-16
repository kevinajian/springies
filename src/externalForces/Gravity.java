package externalForces;

import org.jbox2d.common.Vec2;

public class Gravity extends FixedForce {
	
	public Gravity(float degree, float magnitude){
		this(convertDegreeToVector(degree).mul(magnitude));
	}
	
	public Gravity(Vec2 force){
		super(force);
	}
	
	public static Vec2 convertDegreeToVector(float degree){
		return new Vec2((float) Math.cos(degree), (float)Math.sin(degree));
	}
}
