 package jboxGlue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import jgame.JGObject;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import com.sun.org.apache.bcel.internal.generic.NEW;

import externalForces.Force;
import externalForces.FixedForce;
import externalForces.Gravity;
import objects.Assembly;
import objects.Mass;
import objects.Spring;
import objects.wall.Wall;

public class CustomWorld extends World {
	//private List<Force> myForces = new ArrayList<Force>();
	private HashMap myForces = new HashMap();
	private List<Assembly> myAssemblies = new ArrayList<Assembly>();
	private List<Wall> myWalls = new ArrayList<Wall>();
	
	public CustomWorld(AABB worldAABB, Vec2 gravity, boolean doSleep) {
		super(worldAABB, gravity, doSleep);
	}
	
	public Mass getMass(String id){
		for(Assembly assembly : myAssemblies){
			if(assembly.getMassesMap().get(id) != null)
				return assembly.getMassesMap().get(id);
		}
		return null;
	}

	/*TODO: REFACTOR TO ALLOW THIS METHOD
	public PhysicalSpring getSpring(String id){
		return mySprings.get(id);
	}
	*/
	
	public Mass[] getMasses(){
		List<Mass> masses = new ArrayList<Mass>();
		for(Assembly assembly : myAssemblies){
			masses.addAll(assembly.getMasses());
		}		
		return masses.toArray(new Mass[0]);
	}
	
	public PhysicalSpring[] getSprings(){
		List<PhysicalSpring> springs = new ArrayList<PhysicalSpring>();
		for(Assembly assembly : myAssemblies){
			springs.addAll(assembly.getSprings());
		}
		return springs.toArray(new PhysicalSpring[0]);
	}
	
	public void addAssembly(Assembly assembly){
		myAssemblies.add(assembly);
	}
	/*
	public void addForce(Force force){
		myForces.add(force);
	}
	*/
	
	public void addForce(String id, Force force){
		myForces.put(id, force);
	}
	
	
	public void addWall(Wall wall){
		myWalls.add(wall);
	}
	
	/*
	public List<Force> getForces(){
		return forces;
	}
	*/
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
		Mass[] masses = getMasses();
		for(Force force: (Force[]) myForces.values().toArray()){
		//for(Force force : myForces){
			for(Mass mass : getMasses()){
				force.applyForceToObject(mass);
			}
		}
	}
	
	public void clearAssemblies(){
		PhysicalSpring[] springs = getSprings();
		for(PhysicalSpring spring : springs)
			spring.remove();
		Mass[] masses = getMasses();
		for(Mass mass : masses)
			mass.remove();
		myAssemblies.clear();
	}
	public void clearWalls(){
		for (Wall wall: myWalls){
			wall.remove();
		}
	}
	/*
	public void clearWall(String id){
		if (myWalls.containsKey(id))
			((Wall) myWalls.get(id)).remove();
			myWalls.remove(id);
	}
	*/
}