package gr.daemon.squarerobin.model;

import gr.daemon.squarerobin.engine.State;
import static org.junit.Assert.*;
import org.junit.Test;

public class StateTest {

    @Test
    public void testMessages() {
        for (State state : State.values()) {
            assertFalse(state.toString().equals(""));
        }
    }

}