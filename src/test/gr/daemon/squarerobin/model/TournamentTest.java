package gr.daemon.squarerobin.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class TournamentTest {

	private static final String TOURNAMENT_A = "Greek Superleague";
	private static final String TOURNAMENT_B = "Greek Cup";
	private static final String SEASON_A = "2012";
	private static final String SEASON_B = "2013";

	@Test
	public void testConstructorSetsName() {
		// Fixture
		final String expected = TournamentTest.TOURNAMENT_A;
		
		// Match
		final Tournament tournament = new Tournament(expected);
		
		// Assertion		
		assertEquals(expected, tournament.getName());
	}
	
	@Test
	public void testGetNameReturnsExpectedName() {
		// Fixture
		final String expected = TournamentTest.TOURNAMENT_A;
		
		// Match
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);		
		
		// Assertion
		assertEquals(expected, tournament.getName());
	}
	
	@Test
	public void testGetSeasonsReturnsExpectedArray() {
		// Fixture
		final Season seasonA = new Season(TournamentTest.SEASON_A);
		final Season seasonB = new Season(TournamentTest.SEASON_B);
		final int expected = 2;
		
		// Match
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		tournament.addSeason(seasonA);
		tournament.addSeason(seasonB);		
		
		// Assertion
		assertEquals(expected, tournament.getSeasons().length);		
	}
	
	@Test
	public void testGetSeasonReturnsExpectedSeason() {
		// Fixture
		final Season seasonA = new Season(TournamentTest.SEASON_A);
		final Season seasonB = new Season(TournamentTest.SEASON_B);
		final Season expected = seasonA;
		
		// Match
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		tournament.addSeason(seasonA);
		tournament.addSeason(seasonB);
		
		// Assertion
		assertSame(expected, tournament.getSeason(TournamentTest.SEASON_A));		
	}
	
	@Test
	public void testGetSeasonReturnsNullForInexistentSeason() {
		// Fixture
		final Season seasonA = new Season(TournamentTest.SEASON_A);		
		
		// Match
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		tournament.addSeason(seasonA);
				
		// Assertion
		assertNull(tournament.getSeason(TournamentTest.SEASON_B));
	}
	
	@Test
	public void testAddSeasonAppendsUniqueSeason() throws DuplicateEntryException {
		// Fixture
		final Season seasonA = new Season(TournamentTest.SEASON_A);
		final Season seasonB = new Season(TournamentTest.SEASON_B);
		final int expected = 2;
		
		// Match
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		tournament.addSeason(seasonA);
		tournament.addSeason(seasonB);
		
		// Assertion
		assertEquals(expected, tournament.getSeasons().length);
	}
	
	@Test
	public void testAddSeasonThrowsExceptionForDuplicateSeason() {
		// Fixture
		final Season seasonA = new Season(TournamentTest.SEASON_A);
		
		// Match
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		tournament.addSeason(seasonA);
		try {			
			tournament.addSeason(seasonA);
			fail("Exception not thrown for addition of duplicate season");
		} catch(Exception e) {
			// Assertion
			assertTrue(e instanceof DuplicateEntryException);
		}
	}
	
	@Test
	public void testRemoveSeasonRemovesExistentSeason() throws InexistentEntryException {
		// Fixture
		final Season seasonA = new Season(TournamentTest.SEASON_A);
		final Season seasonB = new Season(TournamentTest.SEASON_B);
		
		// Match
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		tournament.addSeason(seasonA);
		tournament.addSeason(seasonB);
		tournament.removeSeason(TournamentTest.SEASON_A);
		
		// Assertion
		assertNull(tournament.getSeason(TournamentTest.SEASON_A));	
	}
	
	@Test
	public void testRemoveSeasonThrowsExceptionForInexistentSeason() {
		// Fixture		
		
		// Match
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);		
		try {
			tournament.removeSeason(TournamentTest.SEASON_A);			
			fail("Exception not thrown for removal of inexistent season");
		} catch(Exception e) {
			// Assertion
			assertTrue(e instanceof InexistentEntryException);
		}		
	}
	
	@Test
	public void testClearSeasonsEmptiesSeasonsCollection() {
		// Fixture
		final Season seasonA = new Season(TournamentTest.SEASON_A);
		final Season seasonB = new Season(TournamentTest.SEASON_B);
		final int expected = 0;
		
		// Match
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		tournament.addSeason(seasonA);
		tournament.addSeason(seasonB);
		tournament.clearSeasons();
		
		// Assertion		
		assertEquals(expected, tournament.getSeasons().length);
	}

}
