package springies;
import jboxGlue.*;
import jgame.JGColor;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import objects.Mass;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

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
			
			createMasses(doc.getElementsByTagName("mass"));
			createFixedMasses(doc.getElementsByTagName("fixed"));
			createSprings(doc.getElementsByTagName("spring"));
			createMuscles(doc.getElementsByTagName("muscle"));
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void createFixedMasses(NodeList masses){
		for (int i=0; i<masses.getLength(); i++){
			Node currMassObject = masses.item(i);
			NamedNodeMap currMassAttr = currMassObject.getAttributes();
			Node currID = currMassAttr.getNamedItem("id");
			Node currMass = currMassAttr.getNamedItem("mass"); // make default 0
			Node currX = currMassAttr.getNamedItem("x");
			Node currY = currMassAttr.getNamedItem("y");
			
			//Mass mass = new Mass(currID.getNodeValue(),1,JGColor.blue,10,5);
			//mass.setPos(Double.parseDouble(currX.getNodeValue()),Double.parseDouble(currY.getNodeValue()));
		}
	}
	
	//fix later
	public void createMasses(NodeList masses){
		for (int i=0; i<masses.getLength(); i++){
			Node currMassObject = masses.item(i);
			NamedNodeMap currMassAttr = currMassObject.getAttributes();
			Node currID = currMassAttr.getNamedItem("id");
			Node currMass = currMassAttr.getNamedItem("mass"); // make default 0
			Node currX = currMassAttr.getNamedItem("x");
			Node currY = currMassAttr.getNamedItem("y");
			Node currvX = currMassAttr.getNamedItem("vx"); // default 0
			Node currvY = currMassAttr.getNamedItem("vy"); // default 0
			
			//Mass mass = new Mass(currID.getNodeValue(),1,JGColor.blue,10,5,Float.parseFloat(currvX.getNodeValue()),Float.parseFloat(currY.getNodeValue()));
			//mass.setPos(Double.parseDouble(currX.getNodeValue()),Double.parseDouble(currY.getNodeValue()));
		}
	}

	
	public void createSprings(NodeList springs){
		for (int i=0; i<springs.getLength(); i++){
			Node currSpring = springs.item(i);
			NamedNodeMap springAttr = currSpring.getAttributes();
			Node massA = springAttr.getNamedItem("a");
			Node massB = springAttr.getNamedItem("b");
			Node length = springAttr.getNamedItem("restLength"); // default distance b/n a and b
			Node k = springAttr.getNamedItem("constant"); // default k = 1
			
			// create springs Spring spring = new Spring();
		}
	}
	
	public void createMuscles(NodeList muscles){
		for (int i=0; i<muscles.getLength(); i++){
			Node currMuscle = muscles.item(i);
			NamedNodeMap springAttr = currMuscle.getAttributes();
			Node massA = springAttr.getNamedItem("a");
			Node massB = springAttr.getNamedItem("b");
			Node length = springAttr.getNamedItem("restLength"); // default distance b/n a and b
			Node k = springAttr.getNamedItem("constant"); // default k = 1
			Node amplitude = springAttr.getNamedItem("amplitude");
			
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
//			PhysicalObject wall = new PhysicalObjectRect( "wall", 2, JGColor.green, WALL_WIDTH, WALL_THICKNESS );
		}
	}
	
	private boolean hasAttribute(NamedNodeMap attributes, String attribute){
		for (int i=0; i<attributes.getLength(); i++){
			if (attributes.item(i).getNodeName().equals(attribute)) return true;
		}
		return false;
	}
}
