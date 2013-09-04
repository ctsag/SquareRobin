package gr.daemon.squarerobin.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class SeasonTest {

	private static final String SEASON_A = "2012";
	private static final int ROUND_A = 1;
	private static final int ROUND_B = 2;
	private static final String TOURNAMENT_A = "Greek Superleague";
	private static final int EMPTY_LIST = 0;
	private static final int FULL_LIST = 2;

	@Test
	public void testConstructorSetsName() {
		// Fixture
		final String expected = SeasonTest.SEASON_A;
		
		// Match
		final Season season = new Season(expected);
		
		// Assertion
		assertEquals(expected, season.getName());
	}
	
	@Test
	public void testGetNameReturnsExpectedName() {
		// Fixture
		final String expected = SeasonTest.SEASON_A;
		
		// Match
		final Season season = new Season(SeasonTest.SEASON_A);		
		
		// Assertion
		assertEquals(expected, season.getName());
	}
	
	@Test
	public void testGetTournamentReturnsExpectedTournament() {
		// Fixture
		final Tournament tournament = new Tournament(SeasonTest.TOURNAMENT_A);
		final Season season = new Season(SeasonTest.SEASON_A);
		final Tournament expected = tournament;

		// Match
		season.setTournament(tournament);
		
		// Assertion
		assertSame(expected, season.getTournament());
	}

	@Test
	public void testSetTournamentSetsExpectedTournament() {
		// Fixture
		final Tournament tournament = new Tournament(SeasonTest.TOURNAMENT_A);
		final Season season = new Season(SeasonTest.SEASON_A);
		final Tournament expected = tournament;

		// Match
		season.setTournament(tournament);
		
		// Assertion
		assertSame(expected, season.getTournament());
	}

	@Test
	public void testGetRoundsReturnsExpectedArray() throws DuplicateEntryException {
		// Fixture
		final Season season = new Season(SeasonTest.SEASON_A);
		final Round roundA = new Round(SeasonTest.ROUND_A);
		final Round roundB = new Round(SeasonTest.ROUND_B);
		final int expected = SeasonTest.FULL_LIST;
		
		// Match		
		season.addRound(roundA);
		season.addRound(roundB);
		
		// Assertion
		assertEquals(expected, season.getRounds().length);		
	}
	
	@Test
	public void testGetRoundReturnsExpectedRound() throws DuplicateEntryException {
		// Fixture
		final Season season = new Season(SeasonTest.SEASON_A);
		final Round roundA = new Round(SeasonTest.ROUND_A);
		final Round roundB = new Round(SeasonTest.ROUND_B);
		final Round expected = roundA;
		
		// Match
		season.addRound(roundA);
		season.addRound(roundB);
		
		// Assertion
		assertSame(expected, season.getRound(SeasonTest.ROUND_A));		
	}
	
	@Test
	public void testGetRoundReturnsNullForInexistentRound() throws DuplicateEntryException {
		// Fixture
		final Season season = new Season(SeasonTest.SEASON_A);
		final Round roundA = new Round(SeasonTest.ROUND_A);		
		
		// Match
		season.addRound(roundA);
				
		// Assertion
		assertNull(season.getRound(SeasonTest.ROUND_B));
	}
	
	@Test
	public void testAddRoundAppendsUniqueRound() throws DuplicateEntryException {
		// Fixture
		final Season season = new Season(SeasonTest.SEASON_A);
		final Round roundA = new Round(SeasonTest.ROUND_A);
		final Round roundB = new Round(SeasonTest.ROUND_B);
		final int expected = SeasonTest.FULL_LIST;
		
		// Match		
		season.addRound(roundA);
		season.addRound(roundB);
		
		// Assertion
		assertEquals(expected, season.getRounds().length);
	}
	
	@Test
	public void testAddRoundSetsTournamentForRound() throws DuplicateEntryException {
		// Fixture
		final Season season = new Season(SeasonTest.SEASON_A);
		final Round roundA = new Round(SeasonTest.ROUND_A);
		final Season expected = season;
		
		// Match		
		season.addRound(roundA);
		
		// Assertion
		assertSame(expected, roundA.getSeason());
	}
	
	@Test
	public void testAddRoundThrowsExceptionForDuplicateRound() throws DuplicateEntryException {
		// Fixture
		final Season season = new Season(SeasonTest.SEASON_A);
		final Round roundA = new Round(SeasonTest.ROUND_A);
		
		// Match
		season.addRound(roundA);
		try {			
			season.addRound(roundA);
			fail("Exception not thrown for addition of duplicate round");
		} catch(DuplicateEntryException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());
		}
	}
	
	@Test
	public void testRemoveRoundRemovesExistentRound() throws InexistentEntryException, DuplicateEntryException {
		// Fixture
		final Season season = new Season(SeasonTest.SEASON_A);
		final Round roundA = new Round(SeasonTest.ROUND_A);
		final Round roundB = new Round(SeasonTest.ROUND_B);
		
		// Match
		season.addRound(roundA);
		season.addRound(roundB);
		season.removeRound(SeasonTest.ROUND_A);
		
		// Assertion
		assertNull(season.getRound(SeasonTest.ROUND_A));
	}
	
	@Test
	public void testRemoveRoundThrowsExceptionForInexistentRound() {
		// Fixture		
		final Season season = new Season(SeasonTest.SEASON_A);
		
		// Match		
		try {
			season.removeRound(SeasonTest.ROUND_A);			
			fail("Exception not thrown for removal of inexistent round");
		} catch(InexistentEntryException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());
		}		
	}
	
	@Test
	public void testClearRoundsEmptiesRoundsCollection() throws DuplicateEntryException {
		// Fixture
		final Season season = new Season(SeasonTest.SEASON_A);
		final Round roundA = new Round(SeasonTest.ROUND_A);
		final Round roundB = new Round(SeasonTest.ROUND_B);
		final int expected = SeasonTest.EMPTY_LIST;
		
		// Match
		season.addRound(roundA);
		season.addRound(roundB);
		season.clearRounds();
		
		// Assertion		
		assertEquals(expected, season.getRounds().length);
	}

}
