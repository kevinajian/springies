package objects;

public class MouseMass extends FixedMass {

	public MouseMass(String id, int collisionId, double x, double y) {
		super(id, collisionId, x, y);
	}
	
	public void updatePosition(double x, double y){
		setPos(x, y);
	}

}
