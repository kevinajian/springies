package externalForces;

import jboxGlue.PhysicalObject;

import org.jbox2d.common.Vec2;

public class Viscosity extends FixedForce {
	private float myMagnitude;
	public Viscosity(float magnitude) {
		super(magnitude, new Vec2());
		float scaling = (float) 0.5;
		myMagnitude = (scaling*magnitude);
	}
	
	public Vec2 getForceToApply(){
		return getForce();
	}
	
	@Override
	public void applyForceToObject(PhysicalObject obj){
		Vec2 linearVelocity = obj.getBody().getLinearVelocity();
		obj.getBody().m_linearVelocity = linearVelocity.mul(myMagnitude);
	}
	
}
