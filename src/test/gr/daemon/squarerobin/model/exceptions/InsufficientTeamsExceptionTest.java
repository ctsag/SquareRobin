package gr.daemon.squarerobin.model.exceptions;

import static org.junit.Assert.*;
import org.junit.Test;

public class InsufficientTeamsExceptionTest {

	private static final String MESSAGE_A = "Duplicate teams";	

	@Test
	public void testEmptyConstructorReturnsNoMessage() {
		// Fixture		
		
		// Match
		final InsufficientTeamsException e = new InsufficientTeamsException();
		
		// Assertion
		assertTrue(e.getMessage().isEmpty());
	}
	
	@Test
	public void testNonEmptyConstructorReturnsMessage() {
		// Fixture
		final String expected = InsufficientTeamsExceptionTest.MESSAGE_A;
		
		// Match
		final InsufficientTeamsException e = new InsufficientTeamsException(InsufficientTeamsExceptionTest.MESSAGE_A);
		
		// Assertion
		assertEquals(expected, e.getMessage());
	}
	
}
