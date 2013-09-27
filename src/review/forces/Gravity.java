package review.forces;

import org.jbox2d.common.Vec2;

import review.TestMass;

import jboxGlue.PhysicalObject;

public class Gravity extends FixedDirectionForce {
	
	public Gravity(float magnitude, float degrees){
		this(magnitude, convertDegreesToUnitVector(degrees));
	}
	
	public Gravity(float magnitude, Vec2 direction){
		super(magnitude, 0, direction);
	}
	
	/**
	 * Calculates force based on maginitude and mass of object
	 */
	@Override
	protected Vec2 getForceToApply(TestMass object){
		Vec2 forceByMagnitude = getDirectionScaled().mul(myMagnitude);
		float mass = object.getMass();
		Vec2 forceToApply = forceByMagnitude.mul(mass);
		return forceToApply;
	}
}
