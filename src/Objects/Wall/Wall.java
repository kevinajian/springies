package objects.Wall;

import objects.Mass;

import org.jbox2d.common.Vec2;

import externalForces.Force;
import externalForces.WallRepulsion;
import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectRect;
import jboxGlue.WorldManager;
import jgame.JGColor;

public abstract class Wall extends PhysicalObjectRect {
	protected Force myForce;
	protected  Vec2 myUnitDirectionToRepel;
	
	public Wall(String id, int collisionId, JGColor color, double width, double height){
		super(id, collisionId, color, width, height);
		initWall();
		
	}
	public void initWall( ){
		setUnitVector();
		myUnitDirectionToRepel.normalize();
		myForce = new WallRepulsion((float)0.0, myUnitDirectionToRepel, (float)0.0);
	}
		
	public void setRepulsionForce(WallRepulsion force){
		myForce = force;
	}
	
	public void setRepulsionForce(float magnitude, float exponent){
		WallRepulsion rep =  new WallRepulsion(magnitude, myUnitDirectionToRepel, exponent);
		System.out.println("rep: " +rep.toString());
		myForce = rep;
	}
	
	
	@Override
	public void move( )
	{
		super.move();
		
		Mass[] masses = WorldManager.getWorld().getMasses();
		for(Mass mass : masses){
			float distance = calculateDistance(mass);
			Vec2 forceToApply = myForce.getForceToApply(distance);
			System.out.println(this.getName()+"  "+forceToApply.toString());
			mass.applyForce(forceToApply);
		}
	}
	
	public Vec2 getPosition(){
		return myBody.getPosition();
	}
	
	public abstract float calculateDistance(PhysicalObject object);
	protected abstract void setUnitVector();

}
