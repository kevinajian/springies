package springies;
import input.AssemblyLoaderDialog;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
			
			myEngine.clearKey(KeyEvent.VK_G);
		}
		if (myEngine.getKey(KeyEvent.VK_V)){
			
			myEngine.clearKey(KeyEvent.VK_V);
		}
		if (myEngine.getKey(KeyEvent.VK_M)){
			
			myEngine.clearKey(KeyEvent.VK_M);
		}
		if (myEngine.getKey(KeyEvent.VK_1)){
		}
		if (myEngine.getKey(KeyEvent.VK_2)){
			
			myEngine.clearKey(KeyEvent.VK_2);
		}
		if (myEngine.getKey(KeyEvent.VK_3)){
			
			myEngine.clearKey(KeyEvent.VK_3);
		}
		if (myEngine.getKey(KeyEvent.VK_4)){
			
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
		//WorldManager.getWorld().clearWall("1");
		//WorldManager.getWorld().clearWall("2");
		//WorldManager.getWorld().clearWall("3");
		//WorldManager.getWorld().clearWall("4");
		WorldManager.getWorld().clearWalls();
		myParser.makeNewWalls(newMargin);
	}
}
