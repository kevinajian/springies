package objects;


/**
 * Mass used by the mouse when interacting with assemblies
 * Like a fixed mass whose position can change
 * Extends FixedMass
 * @author tylernisonoff
 *
 */
public class MouseMass extends FixedMass {

	/**
	 * 
	 * @param id - JGame id to be used
	 * @param collisionId - CID for JGame collisions 
	 * @param x - x position
	 * @param y - y position
	 */
	public MouseMass(String id, int collisionId, double x, double y) {
		super(id, collisionId, x, y);
	}
	
	/**
	 * Updates the position of the mass
	 * @param x - x position
	 * @param y - y position
	 */
	public void updatePosition(double x, double y){
		setPos(x, y);
	}

}
