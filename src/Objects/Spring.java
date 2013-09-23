package objects;

import org.jbox2d.common.Vec2;

import org.jbox2d.dynamics.Body;

import jboxGlue.PhysicalSpring;
import jboxGlue.WorldManager;
import jgame.JGColor;
import jgame.JGObject;
import jgame.JGColor;

/**
 * Extends Muscle class and uses an amplitude of 0, frequency of 1
 * A Muscle with a constant restlength
 * @author tylernisonoff
 *
 */
public class Spring extends Muscle {
	public static final JGColor COLOR = JGColor.black;
	
	/**
	 * 
	 * @param id - JGame id for finding objects 
	 * @param collisionId - JGame CID to check collisions
	 * @param body1 - first Body for the muscle
	 * @param body2 - second Body for the muscle
	 */
	public Spring( String id, int collisionId, Mass body1, Mass body2) {
		this(id, 3, body1, body2, DEFAULT_RESTLENGTH);
	}
	
	/**
	 * 
	 * @param id - JGame id for finding objects 
	 * @param collisionId - JGame CID to check collisions
	 * @param body1 - first Body for the muscle
	 * @param body2 - second Body for the muscle
	 * @param restLength - restlength for Hookes Law calculation
	 */
	public Spring( String id, int collisionId, Mass body1, Mass body2, float restLength) {
		this(id, 3, body1, body2, restLength, DEFAULT_SPRINGYNESS);
	}
	
	/**
	 * 
	 * @param id - JGame id for finding objects 
	 * @param collisionId - JGame CID to check collisions
	 * @param body1 - first Body for the muscle
	 * @param body2 - second Body for the muscle
	 * @param restLength - restlength for Hookes Law calculation
	 * @param sprinyness - K constant in Hookes Law
	 */
	public Spring( String id, int collisionId, Mass body1, Mass body2, float restLength, float springyness) {
		super(id, 3, body1, body2, restLength, springyness, 0,1);
	}

}
