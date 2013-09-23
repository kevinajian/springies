package objects.wall;

import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObject;
import jgame.JGColor;

//side walls
public abstract class VerticalWall extends Wall {

	/**
	 * 
	 * @param id JGame id 
	 * @param collisionId Jgame collision id for collisions
	 * @param color Color for the wall
	 * @param width Width of the wall
	 * @param height Height of the wall
	 */
	public VerticalWall(String id, int collisionId, JGColor color,
			double width, double height) {
		super(id, collisionId, color, width, height);
	}
	
	/**
	 * Calculates the distance by taking the difference in x coordinates
	 */
	@Override
	public float calculateDistance(PhysicalObject object) {
		float myX = getPosition().x;
		float itsX = object.getPosition().x;
		
		return itsX-myX;
	}
	

}
