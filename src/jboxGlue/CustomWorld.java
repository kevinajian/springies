package jboxGlue;

import java.util.HashMap;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import com.sun.org.apache.bcel.internal.generic.NEW;

import externalForces.Force;
import externalForces.FixedForce;
import externalForces.Gravity;

import objects.Mass;
import objects.Spring;

public class CustomWorld extends World {
	private HashMap<String, Mass> myMasses = new HashMap<String, Mass>();
	private HashMap<String, Spring> mySprings = new HashMap<String, Spring>();
	private FixedForce[] FixedForces = { new Gravity(new Vec2((float)0.0, (float)9.8)) };
	
	public CustomWorld(AABB worldAABB, Vec2 gravity, boolean doSleep) {
		super(worldAABB, gravity, doSleep);
	}
	
	public Mass getMass(String id){
		return myMasses.get(id);
	}
	
	public Spring getSpring(String id){
		return mySprings.get(id);
	}
	
	public Mass[] getMasses(){
		return myMasses.values().toArray(new Mass[0]);
	}
	
	public Spring[] getSprings(){
		return myMasses.values().toArray(new Spring[0]);
	}
	
	public void addMass(Mass mass){
		myMasses.put(mass.getName(), mass);
	}
	
	public void addSpring(Spring spring){
		mySprings.put(spring.getName(), spring);
	}
	
	public Vec2 getCenterOfMass(){
		if(getMasses().length == 0) 
			return new Vec2();
		
		float weightedX = 0;
		float weightedY = 0;
		for(Mass mass : getMasses()){
			weightedX += mass.getX()*mass.getMass();
			weightedY += mass.getY()*mass.getMass();
		}
		weightedX /= getTotalMassOfSystem();
		weightedY /= getTotalMassOfSystem();

		Vec2 centerPosition = new Vec2(weightedX, weightedY);
		return centerPosition;
	}
	
	private float getTotalMassOfSystem(){
		float totalMass = 0;
		for(Mass mass : getMasses()){
			totalMass += mass.getMass();
		}
		return totalMass;
	}
	
	public void applyEnvironmentalForces(){
		for(FixedForce force : FixedForces){
			for(Mass mass : getMasses()){
				Vec2 forceToApply = force.getForceToApply();
				mass.applyForce(forceToApply);
			}
		}
	}
	

}