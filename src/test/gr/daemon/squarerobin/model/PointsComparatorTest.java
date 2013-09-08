package gr.daemon.squarerobin.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class PointsComparatorTest {

	private static final String TEAM_A = "Panathinaikos";
	private static final String TEAM_B = "Panathinaikos";
	private static final int GOALS_A = 3;
	private static final int GOALS_B = 1;
	private static final int GOALS_C = 2;
	private static final int GOALS_D = 0;
	private static final int ZERO = 0;

	@Test
	public void testEqual() {
		// Fixture
		final PointsComparator comparator = new PointsComparator(); 
		final Team teamA = new Team(PointsComparatorTest.TEAM_A);
		final Team teamB = new Team(PointsComparatorTest.TEAM_B);
		final int expected = PointsComparatorTest.ZERO;
		
		// Match
		teamA.win(PointsComparatorTest.GOALS_A, PointsComparatorTest.GOALS_B, true);
		teamB.win(PointsComparatorTest.GOALS_C, PointsComparatorTest.GOALS_D, true);
		final int result = comparator.compare(teamA, teamB);
		
		// Assertion
		assertEquals(expected, result);
	}
	
	@Test
	public void testGreaterThan() {
		// Fixture
		final PointsComparator comparator = new PointsComparator(); 
		final Team teamA = new Team(PointsComparatorTest.TEAM_A);
		final Team teamB = new Team(PointsComparatorTest.TEAM_B);
		final int expected = PointsComparatorTest.ZERO;
		
		// Match
		teamA.win(PointsComparatorTest.GOALS_A, PointsComparatorTest.GOALS_B, true);
		teamB.draw(PointsComparatorTest.GOALS_C, true);
		final int result = comparator.compare(teamA, teamB);
		
		// Assertion
		assertTrue(expected < result);
	}
	
	@Test
	public void testLessThan() {
		// Fixture
		final PointsComparator comparator = new PointsComparator(); 
		final Team teamA = new Team(PointsComparatorTest.TEAM_A);
		final Team teamB = new Team(PointsComparatorTest.TEAM_B);
		final int expected = PointsComparatorTest.ZERO;
		
		// Match
		teamA.draw(PointsComparatorTest.GOALS_A, true);
		teamB.win(PointsComparatorTest.GOALS_C, PointsComparatorTest.GOALS_D, true);
		final int result = comparator.compare(teamA, teamB);
		
		// Assertion
		assertTrue(expected > result);
	}
	
}
