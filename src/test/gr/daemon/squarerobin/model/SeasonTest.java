package gr.daemon.squarerobin.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class SeasonTest {
	
	@Test
	public void testConstructor() {
		String expected = "2011";
		Season season = new Season(expected);
		assertEquals(expected, season.getId());
	}
	
	@Test
	public void testId() {
		String expected = "2011";
		Season season = new Season("2012");
		season.setId(expected);
		assertEquals(expected, season.getId());
	}
	
	@Test 
	public void testAddRound() {
		Round round1 = new Round(1);
		Round round2 = new Round(2);
		Season season = new Season("2013");
		season.addRound(round1);
		season.addRound(round2);		
		assertSame(round1, season.getRound(1));
	}
	
	@Test
	public void testRemoveRound() {
		Round round1 = new Round(1);
		Round round2 = new Round(2);
		Season season = new Season("2013");
		season.addRound(round1);
		season.addRound(round2);
		season.removeRound(1);
		assertNull(season.getRound(1));
	}

	@Test
	public void testGetRounds() {
		Round round1 = new Round(1);
		Round round2 = new Round(2);
		Season season = new Season("2013");
		season.addRound(round1);
		season.addRound(round2);
		assertEquals(2, season.getRounds().length);
	}
	
	@Test
	public void testClearRounds() {
		Round round1 = new Round(1);
		Round round2 = new Round(2);
		Season season = new Season("2013");
		season.addRound(round1);
		season.addRound(round2);
		season.clearRounds();		
		assertEquals(0, season.getRounds().length);
	}
	
}
