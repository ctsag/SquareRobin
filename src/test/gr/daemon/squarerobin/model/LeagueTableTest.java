package gr.daemon.squarerobin.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class LeagueTableTest {

	private static final String TEAM_A = "Panathinaikos";
	private static final String TEAM_B = "Panathinaikos";
	private static final int GOALS_A = 3;
	private static final int GOALS_B = 1;
	private static final int GOALS_C = 2;
	private static final int GOALS_D = 0;
	private static final int ZERO = 0;

	@Test
	public void testFormalComparatorReturnsExpectedResultForEquals() {
		// Fixture		
		final LeagueTable.FormalComparator comparator = new LeagueTable.FormalComparator(); 
		final Team teamA = new Team(LeagueTableTest.TEAM_A);
		final Team teamB = new Team(LeagueTableTest.TEAM_B);
		final int expected = LeagueTableTest.ZERO;
		
		// Match
		teamA.win(LeagueTableTest.GOALS_A, LeagueTableTest.GOALS_B, true);
		teamB.win(LeagueTableTest.GOALS_C, LeagueTableTest.GOALS_D, true);
		final int result = comparator.compare(teamA, teamB);
		
		// Assertion
		assertEquals(expected, result);
	}
	
	@Test
	public void testFormalComparatorReturnsExpectedResultForReverseGreaterThan() {
		// Fixture
		final LeagueTable.FormalComparator comparator = new LeagueTable.FormalComparator(); 
		final Team teamA = new Team(LeagueTableTest.TEAM_A);
		final Team teamB = new Team(LeagueTableTest.TEAM_B);
		final int expected = LeagueTableTest.ZERO;
		
		// Match
		teamA.win(LeagueTableTest.GOALS_A, LeagueTableTest.GOALS_B, true);
		teamB.draw(LeagueTableTest.GOALS_C, true);
		final int result = comparator.compare(teamA, teamB);
		
		// Assertion
		assertTrue(expected > result);
	}
	
	@Test
	public void testFormalComparatorReturnsExpectedResultForReverseLessThan() {
		// Fixture
		final LeagueTable.FormalComparator comparator = new LeagueTable.FormalComparator(); 
		final Team teamA = new Team(LeagueTableTest.TEAM_A);
		final Team teamB = new Team(LeagueTableTest.TEAM_B);
		final int expected = LeagueTableTest.ZERO;
		
		// Match
		teamA.draw(LeagueTableTest.GOALS_A, true);
		teamB.win(LeagueTableTest.GOALS_C, LeagueTableTest.GOALS_D, true);
		final int result = comparator.compare(teamA, teamB);
		
		// Assertion
		assertTrue(expected < result);
	}
	
}
