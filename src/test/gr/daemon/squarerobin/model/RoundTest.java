package gr.daemon.squarerobin.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class RoundTest {

	private static final int ROUND_A = 1;	
	private static final int SLOT_A = 1;
	private static final int SLOT_B = 2;
	private static final String SEASON_A = "2013";

	@Test
	public void testConstructorSetsName() {
		// Fixture
		final int expected = RoundTest.ROUND_A;
		
		// Match
		final Round round = new Round(expected);
		
		// Assertion
		assertEquals(expected, round.getIndex());
	}
	
	@Test
	public void testGetNameReturnsExpectedName() {
		// Fixture
		final int expected = RoundTest.ROUND_A;
		
		// Match
		final Round round = new Round(RoundTest.ROUND_A);		
		
		// Assertion
		assertEquals(expected, round.getIndex());
	}
	
	@Test
	public void testGetSeasonReturnsExpectedSeason() {
		// Fixture
		final Season season = new Season(RoundTest.SEASON_A);
		final Round round = new Round(RoundTest.ROUND_A);
		final Season expected = season;

		// Match
		round.setSeason(season);
		
		// Assertion
		assertSame(expected, round.getSeason());
	}

	@Test
	public void testSetSeasonSetsExpectedSeason() {
		// Fixture
		final Season season = new Season(RoundTest.SEASON_A);
		final Round round = new Round(RoundTest.ROUND_A);
		final Season expected = season;

		// Match
		round.setSeason(season);
		
		// Assertion
		assertSame(expected, round.getSeason());
	}

	@Test
	public void testGetSlotsReturnsExpectedArray() throws DuplicateEntryException {
		// Fixture
		final Round round = new Round(RoundTest.ROUND_A);
		final Slot slotA = new Slot(RoundTest.SLOT_A);
		final Slot slotB = new Slot(RoundTest.SLOT_B);
		final int expected = 2;
		
		// Match		
		round.addSlot(slotA);
		round.addSlot(slotB);
		
		// Assertion
		assertEquals(expected, round.getSlots().length);		
	}
	
	@Test
	public void testGetSlotReturnsExpectedSlot() {
		// Fixture
		final Round round = new Round(RoundTest.ROUND_A);
		final Slot slotA = new Slot(RoundTest.SLOT_A);
		final Slot slotB = new Slot(RoundTest.SLOT_B);
		final Slot expected = slotA;
		
		// Match
		round.addSlot(slotA);
		round.addSlot(slotB);
		
		// Assertion
		assertSame(expected, round.getSlot(RoundTest.SLOT_A));		
	}
	
	@Test
	public void testGetSlotReturnsNullForInexistentSlot() {
		// Fixture
		final Round round = new Round(RoundTest.ROUND_A);
		final Slot slotA = new Slot(RoundTest.SLOT_A);		
		
		// Match
		round.addSlot(slotA);
				
		// Assertion
		assertNull(round.getSlot(RoundTest.SLOT_B));
	}
	
	@Test
	public void testAddSlotAppendsUniqueSlot() throws DuplicateEntryException {
		// Fixture
		final Round round = new Round(RoundTest.ROUND_A);
		final Slot slotA = new Slot(RoundTest.SLOT_A);
		final Slot slotB = new Slot(RoundTest.SLOT_B);
		final int expected = 2;
		
		// Match		
		round.addSlot(slotA);
		round.addSlot(slotB);
		
		// Assertion
		assertEquals(expected, round.getSlots().length);
	}
	
//	@Test
//	public void testAddSlotSetsTournamentForSlot() throws DuplicateEntryException {
//		// Fixture
//		final Round round = new Round(RoundTest.ROUND_A);
//		final Slot slotA = new Slot(RoundTest.SLOT_A);
//		final Round expected = round;
//		
//		// Match		
//		round.addSlot(slotA);
//		
//		// Assertion
//		assertSame(expected, slotA.getRound());
//	}
	
	@Test
	public void testAddSlotThrowsExceptionForDuplicateSlot() {
		// Fixture
		final Round round = new Round(RoundTest.ROUND_A);
		final Slot slotA = new Slot(RoundTest.SLOT_A);
		
		// Match
		round.addSlot(slotA);
		try {			
			round.addSlot(slotA);
			fail("Exception not thrown for addition of duplicate slot");
		} catch(Exception e) {
			// Assertion
			assertTrue(e instanceof DuplicateEntryException);
		}
	}
	
	@Test
	public void testRemoveSlotRemovesExistentSlot() throws InexistentEntryException {
		// Fixture
		final Round round = new Round(RoundTest.ROUND_A);
		final Slot slotA = new Slot(RoundTest.SLOT_A);
		final Slot slotB = new Slot(RoundTest.SLOT_B);
		
		// Match
		round.addSlot(slotA);
		round.addSlot(slotB);
		round.removeSlot(RoundTest.SLOT_A);
		
		// Assertion
		assertNull(round.getSlot(RoundTest.SLOT_A));
	}
	
	@Test
	public void testRemoveSlotThrowsExceptionForInexistentSlot() {
		// Fixture		
		final Round round = new Round(RoundTest.ROUND_A);
		
		// Match		
		try {
			round.removeSlot(RoundTest.SLOT_A);			
			fail("Exception not thrown for removal of inexistent slot");
		} catch(Exception e) {
			// Assertion
			assertTrue(e instanceof InexistentEntryException);
		}		
	}
	
	@Test
	public void testClearSlotsEmptiesSlotsCollection() {
		// Fixture
		final Round round = new Round(RoundTest.ROUND_A);
		final Slot slotA = new Slot(RoundTest.SLOT_A);
		final Slot slotB = new Slot(RoundTest.SLOT_B);
		final int expected = 0;
		
		// Match
		round.addSlot(slotA);
		round.addSlot(slotB);
		round.clearSlots();
		
		// Assertion		
		assertEquals(expected, round.getSlots().length);
	}

}
