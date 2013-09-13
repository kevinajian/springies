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
		super(name, 3, COLOR, body1, body2, (float)20.0, (float)1.0);
	}

	@Override
	protected void paintJoint() {
		myEngine.setColor( myColor );
		Vec2 position1 = myBody1.getBody().getPosition();
		Vec2 position2 = myBody2.getBody().getPosition();

		myEngine.drawLine(position1.x, position1.y, position2.x, position2.y);
	}

}
