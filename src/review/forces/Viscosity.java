package review.forces;

import jboxGlue.PhysicalObject;

import org.jbox2d.common.Vec2;

import review.TestMass;

public class Viscosity extends AbstractForce {

	public Viscosity(float scale) {
		super(scale);
	}

	/**
	 * Calculates force based on linear velocity of object
	 * scaled down by magnitude and scale factor
	 */
	@Override
	protected Vec2 getForceToApply(TestMass obj) {
		Vec2 directionToApply = obj.getVelocity();
		return directionToApply.mul(-1*getScaledMagnitude());
	}
}
