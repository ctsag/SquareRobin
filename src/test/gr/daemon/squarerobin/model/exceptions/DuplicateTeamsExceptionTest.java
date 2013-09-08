package gr.daemon.squarerobin.model.exceptions;

import static org.junit.Assert.*;
import org.junit.Test;

public class DuplicateTeamsExceptionTest {

	private static final String MESSAGE_A = "Duplicate teams";	

	@Test
	public void testEmptyConstructorReturnsNoMessage() {
		// Fixture		
		
		// Match
		final DuplicateTeamsException e = new DuplicateTeamsException();
		
		// Assertion
		assertTrue(e.getMessage().isEmpty());
	}
	
	@Test
	public void testNonEmptyConstructorReturnsMessage() {
		// Fixture
		final String expected = DuplicateTeamsExceptionTest.MESSAGE_A;
		
		// Match
		final DuplicateTeamsException e = new DuplicateTeamsException(DuplicateTeamsExceptionTest.MESSAGE_A);
		
		// Assertion
		assertEquals(expected, e.getMessage());
	}
	
}
