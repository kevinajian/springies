package objects.wall;

import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObject;
import jgame.JGColor;

public abstract class HorizontalWall extends Wall {

	/**
	 * 
	 * @param id JGame id 
	 * @param collisionId Jgame collision id for collisions
	 * @param color Color for the wall
	 * @param width Width of the wall
	 * @param height Height of the wall
	 */
	public HorizontalWall(String id, int collisionId, JGColor color,
			double width, double height) {
		super(id, collisionId, color, width, height);
	}

	/**
	 * calculates distance by taking the difference in y coordinates
	 */
	@Override
	public float calculateDistance(PhysicalObject object) {
		float myY = getPosition().y;
		float itsY = object.getPosition().y;
		return itsY-myY;
	}

	
}
