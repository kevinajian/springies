package objects.wall;

import org.jbox2d.common.Vec2;

import jgame.JGColor;

public class TopWall extends HorizontalWall {

	public TopWall(String id, int collisionId, JGColor color, double width,
			double height) {
		super(id, collisionId, color, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setUnitVector() {
		myUnitDirectionToRepel = new Vec2((float)0.0, (float)1.0);
	}

}
