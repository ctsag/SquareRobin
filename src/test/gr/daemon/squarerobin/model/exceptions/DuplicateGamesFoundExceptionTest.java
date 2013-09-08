package gr.daemon.squarerobin.model.exceptions;

import static org.junit.Assert.*;
import org.junit.Test;

public class DuplicateGamesFoundExceptionTest {

	private static final String MESSAGE_A = "Duplicate entry";	

	@Test
	public void testEmptyConstructorReturnsNoMessage() {
		// Fixture		
		
		// Match
		final DuplicateGamesFoundException e = new DuplicateGamesFoundException();
		
		// Assertion
		assertTrue(e.getMessage().isEmpty());
	}
	
	@Test
	public void testNonEmptyConstructorReturnsMessage() {
		// Fixture
		final String expected = DuplicateGamesFoundExceptionTest.MESSAGE_A;
		
		// Match
		final DuplicateGamesFoundException e = new DuplicateGamesFoundException(DuplicateGamesFoundExceptionTest.MESSAGE_A);
		
		// Assertion
		assertEquals(expected, e.getMessage());
	}
	
}