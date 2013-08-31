package gr.daemon.squarerobin.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameTest {

	private static final int GAME_A = 1;	
	private static final String TEAM_A = "Panathinaikos";
	private static final String TEAM_B = "Olympiakos";
	private static final int GOALS_A = 3;
	private static final int GOALS_B = 1;
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
	public void testGetSlotReturnsExpectedSlot() {
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
	public void testSetSlotSetsExpectedSlot() {
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
	public void testIsSettledReturnsTrueWhenSettled() {
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
	public void testSettleSetsExpectedHomeGoals() throws GameAlreadySettledException {
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
	public void testSettleSetsExpectedAwayGoals() throws GameAlreadySettledException {
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
	public void testSettleThrowsExceptionWhenSettled() {
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
	public void testGetScoreReturnsExpectedScore() throws GameNotSettledException {
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
	public void testGetHomeGoalsThrowsExceptionWhenNotSettled(){
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

}
