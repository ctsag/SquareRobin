package gr.daemon.squarerobin.cli;

import static org.junit.Assert.*;
import org.junit.Test;

public class StateTest {
	
	private static final State STATE_A = State.OK;
	private static final String EMPTY_STRING = "";

	@Test
	public void testConstructorSetsValue() {
		// Fixtures
		final int expected = StateTest.STATE_A.getValue();
		
		// Match
		final State state = StateTest.STATE_A;
		
		// Assertion
		assertEquals(expected, state.getValue());
	}
			
	@Test
	public void testGetValueReturnsExpectedValue() {
		// Fixtures
		final int expected = StateTest.STATE_A.getValue();
		
		// Match
		final State state = StateTest.STATE_A;
		
		// Assertion
		assertEquals(expected, state.getValue());
	}
	
	@Test
	public void testToStringReturnsNonEmptyStringForAllCasesExceptOk() {
		// Fixtures
		final String expected = StateTest.EMPTY_STRING;
				
		// Match		
		for (final State state : State.values()) {			
			if (state.getValue() > 0) {
				// Assertion
				assertFalse(expected.equals(state.toString()));
			}
		}
	}

	@Test
	public void testToStringReturnsEmptyStringForOk() {
		// Fixtures
		final String expected = StateTest.EMPTY_STRING;
		
		// Match
		final State ok = State.OK;
		
		// Assertion		
		assertEquals(expected, ok.toString());
	}
	
	@Test
	public void testValueOfReturnsExpectedState() {
		// Fixtures
		final State expected = StateTest.STATE_A;
		
		// Match
		final State state = State.valueOf("OK");
		
		// Assertion
		assertSame(expected, state);
	}

}