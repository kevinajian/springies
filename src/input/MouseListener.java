package input;

import jboxGlue.WorldManager;
import jgame.platform.JGEngine;
import objects.Mass;
import objects.MouseMass;
import objects.Spring;

public class MouseListener {
	private MouseMass myMass;
	private Spring mySpring;
	private JGEngine myEngine;
	private boolean lastFrameWasClicked = false;
	public MouseListener(JGEngine eng){
		myEngine = eng;
	}
	
	public void listen(){
		if(myEngine.getMouseButton(1)){
			if(!lastFrameWasClicked)
				handleFirstClick();
			else
				handleContinuedClick();
		} else{
			if(lastFrameWasClicked)
				handleRelease();
			else
				handleContinuedRelease();
		}
	}
	
	private void handleFirstClick(){
		myMass = new MouseMass("fixed", 1, myEngine.getMouseX(), myEngine.getMouseY());
		Mass closestMass = WorldManager.getWorld().findClosestMass(myMass);
		mySpring = new Spring("mouseSpring", 2, myMass, closestMass, closestMass.distance(myMass));
		mySpring.setSpringyness(100);
		lastFrameWasClicked = true;
	}
	
	private void handleRelease(){
		lastFrameWasClicked = false;
		myMass.remove();
		myMass = null;
		mySpring.remove();
		mySpring = null;
	}
	
	private void handleContinuedClick(){
		myMass.updatePosition(myEngine.getMouseX(), myEngine.getMouseY());
	}
	
	private void handleContinuedRelease(){
		
	}
}
