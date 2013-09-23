package objects;

import org.jbox2d.common.Vec2;

/**
 * Like a mass but does not move!
 * @author tylernisonoff
 *
 */
public class FixedMass extends Mass {

	/**
	 * 
	 * @param id - JGame id to be used
	 * @param collisionId - CID for JGame collisions 
	 * @param x - x position
	 * @param y - y position
	 */
	public FixedMass(String id, int collisionId, double x, double y) {
		super(id, collisionId, x, y);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * The main fixed mass functionality - not allowing anything to apply forces to it
	 * so that it stays fixed
	 */
	@Override
	public void applyForce(Vec2 force){
		
	}
}
