package gr.daemon.squarerobin.model;

import gr.daemon.squarerobin.model.exceptions.DuplicateEntryException;
import gr.daemon.squarerobin.model.exceptions.InexistentEntryException;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Test;

public class TournamentTest {

	private static final String TOURNAMENT_A = "Greek Superleague";	
	private static final String SEASON_A = "2012";
	private static final String SEASON_B = "2013";
	private static final int EMPTY_LIST = 0;
	private static final int FULL_LIST = 2;
	private static final ArrayList<Team> NULL_TEAMS = null;

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
	public void testGetSeasonsReturnsExpectedArray() throws DuplicateEntryException {
		// Fixture
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		final Season seasonA = new Season(TournamentTest.SEASON_A, TournamentTest.NULL_TEAMS);
		final Season seasonB = new Season(TournamentTest.SEASON_B, TournamentTest.NULL_TEAMS);
		final int expected = TournamentTest.FULL_LIST;
		
		// Match		
		tournament.addSeason(seasonA);
		tournament.addSeason(seasonB);
		
		// Assertion
		assertEquals(expected, tournament.getSeasons().length);		
	}
	
	@Test
	public void testGetSeasonReturnsExpectedSeason() throws DuplicateEntryException {
		// Fixture
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		final Season seasonA = new Season(TournamentTest.SEASON_A, TournamentTest.NULL_TEAMS);
		final Season seasonB = new Season(TournamentTest.SEASON_B, TournamentTest.NULL_TEAMS);
		final Season expected = seasonA;
		
		// Match
		tournament.addSeason(seasonA);
		tournament.addSeason(seasonB);
		
		// Assertion
		assertSame(expected, tournament.getSeason(TournamentTest.SEASON_A));		
	}
	
	@Test
	public void testGetSeasonReturnsNullForInexistentSeason() throws DuplicateEntryException {
		// Fixture
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		final Season seasonA = new Season(TournamentTest.SEASON_A, TournamentTest.NULL_TEAMS);		
		
		// Match
		tournament.addSeason(seasonA);
				
		// Assertion
		assertNull(tournament.getSeason(TournamentTest.SEASON_B));
	}
	
	@Test
	public void testAddSeasonAppendsUniqueSeason() throws DuplicateEntryException {
		// Fixture
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		final Season seasonA = new Season(TournamentTest.SEASON_A, TournamentTest.NULL_TEAMS);
		final Season seasonB = new Season(TournamentTest.SEASON_B, TournamentTest.NULL_TEAMS);
		final int expected = TournamentTest.FULL_LIST;
		
		// Match		
		tournament.addSeason(seasonA);
		tournament.addSeason(seasonB);
		
		// Assertion
		assertEquals(expected, tournament.getSeasons().length);
	}
	
	@Test
	public void testAddSeasonSetsTournamentForSeason() throws DuplicateEntryException {
		// Fixture
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		final Season seasonA = new Season(TournamentTest.SEASON_A, TournamentTest.NULL_TEAMS);
		final Tournament expected = tournament;
		
		// Match		
		tournament.addSeason(seasonA);
		
		// Assertion
		assertSame(expected, seasonA.getTournament());
	}
	
	@Test
	public void testAddSeasonThrowsExceptionForDuplicateSeason() throws DuplicateEntryException {
		// Fixture
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		final Season seasonA = new Season(TournamentTest.SEASON_A, TournamentTest.NULL_TEAMS);
		
		// Match
		tournament.addSeason(seasonA);
		try {			
			tournament.addSeason(seasonA);
			fail("Exception not thrown for addition of duplicate season");
		} catch(DuplicateEntryException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());
		}
	}
	
	@Test
	public void testRemoveSeasonRemovesExistentSeason() throws InexistentEntryException, DuplicateEntryException {
		// Fixture
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		final Season seasonA = new Season(TournamentTest.SEASON_A, TournamentTest.NULL_TEAMS);
		final Season seasonB = new Season(TournamentTest.SEASON_B, TournamentTest.NULL_TEAMS);
		
		// Match
		tournament.addSeason(seasonA);
		tournament.addSeason(seasonB);
		tournament.removeSeason(TournamentTest.SEASON_A);
		
		// Assertion
		assertNull(tournament.getSeason(TournamentTest.SEASON_A));	
	}
	
	@Test
	public void testRemoveSeasonThrowsExceptionForInexistentSeason() {
		// Fixture		
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		
		// Match		
		try {
			tournament.removeSeason(TournamentTest.SEASON_A);			
			fail("Exception not thrown for removal of inexistent season");
		} catch(InexistentEntryException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());
		}		
	}
	
	@Test
	public void testClearSeasonsEmptiesSeasonsCollection() throws DuplicateEntryException {
		// Fixture
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		final Season seasonA = new Season(TournamentTest.SEASON_A, TournamentTest.NULL_TEAMS);
		final Season seasonB = new Season(TournamentTest.SEASON_B, TournamentTest.NULL_TEAMS);
		final int expected = TournamentTest.EMPTY_LIST;
		
		// Match
		tournament.addSeason(seasonA);
		tournament.addSeason(seasonB);
		tournament.clearSeasons();
		
		// Assertion		
		assertEquals(expected, tournament.getSeasons().length);
	}

}
