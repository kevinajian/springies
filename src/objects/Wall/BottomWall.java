package objects.wall;

import org.jbox2d.common.Vec2;

import jgame.JGColor;

public class BottomWall extends HorizontalWall{
	
	/**
	 * 
	 * @param id JGame id 
	 * @param collisionId Jgame collision id for collisions
	 * @param color Color for the wall
	 * @param width Width of the wall
	 * @param height Height of the wall
	 */
	public BottomWall(String id, int collisionId, JGColor color, double width,
			double height) {
		super(id, collisionId, color, width, height);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * sets unit vector to 0 in the x direction, -1 in the y direction
	 */
	@Override
	protected void setUnitVector() {
		myUnitDirectionToRepel = new Vec2((float)0.0, (float)-1.0);
	}

}
