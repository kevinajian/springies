package review;

import org.jbox2d.common.Vec2;

public class TestWorld {
	private Vec2 myCenterOfMassLocation;
	public TestWorld(float x, float y){
		myCenterOfMassLocation = new Vec2(x, y);
	}
	
	public void setCenterOfMass(float x, float y){
		myCenterOfMassLocation = new Vec2(x, y);
	}
	
	public Vec2 getCenterOfMass(){
		return myCenterOfMassLocation;
	}
}
