package review.forces;

import review.TestMass;
import review.TestVerticalWall;
import review.TestWorld;
import review.TestWorldManager;
import jboxGlue.WorldManager;

import org.jbox2d.common.Vec2;

public class CenterOfMass extends AbstractForce {

	public CenterOfMass(float magnitude, float exponent) {
		super(magnitude, exponent);
	}
	
	/**
	 * calculates force based on distance from object to COM
	 * @param obj - object we want to apply the force to
	 * @reutrn force to be applied
	 */
	@Override
	protected Vec2 getForceToApply(TestMass obj) {
		Vec2 unitDirection = getVectorFromObjectToCenter(obj);
		float distance = getDistanceToCenter(obj);
		Vec2 force = unitDirection.mul(getForceIntensity(distance));
		
		return force;
	}
	
	/**
	 * @param obj - object we want to apply the force to
	 * @return - unit Dection pointing towards center of mass from object
	 */
	private Vec2 getVectorFromObjectToCenter(TestMass obj){
		Vec2 center = TestWorldManager.getWorld().getCenterOfMass();
		Vec2 position = obj.getPosition();
		Vec2 unitDirection = center.sub(position);
		unitDirection.normalize();
		return unitDirection;
	}
	
	/**
	 * Calculates the distance between an object and the COM
	 * @param obj - object we want to apply the force to
	 * @return distance between an object and the COM
	 */
	private float getDistanceToCenter(TestMass obj){
		Vec2 center = TestWorldManager.getWorld().getCenterOfMass();
		Vec2 position = obj.getPosition();
		return distance(center, position);
	}
}
