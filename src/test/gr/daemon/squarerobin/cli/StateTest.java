package gr.daemon.squarerobin.cli;

import static org.junit.Assert.*;
import org.junit.Test;

public class StateTest {

	@Test
	public void testMessages() {
		for (final State state : State.values()) {
			if (state.getValue() > 0) {
				assertFalse(state.toString().equals(""));
			}
		}
	}

	@Test
	public void testOk() {
		final State ok = State.OK;
		assertTrue("".equals(ok.toString()));
	}

}