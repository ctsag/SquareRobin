package gr.daemon.squarerobin.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class InvalidScoreExceptionTest {

	private static final String MESSAGE_A = "Invalid score";	

	@Test
	public void testEmptyConstructorReturnsNoMessage() {
		// Fixture		
		
		// Match
		final InvalidScoreException e = new InvalidScoreException();
		
		// Assertion
		assertTrue(e.getMessage().isEmpty());
	}
	
	@Test
	public void testNonEmptyConstructorReturnsMessage() {
		// Fixture
		final String expected = InvalidScoreExceptionTest.MESSAGE_A;
		
		// Match
		final InvalidScoreException e = new InvalidScoreException(InvalidScoreExceptionTest.MESSAGE_A);
		
		// Assertion
		assertEquals(expected, e.getMessage());
	}
	
}
