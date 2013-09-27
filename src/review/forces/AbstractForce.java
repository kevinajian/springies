package review.forces;

import org.jbox2d.common.Vec2;

import review.TestMass;


/**
 * Base class for all Forces
 * 
 * A force contains a magnitude and an exponent
 * The magnitude determines the intensity of the 
 * force
 * 
 * The exponent determines the proportionality factor
 * based on some paramter based on the mass' state
 * 
 * Forces can be toggled on and off, which is tracked
 * by the isForceOn boolean
 * 
 * A Force implements Togglable, which insures that a Force
 * can be toggled by the toggle() method
 * 
 * Forces can also have a separate scale factor that can be set
 * to have more realistic intensity
 * 
 * @author tylernisonoff
 *
 */
public abstract class AbstractForce implements Togglable {
	protected float myMagnitude;
	protected float myExponent;
	private boolean isForceOn = true;
	private float myScaleFactor = (float)1.0;
	
	/**
	 * Simple Constructor for force
	 * assumes exponent is 0
	 * @param magnitude intensity of the force
	 */
	public AbstractForce(float magnitude){
		this(magnitude, (float)0.0);
	}
	
	/**
	 * Constructor that takes a magnitude and exponent
	 * @param magnitude - intensity of the force
	 * @param exponent - exponent for proportional scaling
	 */
	public AbstractForce(float magnitude, float exponent){
		myMagnitude = magnitude;
		myExponent = exponent;
	}

	/**
	 * @param magnitude - magnitude for force
	 */
	public void setMagnitude(float magnitude) {
		myMagnitude = magnitude;
	}
	
	/**
	 * @param exponent - exponent for force
	 */
	public void setExponent(float exponent) {
		myExponent = exponent;
	}
	
	/**
	 * toggles the force
	 */
	public void toggle() {
		isForceOn = !isForceOn; 
	}
	
	/**
	 * @return scale Factor for force
	 */
	public float getScaleFactor(){
		return myScaleFactor;
	}
	
	/**
	 * sets scale factor
	 * @param scale scale factor to set
	 */
	public void setScaleFactor(float scale){
		myScaleFactor = scale;
	}
	
	/**
	 * Main functionality to be implemented by subclasses
	 * This is the function that calculates the force
	 * to be applied to an object
	 * @param obj - object to apply the force to
	 * @return force to be applied to an object
	 */
	protected abstract Vec2 getForceToApply(TestMass obj);
	
	/**
	 * The method that should be called to apply a force to an object
	 * @param obj object to apply the force to
	 */
	public void applyForceToObject(TestMass obj){
		if(isForceOn)
			obj.applyForce(getForceToApply(obj));
	}
	
	/**
	 * factor basic force calculation taking into account
	 * some factor from the mass, the magnitude, scale factor, and exponent
	 * @param proportional factor from mass
	 * @return a force that can be applied
	 */
	protected float getForceIntensity(float factor){
		return (float)(getScaledMagnitude()/(Math.pow(factor, myExponent)));
	}
	
	/**
	 * @return the magnitude scaled down by the scaleFactor
	 */
	protected float getScaledMagnitude(){
		return (float)((myMagnitude*myScaleFactor));
	}
	
	
	/**
	 * Simple function to calculate the distance between two vectors
	 * @param one first vector
	 * @param two second vector
	 * @return distance between the two vectors
	 */
	protected float distance(Vec2 one, Vec2 two){
		float dx = one.x - two.x;
		float dy = one.y - two.y;
		return (float) Math.sqrt(dx*dx+dy*dy);
	}
}
