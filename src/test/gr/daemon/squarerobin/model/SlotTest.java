package gr.daemon.squarerobin.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class SlotTest {

	private static final int SLOT_A = 1;	
	private static final int GAME_A = 1;
	private static final int GAME_B = 2;
	private static final String TEAM_A = "Panathinaikos";
	private static final String TEAM_B = "Olympiakos";
	private static final String TEAM_C = "AEK";
	private static final String TEAM_D = "PAOK";
	private static final int ROUND_A = 1;
	private static final int EMPTY_LIST = 0;
	private static final int FULL_LIST = 2;

	@Test
	public void testConstructorSetsIndex() {
		// Fixture
		final int expected = SlotTest.SLOT_A;
		
		// Match
		final Slot slot = new Slot(expected);
		
		// Assertion
		assertEquals(expected, slot.getIndex());
	}
	
	@Test
	public void testGetIndexReturnsExpectedIndex() {
		// Fixture
		final int expected = SlotTest.SLOT_A;
		
		// Match
		final Slot slot = new Slot(SlotTest.SLOT_A);		
		
		// Assertion
		assertEquals(expected, slot.getIndex());
	}
	
	@Test
	public void testGetRoundReturnsExpectedRound() {
		// Fixture
		final Round round = new Round(SlotTest.ROUND_A);
		final Slot slot = new Slot(SlotTest.SLOT_A);
		final Round expected = round;

		// Match
		slot.setRound(round);
		
		// Assertion
		assertSame(expected, slot.getRound());
	}

	@Test
	public void testSetRoundSetsExpectedRound() {
		// Fixture
		final Round round = new Round(SlotTest.ROUND_A);
		final Slot slot = new Slot(SlotTest.SLOT_A);
		final Round expected = round;

		// Match
		slot.setRound(round);
		
		// Assertion
		assertSame(expected, slot.getRound());
	}

	@Test
	public void testGetGamesReturnsExpectedArray() throws DuplicateEntryException {
		// Fixture
		final Slot slot = new Slot(SlotTest.SLOT_A);
		final Team teamA = new Team(SlotTest.TEAM_A);
		final Team teamB = new Team(SlotTest.TEAM_B);
		final Team teamC = new Team(SlotTest.TEAM_C);
		final Team teamD = new Team(SlotTest.TEAM_D);
		final Game gameA = new Game(SlotTest.GAME_A, teamA, teamB);
		final Game gameB = new Game(SlotTest.GAME_B, teamC, teamD);
		final int expected = SlotTest.FULL_LIST;
		
		// Match		
		slot.addGame(gameA);
		slot.addGame(gameB);
		
 		// Assertion
		assertEquals(expected, slot.getGames().length);		
	}
	
	@Test
	public void testGetGameReturnsExpectedGame() throws DuplicateEntryException {
		// Fixture
		final Slot slot = new Slot(SlotTest.SLOT_A);
		final Team teamA = new Team(SlotTest.TEAM_A);
		final Team teamB = new Team(SlotTest.TEAM_B);
		final Team teamC = new Team(SlotTest.TEAM_C);
		final Team teamD = new Team(SlotTest.TEAM_D);
		final Game gameA = new Game(SlotTest.GAME_A, teamA, teamB);
		final Game gameB = new Game(SlotTest.GAME_B, teamC, teamD);
		final Game expected = gameA;
		
		// Match
		slot.addGame(gameA);
		slot.addGame(gameB);
		
		// Assertion
		assertSame(expected, slot.getGame(SlotTest.GAME_A));		
	}
	
	@Test
	public void testGetGameReturnsNullForInexistentGame() throws DuplicateEntryException {
		// Fixture
		final Slot slot = new Slot(SlotTest.SLOT_A);
		final Team teamA = new Team(SlotTest.TEAM_A);
		final Team teamB = new Team(SlotTest.TEAM_B);
		final Game gameA = new Game(SlotTest.GAME_A, teamA, teamB);

		// Match
		slot.addGame(gameA);
				
		// Assertion
		assertNull(slot.getGame(SlotTest.GAME_B));
	}
	
	@Test
	public void testAddGameAppendsUniqueGame() throws DuplicateEntryException {
		// Fixture
		final Slot slot = new Slot(SlotTest.SLOT_A);
		final Team teamA = new Team(SlotTest.TEAM_A);
		final Team teamB = new Team(SlotTest.TEAM_B);
		final Team teamC = new Team(SlotTest.TEAM_C);
		final Team teamD = new Team(SlotTest.TEAM_D);
		final Game gameA = new Game(SlotTest.GAME_A, teamA, teamB);
		final Game gameB = new Game(SlotTest.GAME_B, teamC, teamD);
		final int expected = SlotTest.FULL_LIST;
		
		// Match		
		slot.addGame(gameA);
		slot.addGame(gameB);
		
		// Assertion
		assertEquals(expected, slot.getGames().length);
	}
	
	@Test
	public void testAddGameSetsTournamentForGame() throws DuplicateEntryException {
		// Fixture
		final Slot slot = new Slot(SlotTest.SLOT_A);
		final Team teamA = new Team(SlotTest.TEAM_A);
		final Team teamB = new Team(SlotTest.TEAM_B);
		final Game gameA = new Game(SlotTest.GAME_A, teamA, teamB);
		final Slot expected = slot;
		
		// Match		
		slot.addGame(gameA);
		
		// Assertion
		assertSame(expected, gameA.getSlot());
	}
	
	@Test
	public void testAddGameThrowsExceptionForDuplicateGame() throws DuplicateEntryException {
		// Fixture
		final Slot slot = new Slot(SlotTest.SLOT_A);
		final Team teamA = new Team(SlotTest.TEAM_A);
		final Team teamB = new Team(SlotTest.TEAM_B);
		final Game gameA = new Game(SlotTest.GAME_A, teamA, teamB);
		
		// Match
		slot.addGame(gameA);
		try {			
			slot.addGame(gameA);
			fail("Exception not thrown for addition of duplicate game");
		} catch(DuplicateEntryException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());
		}
	}
	
	@Test
	public void testRemoveGameRemovesExistentGame() throws InexistentEntryException, DuplicateEntryException {
		// Fixture
		final Slot slot = new Slot(SlotTest.SLOT_A);
		final Team teamA = new Team(SlotTest.TEAM_A);
		final Team teamB = new Team(SlotTest.TEAM_B);
		final Team teamC = new Team(SlotTest.TEAM_C);
		final Team teamD = new Team(SlotTest.TEAM_D);
		final Game gameA = new Game(SlotTest.GAME_A, teamA, teamB);
		final Game gameB = new Game(SlotTest.GAME_B, teamC, teamD);
		
		// Match
		slot.addGame(gameA);
		slot.addGame(gameB);
		slot.removeGame(SlotTest.GAME_A);
		
		// Assertion
		assertNull(slot.getGame(SlotTest.GAME_A));
	}
	
	@Test
	public void testRemoveGameThrowsExceptionForInexistentGame() {
		// Fixture		
		final Slot slot = new Slot(SlotTest.SLOT_A);
		
		// Match		
		try {
			slot.removeGame(SlotTest.GAME_A);			
			fail("Exception not thrown for removal of inexistent game");
		} catch(InexistentEntryException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());
		}		
	}
	
	@Test
	public void testClearGamesEmptiesGamesCollection() throws DuplicateEntryException {
		// Fixture
		final Slot slot = new Slot(SlotTest.SLOT_A);
		final Team teamA = new Team(SlotTest.TEAM_A);
		final Team teamB = new Team(SlotTest.TEAM_B);
		final Team teamC = new Team(SlotTest.TEAM_C);
		final Team teamD = new Team(SlotTest.TEAM_D);
		final Game gameA = new Game(SlotTest.GAME_A, teamA, teamB);
		final Game gameB = new Game(SlotTest.GAME_B, teamC, teamD);
		final int expected = SlotTest.EMPTY_LIST;
		
		// Match
		slot.addGame(gameA);
		slot.addGame(gameB);
		slot.clearGames();
		
		// Assertion		
		assertEquals(expected, slot.getGames().length);
	}

}
