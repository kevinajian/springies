package springies;

import java.io.File;

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
	private final double WALL_MARGIN = 10;
	private final double WALL_THICKNESS = 10;
	private final double WALL_WIDTH; 
	private final double WALL_HEIGHT; 
	
	protected EnvironmentalParser(File file, JGEngine engine) {
		super(file, engine);
		WALL_WIDTH  = engine.displayWidth() - WALL_MARGIN*2 + WALL_THICKNESS;
		WALL_HEIGHT = engine.displayHeight() - WALL_MARGIN*2 + WALL_THICKNESS;
	}

	@Override
	public void parse() {
		// TODO Auto-generated method stub
		setGravity(myDocument.getElementsByTagName("gravity"));
		setViscosity(myDocument.getElementsByTagName("viscosity"));
		setCenterMass(myDocument.getElementsByTagName("centermass"));
		setWalls(myDocument.getElementsByTagName("wall"),WALL_WIDTH,WALL_HEIGHT,WALL_THICKNESS,WALL_MARGIN,
															myEngine.displayWidth(),myEngine.displayHeight());

	}
	
	private void setGravity(NodeList gravity){
		float direction = Float.parseFloat(gravity.item(0).getAttributes().getNamedItem("direction").getNodeValue());
	    float magnitude = Float.parseFloat(gravity.item(0).getAttributes().getNamedItem("magnitude").getNodeValue());
	    
	    Gravity g = new Gravity(direction, magnitude);
	    WorldManager.getWorld().addForce(g);
	}
	 
	private void setViscosity(NodeList viscosity){
	    float magnitude = Float.parseFloat(viscosity.item(0).getAttributes().getNamedItem("magnitude").getNodeValue());
	    
	    Viscosity v = new Viscosity(magnitude);
	    WorldManager.getWorld().addForce(v);
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
	
	private void setWalls(NodeList walls, double width, double height, double thickness,double margin,double displayWidth,double displayHeight){
	    for(int i=0; i<walls.getLength(); i++){
	    	int id = Integer.parseInt(walls.item(i).getAttributes().getNamedItem("id").getNodeValue());
	    	float magnitude =0;// Float.parseFloat(walls.item(i).getAttributes().getNamedItem("magnitude").getNodeValue());
	    	float exponent = Float.parseFloat(walls.item(i).getAttributes().getNamedItem("exponent").getNodeValue());
	    	margin=0;
	    	if (id==1){
	    		Wall ceiling = new TopWall("wallC", 2, JGColor.green, width, thickness);
	    		ceiling.setPos(displayWidth/2, margin);
	    		ceiling.setRepulsionForce(magnitude,exponent);
	    	}
	    	else if (id==2){
	    		Wall floor = new BottomWall( "wallF", 2, JGColor.green, width, thickness);
				floor.setPos(displayWidth/2, displayHeight - margin);
				floor.setRepulsionForce(magnitude,exponent);
	    	}
	    	else if (id==3){
	    		Wall left = new LeftWall("wallL", 2, JGColor.green, thickness, height);
	    		left.setPos(margin, displayHeight/2);
	    		left.setRepulsionForce(magnitude,exponent);
	    	}
	    	else if (id==4){
	    		Wall right = new RightWall("wallR", 2, JGColor.green, thickness, height);
	    		right.setPos(displayWidth-margin, displayHeight/2);
	    		right.setRepulsionForce(magnitude,exponent);
	    	}
	    }
	}
	  

}
