package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import objects.wall.*;
import parser.Constants;
import parser.EnvironmentalParser;
import springies.Springies;
import externalForces.*;
import jboxGlue.WorldManager;
import jgame.platform.JGEngine;

/**
 * Listener that checks for input during the simulation
 * @author Kevin
 *
 */
public class InputListener extends AbstractListener{
	private Springies myEngine;
	private EnvironmentalParser myParser;
	
	/**
	 * Sets engine and parser to be used
	 * @param engine - JGEngine that contains the simulation
	 * @param environmentParser - EnvironmentalParser that set the forces and created the walls
	 */
	public InputListener(Springies engine, EnvironmentalParser environmentParser){
		myEngine = engine;
		myParser = environmentParser;
	}
	
	/**
	 * Implemented in doFrame of Springies
	 * Checks for input and chooses proper reaction
	 */
	@Override
	public void listen(){
		// checks to create / clear assemblies
		if(getKey(KeyEvent.VK_N)){
			new AssemblyLoaderDialog(myEngine);
			myEngine.clearKey(KeyEvent.VK_N);
		}
		if(getKey(KeyEvent.VK_C)){
			WorldManager.getWorld().clearAssemblies();
			myEngine.clearKey(KeyEvent.VK_C);
		}
		// toggle forces
		if (getKey(KeyEvent.VK_G)){
			Gravity.toggleForce();
			myEngine.clearKey(KeyEvent.VK_G);
		}
		if (getKey(KeyEvent.VK_V)){
			Viscosity.toggleForce();
			myEngine.clearKey(KeyEvent.VK_V);
		}
		if (getKey(KeyEvent.VK_M)){
			CenterOfMass.toggleForce();
			myEngine.clearKey(KeyEvent.VK_M);
		}
		if (getKey(KeyEvent.VK_1)){
			WallForceToggle(Constants.CEILING_ID);
			myEngine.clearKey(KeyEvent.VK_1);
		}
		if (getKey(KeyEvent.VK_2)){
			WallForceToggle(Constants.RIGHT_WALL_ID);
			myEngine.clearKey(KeyEvent.VK_2);
		}
		if (getKey(KeyEvent.VK_3)){
			WallForceToggle(Constants.FLOOR_ID);
			myEngine.clearKey(KeyEvent.VK_3);
		}
		if (getKey(KeyEvent.VK_4)){
			WallForceToggle(Constants.LEFT_WALL_ID);
			myEngine.clearKey(KeyEvent.VK_4);
		}
		// change the walled size area
		if (getKey(KeyEvent.VK_DOWN)){
			double newMargin = -10;
			makeNewWalls(newMargin);
			myEngine.clearKey(KeyEvent.VK_DOWN);
		}
		if (getKey(KeyEvent.VK_UP)){
			double newMargin = 10;
			makeNewWalls(newMargin);
			myEngine.clearKey(KeyEvent.VK_UP);
		}
	}
	
	/**
	 * Gets the status of JGEngine for the key pressed
	 * @param keyCode code for the key pressed
	 * @return returns if the key was pressed
	 */
	private boolean getKey(int keyCode){
		return myEngine.getKey(keyCode);
	}
	
	/**
	 * Toggles the wall repulsion force
	 * @param id - String for the wall name
	 */
	private void WallForceToggle(String id){
		Wall currWall = WorldManager.getWorld().getWall(id);
		WallRepulsion currWallRepulsionForce = currWall.getRepulsionForce();
		currWallRepulsionForce.toggleWallForce();
	}
	
	/**
	 * Creates new walls based on the new margin
	 * @param newMargin - double of the size of the margin of the new walls being created
	 */
	private void makeNewWalls(double newMargin){
		WorldManager.getWorld().clearWalls();
		myParser.makeNewWalls(newMargin);
	}
}