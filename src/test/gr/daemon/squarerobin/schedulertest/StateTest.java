package gr.daemon.squarerobin.schedulertest;

import gr.daemon.squarerobin.model.State;
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