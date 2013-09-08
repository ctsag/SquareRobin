package gr.daemon.squarerobin.model.exceptions;

import static org.junit.Assert.*;
import org.junit.Test;

public class OddTeamNumberExceptionTest {

	private static final String MESSAGE_A = "Duplicate teams";	

	@Test
	public void testEmptyConstructorReturnsNoMessage() {
		// Fixture		
		
		// Match
		final OddTeamNumberException e = new OddTeamNumberException();
		
		// Assertion
		assertTrue(e.getMessage().isEmpty());
	}
	
	@Test
	public void testNonEmptyConstructorReturnsMessage() {
		// Fixture
		final String expected = OddTeamNumberExceptionTest.MESSAGE_A;
		
		// Match
		final OddTeamNumberException e = new OddTeamNumberException(OddTeamNumberExceptionTest.MESSAGE_A);
		
		// Assertion
		assertEquals(expected, e.getMessage());
	}
	
}
