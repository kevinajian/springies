package parser;
import jboxGlue.*;
import jgame.JGColor;
import jgame.platform.JGEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public AssemblyParser(File file, JGEngine engine){
		super(file, engine);
	}
	
	public void parse(){
		
		Map<String, Mass> masses = getTotalMassMap();
		List<PhysicalSpring> springs = getAllSprings(masses);
		Assembly assembly = new Assembly(masses, springs);
		WorldManager.getWorld().addAssembly(assembly);
	}
	private Map<String, Mass> getTotalMassMap(){
		Map<String, Mass> masses = createMasses(myDocument.getElementsByTagName("mass"),(float) myEngine.displayHeight(), false);
		Map<String, Mass> fixedMasses = createMasses(myDocument.getElementsByTagName("fixed"),(float) myEngine.displayHeight(), true);
		Map<String, Mass> allMasses = new HashMap<String, Mass>();
		allMasses.putAll(masses);
		allMasses.putAll(fixedMasses);
		return allMasses;
	}
	
	private List<PhysicalSpring> getAllSprings(Map<String, Mass> masses){
		List<PhysicalSpring> springs = new ArrayList<PhysicalSpring>();
		springs.addAll(createMusclesAndSprings(myDocument.getElementsByTagName("spring"), masses));
		springs.addAll(createMusclesAndSprings(myDocument.getElementsByTagName("muscle"), masses));
		return springs;
	}
	
	private Map<String, Mass> createMasses(NodeList masses, float gameHeight, boolean isFixed){
		HashMap<String, Mass> massMap = new HashMap<String, Mass>();
		for (int i=0; i<masses.getLength(); i++){
			 
			Node currMassObject = masses.item(i);
			NamedNodeMap currMassAttr = currMassObject.getAttributes();
			String id = currMassAttr.getNamedItem("id").getNodeValue();
			double x = Double.parseDouble(currMassAttr.getNamedItem("x").getNodeValue());
			double y = gameHeight - Double.parseDouble(currMassAttr.getNamedItem("y").getNodeValue());
			if (isFixed){
				FixedMass massObject =  new FixedMass(id,Mass.DEFAULT_CID,x,y);
				massMap.put(id, massObject);
			}
			else {
				double mass=Mass.DEFAULT_MASS;
				if (hasAttribute(currMassAttr,"mass")) mass = Double.parseDouble(currMassAttr.getNamedItem("mass").getNodeValue());
				 float xVel=Mass.INITIAL_X_VELOCITY;
				if (hasAttribute(currMassAttr,"vx")) xVel = Float.parseFloat(currMassAttr.getNamedItem("vx").getNodeValue()); 
				float yVel=Mass.INITIAL_Y_VELOCITY;
				if (hasAttribute(currMassAttr,"vy")) yVel = Float.parseFloat(currMassAttr.getNamedItem("vy").getNodeValue());
				Mass massObject = new Mass(id,Mass.DEFAULT_CID,x,y,mass,xVel,yVel);
				massMap.put(id, massObject);
			}
		}
		return massMap;
	}
	  
	private List<PhysicalSpring> createMusclesAndSprings(NodeList musclesAndSprings, Map<String, Mass> massMap){
		String id; int cID = 2; float k = Muscle.DEFAULT_SPRINGYNESS, length = Muscle.DEFAULT_RESTLENGTH, amplitude = Muscle.DEFAULT_AMPLITUDE, frequency = Muscle.DEFAULT_FREQUENCY; Mass massA, massB; // defaults
	    
		
		List<PhysicalSpring> springs = new ArrayList<PhysicalSpring>();
		
	    for (int i=0; i<musclesAndSprings.getLength(); i++){
	    	Node curr = musclesAndSprings.item(i);
	    	NamedNodeMap attr = curr.getAttributes();
	    	massA = massMap.get(attr.getNamedItem("a").getNodeValue());// 
	    	massB = massMap.get(attr.getNamedItem("b").getNodeValue());
	    	if (hasAttribute(attr,"restLength")) length = Float.parseFloat(attr.getNamedItem("restlength").getNodeValue());
	    	else length = massA.distance(massB); // default to distance between mass A and mass B
	    	if (hasAttribute(attr,"k")) k = Float.parseFloat(attr.getNamedItem("constant").getNodeValue());
	    	PhysicalSpring spring;
	    	if (hasAttribute(attr,"amplitude")) {
	    		id = "muscle";
	    		amplitude = Float.parseFloat(attr.getNamedItem("amplitude").getNodeValue());
	    		spring = new Muscle(id, cID, massA, massB, length, k, amplitude, frequency);
	    		springs.add(spring);
	    	}
	    	else {
	    		id = "spring";
	    		spring = new Spring(id, cID, massA, massB, length, k);
	    		springs.add(spring);
	    	}
	    }
	    return springs;
	}
	
	private boolean hasAttribute(NamedNodeMap attributes, String attribute){
	    for (int i=0; i<attributes.getLength(); i++){
	    	if (attributes.item(i).getNodeName().equals(attribute)) return true;
	    }
	    return false;
	}	
}