package externalForces;

import jboxGlue.PhysicalObject;

import org.jbox2d.common.Vec2;

public class Gravity extends FixedForce {
	
	public Gravity(float degree, float magnitude){
		this(convertDegreeToVector(degree).mul(magnitude));
	}
	
	public Gravity(Vec2 force){
		super(force);
	}
	public void applyForceToObject(PhysicalObject obj){
		float scaling = (float) .002;
		obj.applyForce(getForceToApply().mul(scaling*obj.getBody().getMass()));
	}
	
	public static Vec2 convertDegreeToVector(float degree){
		double radians = degreesToRads(degree);
		return new Vec2((float) Math.cos(radians), (float)Math.sin(radians));
	}
	
	private static double degreesToRads(double degree){
		return (degree*Math.PI/180.0);
	}
}
