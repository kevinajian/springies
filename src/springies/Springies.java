package springies;

import java.io.File;

import Objects.FixedMass;
import Objects.Mass;
import Objects.Muscle;
import Objects.Spring;
import Objects.Wall.BottomWall;
import Objects.Wall.HorizontalWall;
import Objects.Wall.LeftWall;
import Objects.Wall.RightWall;
import Objects.Wall.TopWall;
import Objects.Wall.VerticalWall;
import Objects.Wall.Wall;

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

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@SuppressWarnings( "serial" )
public class Springies extends JGEngine
{
	private int frame = 0;
	public Springies( )
	{
		// set the window size
		int height = 480;
		double aspect = 16.0/9.0; //@Tyler - aspect-ratio for screen
		initEngine( (int)(height*aspect), height ); 
	}
	
	@Override
	public void initCanvas( )
	{
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
		
		WorldManager.initWorld( this );
		
		//WorldManager.getWorld().setGravity( new Vec2( 0.0f, 0.1f ) );
		
		Mass mass1 = new Mass("mass1", 1,displayWidth()/2, displayHeight()/2 -100); 
		Mass mass2 = new Mass("mass2", 1,displayWidth()/2 - 25, displayHeight()/2 -100); 
		Mass mass3 = new FixedMass("mass2", 1,displayWidth()/5, displayHeight()/2); 
		new Spring("spring1", 1, mass1, mass2 ); 
		

		//ball.setForce( 8000, -10000 );
//		PhysicalObject ball = new PhysicalObjectBouncyBall( "ball", 1, JGColor.blue, 10, 5 );
//		ball.setPos( displayWidth()/2, displayHeight()/2 );
//		ball.setForce( 8000, -10000 );
		
		// parses data
		Parser parser = new Parser("xml/daintywalker.xml"); // enter the xml file here
		Document doc  = parser.parse();
//		create objects from data
//		parser.createMasses(doc.getElementsByTagName("mass"));
//		parser.createFixedMasses(doc.getElementsByTagName("fixed"));
//		parser.createSprings(doc.getElementsByTagName("spring"));
//		parser.createMuscles(doc.getElementsByTagName("muscle"));

//		parser.setGravity(doc.getElementsByTagName("gravity"));
//		parser.setViscosity(doc.getElementsByTagName("viscosity"));
//		parser.setCenterMass(doc.getElementsByTagName("centermass"));
//		parser.setWalls(doc.getElementsByTagName("wall"));
		
		// add walls to bounce off of
		// NOTE: immovable objects must have no mass
		final double WALL_MARGIN = 10;
		final double WALL_THICKNESS = 10;
		final double WALL_WIDTH = displayWidth() - WALL_MARGIN*2 + WALL_THICKNESS;
		final double WALL_HEIGHT = displayHeight() - WALL_MARGIN*2 + WALL_THICKNESS;
		Wall ceiling = new TopWall("wallC", 2, JGColor.green, WALL_WIDTH, WALL_THICKNESS);
			 ceiling.setPos(displayWidth()/2, WALL_MARGIN);
			 //ceiling.setRepulsionForce((float)20, (float)2.0);
		Wall floor = new BottomWall( "wallF", 2, JGColor.green, WALL_WIDTH, WALL_THICKNESS );
			 floor.setPos( displayWidth()/2, displayHeight() - WALL_MARGIN);
			 //floor.setRepulsionForce((float)20000, (float)2.0);
		Wall left = new LeftWall( "wallL", 2, JGColor.green, WALL_THICKNESS, WALL_HEIGHT );
			 left.setPos( WALL_MARGIN, displayHeight()/2 );
			 //left.setRepulsionForce((float)20000, (float)2.0);
		Wall right = new RightWall( "wallR", 2, JGColor.green, WALL_THICKNESS, WALL_HEIGHT );
			 right.setPos( displayWidth() - WALL_MARGIN, displayHeight()/2 );
			 //right.setRepulsionForce((float)20000, (float)2.0);
	}

	@Override
	public void doFrame( )
	{
		frame++;
		// update game objects
		WorldManager.getWorld().step( 1f, 1 );
		WorldManager.getWorld().applyEnvironmentalForces();
		moveObjects();
		
		checkCollision( 1 + 2, 1 );
	}
	
	public int getFrame(){
		return frame;
	}
	
	@Override
	public void paintFrame( )
	{
		// nothing to do
		// the objects paint themselves
	}
}
