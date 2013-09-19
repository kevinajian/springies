package springies;
import jboxGlue.*;
import jgame.JGColor;
import jgame.platform.JGEngine;

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

public class AssemblyParser extends Parser{
	private HashMap<String, Mass> myMasses = new HashMap<String, Mass>();
	public AssemblyParser(File file, JGEngine engine){
		super(file, engine);
	}
	
	public void parse(){
		
		createMasses(myDocument.getElementsByTagName("mass"),(float) myEngine.displayHeight(), false);
		createMasses(myDocument.getElementsByTagName("fixed"),(float) myEngine.displayHeight(), true);
		createMusclesAndSprings(myDocument.getElementsByTagName("spring"));
		createMusclesAndSprings(myDocument.getElementsByTagName("muscle"));
	}

	public void createMasses(NodeList masses, float gameHeight, boolean isFixed){
		String id; int cID = 1; double x, y, mass=1; float xVel=0, yVel=0; // defaults
		for (int i=0; i<masses.getLength(); i++){
			Node currMassObject = masses.item(i);
			NamedNodeMap currMassAttr = currMassObject.getAttributes();
			id = currMassAttr.getNamedItem("id").getNodeValue();
			x = Double.parseDouble(currMassAttr.getNamedItem("x").getNodeValue());
			y = gameHeight - Double.parseDouble(currMassAttr.getNamedItem("y").getNodeValue());
			if (isFixed){
				FixedMass massObject =  new FixedMass(id,cID,x,y);
				myMasses.put(id, massObject);
			}
			else {
				if (hasAttribute(currMassAttr,"mass")) mass = Double.parseDouble(currMassAttr.getNamedItem("mass").getNodeValue());
				if (hasAttribute(currMassAttr,"vx")) xVel = Float.parseFloat(currMassAttr.getNamedItem("vx").getNodeValue()); 
				if (hasAttribute(currMassAttr,"vy")) yVel = Float.parseFloat(currMassAttr.getNamedItem("vy").getNodeValue());
				Mass massObject = new Mass(id,cID,x,y,mass,xVel,yVel);
				myMasses.put(id, massObject);
				}
		}
	}
	  
	private void createMusclesAndSprings(NodeList musclesAndSprings){
		String id; int cID = 2; float k = 1, length = 0, amplitude, frequency = 20; Mass massA, massB; // defaults
	    
	    for (int i=0; i<musclesAndSprings.getLength(); i++){
	    	Node curr = musclesAndSprings.item(i);
	    	NamedNodeMap attr = curr.getAttributes();
	    	massA = myMasses.get(attr.getNamedItem("a").getNodeValue());// 
	    	massB = myMasses.get(attr.getNamedItem("b").getNodeValue());
	    	if (hasAttribute(attr,"restLength")) length = Float.parseFloat(attr.getNamedItem("restlength").getNodeValue());
	    	else length = massA.distance(massB); // default to distance between mass A and mass B
	    	if (hasAttribute(attr,"k")) k = Float.parseFloat(attr.getNamedItem("constant").getNodeValue());
	    	if (hasAttribute(attr,"amplitude")) {
	    		id = "muscle";
	    		amplitude = Float.parseFloat(attr.getNamedItem("amplitude").getNodeValue());
	    		Muscle muscle = new Muscle(id, cID, massA, massB, length, k, amplitude, frequency);
	    	}
	    	else {
	    		id = "spring";
	    		Spring spring = new Spring(id, cID, massA, massB, length, k);
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
