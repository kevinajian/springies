package externalForces;

import jboxGlue.PhysicalObject;

import org.jbox2d.common.Vec2;

public abstract class Force {
	protected Vec2 myForce;
	protected float myExponent;
	
	
	/**
	 * Constructor for abstract force class
	 * @param force  - the force vector that would be applied if the exponent is 0
	 * @param exponent -  ex) exponent value of 2.0 means inverse-square force
	 */
	public Force(Vec2 force, float exponent){
		myForce = force;
		myExponent = exponent;
	}
	
	/**
	 * Gets the force vector for a Force
	 * @return force vector passed into constructor
	 */
	protected Vec2 getForce(){
		return myForce;
	}
	
	/**
	 * Method that should be called whenever a force is meant to be applied to an object
	 * @param obj - object for the force to be applied
	 */
	public abstract void applyForceToObject(PhysicalObject obj);
	
	

}
