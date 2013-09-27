package review.forces;

import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObject;

public abstract class FixedDirectionForce extends AbstractForce {
	protected Vec2 myUnitDirection;

	/**
	 * Simple constructor for fixedDirectionForce
	 * Assumes exponent is 0
	 * @param magnitude - intensity of force
	 * @param degree - degree for unit Vector
	 */
	public FixedDirectionForce(float magnitude, float degree) {
		this(magnitude, (float)0.0, convertDegreesToUnitVector(degree));
	}

	/**
	 * sets unitVector properly
	 * @param magnitude - intensity of force
	 * @param exponent - exponent for proportional scaling
	 * @param unitDirection - direction to apply force
	 */
	public FixedDirectionForce(float magnitude, float exponent, Vec2 unitDirection) {
		super(magnitude, exponent);
		myUnitDirection = unitDirection;
	}
	
	/**
	 * 
	 * @return the Unit Vector for direction
	 */
	protected Vec2 getUnitDirection(){
		return myUnitDirection;
	}
	
	/**
	 * @return  the direction scaled by magnitude and scaleFactor
	 */
	protected Vec2 getDirectionScaled(){
		return myUnitDirection.mul(getScaleFactor());
	}
	
	/**
	 * Converts degrees to a vector based on radians
	 * Does so in a clockwise orientation
	 * @param degree - degree to be converted
	 * @return - unit vector in proper direction
	 */
	protected static Vec2 convertDegreesToUnitVector(float degree) {
		double radians = degreesToRads(degree);
		return new Vec2((float) (-1*Math.cos(radians)), (float) (-1*Math.sin(radians)));
	}

	/**
	 * Converts degrees to radians
	 * @param degree - degree to be converted
	 * @return - degree as radians
	 */
	private static double degreesToRads(double degree) {
		return (degree * Math.PI / 180.0);
	}
}
