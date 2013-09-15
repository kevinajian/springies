package Objects;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import jboxGlue.PhysicalSpring;
import jboxGlue.WorldManager;
import jgame.JGColor;
import jgame.JGObject;
import jgame.JGColor;
public class Spring extends Muscle {
	public static final JGColor COLOR = JGColor.black;
	
	public Spring( String name, int collisionId, Mass body1, Mass body2) {
		this(name, 3, body1, body2, DEFAULT_RESTLENGTH);
	}
	
	public Spring( String name, int collisionId, Mass body1, Mass body2, float restLength) {
		this(name, 3, body1, body2, restLength, DEFAULT_SPRINGYNESS);
	}
	
	public Spring( String name, int collisionId, Mass body1, Mass body2, float restLength, float springyness) {
		super(name, 3, body1, body2, restLength, springyness, 0,1);
	}

}
