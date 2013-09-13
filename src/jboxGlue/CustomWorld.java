package jboxGlue;

import java.util.HashMap;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import objects.Mass;
import objects.Spring;

public class CustomWorld extends World {
	private HashMap<String, Mass> myMasses = new HashMap<String, Mass>();
	private HashMap<String, Spring> mySprings = new HashMap<String, Spring>();
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
		return (Mass[])myMasses.values().toArray();
	}
	
	public Spring[] getSprings(){
		return (Spring[])myMasses.values().toArray();
	}
	
	public void addMass(Mass mass){
		myMasses.put(mass.getName(), mass);
	}
	
	public void addSpring(Spring spring){
		mySprings.put(spring.getName(), spring);
	}
	

}
