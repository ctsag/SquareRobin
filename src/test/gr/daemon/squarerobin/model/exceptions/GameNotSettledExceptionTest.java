package gr.daemon.squarerobin.model.exceptions;

import static org.junit.Assert.*;
import org.junit.Test;

public class GameNotSettledExceptionTest {

	private static final String MESSAGE_A = "Game not yet settled";	

	@Test
	public void testEmptyConstructorReturnsNoMessage() {
		// Fixture		
		
		// Match
		final GameNotSettledException e = new GameNotSettledException();
		
		// Assertion
		assertTrue(e.getMessage().isEmpty());
	}
	
	@Test
	public void testNonEmptyConstructorReturnsMessage() {
		// Fixture
		final String expected = GameNotSettledExceptionTest.MESSAGE_A;
		
		// Match
		final GameNotSettledException e = new GameNotSettledException(GameNotSettledExceptionTest.MESSAGE_A);
		
		// Assertion
		assertEquals(expected, e.getMessage());
	}
	
}
