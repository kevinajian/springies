 package jboxGlue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/**
 * A wrapper around Jbox World to include the assemblies, walls, and forces
 * 
 * @author tylernisonoff
 *
 */
public class CustomWorld extends World {
	private List<Force> myForces = new ArrayList<Force>();
	private List<Assembly> myAssemblies = new ArrayList<Assembly>();
	private Map<String, Wall> myWalls = new HashMap<String, Wall>();
	private double[] myWallDimensions = new double[]{10, 0, 0};
	
	/**
	 * 
	 * @param worldAABB - Bounding box for the world
	 * @param gravity - Default gravity for JBox
	 * @param doSleep - JBox doSleep param
	 */
	public CustomWorld(AABB worldAABB, Vec2 gravity, boolean doSleep) {
		super(worldAABB, gravity, doSleep);
	}
	
	
	/**
	 * 
	 * @param id - id of the mass
	 * @return - the mass associated with that id
	 */
	public Mass getMass(String id){
		for(Assembly assembly : myAssemblies){
			if(assembly.getMassesMap().get(id) != null)
				return assembly.getMassesMap().get(id);
		}
		return null;
	}
	
	/**
	 * 
	 * @return List of masses in the world
	 */
	public Mass[] getMasses(){
		List<Mass> masses = new ArrayList<Mass>();
		for(Assembly assembly : myAssemblies){
			masses.addAll(assembly.getMasses());
		}		
		return masses.toArray(new Mass[0]);
	}
	
	/**
	 * 
	 * @return List of Springs in the world
	 */
	public PhysicalSpring[] getSprings(){
		List<PhysicalSpring> springs = new ArrayList<PhysicalSpring>();
		for(Assembly assembly : myAssemblies){
			springs.addAll(assembly.getSprings());
		}
		return springs.toArray(new PhysicalSpring[0]);
	}
	
	/**
	 *  Adds an assembly to the world
	 * @param assembly - Assembly to be added
	 */
	public void addAssembly(Assembly assembly){
		myAssemblies.add(assembly);
	}
	
	/**
	 *  Adds a Force to the world
	 * @param force - Force to be added
	 */
	public void addForce(Force force){
		myForces.add(force);
	}
	
	/**
	 *  Adds a wall to the world
	 * @param id - id of the wall
	 * @param wall - wall to be added
	 */
	public void addWall(String id, Wall wall){
		myWalls.put(id, wall);
	}
	
	/**
	 * Calculates the center of mass of the world based on the masses
	 * @return A vector of the location of the center of mass
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
	
	/**
	 * 
	 * @return Total Mass in the world
	 */
	private float getTotalMassOfSystem(){
		float totalMass = 0;
		for(Mass mass : getMasses()){
			totalMass += mass.getMass();
		}
		return totalMass;
	}
	
	/**
	 * Method called to apply forces set in the world to the masses
	 */
	public void applyEnvironmentalForces() {
		Mass[] masses = getMasses();
		for (Force force : myForces) {
			for (Mass mass : getMasses()) {
				force.applyForceToObject(mass);
			}
		}
	}
	
	/**
	 * Deletes all of the assemblies in the world 
	 */
	public void clearAssemblies(){
		PhysicalSpring[] springs = getSprings();
		for(PhysicalSpring spring : springs)
			spring.remove();
		Mass[] masses = getMasses();
		for(Mass mass : masses)
			mass.remove();
		myAssemblies.clear();
	}
		
	/**
	 * Finds the closest mass to another mass
	 * Used by the mouse for user interaction
	 * @param m - mass who we want to find its nearest neighbor
	 * @return - the mass who is closest
	 */
	public Mass findClosestMass(Mass m){
		Mass[] masses = getMasses();
		if(masses.length==0)return null;
		Mass closestMass = masses[0];
		float minDistance = Integer.MAX_VALUE;
		
		for(Mass mass : masses){
			float distance = mass.distance(m);
			if(distance < minDistance){
				minDistance = distance;
				closestMass = mass;
			}
		}
		return closestMass;
	}
	
	/**
	 * 
	 * @param id - Walls id
	 * @return - Wall with that id
	 */
	public Wall getWall(String id){
		return myWalls.get(id);
	}

	/**
	 * 
	 * @return - returns current wall dimensions
	 */
	public double[] getWallDimensions(){
		return myWallDimensions;
	}
	
	/**
	 * Sets new wall dimensions
	 * @param newWallDimensions - double[] of new wall dimensions
	 */
	public void setWallDimensions(double[] newWallDimensions){
		myWallDimensions[0] = newWallDimensions[0];
		myWallDimensions[1] = newWallDimensions[1];
		myWallDimensions[2] = newWallDimensions[2];
	}
	
	/**
	 * Clears all of the walls from the world
	 */
	public void clearWalls(){
		Wall[] walls  = myWalls.values().toArray(new Wall[0]);
		for (Wall wall: walls){
			wall.remove();
		}
	}
}
