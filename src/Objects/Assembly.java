package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jboxGlue.PhysicalSpring;

/**
 * A collection of masses and springs
 * Can be defined in an xml file
 * 
 * @author tylernisonoff
 *
 */
public class Assembly {
	private Map<String, Mass> myMassesMap;
	private List<PhysicalSpring> mySprings;
	
	/**
	 * Assembly Constructor
	 * @param masses - Map of ids to masses
	 * @param springs - List of Springs
	 */
	public Assembly(Map<String, Mass> masses, List<PhysicalSpring> springs){
		myMassesMap = masses;
		mySprings = springs;
	}
	
	
	/**
	 * Gets a list of masses from the Map
	 * @return List<Mass> list of masses
	 */
	public List<Mass> getMasses(){
		List<Mass> masses = new ArrayList<Mass>();
		for(Mass mass : myMassesMap.values()){
			masses.add(mass);
		}
		return masses;
	}
	
	/**
	 * 
	 * @return Map of ids to Masses
	 */
	public Map<String, Mass> getMassesMap(){
		return myMassesMap;
	}
	
	/**
	 * 
	 * @return list of Springs
	 */
	public List<PhysicalSpring> getSprings(){
		return mySprings;
	}
}
