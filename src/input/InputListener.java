package input;
//import org.lwjgl.input.Keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import objects.wall.*;
import parser.Attributes;
import parser.EnvironmentalParser;
import springies.Springies;
import externalForces.*;
import jboxGlue.WorldManager;
import jgame.platform.JGEngine;

public class InputListener extends AbstractListener{
	private Springies myEngine;
	private EnvironmentalParser myParser;
	
	public InputListener(Springies engine, EnvironmentalParser environmentParser){
		myEngine = engine;
		myParser = environmentParser;
	}
	
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
			WallForceToggle(Attributes.CEILING_ID);
			myEngine.clearKey(KeyEvent.VK_1);
		}
		if (getKey(KeyEvent.VK_2)){
			WallForceToggle(Attributes.RIGHT_WALL_ID);
			myEngine.clearKey(KeyEvent.VK_2);
		}
		if (getKey(KeyEvent.VK_3)){
			WallForceToggle(Attributes.FLOOR_ID);
			myEngine.clearKey(KeyEvent.VK_3);
		}
		if (getKey(KeyEvent.VK_4)){
			WallForceToggle(Attributes.LEFT_WALL_ID);
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
	
	private boolean getKey(int keyCode){
		return myEngine.getKey(keyCode);
	}
	
	private void WallForceToggle(String id){
		Wall currWall = WorldManager.getWorld().getWall(id);
		WallRepulsion currWallRepulsionForce = currWall.getRepulsionForce();
		currWallRepulsionForce.toggleWallForce();
	}
	
	private void makeNewWalls(double newMargin){
		WorldManager.getWorld().clearWalls();
		myParser.makeNewWalls(newMargin);
	}
}