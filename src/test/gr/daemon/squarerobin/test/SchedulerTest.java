/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.daemon.squarerobin.test;

import gr.daemon.squarerobin.model.Scheduler;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tnik
 */
public class SchedulerTest {
    
    @Test
    public void testOddTeams() {
        ArrayList<String> teams = new ArrayList<>();
        HashMap<Integer, ArrayList<String[]>> outSchedule = new HashMap<>();
        
        teams.add("PAO");
        teams.add("OSFP");
        teams.add("MPAOK");
        
        try {
            Scheduler scheduler = new Scheduler(teams);
            outSchedule = scheduler.getSchedule();
            
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
}