package gr.daemon.squarerobin.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class TeamTest {

	private static final String TEAM_A = "Panathinaikos";
	private static final String TEAM_B = "Olympiakos";
	private static final int POINTS = 8;
	private static final int POSITION = 1;
	private static final int GOALS_FOR = 32;
	private static final int GOALS_AGAINST = 22;	

	@Test
	public void testConstructor() {
		final String expected = TeamTest.TEAM_A;
		final Team team = new Team(expected);
		assertEquals(expected, team.getName());
	}

	@Test
	public void testName() {
		final String expected = TeamTest.TEAM_A;
		final Team team = new Team(TeamTest.TEAM_B);
		team.setName(expected);
		assertEquals(expected, team.getName());		
	}

	@Test 
	public void testPoints() {		
		final int expected = TeamTest.POINTS;
		final Team team = new Team(TeamTest.TEAM_A);
		team.setPoints(expected);
		assertEquals(expected, team.getPoints());
	}

	@Test 
	public void testPosition() {		
		final int expected = TeamTest.POSITION;
		final Team team = new Team(TeamTest.TEAM_A);
		team.setPosition(expected);
		assertEquals(expected, team.getPosition());
	}

	@Test 
	public void testGoalsFor() {		
		final int expected = TeamTest.GOALS_FOR;
		final Team team = new Team(TeamTest.TEAM_A);
		team.setGoalsFor(expected);
		assertEquals(expected, team.getGoalsFor());
	}

	@Test 
	public void testGoalsAgainst() {		
		final int expected = TeamTest.GOALS_AGAINST;
		final Team team = new Team(TeamTest.TEAM_A);
		team.setGoalsAgainst(expected);
		assertEquals(expected, team.getGoalsAgainst());
	}

	@Test 
	public void testGoalAverage() {		
		final int expected = TeamTest.GOALS_FOR - TeamTest.GOALS_AGAINST;
		final Team team = new Team(TeamTest.TEAM_A);
		team.setGoalsFor(TeamTest.GOALS_FOR);
		team.setGoalsAgainst(TeamTest.GOALS_AGAINST);
		assertEquals(expected, team.getGoalAverage());
	}

}
