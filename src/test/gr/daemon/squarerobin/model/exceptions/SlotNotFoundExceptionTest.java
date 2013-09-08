package gr.daemon.squarerobin.model.exceptions;

import static org.junit.Assert.*;
import org.junit.Test;

public class SlotNotFoundExceptionTest {

	private static final String MESSAGE_A = "Duplicate entry";	

	@Test
	public void testEmptyConstructorReturnsNoMessage() {
		// Fixture		
		
		// Match
		final SlotNotFoundException e = new SlotNotFoundException();
		
		// Assertion
		assertTrue(e.getMessage().isEmpty());
	}
	
	@Test
	public void testNonEmptyConstructorReturnsMessage() {
		// Fixture
		final String expected = SlotNotFoundExceptionTest.MESSAGE_A;
		
		// Match
		final SlotNotFoundException e = new SlotNotFoundException(SlotNotFoundExceptionTest.MESSAGE_A);
		
		// Assertion
		assertEquals(expected, e.getMessage());
	}
	
}
