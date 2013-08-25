package gr.daemon.squarerobin.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class SeasonTest {
	
	private static final String SEASON_A = "2012";
	private static final String SEASON_B = "2013";
	private static final int ROUND_A = 1;
	private static final int ROUND_B = 2;
	
	@Test
	public void testConstructor() {
		final String expected = SeasonTest.SEASON_A;
		final Season season = new Season(expected);
		assertEquals(expected, season.getId());
	}
	
	@Test
	public void testId() {
		final String expected = SeasonTest.SEASON_A;
		final Season season = new Season(SeasonTest.SEASON_B);
		season.setId(expected);
		assertEquals(expected, season.getId());
	}
	
	@Test 
	public void testAddRound() {
		final Round round1 = new Round(SeasonTest.ROUND_A);
		final Round round2 = new Round(SeasonTest.ROUND_B);
		final Season season = new Season(SeasonTest.SEASON_A);
		season.addRound(round1);
		season.addRound(round2);		
		assertSame(round1, season.getRound(1));
	}
	
	@Test
	public void testRemoveRound() {
		final Round round1 = new Round(1);
		final Round round2 = new Round(2);
		final Season season = new Season(SeasonTest.SEASON_A);
		season.addRound(round1);
		season.addRound(round2);
		season.removeRound(1);
		assertNull(season.getRound(1));
	}

	@Test
	public void testGetRounds() {
		final Round round1 = new Round(SeasonTest.ROUND_A);
		final Round round2 = new Round(SeasonTest.ROUND_B);
		final Season season = new Season(SeasonTest.SEASON_A);
		season.addRound(round1);
		season.addRound(round2);
		assertEquals(2, season.getRounds().length);
	}
	
	@Test
	public void testClearRounds() {
		final Round round1 = new Round(SeasonTest.ROUND_A);
		final Round round2 = new Round(SeasonTest.ROUND_B);
		final Season season = new Season(SeasonTest.SEASON_A);
		season.addRound(round1);
		season.addRound(round2);
		season.clearRounds();
		assertEquals(0, season.getRounds().length);
	}
	
}
