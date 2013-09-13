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
			
			
			NodeList muscles = doc.getElementsByTagName("muscle");
			NodeList fixedMasses = doc.getElementsByTagName("fixed");
			NodeList springs = doc.getElementsByTagName("spring");
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
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
			
			PhysicalObjectBouncyBall mass = new PhysicalObjectBouncyBall(currID.getNodeValue(),1,JGColor.blue,10,5);//Double.parseDouble(currMass.getNodeValue()));
			mass.setPos(Double.parseDouble(currX.getNodeValue()),Double.parseDouble(currY.getNodeValue()));
		}
	}
	
	
}
