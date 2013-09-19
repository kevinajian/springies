package springies;

import input.AssemblyLoaderDialog;

import java.io.File;

import objects.*;
import objects.wall.*;
import input.AssemblyLoaderDialog;
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
	private static final String xmlDir = "assets";
	private static final String DEFAULT_ASSEMBLY_FILEPATH = "ball.xml"; 
	private static final String DEFAULT_ENVIRONMENT_FILEPATH = "assets/environment.xml"; 

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
		loadAssembly(DEFAULT_ASSEMBLY_FILEPATH);
	}
	
	@Override
	public void doFrame( )
	{
		frame++;
		//TODO: Fix input key listener
		if(getKey('A')){
			new AssemblyLoaderDialog(this);
		}
		
		WorldManager.getWorld().step( 1f, 1 );
		WorldManager.getWorld().applyEnvironmentalForces();
		moveObjects();
		
		checkCollision( 1 + 2, 1 );
	}
	
	public int getFrame(){
		return frame;
	}
	
	public void loadAssembly(String filename){
		File data = new File(getXMLFilepath(filename));
		Parser parser = new AssemblyParser(data, this);
		parser.parse();
	}
	
	private String getXMLFilepath(String filename){
		return xmlDir+"/"+filename;
	}
	
	private void setupEnvironment(){
		File environment = new File(getXMLFilepath(DEFAULT_ENVIRONMENT_FILEPATH));
		if (environment.exists()){
			Parser environmentParser = new EnvironmentalParser(environment, this);
			environmentParser.parse();		
		}
	}
	
	@Override
	public void paintFrame( )
	{
		// nothing to do
		// the objects paint themselves
	}
}
