package gr.daemon.squarerobin.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class TournamentTest {

	private static final String TOURNAMENT_A = "Greek Superleague";
	private static final String TOURNAMENT_B = "Greek Cup";
	private static final String SEASON_A = "2012";
	private static final String SEASON_B = "2013";

	@Test
	public void testConstructor() {
		final String expected = TournamentTest.TOURNAMENT_A;
		final Tournament tournament = new Tournament(expected);
		assertEquals(expected, tournament.getName());
	}

	@Test
	public void testName() {
		final String expected = TournamentTest.TOURNAMENT_A;
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_B);
		tournament.setName(expected);
		assertEquals(expected, tournament.getName());
	}

	@Test
	public void testAddSeason() {
		final Season season2011 = new Season(TournamentTest.SEASON_A);
		final Season season2012 = new Season(TournamentTest.SEASON_B);
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		tournament.addSeason(season2011);
		tournament.addSeason(season2012);
		assertSame(season2011, tournament.getSeason(TournamentTest.SEASON_A));
	}

	@Test
	public void testRemoveSeason() {
		final Season season2011 = new Season(TournamentTest.SEASON_A);
		final Season season2012 = new Season(TournamentTest.SEASON_B);
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		tournament.addSeason(season2011);
		tournament.addSeason(season2012);
		tournament.removeSeason(TournamentTest.SEASON_A);
		assertNull(tournament.getSeason(TournamentTest.SEASON_A));
	}

	@Test
	public void testGetSeasons() {
		final Season season2011 = new Season(TournamentTest.SEASON_A);
		final Season season2012 = new Season(TournamentTest.SEASON_B);
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		tournament.addSeason(season2011);
		tournament.addSeason(season2012);
		assertEquals(2, tournament.getSeasons().length);
	}

	@Test
	public void testClearSeasons() {
		final Season season2011 = new Season(TournamentTest.SEASON_A);
		final Season season2012 = new Season(TournamentTest.SEASON_B);
		final Tournament tournament = new Tournament(TournamentTest.TOURNAMENT_A);
		tournament.addSeason(season2011);
		tournament.addSeason(season2012);
		tournament.clearSeasons();
		assertEquals(0, tournament.getSeasons().length);
	}

}
