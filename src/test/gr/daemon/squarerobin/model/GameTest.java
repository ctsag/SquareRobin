package gr.daemon.squarerobin.model;

import gr.daemon.squarerobin.model.exceptions.DuplicateEntryException;
import gr.daemon.squarerobin.model.exceptions.GameAlreadySettledException;
import gr.daemon.squarerobin.model.exceptions.GameNotSettledException;
import static org.junit.Assert.*;
import org.junit.Test;

public class GameTest {

	private static final int GAME_A = 1;	
	private static final String TEAM_A = "Panathinaikos";
	private static final String TEAM_B = "Olympiakos";
	private static final int GOALS_A = 3;
	private static final int GOALS_B = 1;
	private static final int RESULT_COUNTER = 1;	
	private static final int SLOT_A = 1;

	@Test
	public void testConstructorSetsIndex() {
		// Fixture		
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final int expected = GameTest.GAME_A;
		
		// Match
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		
		// Assertion
		assertEquals(expected, game.getIndex());
	}
	
	@Test
	public void testConstructorSetsHomeTeam() {
		// Fixture		
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Team expected = teamA;
		
		// Match
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		
		// Assertion
		assertEquals(expected, game.getHomeTeam());
	}
	
	@Test
	public void testConstructorSetsAwayTeam() {
		// Fixture		
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Team expected = teamB;
		
		// Match
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		
		// Assertion
		assertEquals(expected, game.getAwayTeam());
	}
	
	@Test
	public void testGetIndexReturnsExpectedIndex() {
		// Fixture
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final int expected = GameTest.GAME_A;
		
		// Match
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		
		// Assertion
		assertEquals(expected, game.getIndex());
	}
	
	@Test
	public void testGetHomeTeamReturnsExpectedHomeTeam() {
		// Fixture		
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Team expected = teamA;
		
		// Match
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		
		// Assertion
		assertEquals(expected, game.getHomeTeam());
	}
	
	@Test
	public void testGetAwayTeamReturnsExpectedAwayTeam() {
		// Fixture		
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Team expected = teamB;
		
		// Match
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		
		// Assertion
		assertEquals(expected, game.getAwayTeam());
	}
	
	@Test
	public void testGetSlotReturnsExpectedSlot() throws DuplicateEntryException {
		// Fixture
		final Slot slot = new Slot(GameTest.SLOT_A);
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		final Slot expected = slot;

		// Match
		game.setSlot(slot);
		
		// Assertion
		assertSame(expected, game.getSlot());
	}

	@Test
	public void testSetSlotSetsExpectedSlot() throws DuplicateEntryException {
		// Fixture
		final Slot slot = new Slot(GameTest.SLOT_A);
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		final Slot expected = slot;

		// Match
		game.setSlot(slot);
		
		// Assertion
		assertSame(expected, game.getSlot());
	}
	
	@Test
	public void testIsSettledReturnsTrueWhenSettled() throws GameAlreadySettledException {
		// Fixture		
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		
		// Match
		game.settle();
		
		
		// Assertion
		assertTrue(game.isSettled());
	}

	@Test
	public void testIsSettledReturnsFalseWhenNotSettled() {
		// Fixture
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);	
		
		// Match
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		
		// Assertion
		assertFalse(game.isSettled());
	}

	@Test
	public void testSettleSetsExpectedSettledStateWhenInRandomMode() throws GameAlreadySettledException {
		// Fixture
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);		
		
		// Match
		game.settle();
		
		// Assertion
		assertTrue(game.isSettled());
	}
	
	@Test
	public void testSettleSetsExpectedSettledStateWhenInFixedMode() throws GameAlreadySettledException {
		// Fixture
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);		
		
		// Match
		game.settle(GameTest.GOALS_A, GameTest.GOALS_B);
		
		// Assertion
		assertTrue(game.isSettled());
	}
	
	@Test
	public void testSettleSetsExpectedHomeGoals() throws GameAlreadySettledException, GameNotSettledException {
		// Fixture
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		final int expected = GameTest.GOALS_A;
		
		// Match
		game.settle(GameTest.GOALS_A, GameTest.GOALS_B);
		
		// Assertion
		assertEquals(expected, game.getHomeGoals());
	}
	
	@Test
	public void testSettleSetsExpectedAwayGoals() throws GameAlreadySettledException, GameNotSettledException {
		// Fixture
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		final int expected = GameTest.GOALS_B;
		
		// Match
		game.settle(GameTest.GOALS_A, GameTest.GOALS_B);
		
		// Assertion
		assertEquals(expected, game.getAwayGoals());
	}
	
	@Test
	public void testSettleSetsExpectedWinner() throws GameAlreadySettledException, GameNotSettledException {
		// Fixture
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		final int expected = GameTest.RESULT_COUNTER;
		
		// Match
		game.settle(GameTest.GOALS_A, GameTest.GOALS_B);
		
		// Assertion
		assertEquals(expected, teamA.getWins());
	}
	
	@Test
	public void testSettleSetsExpectedLoser() throws GameAlreadySettledException, GameNotSettledException {
		// Fixture
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Game game = new Game(GameTest.GAME_A, teamB, teamA);
		final int expected = GameTest.RESULT_COUNTER;
		
		// Match
		game.settle(GameTest.GOALS_B, GameTest.GOALS_A);
		
		// Assertion
		assertEquals(expected, teamB.getLosses());
	}
	
	@Test
	public void testSettleSetsExpectedDraw() throws GameAlreadySettledException, GameNotSettledException {
		// Fixture
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		final int expected = GameTest.RESULT_COUNTER;
		
		// Match
		game.settle(GameTest.GOALS_A, GameTest.GOALS_A);
		
		// Assertion
		assertEquals(expected, teamA.getDraws());
	}
	
	@Test
	public void testSettleThrowsExceptionWhenSettled() throws GameAlreadySettledException {
		// Fixture
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		
		// Match
		game.settle();
		try {			
			game.settle();
			fail("Exception not thrown for attempting to settle an already settled game");
		} catch(GameAlreadySettledException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());
		}
	}
	
	@Test
	public void testGetScoreReturnsExpectedScore() throws GameNotSettledException, GameAlreadySettledException {
		// Fixture
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		final String expected = GameTest.GOALS_A + " - " + GameTest.GOALS_B;
		
		// Match
		game.settle(GameTest.GOALS_A, GameTest.GOALS_B);
		
		// Assertion
		assertEquals(expected, game.getScore());
	}
	
	@Test
	public void testGetHomeGoalsThrowsExceptionWhenNotSettled() {
		// Fixture
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		
		// Match
		try {			
			game.getHomeGoals();
			fail("Exception not thrown for attempting to access the score for an unsettled game");
		} catch(GameNotSettledException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());
		}
	}
	
	@Test
	public void testGetAwayGoalsThrowsExceptionWhenNotSettled() {
		// Fixture
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		
		// Match
		try {			
			game.getAwayGoals();
			fail("Exception not thrown for attempting to access the score for an unsettled game");
		} catch(GameNotSettledException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());
		}
	}
	
	@Test
	public void testGetScoreThrowsExceptionWhenNotSettled() {
		// Fixture
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		
		// Match
		try {			
			game.getScore();
			fail("Exception not thrown for attempting to access the score for an unsettled game");
		} catch(GameNotSettledException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());
		}
	}
	
	@Test
	public void testResetTeamsResetsTeamsWhenNotSettled() throws GameAlreadySettledException{
		// Fixture
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		final Team expected = teamB;
		
		// Match
		game.resetTeams(teamB, teamA);
		
		// Assertion
		assertSame(expected, game.getHomeTeam());		
	}
	
	@Test
	public void testResetTeamsThrowsExceptionWhenSettled() throws GameAlreadySettledException {
		// Fixture
		final Team teamA = new Team(GameTest.TEAM_A);
		final Team teamB = new Team(GameTest.TEAM_B);
		final Game game = new Game(GameTest.GAME_A, teamA, teamB);
		
		// Match
		game.settle();
		try {			
			game.resetTeams(teamB, teamA);
			fail("Exception not thrown for attempting to reset teams for an unsettled game");
		} catch(GameAlreadySettledException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());
		}
	}

}
