package gr.daemon.squarerobin.engine;

import static org.junit.Assert.*;
import org.junit.Test;

public class StateTest {
	
	private static final State STATE_A = State.ERR_ODD_CLUBS;
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
	public void testToStringReturnsNonEmptyString() {
		// Fixtures
		final String expected = StateTest.EMPTY_STRING;
				
		// Match		
		for (final State state : State.values()) {
			// Assertion
			assertFalse(expected.equals(state.toString()));
		}
	}

}