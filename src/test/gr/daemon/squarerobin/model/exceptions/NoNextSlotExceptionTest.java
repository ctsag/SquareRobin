package gr.daemon.squarerobin.model.exceptions;

import static org.junit.Assert.*;
import org.junit.Test;

public class NoNextSlotExceptionTest {

	private static final String MESSAGE_A = "Duplicate entry";	

	@Test
	public void testEmptyConstructorReturnsNoMessage() {
		// Fixture		
		
		// Match
		final NoNextSlotException e = new NoNextSlotException();
		
		// Assertion
		assertTrue(e.getMessage().isEmpty());
	}
	
	@Test
	public void testNonEmptyConstructorReturnsMessage() {
		// Fixture
		final String expected = NoNextSlotExceptionTest.MESSAGE_A;
		
		// Match
		final NoNextSlotException e = new NoNextSlotException(NoNextSlotExceptionTest.MESSAGE_A);
		
		// Assertion
		assertEquals(expected, e.getMessage());
	}
	
}
