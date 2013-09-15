package springies;
import jboxGlue.*;
import jgame.JGColor;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import Objects.Mass;
import Objects.Spring;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

public class Parser {
	private String myPath;
	//private J
	
	public Parser(String path){
		myPath = path;
	}
	
	public Document parse(){
		try{			
			File data = new File(myPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(data);
			doc.getDocumentElement().normalize();
			
			return doc;
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	public void createFixedMasses(NodeList masses){
		String id; double x, y; int cID = 1; // defaults
		
		for (int i=0; i<masses.getLength(); i++){
			Node currMassObject = masses.item(i);
		    NamedNodeMap currMassAttr = currMassObject.getAttributes();
		    Node currID = currMassAttr.getNamedItem("id");
		    id = currID.getNodeValue();
		    x = Double.parseDouble(currMassAttr.getNamedItem("x").getNodeValue());
		    y = Double.parseDouble(currMassAttr.getNamedItem("y").getNodeValue());
		    
		    //Mass mass = new Mass(id,cID,x,y);
		  }
		}
		  
	public void createMasses(NodeList masses){
		String id; int cID = 1; double x, y, mass=0; float xVel=0, yVel=0; // defaults
		for (int i=0; i<masses.getLength(); i++){
			Node currMassObject = masses.item(i);
			NamedNodeMap currMassAttr = currMassObject.getAttributes();
			id = currMassAttr.getNamedItem("id").getNodeValue();
			x = Double.parseDouble(currMassAttr.getNamedItem("x").getNodeValue());
			y = Double.parseDouble(currMassAttr.getNamedItem("y").getNodeValue());
			if (hasAttribute(currMassAttr,"mass")) mass = Double.parseDouble(currMassAttr.getNamedItem("mass").getNodeValue());
			System.out.println(mass);
			if (hasAttribute(currMassAttr,"vx")) xVel = Float.parseFloat(currMassAttr.getNamedItem("vx").getNodeValue()); 
			if (hasAttribute(currMassAttr,"vy")) yVel = Float.parseFloat(currMassAttr.getNamedItem("vy").getNodeValue());
			
			//Mass massObject = new Mass(id,cID,x,y,mass,xVel,yVel);
		}
	}
		
	public void createSprings(NodeList springs){
		String id = "spring"; int cID = 2; double k = 1;Mass massA, massB; // defaults
	    
	    for (int i=0; i<springs.getLength(); i++){
	    	Node currSpring = springs.item(i);
	    	NamedNodeMap springAttr = currSpring.getAttributes();
	    	//massA = (Mass) getObject(springAttr.getNamedItem("a").getNodeValue());
	    	//massB = (Mass) getObject(springAttr.getNamedItem("b").getNodeValue());
	    	Node length = springAttr.getNamedItem("restLength"); // default distance b/n a and b
	    	if (hasAttribute(springAttr,"k")) k = Double.parseDouble(springAttr.getNamedItem("constant").getNodeValue());
	      
	    	//Spring spring = new Spring(id, cID, massA, massB);
	    }
	}
	  
	// can combine with createSprings - if (has amplitude) create muscle, else spring;
	public void createMuscles(NodeList muscles){
		String id = "muscle"; int cID = 2; double k = 1, amplitude; Mass massA, massB; // defaults
	    
	    for (int i=0; i<muscles.getLength(); i++){
	    	Node currMuscle = muscles.item(i);
	    	NamedNodeMap muscleAttr = currMuscle.getAttributes();
	    	//massA = (Mass) getObject(muscleAttr.getNamedItem("a").getNodeValue());
	    	//massB = (Mass) getObject(muscleAttr.getNamedItem("b").getNodeValue());
	    	Node length = muscleAttr.getNamedItem("restLength"); // default distance b/n a and b
	    	if (hasAttribute(muscleAttr,"k")) k = Double.parseDouble(muscleAttr.getNamedItem("constant").getNodeValue());
	    	amplitude = Double.parseDouble(muscleAttr.getNamedItem("amplitude").getNodeValue());
	      
	    	// create muscles Muscle muscle = new Muscle();
	    }
	}
	  
	public void setGravity(NodeList gravity){
		int direction = Integer.parseInt(gravity.item(0).getAttributes().getNamedItem("direction").getNodeValue());
	    int magnitude = Integer.parseInt(gravity.item(0).getAttributes().getNamedItem("magnitude").getNodeValue());
	}
	  
	public void setViscosity(NodeList viscosity){
	    double magnitude = Double.parseDouble(viscosity.item(0).getAttributes().getNamedItem("magnitude").getNodeValue());
	}
	  
	public void setCenterMass(NodeList centerMass){
		int magnitude = Integer.parseInt(centerMass.item(0).getAttributes().getNamedItem("magnitude").getNodeValue());
	    double exponent = Double.parseDouble(centerMass.item(0).getAttributes().getNamedItem("exponent").getNodeValue());
	}
	  
	public void setWalls(NodeList walls){
	    for(int i=0; i<walls.getLength(); i++){
	    	int magnitude = Integer.parseInt(walls.item(i).getAttributes().getNamedItem("magnitude").getNodeValue());
	    	double exponent = Double.parseDouble(walls.item(i).getAttributes().getNamedItem("exponent").getNodeValue());
	      
	    	// create wall objects
//		    	PhysicalObject wall = new PhysicalObjectRect( "wall", 2, JGColor.green, WALL_WIDTH, WALL_THICKNESS );
	    }
	  }
	  
	private boolean hasAttribute(NamedNodeMap attributes, String attribute){
	    for (int i=0; i<attributes.getLength(); i++){
	    	if (attributes.item(i).getNodeName().equals(attribute)) return true;
	    }
	    return false;
	}
	
	// calculate distance between two masses
	private double getRestLength(Mass a, Mass b){
	    return 0;
	}	
}
