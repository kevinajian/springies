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
	
	// Creates assembly from data file
	public void parse(){
		Map<String, Mass> masses = getTotalMassMap();
		List<PhysicalSpring> springs = getAllSprings(masses);
		Assembly assembly = new Assembly(masses, springs);
		WorldManager.getWorld().addAssembly(assembly);
	}
	
	// Finds all mass and fixed mass elements within the data file, creates and stores them.
	private Map<String, Mass> getTotalMassMap(){
		Map<String, Mass> masses = createMasses(myDocument.getElementsByTagName(Attributes.MASS_ELEMENT),(float) myEngine.displayHeight(), false);
		Map<String, Mass> fixedMasses = createMasses(myDocument.getElementsByTagName(Attributes.FIXED_MASS_ELEMENT),(float) myEngine.displayHeight(), true);
		Map<String, Mass> allMasses = new HashMap<String, Mass>();
		allMasses.putAll(masses);
		allMasses.putAll(fixedMasses);
		return allMasses;
	}
	
	// Finds all spring and muscle elements within the data file, creates and stores them.
	private List<PhysicalSpring> getAllSprings(Map<String, Mass> masses){
		List<PhysicalSpring> springs = new ArrayList<PhysicalSpring>();
		springs.addAll(createMusclesAndSprings(myDocument.getElementsByTagName(Attributes.SPRING_ELEMENT), masses));
		springs.addAll(createMusclesAndSprings(myDocument.getElementsByTagName(Attributes.MUSCLE_ELEMENT), masses));
		return springs;
	}
	
	// Creates masses and fixed masses using attributes from the data file.
	private Map<String, Mass> createMasses(NodeList masses, float gameHeight, boolean isFixed){
		HashMap<String, Mass> massMap = new HashMap<String, Mass>();
		for (int i=0; i<masses.getLength(); i++){
			Node currMassObject = masses.item(i);
			NamedNodeMap currMassAttr = currMassObject.getAttributes();
			String id = currMassAttr.getNamedItem(Attributes.ID).getNodeValue();
			double x = Double.parseDouble(currMassAttr.getNamedItem(Attributes.X_POS).getNodeValue());
			double y = gameHeight - Double.parseDouble(currMassAttr.getNamedItem(Attributes.Y_POS).getNodeValue());
			if (isFixed){
				FixedMass massObject =  new FixedMass(id,Mass.DEFAULT_CID,x,y);
				massMap.put(id, massObject);
			}
			else {
				double mass=Mass.DEFAULT_MASS;
				if (hasAttribute(currMassAttr,Attributes.MASS)) mass = Double.parseDouble(currMassAttr.getNamedItem(Attributes.MASS).getNodeValue());
				float xVel=Mass.INITIAL_X_VELOCITY;
				if (hasAttribute(currMassAttr,Attributes.X_VEL)) xVel = Float.parseFloat(currMassAttr.getNamedItem(Attributes.X_VEL).getNodeValue()); 
				float yVel=Mass.INITIAL_Y_VELOCITY;
				if (hasAttribute(currMassAttr,Attributes.Y_VEL)) yVel = Float.parseFloat(currMassAttr.getNamedItem(Attributes.Y_VEL).getNodeValue());
				Mass massObject = new Mass(id,Mass.DEFAULT_CID,x,y,mass,xVel,yVel);
				massMap.put(id, massObject);
			}
		}
		return massMap;
	}
	  
	// Creates muscles and springs using attributes from the data file.
	private List<PhysicalSpring> createMusclesAndSprings(NodeList musclesAndSprings, Map<String, Mass> massMap){
		List<PhysicalSpring> springs = new ArrayList<PhysicalSpring>();
	    for (int i=0; i<musclesAndSprings.getLength(); i++){
	    	Node curr = musclesAndSprings.item(i);
	    	NamedNodeMap attr = curr.getAttributes();
	    	Mass massA, massB;
	    	massA = massMap.get(attr.getNamedItem(Attributes.MASS_A).getNodeValue()); 
	    	massB = massMap.get(attr.getNamedItem(Attributes.MASS_B).getNodeValue());
	    	float length = Muscle.DEFAULT_RESTLENGTH;
	    	if (hasAttribute(attr,Attributes.REST_LENGTH)) length = Float.parseFloat(attr.getNamedItem(Attributes.REST_LENGTH).getNodeValue());
	    	else length = massA.distance(massB);
	    	float k = Muscle.DEFAULT_SPRINGYNESS;
	    	if (hasAttribute(attr,Attributes.SPRINGYNESS)) k = Float.parseFloat(attr.getNamedItem(Attributes.SPRINGYNESS).getNodeValue());
	    	PhysicalSpring spring;
	    	if (hasAttribute(attr,Attributes.AMPLITUDE)) {
	    		float amplitude = Muscle.DEFAULT_AMPLITUDE;
	    		amplitude = Float.parseFloat(attr.getNamedItem(Attributes.AMPLITUDE).getNodeValue());
	    		float frequency = Muscle.DEFAULT_FREQUENCY;
	    		spring = new Muscle(Attributes.MUSCLE_ELEMENT, Muscle.DEFAULT_CID , massA, massB, length, k, amplitude, frequency);
	    		springs.add(spring);
	    	}
	    	else {
	    		spring = new Spring(Attributes.SPRING_ELEMENT, Muscle.DEFAULT_CID, massA, massB, length, k);
	    		springs.add(spring);
	    	}
	    }
	    return springs;
	}
	
	// Check if the given node has the particular attribute within it.
	private boolean hasAttribute(NamedNodeMap attributes, String attribute){
	    for (int i=0; i<attributes.getLength(); i++){
	    	if (attributes.item(i).getNodeName().equals(attribute)) return true;
	    }
	    return false;
	}	
}
