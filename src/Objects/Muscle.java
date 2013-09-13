package objects;

import jboxGlue.PhysicalSpring;
import jgame.JGColor;

public class Muscle extends PhysicalSpring {
	public static final JGColor COLOR = JGColor.black;

	public Muscle( String name, int collisionId, Mass body1, Mass body2) {
		super(name, 3, COLOR, body1, body2, (float)20.0, (float)1.0);
	}
}
