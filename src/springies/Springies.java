package springies;

import java.io.File;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectBouncyBall;
import jboxGlue.PhysicalObjectCircle;
import jboxGlue.PhysicalObjectRect;
import jboxGlue.WorldManager;
import jgame.JGColor;
import jgame.JGObject;
import jgame.platform.JGEngine;
import Objects.Mass;
import Objects.Spring;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@SuppressWarnings( "serial" )
public class Springies extends JGEngine
{
	public Springies( )
	{
		// set the window size
		int height = 480;
		double aspect = 16.0/9.0; //@Tyler - aspect-ratio for screen
		
		/* @Tyler - calls jGEnine initEgine() which then calls init(), 
		 * which calls initCanvas() and initGame() below */ 
		initEngine( (int)(height*aspect), height ); 
	}
	
	@Override
	public void initCanvas( )
	{
		// I have no idea what tiles do...
		setCanvasSettings(
			1, // width of the canvas in tiles
			1, // height of the canvas in tiles
			displayWidth(), // width of one tile
			displayHeight(), // height of one tile
			null,// foreground colour -> use default colour white
			null,// background colour -> use default colour black
			null // standard font -> use default font
		);
	}
	
	@Override
	public void initGame( )
	{
		setFrameRate( 60, 2 );
		
		
		// @Tyler REALLY IMPORTANT
		// init the world
		// One thing to keep straight: The world coordinates have y pointing down
		// the game coordinates have y pointing up
		// so gravity is along the positive y axis in world coords to point down in game coords
		// remember to set all directions (eg forces, velocities) in world coords
		WorldManager.initWorld( this );
		
		// @Tyler - no x gravity, a little y  gravity - try messing around with the values to see what happens
		WorldManager.getWorld().setGravity( new Vec2( 0.0f, 0.01f ) );
		
		// add a bouncy ball
		// NOTE: you could make this into a separate class, but I'm lazy
		// @Tyler - I recreated a PhysicalObjectBall class as he said
		Mass mass1 = new Mass("mass1", 1,displayWidth()/2, displayHeight()/2); 
		Mass mass2 = new Mass("mass2", 1,displayWidth()/3, displayHeight()/3); 
		new Spring("spring1", 1, mass1, mass2 ); 

		//ball.setForce( 8000, -10000 );
//		PhysicalObject ball = new PhysicalObjectBouncyBall( "ball", 1, JGColor.blue, 10, 5 );
//		ball.setPos( displayWidth()/2, displayHeight()/2 );
//		ball.setForce( 8000, -10000 );
		
		// parses data
		Document doc = parse("..\\xml\\daintywalker.xml"); // enter xml file here
		// create objects from data
		createMasses(doc.getElementsByTagName("mass"));
		createFixedMasses(doc.getElementsByTagName("fixed"));
		createSprings(doc.getElementsByTagName("spring"));
		createMuscles(doc.getElementsByTagName("muscle"));
		setGravity(doc.getElementsByTagName("gravity"));
		setViscosity(doc.getElementsByTagName("viscosity"));
		setCenterMass(doc.getElementsByTagName("centermass"));
		setWalls(doc.getElementsByTagName("wall"));
		
		// add walls to bounce off of
		// NOTE: immovable objects must have no mass
		final double WALL_MARGIN = 10;
		final double WALL_THICKNESS = 10;
		final double WALL_WIDTH = displayWidth() - WALL_MARGIN*2 + WALL_THICKNESS;
		final double WALL_HEIGHT = displayHeight() - WALL_MARGIN*2 + WALL_THICKNESS;
		PhysicalObject wall = new PhysicalObjectRect( "wall", 2, JGColor.green, WALL_WIDTH, WALL_THICKNESS );
		wall.setPos( displayWidth()/2, WALL_MARGIN );
		wall = new PhysicalObjectRect( "wall", 2, JGColor.green, WALL_WIDTH, WALL_THICKNESS );
		wall.setPos( displayWidth()/2, displayHeight() - WALL_MARGIN );
		wall = new PhysicalObjectRect( "wall", 2, JGColor.green, WALL_THICKNESS, WALL_HEIGHT );
		wall.setPos( WALL_MARGIN, displayHeight()/2 );
		wall = new PhysicalObjectRect( "wall", 2, JGColor.green, WALL_THICKNESS, WALL_HEIGHT );
		wall.setPos( displayWidth() - WALL_MARGIN, displayHeight()/2 );
	}

	@Override
	public void doFrame( )
	{
		// update game objects
		WorldManager.getWorld().step( 1f, 1 );
		moveObjects();
		
		checkCollision( 1 + 2, 1 );
	}
	
	@Override
	public void paintFrame( )
	{
		// nothing to do
		// the objects paint themselves
	}
	
	public Document parse(String path){
		try{			
			File data = new File(path);
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
			
			Mass mass = new Mass(id,cID,x,y);
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
			if (hasAttribute(currMassAttr,"vx")) xVel = Float.parseFloat(currMassAttr.getNamedItem("vx").getNodeValue()); 
			if (hasAttribute(currMassAttr,"vy")) yVel = Float.parseFloat(currMassAttr.getNamedItem("vy").getNodeValue());
			
			Mass massObject = new Mass(id,cID,x,y,mass,xVel,yVel);
		}
	}

	public void createSprings(NodeList springs){
		String id = "spring"; int cID = 2; double k = 1;Mass massA, massB; // defaults
		
		for (int i=0; i<springs.getLength(); i++){
			Node currSpring = springs.item(i);
			NamedNodeMap springAttr = currSpring.getAttributes();
			massA = (Mass) getObject(springAttr.getNamedItem("a").getNodeValue());
			massB = (Mass) getObject(springAttr.getNamedItem("b").getNodeValue());
			Node length = springAttr.getNamedItem("restLength"); // default distance b/n a and b
			if (hasAttribute(springAttr,"k")) k = Double.parseDouble(springAttr.getNamedItem("constant").getNodeValue());
			
			Spring spring = new Spring(id, cID, massA, massB);
		}
	}
	
	// can combine with createSprings - if (has amplitude) create muscle, else spring;
	public void createMuscles(NodeList muscles){
		String id = "muscle"; int cID = 2; double k = 1, amplitude; Mass massA, massB; // defaults
		
		for (int i=0; i<muscles.getLength(); i++){
			Node currMuscle = muscles.item(i);
			NamedNodeMap muscleAttr = currMuscle.getAttributes();
			massA = (Mass) getObject(muscleAttr.getNamedItem("a").getNodeValue());
			massB = (Mass) getObject(muscleAttr.getNamedItem("b").getNodeValue());
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
//			PhysicalObject wall = new PhysicalObjectRect( "wall", 2, JGColor.green, WALL_WIDTH, WALL_THICKNESS );
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
