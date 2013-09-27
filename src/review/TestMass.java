package review;

import org.jbox2d.common.Vec2;

public class TestMass {
	private float myMass;
	private Vec2 myPosition;
	private Vec2 myVelocity;
	public TestMass(float mass){
		myMass = mass;
		myPosition = new Vec2();
		myVelocity = new Vec2();
	}
	
	public float getMass(){
		return myMass;
	}
	
	public Vec2 getPosition(){
		return myPosition;
	}
	
	public void setPosition(float x, float y){
		myPosition = new Vec2(x,y);
	}
	
	public Vec2 getVelocity(){
		return myVelocity;
	}
	
	public void setVecolity(float x, float y){
		myVelocity = new Vec2(x,y);
	}
	
	public void applyForce(Vec2 force){
		
	}
}
