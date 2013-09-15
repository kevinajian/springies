package Objects.Wall;

import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObject;
import jgame.JGColor;

public abstract class HorizontalWall extends Wall {

	public HorizontalWall(String id, int collisionId, JGColor color,
			double width, double height) {
		super(id, collisionId, color, width, height);
	}

	@Override
	public float calculateDistance(PhysicalObject object) {
		float myY = getPosition().y;
		float itsY = object.getPosition().y;
		return itsY-myY;
	}

	
}
