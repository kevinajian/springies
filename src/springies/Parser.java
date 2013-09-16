package springies;
import jboxGlue.*;
import jgame.JGColor;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import objects.*;
import objects.wall.*;

import externalForces.*;

import jboxGlue.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

public class Parser {
	private HashMap<String, Mass> myMasses = new HashMap<String, Mass>();
	
	public Parser(){
	}
	
	public Document parse(File file){
		try{			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			
			return doc;
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public void createFixedMasses(NodeList masses, float gameHeight){
		String id; double x, y; int cID = 1; // defaults
		
		for (int i=0; i<masses.getLength(); i++){
			Node currMassObject = masses.item(i);
		    NamedNodeMap currMassAttr = currMassObject.getAttributes();
		    Node currID = currMassAttr.getNamedItem("id");
		    id = currID.getNodeValue();
		    x = Double.parseDouble(currMassAttr.getNamedItem("x").getNodeValue());
		    y = gameHeight - Double.parseDouble(currMassAttr.getNamedItem("y").getNodeValue());
		    
		    FixedMass mass = new FixedMass(id,cID,x,y);
		    myMasses.put(id, mass);
		  }
	}

		  
	public void createMasses(NodeList masses, float gameHeight){
		String id; int cID = 1; double x, y, mass=1; float xVel=0, yVel=0; // defaults
		for (int i=0; i<masses.getLength(); i++){
			Node currMassObject = masses.item(i);
			NamedNodeMap currMassAttr = currMassObject.getAttributes();
			id = currMassAttr.getNamedItem("id").getNodeValue();
			x = Double.parseDouble(currMassAttr.getNamedItem("x").getNodeValue());
			y = gameHeight - Double.parseDouble(currMassAttr.getNamedItem("y").getNodeValue());
			if (hasAttribute(currMassAttr,"mass")) mass = Double.parseDouble(currMassAttr.getNamedItem("mass").getNodeValue());
			if (hasAttribute(currMassAttr,"vx")) xVel = Float.parseFloat(currMassAttr.getNamedItem("vx").getNodeValue()); 
			if (hasAttribute(currMassAttr,"vy")) yVel = Float.parseFloat(currMassAttr.getNamedItem("vy").getNodeValue());
			
			Mass massObject = new Mass(id,cID,x,y,mass,xVel,yVel);
			myMasses.put(id, massObject);
		}
	}
		
	public void createSprings(NodeList springs){
		String id = "spring"; int cID = 2; float k = 1, length=0;Mass massA, massB; // defaults
	    
	    for (int i=0; i<springs.getLength(); i++){
	    	Node currSpring = springs.item(i);
	    	NamedNodeMap springAttr = currSpring.getAttributes();
	    	massA = myMasses.get(springAttr.getNamedItem("a").getNodeValue());
	    	massB = myMasses.get(springAttr.getNamedItem("b").getNodeValue());
	    	if (hasAttribute(springAttr,"restLength")) length = Float.parseFloat(springAttr.getNamedItem("restlength").getNodeValue());
	    	else length = massA.distance(massB); // default to distance between mass A and mass B
	    	if (hasAttribute(springAttr,"k")) k = Float.parseFloat(springAttr.getNamedItem("constant").getNodeValue());
	      
	    	Spring spring = new Spring(id, cID, massA, massB, length, k);
	    }
	}
	  
	// can combine with createSprings - if (has amplitude) create muscle, else spring;
	public void createMuscles(NodeList muscles){
		String id = "muscle"; int cID = 2; float k = 1, length = 0, amplitude, frequency = 20; Mass massA, massB; // defaults
	    
	    for (int i=0; i<muscles.getLength(); i++){
	    	Node currMuscle = muscles.item(i);
	    	NamedNodeMap muscleAttr = currMuscle.getAttributes();
	    	massA = myMasses.get(muscleAttr.getNamedItem("a").getNodeValue());
	    	massB = myMasses.get(muscleAttr.getNamedItem("b").getNodeValue());
	    	if (hasAttribute(muscleAttr,"restLength")) length = Float.parseFloat(muscleAttr.getNamedItem("restlength").getNodeValue());
	    	else length = massA.distance(massB); // default to distance between mass A and mass B
	    	if (hasAttribute(muscleAttr,"k")) k = Float.parseFloat(muscleAttr.getNamedItem("constant").getNodeValue());
	    	amplitude = Float.parseFloat(muscleAttr.getNamedItem("amplitude").getNodeValue());
	      
	    	Muscle muscle = new Muscle(id, cID, massA, massB, length, k, amplitude, frequency);
	    }
	}
	  
	public void setGravity(NodeList gravity){
		float direction = Float.parseFloat(gravity.item(0).getAttributes().getNamedItem("direction").getNodeValue());
	    float magnitude = Float.parseFloat(gravity.item(0).getAttributes().getNamedItem("magnitude").getNodeValue());
	    
	    Gravity g = new Gravity(direction, magnitude);
	    WorldManager.getWorld().addForce(g);
	}
	 
	public void setViscosity(NodeList viscosity){
	    float magnitude = Float.parseFloat(viscosity.item(0).getAttributes().getNamedItem("magnitude").getNodeValue());
	    
	    Viscosity v = new Viscosity(magnitude);
	    WorldManager.getWorld().addForce(v);
	}
	  
	public void setCenterMass(NodeList centerMass){
		float magnitude = Float.parseFloat(centerMass.item(0).getAttributes().getNamedItem("magnitude").getNodeValue());
	    float exponent = Float.parseFloat(centerMass.item(0).getAttributes().getNamedItem("exponent").getNodeValue());
	    
	    if (exponent==2){
	    	CenterOfMass com = new CenterOfMass(magnitude,exponent);
	    }
	    else if (exponent==0){
	    	CenterOfMass com = new CenterOfMass(magnitude,exponent);
	    }
	    
	}
	  
	public void setWalls(NodeList walls, double width, double height, double thickness,double margin,double displayWidth,double displayHeight){
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
	
	private boolean hasAttribute(NamedNodeMap attributes, String attribute){
	    for (int i=0; i<attributes.getLength(); i++){
	    	if (attributes.item(i).getNodeName().equals(attribute)) return true;
	    }
	    return false;
	}	
}
