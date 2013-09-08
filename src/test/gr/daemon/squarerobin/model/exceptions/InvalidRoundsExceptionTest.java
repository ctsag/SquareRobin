package gr.daemon.squarerobin.model.exceptions;

import static org.junit.Assert.*;
import org.junit.Test;

public class InvalidRoundsExceptionTest {

	private static final String MESSAGE_A = "Game not yet settled";	

	@Test
	public void testEmptyConstructorReturnsNoMessage() {
		// Fixture		
		
		// Match
		final InvalidRoundsException e = new InvalidRoundsException();
		
		// Assertion
		assertTrue(e.getMessage().isEmpty());
	}
	
	@Test
	public void testNonEmptyConstructorReturnsMessage() {
		// Fixture
		final String expected = InvalidRoundsExceptionTest.MESSAGE_A;
		
		// Match
		final InvalidRoundsException e = new InvalidRoundsException(InvalidRoundsExceptionTest.MESSAGE_A);
		
		// Assertion
		assertEquals(expected, e.getMessage());
	}
	
}
