package springies;
import jboxGlue.*;
import jgame.JGColor;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
			
			System.out.println("test");
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
			Node currMass = currMassAttr.getNamedItem("mass");
			Node currX = currMassAttr.getNamedItem("x");
			Node currY = currMassAttr.getNamedItem("y");
			
			Mass mass = new Mass(currID.getNodeValue(),1,JGColor.blue,10,5);//Double.parseDouble(currMass.getNodeValue()));
			mass.setPos(Double.parseDouble(currX.getNodeValue()),Double.parseDouble(currY.getNodeValue()));
		}
	}
	
	//fix later
	public void createMasses(NodeList masses){
		for (int i=0; i<masses.getLength(); i++){
			Node currMassObject = masses.item(i);
			NamedNodeMap currMassAttr = currMassObject.getAttributes();
			Node currID = currMassAttr.getNamedItem("id");
			Node currMass = currMassAttr.getNamedItem("mass");
			Node currX = currMassAttr.getNamedItem("x");
			Node currY = currMassAttr.getNamedItem("y");
			Node currvX = currMassAttr.getNamedItem("vx");
			Node currvY = currMassAttr.getNamedItem("vy");
			
			Mass mass = new Mass(currID.getNodeValue(),1,JGColor.blue,10,5,Float.parseFloat(currvX.getNodeValue()),Float.parseFloat(currY.getNodeValue()));//Double.parseDouble(currMass.getNodeValue()));
			mass.setPos(Double.parseDouble(currX.getNodeValue()),Double.parseDouble(currY.getNodeValue()));
		}
	}

	
	public void createSprings(NodeList springs){
		for (int i=0; i<springs.getLength(); i++){
			Node currSpring = springs.item(i);
			NamedNodeMap springAttr = currSpring.getAttributes();
			Node massA = springAttr.getNamedItem("a");
			Node massB = springAttr.getNamedItem("b");
			Node length = springAttr.getNamedItem("restLength");
			Node k = springAttr.getNamedItem("constant");
		}
	}
	
	public void createMuscles(NodeList muscles){
		for (int i=0; i<muscles.getLength(); i++){
			Node currMuscle = muscles.item(i);
			NamedNodeMap springAttr = currMuscle.getAttributes();
			Node massA = springAttr.getNamedItem("a");
			Node massB = springAttr.getNamedItem("b");
			Node length = springAttr.getNamedItem("restLength");
			Node k = springAttr.getNamedItem("constant");
			Node amplitude = springAttr.getNamedItem("amplitude");
		}
	}
}
