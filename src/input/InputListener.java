package input;
//import org.lwjgl.input.Keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import objects.wall.*;
import parser.EnvironmentalParser;
import springies.Springies;
import externalForces.*;
import jboxGlue.WorldManager;
import jgame.platform.JGEngine;



public class InputListener extends AbstractListener{
	private JGEngine myEngine;
	private EnvironmentalParser myParser;
	
	public InputListener(JGEngine engine){
		myEngine = engine;
	}
	
	@Override
	public void listen(){//KeyEvent e){
		// checks to create / clear assemblies
		if(getKey(KeyEvent.VK_N)){
			new AssemblyLoaderDialog((Springies) myEngine);
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
			WallForceToggle("1");
			myEngine.clearKey(KeyEvent.VK_1);
		}
		if (getKey(KeyEvent.VK_2)){
			WallForceToggle("2");
			myEngine.clearKey(KeyEvent.VK_2);
		}
		if (getKey(KeyEvent.VK_3)){
			WallForceToggle("3");
			myEngine.clearKey(KeyEvent.VK_3);
		}
		if (getKey(KeyEvent.VK_4)){
			WallForceToggle("4");
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
		myParser.setWallRepulsionForceToggle(Integer.parseInt(id)-1);
	}
	
	public void makeNewWalls(double newMargin){
		WorldManager.getWorld().clearWalls();
		myParser.makeNewWalls(newMargin);
	}
}
