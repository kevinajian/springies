package externalForces;

import jboxGlue.PhysicalObject;

import org.jbox2d.common.Vec2;

public abstract class Force {
	protected Vec2 myForce;
	protected float myExponent;
	private static boolean forceOn;
	
	public Force(Vec2 force, float exponent){
		myForce = force;
		myExponent = exponent;
		forceOn=true;
	}
		
	protected Vec2 getForce(){
		return myForce;
	}
	
	public abstract void applyForceToObject(PhysicalObject obj);
	
	public static void toggleForce(){
		System.out.println("toggled");
		forceOn = !forceOn;
	}
	
	public boolean shouldApply(){
		return forceOn;
	}
}
