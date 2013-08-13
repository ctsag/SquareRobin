package gr.daemon.squarerobin.schedulertest;

import gr.daemon.squarerobin.model.Scheduler;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SchedulerTest {

    private ArrayList<String> teams = new ArrayList<>();
    private HashMap<Integer, ArrayList<String[]>> outSchedule = new HashMap<>();
    
    @Before
    public void setUp() {
        teams = new ArrayList<>();
        outSchedule = new HashMap<>();
    }
    
    @Test
    public void testOddTeams() {
        
        teams.add("PAO");
        teams.add("OSFP");
        teams.add("MPAOK");
        
        try {
            Scheduler scheduler = new Scheduler(teams);
            outSchedule = scheduler.getSchedule();
            
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(Scheduler.ERR_ODDEMPTY_CLUBS, e.getMessage());
        }
    }
    
    @Test
    public void testEmptyInput() {
        
        try {
            Scheduler scheduler = new Scheduler(teams);
            outSchedule = scheduler.getSchedule();
            
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(Scheduler.ERR_ODDEMPTY_CLUBS, e.getMessage());
        }
    }
    
    @Test
    public void testNotUniqueTeams() {
        
        teams.add("PAO");
        teams.add("OSFP");
        teams.add("MPAOK");
        teams.add("MPAOK");
        
        try {
            Scheduler scheduler = new Scheduler(teams);
            outSchedule = scheduler.getSchedule();
            
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(Scheduler.ERR_CLUBS_NOTUNIQUE, e.getMessage());
        }
    }
    
    @Test
    public void testProperOutput() {
        
        teams.add("PAO");
        teams.add("OSFP");
        teams.add("MPAOK");
        teams.add("ARIS");
        teams.add("ASTERAS");
        teams.add("OFI");
        
        try {
            Scheduler scheduler = new Scheduler(teams);
            outSchedule = scheduler.getSchedule();
            assertEquals(teams.size() - 1, outSchedule.size());
        } catch(Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testHomeAway() {
        
        teams.add("PAO");
        teams.add("OSFP");
        teams.add("MPAOK");
        teams.add("ARIS");
        teams.add("ASTERAS");
        teams.add("OFI");
        
        try {
            Scheduler scheduler = new Scheduler(teams);
            outSchedule = scheduler.getSchedule();
            int a = 1;
        } catch(IllegalStateException e) {
            fail(e.getMessage());
        } catch(IllegalArgumentException e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(Scheduler.ERR_CLUBS_NOTUNIQUE, e.getMessage());
        }
    }
}