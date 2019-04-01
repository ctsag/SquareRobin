package gr.nothingness.squarerobin.cli;

import static org.junit.Assert.*;
import org.junit.Test;

public class StateTest {

	@Test
	public void testMessages() {
		for (State state : State.values()) {
			if (state.getValue() > 0) {
				assertFalse(state.toString().equals(""));
			} else {
				assertTrue(state.toString().equals(""));
			}
		}		
	}

}