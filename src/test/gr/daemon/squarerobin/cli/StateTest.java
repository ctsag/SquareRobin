package gr.daemon.squarerobin.cli;

import gr.daemon.squarerobin.cli.State;
import static org.junit.Assert.*;
import org.junit.Test;

public class StateTest {

	@Test
	public void testMessages() {
		for (State state : State.values()) {
			if (state.getValue() > 0) {
				assertFalse(state.toString().equals(""));
			}
		}
	}

}