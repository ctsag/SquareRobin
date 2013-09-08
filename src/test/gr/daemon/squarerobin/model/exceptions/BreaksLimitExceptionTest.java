package gr.daemon.squarerobin.model.exceptions;

import static org.junit.Assert.*;
import org.junit.Test;

public class BreaksLimitExceptionTest {

	private static final String MESSAGE_A = "Game not yet settled";	

	@Test
	public void testEmptyConstructorReturnsNoMessage() {
		// Fixture		
		
		// Match
		final BreaksLimitException e = new BreaksLimitException();
		
		// Assertion
		assertTrue(e.getMessage().isEmpty());
	}
	
	@Test
	public void testNonEmptyConstructorReturnsMessage() {
		// Fixture
		final String expected = BreaksLimitExceptionTest.MESSAGE_A;
		
		// Match
		final BreaksLimitException e = new BreaksLimitException(BreaksLimitExceptionTest.MESSAGE_A);
		
		// Assertion
		assertEquals(expected, e.getMessage());
	}
	
}
