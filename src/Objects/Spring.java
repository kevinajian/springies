package Objects;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import jboxGlue.PhysicalSpring;
import jgame.JGColor;
import jgame.JGObject;
import jgame.JGColor;
public class Spring extends PhysicalSpring {
	public static final JGColor COLOR = JGColor.black;
	
	public Spring( String name, int collisionId, Mass body1, Mass body2) {
		super(name, collisionId, COLOR, body1, body2);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void paintJoint() {
		myEngine.setColor( myColor );
		Vec2 position1 = myJoint.getBody1().getPosition();
		Vec2 position2 = myJoint.getBody2().getPosition();

		myEngine.drawLine(position1.x, position1.y, position2.x, position2.y);
	}

}
