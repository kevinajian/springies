package springies;
import jgame.*;
import jboxGlue.*;
import jgame.JGColor;
import jgame.platform.JGEngine;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

import Objects.*;

public class Parser {
	
	private String myPath;
	
	public Parser(String path){
		myPath = path;
	}
	
	public void parse(){
		try{			
			File data = new File(myPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(data);
			doc.getDocumentElement().normalize();
			
			// create objects from data
			createMasses(doc.getElementsByTagName("mass"));
			createFixedMasses(doc.getElementsByTagName("fixed"));
			createSprings(doc.getElementsByTagName("spring"));
			createMuscles(doc.getElementsByTagName("muscle"));
		} catch(Exception ex){
			ex.printStackTrace();
		}
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
			
			Mass mass = new Mass(id,cID,x,y);
		}
	}
	
	//can combine with FixedMasses (vx,vy)?
	public void createMasses(NodeList masses){
		String id; int cID = 1; double x=0, y=0, mass=0; float xVel=0, yVel=0; // defaults
		
		for (int i=0; i<masses.getLength(); i++){
			Node currMassObject = masses.item(i);

			NamedNodeMap currMassAttr = currMassObject.getAttributes();
			id = currMassAttr.getNamedItem("id").getNodeValue();
			if (hasAttribute(currMassAttr,"mass")) mass = Double.parseDouble(currMassAttr.getNamedItem("mass").getNodeValue());
//			if (hasAttribute(currMassAttr,"x"))	
			x = Double.parseDouble(currMassAttr.getNamedItem("x").getNodeValue()); 
//			if (hasAttribute(currMassAttr,"y")) 
			y = Double.parseDouble(currMassAttr.getNamedItem("y").getNodeValue());
			if (hasAttribute(currMassAttr,"vx")) xVel = Float.parseFloat(currMassAttr.getNamedItem("vx").getNodeValue()); 
			if (hasAttribute(currMassAttr,"vy")) yVel = Float.parseFloat(currMassAttr.getNamedItem("vy").getNodeValue());
			Node currY = currMassAttr.getNamedItem("y");
			
			Mass massObject = new Mass(id,cID,x,y);
//			mass.setPos(Double.parseDouble(currX.getNodeValue()),Double.parseDouble(currY.getNodeValue()));
		}
	}

	
	public void createSprings(NodeList springs){
		String id = "spring"; int cID = 2; double k = 1;//Mass massA, massB; // defaults
		
		for (int i=0; i<springs.getLength(); i++){
			Node currSpring = springs.item(i);
			NamedNodeMap springAttr = currSpring.getAttributes();
			// massA = (Mass) getObject(springAttr.getNamedItem("a").getNodeValue());
			// massB = (Mass) getObject(springAttr.getNamedItem("b").getNodeValue());
			Node length = springAttr.getNamedItem("restLength"); // default distance b/n a and b
			if (hasAttribute(springAttr,"k")) k = Double.parseDouble(springAttr.getNamedItem("constant").getNodeValue());
			
			// create springs Spring spring = new Spring();
//			Spring spring = new Spring(id, cID, massA, massB);
		}
	}
	
	// can combine with createSprings - if (has amplitude) create muscle, else spring;
	public void createMuscles(NodeList muscles){
		String id = "muscle"; int cID = 2; double k = 1, amplitude; //Mass massA, massB; // defaults
		
		for (int i=0; i<muscles.getLength(); i++){
			Node currMuscle = muscles.item(i);
			NamedNodeMap muscleAttr = currMuscle.getAttributes();
			// massA = (Mass) getObject(muscleAttr.getNamedItem("a").getNodeValue());
			// massB = (Mass) getObject(muscleAttr.getNamedItem("b").getNodeValue());
			Node length = muscleAttr.getNamedItem("restLength"); // default distance b/n a and b
			if (hasAttribute(muscleAttr,"k")) k = Double.parseDouble(muscleAttr.getNamedItem("constant").getNodeValue());
			amplitude = Double.parseDouble(muscleAttr.getNamedItem("amplitude").getNodeValue());
			
			// create muscles Muscle muscle = new Muscle();
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
