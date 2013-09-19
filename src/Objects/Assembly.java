package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jboxGlue.PhysicalSpring;

public class Assembly {
	private Map<String, Mass> myMassesMap;
	private List<PhysicalSpring> mySprings;
	public Assembly(Map<String, Mass> masses, List<PhysicalSpring> springs){
		myMassesMap = masses;
		mySprings = springs;
	}
	
	public List<Mass> getMasses(){
		List<Mass> masses = new ArrayList<Mass>();
		for(Mass mass : myMassesMap.values()){
			masses.add(mass);
		}
		System.out.printf("get masses of size %d\n", masses.size());
		return masses;
	}
	
	public Map<String, Mass> getMassesMap(){
		return myMassesMap;
	}
	
	public List<PhysicalSpring> getSprings(){
		return mySprings;
	}
}
