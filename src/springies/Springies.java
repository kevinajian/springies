package springies;

import java.io.File;

import objects.Mass;
import objects.Spring;

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
		
		WorldManager.getWorld().setGravity( new Vec2( 0.0f, 0.1f ) );
		
		Mass mass1 = new Mass("mass1", 1,displayWidth()/2, displayHeight()/2); 
		Mass mass2 = new Mass("mass2", 1,displayWidth()/2 - 20, displayHeight()/2); 
		new Spring("spring1", 1, mass1, mass2 ); 
		
		//ball.setForce( 8000, -10000 );
//		PhysicalObject ball = new PhysicalObjectBouncyBall( "ball", 1, JGColor.blue, 10, 5 );
//		ball.setPos( displayWidth()/2, displayHeight()/2 );
//		ball.setForce( 8000, -10000 );
		
		// parses data
//		Document doc = parse("xml/daintywalker.xml"); // enter xml file here
//		create objects from data
//		createMasses(doc.getElementsByTagName("mass"));
//		createFixedMasses(doc.getElementsByTagName("fixed"));
//		createSprings(doc.getElementsByTagName("spring"));
//		createMuscles(doc.getElementsByTagName("muscle"));
		//setGravity(doc.getElementsByTagName("gravity"));
		//setViscosity(doc.getElementsByTagName("viscosity"));
		//setCenterMass(doc.getElementsByTagName("centermass"));
		//setWalls(doc.getElementsByTagName("wall"));
		
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
	
}
