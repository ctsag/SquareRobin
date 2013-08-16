package gr.daemon.squarerobin.model;

import gr.daemon.squarerobin.model.State;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.HashSet;
import java.util.Collections;
import java.util.Iterator;

public class Scheduler {
    
    private HashMap<Integer, HashMap<Integer, ArrayList<String[]>>> fullSchedule = new HashMap<>();
    private HashMap<Integer, ArrayList<String[]>> normalSchedule = new HashMap<>();
    private HashMap<Integer, ArrayList<String[]>> normalizedSchedule;
    private ArrayList<String> teams = new ArrayList<>();
    
    private int rounds;
    private static final int FIXED_TEAM_NUMBER = 0; // number of array index
    public static final int MIN_ROUNDS = 1;
    public static final int MAX_ROUNDS = 3;
    
    
    public Scheduler(ArrayList<String> teamList) {
        this(teamList, 1);
    }
    
    public Scheduler(ArrayList<String> teamList, int rounds) throws IllegalArgumentException,IllegalStateException {

        // unique check
        ArrayList<String> uniqueList = new ArrayList<>(new HashSet<String>(teamList)); 
        if (teamList.size() != uniqueList.size()) {
            throw new IllegalArgumentException(State.ERR_CLUBS_NOT_UNIQUE.toString());
        }
        // empty input check
        if (teamList.isEmpty()) {
            throw new IllegalArgumentException(State.ERR_EMPTY_INPUT.toString());
        }
        // odd clubs check
        if ((teamList.size() % 2) == 1) {
            throw new IllegalArgumentException(State.ERR_ODD_CLUBS.toString());
        }
        // round range check
        if ( (rounds >= MIN_ROUNDS) && (rounds <= MAX_ROUNDS) ) {
            this.rounds = rounds;
        } else {
            throw new IllegalArgumentException(State.ERR_ROUNDS_RANGE.toString());
        }

        this.teams = new ArrayList<>(teamList);
        Collections.shuffle(teams);
        
        // try up to 5 times to create a proper schedule
        int i = 0;
        while (i < 5) {
            try {
                normalizeSchedule();
                break;
            } catch (IllegalStateException e) {
                i++;
            }
        }
        if (i == 5) {
            throw new IllegalStateException(State.ERR_HOME_AWAY.toString());
        }
        
        createRounds();
    }
    
    public void createRounds() {

        HashMap<Integer, ArrayList<String[]>> currentSchedule;
        ArrayList<String[]> pairList, newPairList;
        String[] newPair;
        Iterator it;
        int currentRound = 1;
        int day;
        
        fullSchedule.put(currentRound, (HashMap<Integer, ArrayList<String[]>>) normalizedSchedule.clone());

        while (currentRound <= (rounds - 1)) {
            currentSchedule = new HashMap<>();
            it = fullSchedule.get(currentRound).entrySet().iterator();
            while (it.hasNext()) {
                Entry thisEntry = (Entry) it.next();
                day = (int) thisEntry.getKey();
                pairList = (ArrayList<String[]>) thisEntry.getValue();
                newPairList = new ArrayList<>();

                for (String[] pair : pairList) {
                    newPair = new String[]{pair[1], pair[0]};
                    newPairList.add(newPair.clone());
                }
                currentSchedule.put(day, (ArrayList<String[]>) newPairList.clone());
            }
            currentRound++;
            fullSchedule.put(currentRound, (HashMap<Integer, ArrayList<String[]>>) currentSchedule.clone());
        }
    }
    
    public void normalizeSchedule() throws IllegalStateException {
        
        HashMap<String, Integer> homeAwayCounter = new HashMap<>();
        ArrayList<String[]> pairList;
        ArrayList<String[]> newDay;
        String[] reversePair;
        String team;
        Iterator it;
        int day;

        // initialize home/away counters
        it = teams.iterator();
        while (it.hasNext()) {
            team = (String) it.next();
            homeAwayCounter.put(team, 0);
        }
        
        schedule();
        normalizedSchedule = (HashMap<Integer, ArrayList<String[]>>) normalSchedule.clone();
        it = normalSchedule.entrySet().iterator();
        
        while (it.hasNext()) {
            Entry thisEntry = (Entry) it.next();
            day = (int) thisEntry.getKey();
            pairList = (ArrayList<String[]>) thisEntry.getValue();
            
            for (String[] pair : pairList) {
                
                // make sure that each clud has the least home or away games in a row
                if (homeAwayCounter.get(pair[1]) < 2 && homeAwayCounter.get(pair[0]) > -2) {
                    if (homeAwayCounter.get(pair[0]) > 0) {
                        pair = reversePair(pair, day);
                    } else if (homeAwayCounter.get(pair[0]) > -2 && homeAwayCounter.get(pair[1]) < 2) {
                        if (homeAwayCounter.get(pair[1]) < 0) {
                            pair = reversePair(pair, day);
                        }
                    }
                }

                for (int i = 0; i <= 1; i++) {
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
                    if (Math.abs(homeAwayCounter.get(pair[i])) == 3) { // a club has reached 3 home/away games in a row
                        throw new IllegalStateException(State.ERR_HOME_AWAY.toString());
                    }
                }
            }
        }
    }
    
    private String[] reversePair(String[] pair, int day) {
        String[] newPair;
        ArrayList<String[]> newDay;
        
        newPair = new String[] {pair[1], pair[0]};
        newDay = normalizedSchedule.get(day);
        newDay.set(newDay.indexOf(pair), newPair.clone());
        normalizedSchedule.put(day, newDay);
        
        return newPair;
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
            
            day = new ArrayList<>();
            
            for (int i = 0; i < (teams.size() / 2); i++) {
            	pair[0] = teams.get(i);
            	pair[1] = teams.get(teams.size() - (i + 1));               

                day.add(pair.clone());
            }
            normalSchedule.put(j, day);
            
            // remove fixed team and rotate
            teams.remove(0);
            Collections.rotate(teams, 1);
        }
        
        teams.add(0, fixedTeam);
    }
    
    public HashMap<Integer, HashMap<Integer, ArrayList<String[]>>> getSchedule() {
        return fullSchedule;
    }

}
