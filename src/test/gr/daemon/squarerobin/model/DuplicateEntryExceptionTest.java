package gr.daemon.squarerobin.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class DuplicateEntryExceptionTest {

	private static final String MESSAGE_A = "Duplicate entry";	

	@Test
	public void testEmptyConstructorReturnsNoMessage() {
		// Fixture		
		
		// Match
		final DuplicateEntryException e = new DuplicateEntryException();
		
		// Assertion
		assertTrue(e.getMessage().isEmpty());
	}
	
	@Test
	public void testNonEmptyConstructorReturnsMessage() {
		// Fixture
		final String expected = DuplicateEntryExceptionTest.MESSAGE_A;
		
		// Match
		final DuplicateEntryException e = new DuplicateEntryException(DuplicateEntryExceptionTest.MESSAGE_A);
		
		// Assertion
		assertEquals(expected, e.getMessage());
	}
	
}
