package gr.daemon.squarerobin.model.exceptions;

import static org.junit.Assert.*;
import org.junit.Test;

public class GameAlreadySettledExceptionTest {

	private static final String MESSAGE_A = "Game already settled";	

	@Test
	public void testEmptyConstructorReturnsNoMessage() {
		// Fixture		
		
		// Match
		final GameAlreadySettledException e = new GameAlreadySettledException();
		
		// Assertion
		assertTrue(e.getMessage().isEmpty());
	}
	
	@Test
	public void testNonEmptyConstructorReturnsMessage() {
		// Fixture
		final String expected = GameAlreadySettledExceptionTest.MESSAGE_A;
		
		// Match
		final GameAlreadySettledException e = new GameAlreadySettledException(GameAlreadySettledExceptionTest.MESSAGE_A);
		
		// Assertion
		assertEquals(expected, e.getMessage());
	}
	
}
