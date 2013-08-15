package gr.daemon.squarerobin.schedulertest;

import gr.daemon.squarerobin.model.State;
import gr.daemon.squarerobin.model.Scheduler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
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
            
        } catch(IllegalStateException e) {
            fail(e.getMessage());
        } catch(IllegalArgumentException e) {
            assertEquals(State.ERR_ODD_CLUBS.toString(), e.getMessage());
        }
    }
    
    @Test
    public void testEmptyInput() {
        
        try {
            Scheduler scheduler = new Scheduler(teams);
            outSchedule = scheduler.getSchedule();
            
        } catch(IllegalStateException e) {
            fail(e.getMessage());
        } catch(IllegalArgumentException e) {
            assertEquals(State.ERR_EMPTY_INPUT.toString(), e.getMessage());
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
            
        } catch(IllegalStateException e) {
            fail(e.getMessage());
        } catch(IllegalArgumentException e) {
            assertEquals(State.ERR_CLUBS_NOT_UNIQUE.toString(), e.getMessage());
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
        } catch(IllegalStateException|IllegalArgumentException e) {
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
        } catch(IllegalArgumentException e) {
            fail(e.getMessage());
        } catch(IllegalStateException e) {
            assertEquals(State.ERR_CLUBS_NOT_UNIQUE.toString(), e.getMessage());
        }
    }
    
    @Test
    public void testThreeGames() {
        
        HashMap<String, Integer> homeAwayCounter = new HashMap<>();
        ArrayList<String[]> pairList;
        Iterator it;

        teams.add("PAO");
        teams.add("OSFP");
        teams.add("MPAOK");
        teams.add("ARIS");
        teams.add("ASTERAS");
        teams.add("OFI");
        
        try {
            Scheduler scheduler = new Scheduler(teams);
            outSchedule = scheduler.getSchedule();

            it = outSchedule.entrySet().iterator();
            while (it.hasNext()) {
                Entry thisEntry = (Entry) it.next();
                pairList = (ArrayList<String[]>) thisEntry.getValue();
                
                for (String[] pair : pairList) {
                    for (int i = 0; i <= 1; i++) {
                        if (homeAwayCounter.get(pair[i]) == null) { // create home/away counter entry
                            homeAwayCounter.put(pair[i], 0);
                        }
                        
                        if (homeAwayCounter.get(pair[i]) >= 0) {
                            if (i == 0) {
                                homeAwayCounter.put(pair[i], homeAwayCounter.get(pair[i]) + 1);
                            } else {
                                homeAwayCounter.put(pair[i], -1);
                            }
                        } else {
                            if (i == 0) {
                                homeAwayCounter.put(pair[i], 1);
                            } else {
                                homeAwayCounter.put(pair[i], homeAwayCounter.get(pair[i]) - 1);
                            }
                        }
                        if (Math.abs(homeAwayCounter.get(pair[i])) == 3) {
                            fail("Team " + pair[i] + " has reached 3 home/away games in a row");
                        }
                    }
                }
            }
        } catch(IllegalStateException|IllegalArgumentException e) {
            fail(e.getMessage());
        }
    }
}