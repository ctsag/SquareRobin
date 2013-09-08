package gr.daemon.squarerobin.model;

import gr.daemon.squarerobin.model.exceptions.DuplicateEntryException;
import gr.daemon.squarerobin.model.exceptions.InexistentEntryException;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Test;

public class RoundTest {

	private static final int ROUND_A = 1;	
	private static final int SLOT_A = 1;
	private static final int SLOT_B = 2;
	private static final String SEASON_A = "2013";
	private static final int EMPTY_LIST = 0;
	private static final int FULL_LIST = 2;
	private static final ArrayList<Team> NULL_TEAMS = null;

	@Test
	public void testConstructorSetsIndex() {
		// Fixture
		final int expected = RoundTest.ROUND_A;
		
		// Match
		final Round round = new Round(expected);
		
		// Assertion
		assertEquals(expected, round.getIndex());
	}
	
	@Test
	public void testGetIndexReturnsExpectedIndex() {
		// Fixture
		final int expected = RoundTest.ROUND_A;
		
		// Match
		final Round round = new Round(RoundTest.ROUND_A);		
		
		// Assertion
		assertEquals(expected, round.getIndex());
	}
	
	@Test
	public void testGetSeasonReturnsExpectedSeason() throws DuplicateEntryException {
		// Fixture
		final Season season = new Season(RoundTest.SEASON_A, RoundTest.NULL_TEAMS);
		final Round round = new Round(RoundTest.ROUND_A);
		final Season expected = season;

		// Match
		round.setSeason(season);
		
		// Assertion
		assertSame(expected, round.getSeason());
	}

	@Test
	public void testSetSeasonSetsExpectedSeason() throws DuplicateEntryException {
		// Fixture
		final Season season = new Season(RoundTest.SEASON_A, RoundTest.NULL_TEAMS);
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
		final int expected = RoundTest.FULL_LIST;
		
		// Match		
		round.addSlot(slotA);
		round.addSlot(slotB);
		
		// Assertion
		assertEquals(expected, round.getSlots().length);		
	}
	
	@Test
	public void testGetSlotReturnsExpectedSlot() throws DuplicateEntryException {
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
	public void testGetSlotReturnsNullForInexistentSlot() throws DuplicateEntryException {
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
		final int expected = RoundTest.FULL_LIST;
		
		// Match		
		round.addSlot(slotA);
		round.addSlot(slotB);
		
		// Assertion
		assertEquals(expected, round.getSlots().length);
	}
	
	@Test
	public void testAddSlotSetsTournamentForSlot() throws DuplicateEntryException {
		// Fixture
		final Round round = new Round(RoundTest.ROUND_A);
		final Slot slotA = new Slot(RoundTest.SLOT_A);
		final Round expected = round;
		
		// Match		
		round.addSlot(slotA);
		
		// Assertion
		assertSame(expected, slotA.getRound());
	}
	
	@Test
	public void testAddSlotThrowsExceptionForDuplicateSlot() throws DuplicateEntryException {
		// Fixture
		final Round round = new Round(RoundTest.ROUND_A);
		final Slot slotA = new Slot(RoundTest.SLOT_A);
		
		// Match
		round.addSlot(slotA);
		try {			
			round.addSlot(slotA);
			fail("Exception not thrown for addition of duplicate slot");
		} catch(DuplicateEntryException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());
		}
	}
	
	@Test
	public void testRemoveSlotRemovesExistentSlot() throws InexistentEntryException, DuplicateEntryException {
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
		} catch(InexistentEntryException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());
		}		
	}
	
	@Test
	public void testClearSlotsEmptiesSlotsCollection() throws DuplicateEntryException {
		// Fixture
		final Round round = new Round(RoundTest.ROUND_A);
		final Slot slotA = new Slot(RoundTest.SLOT_A);
		final Slot slotB = new Slot(RoundTest.SLOT_B);
		final int expected = RoundTest.EMPTY_LIST;
		
		// Match
		round.addSlot(slotA);
		round.addSlot(slotB);
		round.clearSlots();
		
		// Assertion		
		assertEquals(expected, round.getSlots().length);
	}

}
