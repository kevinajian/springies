package springies;
import input.AssemblyLoaderDialog;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import objects.wall.*;
import externalForces.*;
import jboxGlue.WorldManager;
import jgame.platform.JGEngine;

public class InputListener{
	private JGEngine myEngine;
	private EnvironmentalParser myParser;
	
	public InputListener(JGEngine engine, EnvironmentalParser parser){
		myEngine = engine;
		myParser = parser;
	}
	
	public void checkForInput(){
		// checks to create / clear assemblies
		if(myEngine.getKey(KeyEvent.VK_N)){
			new AssemblyLoaderDialog((Springies) myEngine);
			myEngine.clearKey(KeyEvent.VK_N);
		}
		if(myEngine.getKey(KeyEvent.VK_C)){
			WorldManager.getWorld().clearAssemblies();
			myEngine.clearKey(KeyEvent.VK_C);
		}
		
		// toggle forces
		if (myEngine.getKey(KeyEvent.VK_G)){
			Gravity.toggleForce();
			//System.out.println("nothing");
			myEngine.clearKey(KeyEvent.VK_G);
		}
		if (myEngine.getKey(KeyEvent.VK_V)){
			Viscosity.toggleForce();
			myEngine.clearKey(KeyEvent.VK_V);
		}
		if (myEngine.getKey(KeyEvent.VK_M)){
			CenterOfMass.toggleForce();
			myEngine.clearKey(KeyEvent.VK_M);
		}
		if (myEngine.getKey(KeyEvent.VK_1)){
			Wall currWall = myParser.getWall("1");
			//System.out.println(currWall.getName());
			WallRepulsion currWallRepulsionForce = currWall.getRepulsionForce();
			currWallRepulsionForce.toggleForce();
			myEngine.clearKey(KeyEvent.VK_1);
		}
		if (myEngine.getKey(KeyEvent.VK_2)){
			Wall currWall = myParser.getWall("2");
			WallRepulsion currWallRepulsionForce = currWall.getRepulsionForce();
			currWallRepulsionForce.toggleForce();
			myEngine.clearKey(KeyEvent.VK_2);
		}
		if (myEngine.getKey(KeyEvent.VK_3)){
			Wall currWall = myParser.getWall("3");
			WallRepulsion currWallRepulsionForce = currWall.getRepulsionForce();
			currWallRepulsionForce.toggleForce();
			myEngine.clearKey(KeyEvent.VK_3);
		}
		if (myEngine.getKey(KeyEvent.VK_4)){
			Wall currWall = myParser.getWall("4");
			WallRepulsion currWallRepulsionForce = currWall.getRepulsionForce();
			currWallRepulsionForce.toggleForce();
			myEngine.clearKey(KeyEvent.VK_4);
		}
		
		// change the walled size area
		if (myEngine.getKey(KeyEvent.VK_DOWN)){
			double newMargin = -10;
			makeNewWalls(newMargin);
			myEngine.clearKey(KeyEvent.VK_DOWN);
		}
		if (myEngine.getKey(KeyEvent.VK_UP)){
			double newMargin = 10;
			makeNewWalls(newMargin);
			myEngine.clearKey(KeyEvent.VK_UP);
		}
	}
	
	public void makeNewWalls(double newMargin){
		WorldManager.getWorld().clearWalls();
		myParser.makeNewWalls(newMargin);
	}
}
