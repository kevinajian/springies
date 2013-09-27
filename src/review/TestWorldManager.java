package review;

import jboxGlue.CustomWorld;

public class TestWorldManager {
	
public static TestWorld ourWorld;
	
	static
	{
		ourWorld = new TestWorld(0, 0);
	}
	
	public static TestWorld getWorld( )
	{	
		return ourWorld;
	}
}
