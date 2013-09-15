package springies;
import jboxGlue.*;
import jgame.JGColor;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import Objects.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

public class Parser {
	private String myPath;
	private Springies myEngine;

	private HashMap<String, FixedMass> myFixedMasses = new HashMap<String, FixedMass>();
	private HashMap<String, Mass> myMasses = new HashMap<String, Mass>();
	
	public Parser(String path, Springies engine){
		myPath = path;
		myEngine = engine;
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
		    
		    FixedMass mass = new FixedMass(id,cID,x,y);
		    myFixedMasses.put(id, mass);
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
			
			Mass massObject = new Mass(id,cID,x,y,mass,xVel,yVel);
			myMasses.put(id, massObject);
		}
	}
		
	public void createSprings(NodeList springs){
		String id = "spring"; int cID = 2; float k = 1, length=0;Mass massA, massB; // defaults
	    
	    for (int i=0; i<springs.getLength(); i++){
	    	Node currSpring = springs.item(i);
	    	NamedNodeMap springAttr = currSpring.getAttributes();
	    	//massA = (Mass) myEngine.getObject(springAttr.getNamedItem("a").getNodeValue());
	    	//massB = (Mass) myEngine.getObject(springAttr.getNamedItem("b").getNodeValue());
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
//	    	massA = (Mass) myEngine.getObject(muscleAttr.getNamedItem("a").getNodeValue());
//	    	massB = (Mass) myEngine.getObject(muscleAttr.getNamedItem("b").getNodeValue());
	    	massA = myMasses.get(muscleAttr.getNamedItem("a").getNodeValue());
	    	massB = myMasses.get(muscleAttr.getNamedItem("b").getNodeValue());
	    	if (hasAttribute(muscleAttr,"restLength")) length = Float.parseFloat(muscleAttr.getNamedItem("restlength").getNodeValue());
	    	else length = massA.distance(massB); // default to distance between mass A and mass B
	    	if (hasAttribute(muscleAttr,"k")) k = Float.parseFloat(muscleAttr.getNamedItem("constant").getNodeValue());
	    	amplitude = Float.parseFloat(muscleAttr.getNamedItem("amplitude").getNodeValue());
	      
	    	Muscle muscle = new Muscle(id, cID, massA, massB, length, k, amplitude, frequency);
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
