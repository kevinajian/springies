package review;

import org.jbox2d.common.Vec2;

public class TestVerticalWall extends TestWall{
	
	public TestVerticalWall(float x, float y){
		super(x, y);
	}
	
	public float calculateDistance(TestMass mass){
		return Math.abs(myPosition.x - mass.getPosition().x);
	}
}
