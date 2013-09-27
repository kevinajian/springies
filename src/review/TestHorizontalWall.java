package review;

import org.jbox2d.common.Vec2;

public class TestHorizontalWall extends TestWall{
	
	public TestHorizontalWall(float x, float y){
		super(x,y);
	}
	
	public float calculateDistance(TestMass mass){
		return Math.abs(myPosition.y - mass.getPosition().y);
	}
}
