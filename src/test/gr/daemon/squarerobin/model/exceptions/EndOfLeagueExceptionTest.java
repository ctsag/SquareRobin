package gr.daemon.squarerobin.model.exceptions;

import static org.junit.Assert.*;
import org.junit.Test;

public class EndOfLeagueExceptionTest {

	private static final String MESSAGE_A = "Duplicate entry";	

	@Test
	public void testEmptyConstructorReturnsNoMessage() {
		// Fixture		
		
		// Match
		final EndOfLeagueException e = new EndOfLeagueException();
		
		// Assertion
		assertTrue(e.getMessage().isEmpty());
	}
	
	@Test
	public void testNonEmptyConstructorReturnsMessage() {
		// Fixture
		final String expected = EndOfLeagueExceptionTest.MESSAGE_A;
		
		// Match
		final EndOfLeagueException e = new EndOfLeagueException(EndOfLeagueExceptionTest.MESSAGE_A);
		
		// Assertion
		assertEquals(expected, e.getMessage());
	}
	
}
