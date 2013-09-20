package springies;

import java.io.File;
import java.util.*;

import jboxGlue.WorldManager;
import jgame.JGColor;
import jgame.platform.JGEngine;

import objects.wall.BottomWall;
import objects.wall.LeftWall;
import objects.wall.RightWall;
import objects.wall.TopWall;
import objects.wall.Wall;

import org.w3c.dom.NodeList;

import externalForces.CenterOfMass;
import externalForces.Gravity;
import externalForces.Viscosity;

public class EnvironmentalParser extends Parser{
	private double wall_margin = 10;
	private final double WALL_THICKNESS = 10;
	private double wall_width; 
	private double wall_height;
	private JGEngine myEngine;
	private HashMap myWalls = new HashMap();

	protected EnvironmentalParser(File file, JGEngine engine) {
		super(file, engine);
		myEngine = engine;
		wall_width  = engine.displayWidth() - wall_margin*2 + WALL_THICKNESS;
		wall_height = engine.displayHeight() - wall_margin*2 + WALL_THICKNESS;
	}

	@Override
	public void parse() {
		setGravity(myDocument.getElementsByTagName("gravity"));
		setViscosity(myDocument.getElementsByTagName("viscosity"));
		setCenterMass(myDocument.getElementsByTagName("centermass"));
		setWalls(myDocument.getElementsByTagName("wall"));
	}

	private void setGravity(NodeList gravity){
		float direction = Float.parseFloat(gravity.item(0).getAttributes().getNamedItem("direction").getNodeValue());
	    float magnitude = Float.parseFloat(gravity.item(0).getAttributes().getNamedItem("magnitude").getNodeValue());

	    Gravity g = new Gravity(direction, magnitude);
	    WorldManager.getWorld().addForce(g);
	    //WorldManager.getWorld().addForce("Gravity", g);
	}

	private void setViscosity(NodeList viscosity){
	    float magnitude = Float.parseFloat(viscosity.item(0).getAttributes().getNamedItem("magnitude").getNodeValue());

	    Viscosity v = new Viscosity(magnitude);
	    WorldManager.getWorld().addForce(v);
	    //WorldManager.getWorld().addForce("Viscosity", v);
	}

	private void setCenterMass(NodeList centerMass){
		float magnitude = Float.parseFloat(centerMass.item(0).getAttributes().getNamedItem("magnitude").getNodeValue());
	    float exponent = Float.parseFloat(centerMass.item(0).getAttributes().getNamedItem("exponent").getNodeValue());

	    if (exponent==2){
	    	CenterOfMass com = new CenterOfMass(magnitude,exponent);
	    }
	    else if (exponent==0){
	    	CenterOfMass com = new CenterOfMass(magnitude,exponent);
	    }
	}
	
	public Wall getWall(String id){
		return (Wall) myWalls.get(id);
	}

	private void setWalls(NodeList walls){
	    for(int i=0; i<walls.getLength(); i++){
	    	String id = walls.item(i).getAttributes().getNamedItem("id").getNodeValue();
	    	float magnitude =0;// Float.parseFloat(walls.item(i).getAttributes().getNamedItem("magnitude").getNodeValue());
	    	float exponent = Float.parseFloat(walls.item(i).getAttributes().getNamedItem("exponent").getNodeValue());
	    	if (id.equals("1")){
	    		Wall ceiling = new TopWall(id, 2, JGColor.green, wall_width, WALL_THICKNESS);
	    		ceiling.setPos(myEngine.displayWidth()/2, wall_margin);
	    		ceiling.setRepulsionForce(magnitude,exponent);
	    		myWalls.put(id, ceiling);
	    		WorldManager.getWorld().addWall(ceiling);
	    	}
	    	else if (id.equals("2")){
	    		Wall floor = new BottomWall(id, 2, JGColor.green, wall_width, WALL_THICKNESS);
				floor.setPos(myEngine.displayWidth()/2, myEngine.displayHeight() - wall_margin);
				floor.setRepulsionForce(magnitude,exponent);
				myWalls.put(id, floor);
				WorldManager.getWorld().addWall(floor);
	    	}
	    	else if (id.equals("3")){
	    		Wall left = new LeftWall(id, 2, JGColor.green, WALL_THICKNESS, wall_height);
	    		left.setPos(wall_margin, myEngine.displayHeight()/2);
	    		left.setRepulsionForce(magnitude,exponent);
	    		myWalls.put(id, left);
	    		WorldManager.getWorld().addWall(left);
	    	}
	    	else if (id.equals("4")){
	    		Wall right = new RightWall(id, 2, JGColor.green, WALL_THICKNESS, wall_height);
	    		right.setPos(myEngine.displayWidth()-wall_margin, myEngine.displayHeight()/2);
	    		right.setRepulsionForce(magnitude,exponent);
	    		myWalls.put(id, right);
	    		WorldManager.getWorld().addWall(right);
	    	}
	    }
	}

	private void setWallDimensions(){
		wall_width  = myEngine.displayWidth() - wall_margin*2 + WALL_THICKNESS;
		wall_height = myEngine.displayHeight() - wall_margin*2 + WALL_THICKNESS;
	}

	public void makeNewWalls(double newMargin){
		wall_margin+=newMargin;
		setWallDimensions();
		setWalls(myDocument.getElementsByTagName("wall"));
	}

}