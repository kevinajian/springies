package objects;

import jboxGlue.PhysicalSpring;
import jgame.JGColor;

public class Muscle extends PhysicalSpring {
	private static final JGColor COLOR = JGColor.black;
	public static final float DEFAULT_RESTLENGTH = 20;
	public static final float DEFAULT_SPRINGYNESS = 1;
	public static final float DEFAULT_AMPLITUDE = 1;
	public static final float DEFAULT_FREQUENCY = 20;

	public float myRestLength;
	public float mySpringyness;
	public float myAmplitude;
	public float myFrequency;
	
	public Muscle( String name, int collisionId, Mass body1, Mass body2) {
		this(name, 4, body1, body2, DEFAULT_RESTLENGTH, DEFAULT_SPRINGYNESS, DEFAULT_AMPLITUDE, DEFAULT_FREQUENCY);
	}
	
	public Muscle( String name, int collisionId, Mass body1, Mass body2, 
					float restLength, float springyness, float amplitude, float frequency) {
		super(name, 3, COLOR, body1, body2);
		myRestLength = restLength;
		mySpringyness = springyness;
		myAmplitude = amplitude;
		myFrequency = frequency;
	}
	
	public void setRestLength(float length){
		myRestLength = length;
	}
	
	public void setSpringyness(float k){
		mySpringyness = k;
	}
	
	public void setAmplitude(float amplitude){
		myAmplitude = amplitude;
	}
	
	public void setFrequency(float frequency){
		myFrequency = frequency;
	}

	@Override
	public float calculateForce(float distance) {
		int frame = myEngine.getFrame();
		float currentRestLength = (myRestLength) * (float) (1 + myAmplitude*Math.sin((float)frame / (myFrequency)));
		float force = mySpringyness*(currentRestLength-distance);
		return force;
	}
}
