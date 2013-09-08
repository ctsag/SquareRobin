package gr.daemon.squarerobin.model.exceptions;

import static org.junit.Assert.*;
import org.junit.Test;

public class InexistentEntryExceptionTest {

	private static final String MESSAGE_A = "Duplicate entry";	

	@Test
	public void testEmptyConstructorReturnsNoMessage() {
		// Fixture		
		
		// Match
		final InexistentEntryException e = new InexistentEntryException();
		
		// Assertion
		assertTrue(e.getMessage().isEmpty());
	}
	
	@Test
	public void testNonEmptyConstructorReturnsMessage() {
		// Fixture
		final String expected = InexistentEntryExceptionTest.MESSAGE_A;
		
		// Match
		final InexistentEntryException e = new InexistentEntryException(InexistentEntryExceptionTest.MESSAGE_A);
		
		// Assertion
		assertEquals(expected, e.getMessage());
	}
	
}
