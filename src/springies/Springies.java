package springies;

import java.io.File;

import objects.*;
import objects.wall.*;

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
		int height = 800;
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
			JGColor.white,// background colour -> use default colour black
			null // standard font -> use default font
		);
	}
	
	@Override
	public void initGame( )
	{
		setFrameRate( 60, 2 );
		
		WorldManager.initWorld( this );
		
		//WorldManager.getWorld().setGravity( new Vec2( 0.0f, 0.1f ) );
		
//		Mass mass1 = new Mass("mass1", 1,displayWidth()/2, displayHeight()/2 -100); 
//		Mass mass2 = new Mass("mass2", 1,displayWidth()/2 - 25, displayHeight()/2 -100); 
//		Mass mass3 = new FixedMass("mass2", 1,displayWidth()/5, displayHeight()/2); 
//		new Spring("spring1", 1, mass1, mass2 ); 
				
		// parses data
		String xmlFile = "example"; // set xml file here
		File data = new File("xml/"+xmlFile+".xml");
		Parser parser = new Parser();
		Document xmlData  = parser.parse(data);
		
		// create objects from data
		parser.createMasses(xmlData.getElementsByTagName("mass"));
		parser.createFixedMasses(xmlData.getElementsByTagName("fixed"));
		parser.createSprings(xmlData.getElementsByTagName("spring"));
		parser.createMuscles(xmlData.getElementsByTagName("muscle"));
		
		// set environment and world forces
		File environment = new File("xml/environment.xml");
		if (environment.exists()){
			Parser environmentParser = new Parser();
			Document xmlEnvironment = environmentParser.parse(environment);
			
			environmentParser.setGravity(xmlEnvironment.getElementsByTagName("gravity"));
			environmentParser.setViscosity(xmlEnvironment.getElementsByTagName("viscosity"));
			environmentParser.setCenterMass(xmlEnvironment.getElementsByTagName("centermass"));
			
			// add walls to bounce off of
			// NOTE: immovable objects must have no mass}
			final double WALL_MARGIN = 10;
			final double WALL_THICKNESS = 10;
			final double WALL_WIDTH = displayWidth() - WALL_MARGIN*2 + WALL_THICKNESS;
			final double WALL_HEIGHT = displayHeight() - WALL_MARGIN*2 + WALL_THICKNESS;
			environmentParser.setWalls(xmlEnvironment.getElementsByTagName("wall"),WALL_WIDTH,WALL_HEIGHT,WALL_THICKNESS,WALL_MARGIN,displayWidth(),displayHeight());
		}
	}
	
	@Override
	public void doFrame( )
	{
		frame++;
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
