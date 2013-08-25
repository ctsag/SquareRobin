package gr.daemon.squarerobin.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class TournamentTest {

	@Test
	public void testConstructor() {
		String expected = "Greek Superleague";
		Tournament tournament = new Tournament(expected);
		assertEquals(expected, tournament.getName());
	}

	@Test
	public void testName() {
		String expected = "Greek Superleague";
		Tournament tournament = new Tournament("Greek Cup");
		tournament.setName(expected);
		assertEquals(expected, tournament.getName());
	}

	@Test 
	public void testAddSeason() {
		Season season2011 = new Season("2011");
		Season season2012 = new Season("2012");
		Tournament tournament = new Tournament("Greek Superleague");
		tournament.addSeason(season2011);
		tournament.addSeason(season2012);
		assertSame(season2011, tournament.getSeason("2011"));
	}

	@Test
	public void testRemoveSeason() {
		Season season2011 = new Season("2011");
		Season season2012 = new Season("2012");
		Tournament tournament = new Tournament("Greek Superleague");
		tournament.addSeason(season2011);
		tournament.addSeason(season2012);
		tournament.removeSeason("2011");
		assertNull(tournament.getSeason("2011"));
	}

	@Test
	public void testGetSeasons() {
		Season season2011 = new Season("2011");
		Season season2012 = new Season("2012");
		Tournament tournament = new Tournament("Greek Superleague");
		tournament.addSeason(season2011);
		tournament.addSeason(season2012);
		assertEquals(2, tournament.getSeasons().length);
	}

	@Test
	public void testClearSeasons() {
		Season season2011 = new Season("2011");
		Season season2012 = new Season("2012");
		Tournament tournament = new Tournament("Greek Superleague");
		tournament.addSeason(season2011);
		tournament.addSeason(season2012);
		tournament.clearSeasons();
		assertEquals(0, tournament.getSeasons().length);
	}

}
