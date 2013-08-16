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
        if (rounds > 0) {
            this.rounds = rounds;
        } else {
            throw new IllegalArgumentException(State.ERR_ROUNDS.toString());
        }

        this.teams = new ArrayList<>(teamList);
        Collections.shuffle(teams);
        
        // try up to 5 times to create a proper schedule
        int i = 0;
        while (i < 5) {
            try {
                normalizeSchedule();
                createRounds();
                checkSchedule();
                break;
            } catch (IllegalStateException e) {
                i++;
            }
        }
        if (i == 5) {
            throw new IllegalStateException(State.ERR_HOME_AWAY.toString());
        }
        

    }
    
    private void checkSchedule() {
        HashMap<String, Integer> homeAwayCounter = new HashMap<>();
        int[] homeAwayValues;
        
        // loop through the entire schedule in order to check all pair sequence
        for (int round : fullSchedule.keySet()) {
            for (int day : fullSchedule.get(round).keySet()) {
                Iterator it = fullSchedule.get(round).get(day).iterator();
                for (String[] pair : fullSchedule.get(round).get(day)) {

                    for (int i = 0; i <= 1; i++) {
                        if (homeAwayCounter.get(pair[i]) == null) { // create home/away counter entry
                            homeAwayCounter.put(pair[i], 0);
                        }
                    }
                    
                    homeAwayValues = createHomeAwayValues(homeAwayCounter, pair);
                    for (int i = 0; i <= 1; i++) {
                        homeAwayCounter.put(pair[i], homeAwayValues[i]);
                        if (Math.abs(homeAwayCounter.get(pair[i])) == 3) { // a club has reached 3 home/away games in a row
                            throw new IllegalStateException(State.ERR_HOME_AWAY.toString());
                        }
                    }
                }
            }
        }
    }
    
    private void createRounds() {

        HashMap<Integer, ArrayList<String[]>> currentSchedule;
        ArrayList<String[]> pairList, newPairList;
        String[] newPair;
        Iterator it;
        int currentRound = 1;
        int day;
        
        // first add the normalized schadule
        fullSchedule.put(currentRound, (HashMap<Integer, ArrayList<String[]>>) normalizedSchedule.clone());

        while (currentRound < rounds) {
            // for each round, reverse all pairs and add rounds to the map
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
    
    private void normalizeSchedule() throws IllegalStateException {
        
        HashMap<String, Integer> homeAwayCounter = new HashMap<>();
        ArrayList<String[]> pairList;
        ArrayList<String[]> newDay;
        int[] homeAwayValues;
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
                
                // make sure that each club has the least home or away games in a row
                if (homeAwayCounter.get(pair[1]) < 2 && homeAwayCounter.get(pair[0]) > -2) {
                    if (homeAwayCounter.get(pair[0]) > 0) {
                        pair = reversePair(pair, day);
                    } else if (homeAwayCounter.get(pair[0]) > -2 && homeAwayCounter.get(pair[1]) < 2) {
                        if (homeAwayCounter.get(pair[1]) < 0) {
                            pair = reversePair(pair, day);
                        }
                    }
                }

                homeAwayValues = createHomeAwayValues(homeAwayCounter, pair);
                homeAwayCounter.put(pair[0], homeAwayValues[0]);
                homeAwayCounter.put(pair[1], homeAwayValues[1]);
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
    
    private int[] createHomeAwayValues(HashMap<String, Integer> homeAwayCounter, String[] pair) {
        int[] values = new int[2];
        
        for (int i = 0; i <= 1; i++) {
            if (homeAwayCounter.get(pair[i]) >= 0) {
                if (i == 0) {
                    values[i] = homeAwayCounter.get(pair[i]) + 1;
                } else {
                    values[i] = -1;
                }
            } else {
                if (i == 0) {
                    values[i] = 1;
                } else {
                    values[i] = homeAwayCounter.get(pair[i]) - 1;
                }
            }
        }
        
        return values;
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
