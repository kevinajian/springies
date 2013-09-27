package review.forces;


import org.jbox2d.common.Vec2;

import externalForces.Force;
import review.TestHorizontalWall;
import review.TestMass;
import review.TestVerticalWall;
import review.TestWorld;
import review.TestWorldManager;
import junit.*;
import junit.framework.TestCase;
public class ForcesUnitTests extends TestCase {
	private TestMass myMass;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.resetMass();
	}

	private void resetMass() {
		myMass = new TestMass((float)5.0);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Tests basic Gravity functionality without scalar
	 * Tests to see if force to be applied is equal to
	 * the Gravities intitialized force multiplied by 
	 * the objects mass 
	 */
	public void testBasicGravity() {
		Vec2 gravityUnitDirection = new Vec2(0, (float)-1);
		Gravity g = new Gravity((float)5.0, gravityUnitDirection);
		Vec2 expected = new Vec2(0, (float)-25);
		Vec2 result = g.getForceToApply(myMass);
		assertEquals(expected.x, result.x);
		assertEquals(expected.y, result.y);
	}
	
	/**
	 * Tests Gravity functionality with scale factor
	 * Tests to see if force to be applied is equal to
	 * the Gravities intitialized force multiplied by 
	 * the objects mass scaled down by the factor set
	 * by force.setScaleFactor
	 */
	public void testScaledGravity() {
		Vec2 gravityUnitDirection = new Vec2(0, (float)-1);
		Gravity g = new Gravity((float)5.0, gravityUnitDirection);
		g.setScaleFactor((float).1);
		Vec2 expected = new Vec2(0, (float)-2.5);
		Vec2 actual = g.getForceToApply(myMass);
		assertEquals(expected.x, actual.x);
		assertEquals(expected.y, actual.y);
	}

	/**
	 * Tests basic Viscosity functionality without scalar
	 * Tests to see if force to be applied is equal to
	 * the negative of the mass' velocity times the 
	 * forces initial magnitude
	 */
	public void testBasicViscosity() {
		Viscosity v = new Viscosity((float)0.8);
		myMass.setVecolity(0, (float)10);
		Vec2 expected = new Vec2(0, (float)8.0).mul((float)-1);
		Vec2 actual = v.getForceToApply(myMass);
		assertEquals(expected.x, actual.x);
		assertEquals(expected.y, actual.y);
	}
	
	/**
	 * Tests for proper functionality when applied force
	 * is scaled down by a factor of 1/2
	 */
	public void testScaledViscosity() {
		Viscosity v = new Viscosity((float)0.8);
		v.setScaleFactor((float).5);
		myMass.setVecolity(0, (float)10);
		System.out.println(myMass.getVelocity());
		Vec2 expected = new Vec2(0, (float)4).mul((float)-1);
		Vec2 actual = v.getForceToApply(myMass);
		System.out.println(actual.y);
		assertEquals(expected.x, actual.x);
		assertEquals(expected.y, actual.y);
	}

	public void testCenterOfMass() {
		TestWorld test = TestWorldManager.getWorld();
		test.setCenterOfMass((float)10, (float)10);
		CenterOfMass c = new CenterOfMass((float)5, (float)2);
		myMass.setPosition((float)10, 0);
		Vec2 expected = new Vec2(0, (float)0.05);
		Vec2 actual = c.getForceToApply(myMass);
		assertEquals(expected.x, actual.x);
		assertEquals(expected.y, actual.y);
	}
	
	/**
	 * Tests wall Repulsion on vertical wall
	 * checks that force is inversely proportional
	 * to the distance, which is based on x coords
	 */
	public void testVerticalWallRepulsion() {
		TestVerticalWall w = new TestVerticalWall(10, 0);
		WallRepulsion wr = new WallRepulsion((float)5, (float)2, new Vec2((float)-1, 0), w);
		Vec2 expected = new Vec2((float)-0.05, 0);
		Vec2 actual = wr.getForceToApply(myMass);
		assertEquals(expected.x, actual.x);
		assertEquals(expected.y, actual.y);
	}
	
	/**
	 * Tests wall Repulsion on horizontal wall
	 * checks that force is inversely proportional
	 * to the distance, which is based on y coords
	 */
	public void testHorizontalWallRepulsion() {
		TestHorizontalWall w = new TestHorizontalWall(0, 10);
		WallRepulsion wr = new WallRepulsion((float)5, (float)2, new Vec2(0, (float)-1.0), w);
		Vec2 expected = new Vec2(0, (float) -.05);
		Vec2 actual = wr.getForceToApply(myMass);
		assertEquals(expected.x, actual.x);
		assertEquals(expected.y, actual.y);
	}
}