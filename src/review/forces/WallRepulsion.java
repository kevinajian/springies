package review.forces;

import jboxGlue.PhysicalObject;

import objects.wall.Wall;

import org.jbox2d.common.Vec2;

import forces.Viscosity;

import review.TestMass;
import review.TestWall;

public class WallRepulsion extends FixedDirectionForce {
	private TestWall myWall;
	
	public WallRepulsion(float magnitude, float exponent, Vec2 unitDirection, TestWall wall) {
		super(magnitude, exponent, unitDirection);
		myWall = wall;
	}

	/**
	 * Calcualtes force based on magnitude, scale factor, and distance of the
	 * object to the wall that the wall repulsion force is associated with
	 */
	@Override
	protected Vec2 getForceToApply(TestMass obj) {
		float distanceFromWallToObject = myWall.calculateDistance(obj);
		float intensity = getForceIntensity(distanceFromWallToObject);
		return myUnitDirection.mul(intensity);
	}
}
