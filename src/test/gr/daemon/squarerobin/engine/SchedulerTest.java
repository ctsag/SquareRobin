package gr.daemon.squarerobin.engine;

import gr.daemon.squarerobin.engine.State;
import gr.daemon.squarerobin.engine.Scheduler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SchedulerTest {

    private ArrayList<String> teams = new ArrayList<>();
    private HashMap<Integer, TreeMap<Integer, ArrayList<String[]>>> outSchedule = new HashMap<>();
    private static final int ROUNDS = 2;
    
    @Before
    public void setUp() {
        teams = new ArrayList<>();
        outSchedule = new HashMap<>();
    }
    
    @Test
    public void testOddTeams() throws IllegalStateException {
        
        teams.add("PAO");
        teams.add("OSFP");
        teams.add("MPAOK");
        
        try {
            Scheduler scheduler = new Scheduler(teams, ROUNDS);
            outSchedule = scheduler.getSchedule();
        } catch(IllegalArgumentException e) {
            assertEquals(State.ERR_ODD_CLUBS.toString(), e.getMessage());
        }
    }
    
    @Test
    public void testEmptyInput() throws IllegalStateException {
        
        try {
            Scheduler scheduler = new Scheduler(teams, ROUNDS);
            outSchedule = scheduler.getSchedule();
        } catch(IllegalArgumentException e) {
            assertEquals(State.ERR_EMPTY_INPUT.toString(), e.getMessage());
        }
    }
    
    @Test
    public void testNotUniqueTeams() throws IllegalStateException {
        
        teams.add("PAO");
        teams.add("OSFP");
        teams.add("MPAOK");
        teams.add("MPAOK");
        
        try {
            Scheduler scheduler = new Scheduler(teams, ROUNDS);
            outSchedule = scheduler.getSchedule();
        } catch(IllegalArgumentException e) {
            assertEquals(State.ERR_CLUBS_NOT_UNIQUE.toString(), e.getMessage());
        }
    }
    
    @Test
    public void testProperOutput() throws IllegalStateException, IllegalArgumentException {
        
        teams.add("PAO");
        teams.add("OSFP");
        teams.add("MPAOK");
        teams.add("ARIS");
        teams.add("ASTERAS");
        teams.add("OFI");
        
        Scheduler scheduler = new Scheduler(teams, ROUNDS);
        outSchedule = scheduler.getSchedule();
        assertEquals(teams.size() - 1, outSchedule.get(1).size());
    }
    
    // FIXME: this is an antipattern
    @Test
    public void test3InARow() throws IllegalStateException, IllegalArgumentException {
        
        HashMap<String, Integer> homeAwayCounter = new HashMap<>();
        ArrayList<String[]> pairList;
        Iterator it;

        teams.add("PAO");
        teams.add("OSFP");
        teams.add("MPAOK");
        teams.add("ARIS");
        teams.add("ASTERAS");
        teams.add("OFI");
        teams.add("XANTHI");
        teams.add("ERGOTELIS");
        
        Scheduler scheduler = new Scheduler(teams, 2);
        outSchedule = scheduler.getSchedule();

        it = outSchedule.get(1).entrySet().iterator();
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
    }
    
    @Test
    public void stressTest() throws IllegalStateException, IllegalArgumentException {
        Scheduler scheduler = null;
        
        for (int i = 1; i <= 1000; i++) {
            
            teams.add("PAO");
            teams.add("OSFP");
            teams.add("MPAOK");
            teams.add("ARIS");
            teams.add("ASTERAS");
            teams.add("OFI");

            scheduler = new Scheduler(teams, ROUNDS);
            outSchedule = scheduler.getSchedule();
            teams = new ArrayList<>();
            outSchedule = new HashMap<>();
        }
        assertFalse(scheduler.getSchedule() == null);
    }
    
    @Test
    public void testRoundsOutOfRange() throws IllegalStateException {
        
        teams.add("PAO");
        teams.add("OSFP");
        teams.add("MPAOK");
        teams.add("OFI");
        
        try {
            Scheduler scheduler = new Scheduler(teams, -1);
            outSchedule = scheduler.getSchedule();
        } catch(IllegalArgumentException e) {
            assertEquals(State.ERR_ROUNDS.toString(), e.getMessage());
        }
    }
    
    @Test
    public void testLeagueTableSize() throws IllegalStateException, IllegalArgumentException{
        TreeMap<Integer, String[]> points;
        
        teams.add("PAO");
        teams.add("OSFP");
        teams.add("MPAOK");
        teams.add("ARIS");
        teams.add("ASTERAS");
        teams.add("OFI");
        
        Scheduler scheduler = new Scheduler(teams, ROUNDS);
        outSchedule = scheduler.getSchedule();
        points = scheduler.getLeagueTable(true);
        assertEquals(teams.size(), points.size());
    }
}