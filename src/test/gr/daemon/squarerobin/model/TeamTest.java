package gr.daemon.squarerobin.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TeamTest {

	private static final String TEAM_A = "Panathinaikos";
	private static final int GOALS_A = 3;
	private static final int GOALS_B = 1;
	private static final int GAMES_A = 1;
	private static final int RESULTS_A = 1;	

	@Test
	public void testConstructorSetsName() {
		// Fixture
		final String expected = TeamTest.TEAM_A;
		
		// Match
		final Team team = new Team(TeamTest.TEAM_A);
		
		// Assertion
		assertEquals(expected, team.getName());
	}
	
	@Test
	public void testGetNameReturnsExpectedName() {
		// Fixture
		final String expected = TeamTest.TEAM_A;
		
		// Match
		final Team team = new Team(TeamTest.TEAM_A);
		
		// Assertion
		assertEquals(expected, team.getName());
	}
	
	@Test
	public void testGetHomeGoalsForReturnsExpectedHomeGoalsFor() {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_A;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, true);
		
		// Assertion
		assertEquals(expected, team.getHomeGoalsFor());
	}
	
	@Test
	public void testGetHomeGoalsAgainstReturnsExpectedHomeGoalsAgainst() {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_B;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, true);
		
		// Assertion
		assertEquals(expected, team.getHomeGoalsAgainst());
	}
	
	@Test
	public void testGetAwayGoalsForReturnsExpectedAwayGoalsFor() {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_A;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, false);
		
		// Assertion
		assertEquals(expected, team.getAwayGoalsFor());
	}

	@Test
	public void testGetAwayGoalsAgainstReturnsExpectedAwayGoalsAgainst() {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_B;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, false);
		
		// Assertion
		assertEquals(expected, team.getAwayGoalsAgainst());
	}
	
	@Test
	public void testGetHomeGamesPlayedReturnsExpectedHomeGamesPlayed() {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GAMES_A;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, true);
		
		// Assertion
		assertEquals(expected, team.getHomeGamesPlayed());
	}
	
	@Test
	public void testGetAwayGamesPlayedReturnsExpectedAwayGamesPlayed() {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GAMES_A;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, false);
		
		// Assertion
		assertEquals(expected, team.getAwayGamesPlayed());
	}
	
	@Test
	public void testGetHomeWinsReturnsExpectedHomeWins() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.RESULTS_A;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, true);
		
		// Assertion
		assertEquals(expected, team.getHomeWins());
	}
	
	@Test
	public void testGetAwayWinsReturnsExpectedAwayWins() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.RESULTS_A;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, false);
		
		// Assertion
		assertEquals(expected, team.getAwayWins());
	}
	
	@Test
	public void testGetHomeDrawsReturnsExpectedHomeDraws() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.RESULTS_A;
		
		// Match
		team.draw(TeamTest.GOALS_A, TeamTest.GOALS_A, true);
		
		// Assertion
		assertEquals(expected, team.getHomeDraws());
	}
	
	@Test
	public void testGetAwayDrawsReturnsExpectedAwayDraws() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.RESULTS_A;
		
		// Match
		team.draw(TeamTest.GOALS_A, TeamTest.GOALS_A, false);
		
		// Assertion
		assertEquals(expected, team.getAwayDraws());
	}
	
	@Test
	public void testGetHomeLossesReturnsExpectedHomeLosses() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.RESULTS_A;
		
		// Match
		team.lose(TeamTest.GOALS_B, TeamTest.GOALS_A, true);
		
		// Assertion
		assertEquals(expected, team.getHomeLosses());
	}
	
	@Test
	public void testGetAwayLossesReturnsExpectedAwayLosses() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.RESULTS_A;
		
		// Match
		team.lose(TeamTest.GOALS_B, TeamTest.GOALS_A, false);
		
		// Assertion
		assertEquals(expected, team.getAwayLosses());
	}
	
	@Test
	public void testGetHomePointsReturnsExpectedHomePoints() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = Team.POINTS_FOR_WIN;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, true);
		
		// Assertion
		assertEquals(expected, team.getHomePoints());
	}
	
	@Test
	public void testGetAwayPointsReturnsExpectedAwayPoints() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = Team.POINTS_FOR_WIN;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, false);
		
		// Assertion
		assertEquals(expected, team.getAwayPoints());
	}
	
	@Test
	public void testGetGoalsForReturnsExpectedGoalsFor() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_A * 2;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, true);
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, false);
		
		// Assertion
		assertEquals(expected, team.getGoalsFor());
	}
	
	@Test
	public void testGetGoalsAgainsReturnsExpectedGoalsAgainst() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_B * 2;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, true);
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, false);
		
		// Assertion
		assertEquals(expected, team.getGoalsAgainst());
	}
	
	@Test
	public void testGetGoalAverageReturnsExpectedGoalAverage() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = (TeamTest.GOALS_A - TeamTest.GOALS_B) * 2;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, true);
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, false);
		
		// Assertion
		assertEquals(expected, team.getGoalAverage());
	}

	@Test
	public void testGetHomeGoalAverageReturnsExpectedHomeGoalAverage() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_A - TeamTest.GOALS_B;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, true);		
		
		// Assertion
		assertEquals(expected, team.getHomeGoalAverage());
	}
	
	@Test
	public void testGetAwayGoalAverageReturnsExpectedAwayGoalAverage() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_A - TeamTest.GOALS_B;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, false);		
		
		// Assertion
		assertEquals(expected, team.getAwayGoalAverage());
	}
	
	@Test
	public void testGetWinsReturnsExpectedWins() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.RESULTS_A * 2;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, true);
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, false);
		
		// Assertion
		assertEquals(expected, team.getWins());
	}
	
	@Test
	public void testGetDrawsReturnsExpectedDraws() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.RESULTS_A * 2;
		
		// Match
		team.draw(TeamTest.GOALS_A, TeamTest.GOALS_A, true);
		team.draw(TeamTest.GOALS_A, TeamTest.GOALS_A, false);
		
		// Assertion
		assertEquals(expected, team.getDraws());
	}
	
	@Test
	public void testGetLossesReturnsExpectedLosses() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.RESULTS_A * 2;
		
		// Match
		team.lose(TeamTest.GOALS_B, TeamTest.GOALS_A, true);
		team.lose(TeamTest.GOALS_B, TeamTest.GOALS_A, false);
		
		// Assertion
		assertEquals(expected, team.getLosses());
	}
	
	@Test
	public void testGetGamesPlayedReturnsExpectedGamesPlayed() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GAMES_A * 2;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, true);
		team.lose(TeamTest.GOALS_B, TeamTest.GOALS_A, false);
		
		// Assertion
		assertEquals(expected, team.getGamesPlayed());
	}
	
	@Test
	public void testGetPointsReturnsExpectedPoints() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = Team.POINTS_FOR_WIN + Team.POINTS_FOR_DRAW;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, true);
		team.draw(TeamTest.GOALS_B, TeamTest.GOALS_B, false);
		
		// Assertion
		assertEquals(expected, team.getPoints());
	}
	
	@Test
	public void testWinAddsExpectedHomeGamesPlayed() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GAMES_A;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, true);
		
		// Assertion
		assertEquals(expected, team.getHomeGamesPlayed());
	}
	
	@Test
	public void testWinAddsExpectedHomeWins() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GAMES_A;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, true);
		
		// Assertion
		assertEquals(expected, team.getHomeWins());
	}
	
	@Test
	public void testWinAddsExpectedHomeGoalsFor() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_A;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, true);
		
		// Assertion
		assertEquals(expected, team.getHomeGoalsFor());
	}
	
	@Test
	public void testWinAddsExpectedHomeGoalsAgainst() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_B;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, true);
		
		// Assertion
		assertEquals(expected, team.getHomeGoalsAgainst());
	}
	
	@Test
	public void testWinAddsExpectedHomePoints() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = Team.POINTS_FOR_WIN;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, true);
		
		// Assertion
		assertEquals(expected, team.getHomePoints());
	}
	
	@Test
	public void testWinAddsExpectedAwayGamesPlayed() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GAMES_A;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, false);
		
		// Assertion
		assertEquals(expected, team.getAwayGamesPlayed());
	}
	
	@Test
	public void testWinAddsExpectedAwayWins() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GAMES_A;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, false);
		
		// Assertion
		assertEquals(expected, team.getAwayWins());
	}
	
	@Test
	public void testWinAddsExpectedAwayGoalsFor() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_A;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, false);
		
		// Assertion
		assertEquals(expected, team.getAwayGoalsFor());
	}
	
	@Test
	public void testWinAddsExpectedAwayGoalsAgainst() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_B;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, false);
		
		// Assertion
		assertEquals(expected, team.getAwayGoalsAgainst());
	}
	
	@Test
	public void testWinAddsExpectedAwayPoints() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = Team.POINTS_FOR_WIN;
		
		// Match
		team.win(TeamTest.GOALS_A, TeamTest.GOALS_B, false);
		
		// Assertion
		assertEquals(expected, team.getAwayPoints());
	}
	
	@Test
	public void testDrawAddsExpectedHomeGamesPlayed() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GAMES_A;
		
		// Match
		team.draw(TeamTest.GOALS_A, TeamTest.GOALS_A, true);
		
		// Assertion
		assertEquals(expected, team.getHomeGamesPlayed());
	}
	
	@Test
	public void testDrawAddsExpectedHomeDraws() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GAMES_A;
		
		// Match
		team.draw(TeamTest.GOALS_A, TeamTest.GOALS_A, true);
		
		// Assertion
		assertEquals(expected, team.getHomeDraws());
	}
	
	@Test
	public void testDrawAddsExpectedHomeGoalsFor() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_A;
		
		// Match
		team.draw(TeamTest.GOALS_A, TeamTest.GOALS_A, true);
		
		// Assertion
		assertEquals(expected, team.getHomeGoalsFor());
	}
	
	@Test
	public void testDrawAddsExpectedHomeGoalsAgainst() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_A;
		
		// Match
		team.draw(TeamTest.GOALS_A, TeamTest.GOALS_A, true);
		
		// Assertion
		assertEquals(expected, team.getHomeGoalsAgainst());
	}
	
	@Test
	public void testDrawAddsExpectedHomePoints() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = Team.POINTS_FOR_DRAW;
		
		// Match
		team.draw(TeamTest.GOALS_A, TeamTest.GOALS_A, true);
		
		// Assertion
		assertEquals(expected, team.getHomePoints());
	}
	
	@Test
	public void testDrawAddsExpectedAwayGamesPlayed() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GAMES_A;
		
		// Match
		team.draw(TeamTest.GOALS_A, TeamTest.GOALS_A, false);
		
		// Assertion
		assertEquals(expected, team.getAwayGamesPlayed());
	}
	
	@Test
	public void testDrawAddsExpectedAwayDraws() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GAMES_A;
		
		// Match
		team.draw(TeamTest.GOALS_A, TeamTest.GOALS_A, false);
		
		// Assertion
		assertEquals(expected, team.getAwayDraws());
	}
	
	@Test
	public void testDrawAddsExpectedAwayGoalsFor() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_A;
		
		// Match
		team.draw(TeamTest.GOALS_A, TeamTest.GOALS_A, false);
		
		// Assertion
		assertEquals(expected, team.getAwayGoalsFor());
	}
	
	@Test
	public void testDrawAddsExpectedAwayGoalsAgainst() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_A;
		
		// Match
		team.draw(TeamTest.GOALS_A, TeamTest.GOALS_A, false);
		
		// Assertion
		assertEquals(expected, team.getAwayGoalsAgainst());
	}
	
	@Test
	public void testDrawAddsExpectedAwayPoints() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = Team.POINTS_FOR_DRAW;
		
		// Match
		team.draw(TeamTest.GOALS_A, TeamTest.GOALS_A, false);
		
		// Assertion
		assertEquals(expected, team.getAwayPoints());
	}
	
	@Test
	public void testLoseAddsExpectedHomeGamesPlayed() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GAMES_A;
		
		// Match
		team.lose(TeamTest.GOALS_B, TeamTest.GOALS_A, true);
		
		// Assertion
		assertEquals(expected, team.getHomeGamesPlayed());
	}
	
	@Test
	public void testLoseAddsExpectedHomeLosses() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GAMES_A;
		
		// Match
		team.lose(TeamTest.GOALS_B, TeamTest.GOALS_A, true);
		
		// Assertion
		assertEquals(expected, team.getHomeLosses());
	}
	
	@Test
	public void testLoseAddsExpectedHomeGoalsFor() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_B;
		
		// Match
		team.lose(TeamTest.GOALS_B, TeamTest.GOALS_A, true);
		
		// Assertion
		assertEquals(expected, team.getHomeGoalsFor());
	}
	
	@Test
	public void testLoseAddsExpectedHomeGoalsAgainst() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_A;
		
		// Match
		team.lose(TeamTest.GOALS_B, TeamTest.GOALS_A, true);
		
		// Assertion
		assertEquals(expected, team.getHomeGoalsAgainst());
	}
	
	@Test
	public void testLoseAddsExpectedHomePoints() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = Team.POINTS_FOR_LOSS;
		
		// Match
		team.lose(TeamTest.GOALS_B, TeamTest.GOALS_A, true);
		
		// Assertion
		assertEquals(expected, team.getHomePoints());
	}
	
	@Test
	public void testLoseAddsExpectedAwayGamesPlayed() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GAMES_A;
		
		// Match
		team.lose(TeamTest.GOALS_B, TeamTest.GOALS_A, false);
		
		// Assertion
		assertEquals(expected, team.getAwayGamesPlayed());
	}
	
	@Test
	public void testLoseAddsExpectedAwayLosses() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GAMES_A;
		
		// Match
		team.lose(TeamTest.GOALS_B, TeamTest.GOALS_A, false);
		
		// Assertion
		assertEquals(expected, team.getAwayLosses());
	}
	
	@Test
	public void testLoseAddsExpectedAwayGoalsFor() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_B;
		
		// Match
		team.lose(TeamTest.GOALS_B, TeamTest.GOALS_A, false);
		
		// Assertion
		assertEquals(expected, team.getAwayGoalsFor());
	}
	
	@Test
	public void testLoseAddsExpectedAwayGoalsAgainst() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = TeamTest.GOALS_A;
		
		// Match
		team.lose(TeamTest.GOALS_B, TeamTest.GOALS_A, false);
		
		// Assertion
		assertEquals(expected, team.getAwayGoalsAgainst());
	}
	
	@Test
	public void testLoseAddsExpectedAwayPoints() throws InvalidScoreException {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		final int expected = Team.POINTS_FOR_LOSS;
		
		// Match
		team.lose(TeamTest.GOALS_B, TeamTest.GOALS_A, false);
		
		// Assertion
		assertEquals(expected, team.getAwayPoints());
	}
	
	@Test
	public void testWinThrowsExceptionWhenGoalsAgainstAreMoreThanGoalsFor() {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		
		// Match		
		try {			
			team.win(TeamTest.GOALS_B, TeamTest.GOALS_A, false);
			fail("Exception not thrown for goals against being more than goals for on a win scenario");
		} catch(InvalidScoreException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());
		}
	}
	
	@Test
	public void testDrawThrowsExceptionWhenGoalsForAreNotEqualToGoalsAgainst() {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		
		// Match		
		try {			
			team.draw(TeamTest.GOALS_A, TeamTest.GOALS_B, false);
			fail("Exception not thrown for goals for being not equal to goals against on a draw scenario");
		} catch(InvalidScoreException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());
		}
	}
	
	@Test
	public void testLoseThrowsExceptionWhenGoalsForAreMoreThanGoalsAgainst() {
		// Fixture
		final Team team = new Team(TeamTest.TEAM_A);
		
		// Match		
		try {			
			team.lose(TeamTest.GOALS_A, TeamTest.GOALS_B, false);
			fail("Exception not thrown for goals for being more than goals against on a loss scenario");
		} catch(InvalidScoreException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());
		}
	}

}
