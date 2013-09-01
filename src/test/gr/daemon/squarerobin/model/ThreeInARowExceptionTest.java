package gr.daemon.squarerobin.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ThreeInARowExceptionTest {

	private static final String MESSAGE_A = "Game not yet settled";	

	@Test
	public void testEmptyConstructorReturnsNoMessage() {
		// Fixture		
		
		// Match
		final ThreeInARowException e = new ThreeInARowException();
		
		// Assertion
		assertTrue(e.getMessage().isEmpty());
	}
	
	@Test
	public void testNonEmptyConstructorReturnsMessage() {
		// Fixture
		final String expected = ThreeInARowExceptionTest.MESSAGE_A;
		
		// Match
		final ThreeInARowException e = new ThreeInARowException(ThreeInARowExceptionTest.MESSAGE_A);
		
		// Assertion
		assertEquals(expected, e.getMessage());
	}
	
}
