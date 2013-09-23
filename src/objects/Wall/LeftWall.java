package objects.wall;

import org.jbox2d.common.Vec2;

import jgame.JGColor;

public class LeftWall extends VerticalWall {
	
	/**
	 * 
	 * @param id JGame id 
	 * @param collisionId Jgame collision id for collisions
	 * @param color Color for the wall
	 * @param width Width of the wall
	 * @param height Height of the wall
	 */
	public LeftWall(String id, int collisionId, JGColor color, double width,
			double height) {
		super(id, collisionId, color, width, height);
		// TODO Auto-generated constructor stub
	}

	/**
	 * sets unit vector to 01in the x direction, 0 in the y direction
	 */
	@Override
	protected void setUnitVector() {
		myUnitDirectionToRepel = new Vec2((float)1.0, (float)0.0);
	}

}
