package externalForces;

import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObject;
import jboxGlue.WorldManager;

public class WallRepulsion extends Force {
	
	public WallRepulsion(float magnitude, Vec2 unitDirection, float exponent) {
		this(unitDirection.mul(magnitude), exponent);
	}
	
	public WallRepulsion(Vec2 force, float exponent) {
		super(force, exponent);
	}
	
	public String toString(){
		return getForce().toString();
	}
}
