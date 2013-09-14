package objects.Wall;

import org.jbox2d.common.Vec2;

import jgame.JGColor;

public class RightWall extends VerticalWall {

	public RightWall(String id, int collisionId, JGColor color, double width,
			double height) {
		super(id, collisionId, color, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setUnitVector() {
		myUnitDirectionToRepel = new Vec2((float)-1.0, (float)0.0);
	}
	
	

}
