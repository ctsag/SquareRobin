package gr.daemon.squarerobin.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.HashSet;
import java.util.Collections;
import java.util.Iterator;

public class Scheduler {
    
    private HashMap<Integer, ArrayList<String[]>> fullSchedule = new HashMap<>();
    private ArrayList<String> teams = new ArrayList<>();
    private HashMap<String, Integer> homeAwayCounter = new HashMap<>();
    private static final int FIXED_TEAM_NUMBER = 0; // number of array index
    public static final String ERR_ODDEMPTY_CLUBS = "Input list size must be an even number and not empty";
    public static final String ERR_CLUBS_NOTUNIQUE = "Input list is not unique";
    public static final String ERR_HOMEAWAY = "Found 3 matches home/away";
    
    
    public Scheduler(ArrayList<String> teamList) throws IllegalArgumentException,IllegalStateException {

        // unique check
        ArrayList<String> uniqueList = new ArrayList<>(new HashSet<String>(teamList)); 
        if (teamList.size() != uniqueList.size()) {
            throw new IllegalArgumentException(ERR_CLUBS_NOTUNIQUE);
        }
        // odd or empty check
        if ( ((teamList.size() % 2) == 1) || (teamList.isEmpty()) ) {
            throw new IllegalArgumentException(ERR_ODDEMPTY_CLUBS);
        }

        teams = new ArrayList<>(teamList);
        Collections.shuffle(teams);
        try {
            normalizeSchedule();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
    
    public void normalizeSchedule() throws IllegalStateException {
        
        String[] pair;
        String team;
        Iterator it;

        it = teams.iterator();
        while (it.hasNext()) {
            team = (String) it.next();
            homeAwayCounter.put(team, 0);
        }
        
        schedule();
        it = fullSchedule.entrySet().iterator();
        
        while (it.hasNext()) {
            Entry thisEntry = (Entry) it.next();
            pair = (String[]) thisEntry.getValue();
            for (int i = 0; i <= 1; i++) {
                if (homeAwayCounter.get(pair[i]) >= 0) {
                    if (i == 0) {
                        homeAwayCounter.put(pair[i], homeAwayCounter.get(pair[i]) + 1);
                    } else {
                        homeAwayCounter.put(pair[i], 1);
                    }
                } else {
                    if (i == 0) {
                        homeAwayCounter.put(pair[i], -1);
                    } else {
                        homeAwayCounter.put(pair[i], homeAwayCounter.get(pair[i]) - 1);
                    }
                }
            }
            if ( (Math.abs(homeAwayCounter.get(pair[0])) == 3) || (Math.abs(homeAwayCounter.get(pair[1])) == 3) ) {
                throw new IllegalStateException(ERR_HOMEAWAY);
            }
        }
    }
    
    private void schedule() {
        
        ArrayList<String[]> day;
        String[] pair = new String[2];
        String fixedTeam;

        // get content of fixed team position in order to add it in first position
        fixedTeam = teams.get(FIXED_TEAM_NUMBER);
        teams.remove(FIXED_TEAM_NUMBER);
        
        for (int j = 1; j <= teams.size(); j++) {
            // add fixed team in first position
            teams.add(0, fixedTeam);
            
            // create a new schedule day
            day = new ArrayList<>();
            
            for (int i = 0; i < (teams.size() / 2); i++) {
            	pair[0] = teams.get(i);
            	pair[1] = teams.get(teams.size() - (i + 1));               

                // add each pair to day
                day.add(pair.clone());
            }
            // add day to schedule
            fullSchedule.put(j, day);
            
            // remove fixed team and rotate
            teams.remove(0);
            Collections.rotate(teams, 1);
        }
    }
    
    public HashMap<Integer, ArrayList<String[]>> getSchedule() {
        return fullSchedule;
    }

}
