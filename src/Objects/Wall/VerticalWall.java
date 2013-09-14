package objects.Wall;

import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObject;
import jgame.JGColor;

//side walls
public abstract class VerticalWall extends Wall {

	public VerticalWall(String id, int collisionId, JGColor color,
			double width, double height) {
		super(id, collisionId, color, width, height);
	}
	
	@Override
	public float calculateDistance(PhysicalObject object) {
		float myX = getPosition().x;
		float itsX = object.getPosition().x;
		
		return itsX-myX;
	}
	

}
