package springies;

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
		WorldManager.getWorld().setGravity( new Vec2( 0.0f, 0.1f ) );
		
		// add a bouncy ball
		// NOTE: you could make this into a separate class, but I'm lazy
		// @Tyler - I recreated a PhysicalObjectBall class as he said
		Mass mass1 = new Mass("mass1", 1,displayWidth()/2, displayHeight()/2); 
		Mass mass2 = new Mass("mass2", 1,displayWidth()/2 - 20, displayHeight()/2); 
		new Spring("spring1", 1, mass1, mass2 ); 
		
		//ball.setForce( 8000, -10000 );
//		PhysicalObject ball = new PhysicalObjectBouncyBall( "ball", 1, JGColor.blue, 10, 5 );
//		ball.setPos( displayWidth()/2, displayHeight()/2 );
//		ball.setForce( 8000, -10000 );
		
		// @Kevin - creates objects (eventually)
//		Parser parser = new Parser("xml\\daintywalker.xml");
//		parser.parse();
		
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
