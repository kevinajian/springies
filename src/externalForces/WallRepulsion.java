package externalForces;

import objects.wall.*;

import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObject;
import jboxGlue.WorldManager;

public class WallRepulsion extends Force {
	private Wall myWall;
	public WallRepulsion(float magnitude, Vec2 unitDirection, float exponent, Wall wall) {
		this(unitDirection.mul(magnitude), exponent, wall);
	}
	
	public WallRepulsion(Vec2 force, float exponent, Wall wall) {
		super(force, exponent);
		myWall = wall;
	}
	
	public void applyForceToObject(PhysicalObject obj) {
		float distanceFromWallToObject = myWall.calculateDistance(obj);
		float proportionalMagnitude = (float)((Math.pow(distanceFromWallToObject, myExponent)));
		Vec2 forceToApply =  myForce.mul((float)(1.0/proportionalMagnitude));
		obj.applyForce(forceToApply);
	}
	
	public String toString(){
		return getForce().toString();
	}
}
