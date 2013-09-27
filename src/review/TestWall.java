package review;

import org.jbox2d.common.Vec2;

public abstract class TestWall {
	Vec2 myPosition;
	public TestWall(float x, float y){
		myPosition = new Vec2(x, y);
	}
	
	public abstract float calculateDistance(TestMass mass);
}
