package parser;

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

	public EnvironmentalParser(File file, JGEngine engine) {
		super(file, engine);
		myEngine = engine;
		wall_width  = engine.displayWidth() - wall_margin*2 + WALL_THICKNESS;
		wall_height = engine.displayHeight() - wall_margin*2 + WALL_THICKNESS;
	}

	@Override
	public void parse() {
		setGravity(myDocument.getElementsByTagName(Attributes.GRAVITY_ELEMENT));
		setViscosity(myDocument.getElementsByTagName(Attributes.VISCOSITY_ELEMENT));
		setCenterMass(myDocument.getElementsByTagName(Attributes.CENTER_OF_MASS_ELEMENT));
		setWalls(myDocument.getElementsByTagName(Attributes.WALL_ELEMENT));
	}

	// Sets gravitational force based on environment data file.
	private void setGravity(NodeList gravity){
		float direction = Float.parseFloat(gravity.item(0).getAttributes().getNamedItem(Attributes.DIRECTION).getNodeValue());
	    float magnitude = Float.parseFloat(gravity.item(0).getAttributes().getNamedItem(Attributes.MAGNITUDE).getNodeValue());

	    Gravity g = new Gravity(direction, magnitude);
	    WorldManager.getWorld().addForce(g);
	}

	// Sets viscosity force based on environment data file
	private void setViscosity(NodeList viscosity){
	    float magnitude = Float.parseFloat(viscosity.item(0).getAttributes().getNamedItem(Attributes.MAGNITUDE).getNodeValue());

	    Viscosity v = new Viscosity(magnitude);
	    WorldManager.getWorld().addForce(v);
	}

	// Sets center of mass force based on environment data file
	private void setCenterMass(NodeList centerMass){
		float magnitude = Float.parseFloat(centerMass.item(0).getAttributes().getNamedItem(Attributes.MAGNITUDE).getNodeValue());
	    float exponent = Float.parseFloat(centerMass.item(0).getAttributes().getNamedItem(Attributes.EXPONENT).getNodeValue());
	    
	    CenterOfMass com = new CenterOfMass(magnitude,exponent);
	    WorldManager.getWorld().addForce(com);
	}
	 
	// Creates walls and sets wall repulsion forces based on environment data file
	private void setWalls(NodeList walls){
	    for(int i=0; i<walls.getLength(); i++){
	    	String id = walls.item(i).getAttributes().getNamedItem(Attributes.ID).getNodeValue();
	    	float magnitude = 0;
	    	float exponent = Float.parseFloat(walls.item(i).getAttributes().getNamedItem(Attributes.EXPONENT).getNodeValue());
	    	if (id.equals(Attributes.CEILING_ID)){
	    		Wall ceiling = new TopWall(id, 2, JGColor.green, wall_width, WALL_THICKNESS);
	    		ceiling.setPos(myEngine.displayWidth()/2, wall_margin);
	    		ceiling.setRepulsionForce(magnitude,exponent);
	    		WorldManager.getWorld().addWall(id, ceiling);
	    	}
	    	else if (id.equals(Attributes.FLOOR_ID)){
	    		Wall floor = new BottomWall(id, 2, JGColor.green, wall_width, WALL_THICKNESS);
				floor.setPos(myEngine.displayWidth()/2, myEngine.displayHeight() - wall_margin);
				floor.setRepulsionForce(magnitude,exponent);
				WorldManager.getWorld().addWall(id, floor);
	    	}
	    	else if (id.equals(Attributes.LEFT_WALL_ID)){
	    		Wall left = new LeftWall(id, 2, JGColor.green, WALL_THICKNESS, wall_height);
	    		left.setPos(wall_margin, myEngine.displayHeight()/2);
	    		left.setRepulsionForce(magnitude,exponent);
	    		WorldManager.getWorld().addWall(id, left);
	    	}
	    	else if (id.equals(Attributes.RIGHT_WALL_ID)){
	    		Wall right = new RightWall(id, 2, JGColor.green, WALL_THICKNESS, wall_height);
	    		right.setPos(myEngine.displayWidth()-wall_margin, myEngine.displayHeight()/2);
	    		right.setRepulsionForce(magnitude,exponent);
	    		WorldManager.getWorld().addWall(id, right);
	    	}
	    }
	}

	// Sets height and width of walls based on current wall margins.
	private void setWallDimensions(){
		wall_width  = myEngine.displayWidth() - wall_margin*2 + WALL_THICKNESS;
		wall_height = myEngine.displayHeight() - wall_margin*2 + WALL_THICKNESS;
	}
	
	// Creates new walls based on new margin.
	public void makeNewWalls(double newMargin){
		wall_margin+=newMargin;
		setWallDimensions();
		setWalls(myDocument.getElementsByTagName(Attributes.WALL_ELEMENT));
	}

}