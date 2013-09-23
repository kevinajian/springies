package externalForces;

import java.util.ArrayList;

import jboxGlue.PhysicalObject;

import org.jbox2d.common.Vec2;

import com.sun.xml.internal.fastinfoset.algorithm.BuiltInEncodingAlgorithm.WordListener;

public class Gravity extends FixedForce {
	private static boolean forceIsOn = true;
	
	
	public Gravity(float degree, float magnitude){
		this(convertDegreeToVector(degree).mul(magnitude));
	}
	
	public Gravity(Vec2 force){
		super(force);
	}
	public void applyForceToObject(PhysicalObject obj){
		if(!forceIsOn) 
			return;
		float scaling = (float) .005;
		obj.applyForce(getForceToApply().mul(scaling*obj.getBody().getMass()));
	}
	
	public static Vec2 convertDegreeToVector(float degree){
		double radians = degreesToRads(degree);
		return new Vec2((float) Math.cos(radians), (float)Math.sin(radians));
	}
	
	private static double degreesToRads(double degree){
		return (degree*Math.PI/180.0);
	}

	public static void toggleForce() {
		forceIsOn = !forceIsOn;
	}

}
