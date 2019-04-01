package gr.nothingness.squarerobin.model;

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