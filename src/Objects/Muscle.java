package objects;

import jboxGlue.PhysicalSpring;
import jgame.JGColor;

public class Muscle extends PhysicalSpring {
	private static final JGColor DEFAULT_COLOR = JGColor.black;
	public static final float DEFAULT_RESTLENGTH = 20;
	public static final float DEFAULT_SPRINGYNESS = 1;
	public static final float DEFAULT_AMPLITUDE = 1;
	public static final float DEFAULT_FREQUENCY = 20;
	public static final int DEFAULT_CID = 2;

	public float myRestLength;
	public float mySpringyness;
	public float myAmplitude;
	public float myFrequency;
	
	/**
	 * 
	 * @param id - JGame id for finding objects 
	 * @param collisionId - JGame CID to check collisions
	 * @param body1 - first Body for the muscle
	 * @param body2 - second Body for the muscle
	 */
	public Muscle( String id, int collisionId, Mass body1, Mass body2) {
		this(id, 4, body1, body2, DEFAULT_RESTLENGTH, DEFAULT_SPRINGYNESS, DEFAULT_AMPLITUDE, DEFAULT_FREQUENCY);
	}
	
	/**
	 * 
	 * @param id - JGame id for finding objects 
	 * @param collisionId - JGame CID to check collisions
	 * @param body1 - first Body for the muscle
	 * @param body2 - second Body for the muscle
	 * @param restLength - rest length used in Hookes law calculation
	 * @param springyness - K constant in Hookes law
	 * @param amplitude - amplitude of the muscle
	 * @param frequency - frequence of the muscle
	 */
	public Muscle( String id, int collisionId, Mass body1, Mass body2, 
					float restLength, float springyness, float amplitude, float frequency) {
		super(id, 3, DEFAULT_COLOR, body1, body2);
		myRestLength = restLength;
		mySpringyness = springyness;
		myAmplitude = amplitude;
		myFrequency = frequency;
	}
	
	/**
	 * 
	 * @param length restlength to set
	 */
	public void setRestLength(float length){
		myRestLength = length;
	}
	
	/**
	 * 
	 * @param k springyness to set
	 */
	public void setSpringyness(float k){
		mySpringyness = k;
	}
	
	/**
	 * 
	 * @param amplitude amplitude to set
	 */
	public void setAmplitude(float amplitude){
		myAmplitude = amplitude;
	}
	
	/**
	 * 
	 * @param frequency frequency to set
	 */
	public void setFrequency(float frequency){
		myFrequency = frequency;
	}
	
	/**
	 * Uses Hookes law to calculate a force
	 * @param distance - distance between the two masses
	 */
	@Override
	public float calculateForce(float distance) {
		int frame = myEngine.getFrame();
		float currentRestLength = (myRestLength) * (float) (1 + myAmplitude*Math.sin((float)frame / (myFrequency)));
		float force = mySpringyness*(currentRestLength-distance);
		return force;
	}
}
