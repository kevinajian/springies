package objects;

import org.jbox2d.common.Vec2;

public class FixedMass extends Mass {

	public FixedMass(String id, int collisionId, double x, double y) {
		super(id, collisionId, x, y);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void applyForce(Vec2 force){
		
	}
}
