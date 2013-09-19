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
	private final String DEFAULT_ASSEMBLY_FILEPATH = "assets/daintywalker.xml"; 
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
		
		// set environment and world forces
		setupEnvironment();
		// create objects from data
		loadDefaultAssmebly(DEFAULT_ASSEMBLY_FILEPATH);
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
	
	private void setupEnvironment(){
		File environment = new File("assets/environment.xml");
		if (environment.exists()){
			Parser environmentParser = new EnvironmentalParser(environment, this);
			environmentParser.parse();		
		}
	}
	
	private void loadDefaultAssmebly(String filepath){
		File data = new File(filepath);
		Parser parser = new AssemblyParser(data, this);
		parser.parse();
	}
	
	@Override
	public void paintFrame( )
	{
		// nothing to do
		// the objects paint themselves
	}
}
