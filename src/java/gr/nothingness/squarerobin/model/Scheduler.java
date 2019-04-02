package gr.nothingness.squarerobin.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.HashSet;
import java.util.Collections;
import java.util.Iterator;

public class Scheduler {
    
    private HashMap<Integer, TreeMap<Integer, ArrayList<String[]>>> fullSchedule = new HashMap<>();
    private TreeMap<Integer, ArrayList<String[]>> normalSchedule = new TreeMap<>();
    private TreeMap<Integer, ArrayList<String[]>> normalizedSchedule;
    private ArrayList<String> teams;
    private int rounds;

    private static final int FIXED_TEAM_NUMBER = 0;

    public Scheduler(ArrayList<String> teamList) {
        this(teamList, 2);
    }
    
    public Scheduler(ArrayList<String> teamList, int rounds) throws IllegalArgumentException,IllegalStateException {
        ArrayList<String> uniqueList = new ArrayList<>(new HashSet<>(teamList));

        if (teamList.size() != uniqueList.size()) {
            throw new IllegalArgumentException(State.ERR_CLUBS_NOT_UNIQUE.toString());
        }

        if (teamList.isEmpty()) {
            throw new IllegalArgumentException(State.ERR_EMPTY_INPUT.toString());
        }

        if ((teamList.size() % 2) == 1) {
            throw new IllegalArgumentException(State.ERR_ODD_CLUBS.toString());
        }

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
                addScores();
                break;
            } catch (IllegalStateException e) {
                i++;
            }
        }
        if (i == 5) {
            throw new IllegalStateException(State.ERR_HOME_AWAY.toString());
        }

    }
    
    private void addScores() {
        int pairIndex;
        String scoreHome, scoreAway;
        String[] newPair;
        
        // loop through the entire schedule and add a 3rd element in pair array which represents the score
        for (int round : fullSchedule.keySet()) {
            for (int day : fullSchedule.get(round).keySet()) {
                for (String[] pair : fullSchedule.get(round).get(day)) {
                    // add the score
                    scoreHome = Long.toString(Math.round(Math.random() * 5));
                    scoreAway = Long.toString(Math.round(Math.random() * 5));
                    newPair = new String[] {pair[0], pair[1], scoreHome, scoreAway};
                    pairIndex = fullSchedule.get(round).get(day).indexOf(pair);
                    fullSchedule.get(round).get(day).set(pairIndex, newPair);
                }
            }
        }
    }
    
    private void createRounds() {

        TreeMap<Integer, ArrayList<String[]>> currentSchedule;
        ArrayList<String[]> pairList, newPairList;
        String[] newPair;
        Iterator it;
        int currentRound = 1;
        int day;
        
        // first add the normalized schedule
        fullSchedule.put(currentRound, (TreeMap<Integer, ArrayList<String[]>>) normalizedSchedule.clone());

        while (currentRound < rounds) {
            // for each round, reverse all pairs and add rounds to the map
            currentSchedule = new TreeMap<>();
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
            fullSchedule.put(currentRound, (TreeMap<Integer, ArrayList<String[]>>) currentSchedule.clone());
        }
    }
    
    private void normalizeSchedule() throws IllegalStateException {
        
        HashMap<String, Integer> homeAwayCounter = new HashMap<>();
        ArrayList<String[]> pairList;
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
        normalizedSchedule = (TreeMap<Integer, ArrayList<String[]>>) normalSchedule.clone();

        it = normalSchedule.entrySet().iterator();
        while (it.hasNext()) {
            Entry thisEntry = (Entry) it.next();
            day = (int) thisEntry.getKey();
            pairList = (ArrayList<String[]>) thisEntry.getValue();
            int a = 1;
            
            for (String[] pair : pairList) {
                
                // CAN the home team become away? And CAN the away team become home? 
                if (homeAwayCounter.get(pair[1]) < 2 && homeAwayCounter.get(pair[0]) > -2) {
                    // Yes, both can. But SHOULD the home team become away? 
                    if (homeAwayCounter.get(pair[0]) > 0) {
                        pair = reversePair(pair, day);
                    } else {
                        // No the home team SHOULD NOT become away. But SHOULD the away team become home?
                        if (homeAwayCounter.get(pair[1]) < -1) {
                            // Yes. Reverse
                            pair = reversePair(pair, day); 
                        }
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
                // if home team previously played home, points increased by 1
                if (i == 0) {
                    values[i] = homeAwayCounter.get(pair[i]) + 1;
                // if away team previously played home, points reset to -1
                } else {
                    values[i] = -1;
                }
            } else {
                // if home team previously played away, points reset to 1
                if (i == 0) {
                    values[i] = 1;
                // if away team previously played away, points decreased by 1
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
            	pair[0] = teams.get(i); // home team
            	pair[1] = teams.get(teams.size() - (i + 1)); // away team

                day.add(pair.clone());
            }
            normalSchedule.put(j, day);
            
            // remove fixed team and rotate
            teams.remove(0);
            Collections.rotate(teams, -1);
        }
        teams.add(0, fixedTeam);        
    }
    
    public HashMap<Integer, TreeMap<Integer, ArrayList<String[]>>> getSchedule() {
        return fullSchedule;
    }
    
    public TreeMap<Integer, String[]> getLeagueTable() {
        return this.getLeagueTable(false);
    }
    
    public TreeMap<Integer, String[]> getLeagueTable(boolean tiePreference) {

        HashMap<String, String[]> statsMap = new HashMap<>();
        TreeMap<Integer, String[]> leagueTable = new TreeMap<>();
        String[] stats;
        int points, scoreHome, scoreAway, goalsScored, goalsConceded, positionCounter = 1;
        String position, goalAverage;
        int[] pointsToAdd = new int[2];
        String[][] unsortedTable = new String[teams.size()][4];
        String[][] sortedTable;
        
        // loop through the entire schedule in order to create team's points
        for (int round : fullSchedule.keySet()) {
            for (int day : fullSchedule.get(round).keySet()) {
                for (String[] pair : fullSchedule.get(round).get(day)) {
                    scoreHome = Integer.parseInt(pair[2]);
                    scoreAway = Integer.parseInt(pair[3]);
                    
                    if (scoreHome > scoreAway) { // 3 points for winner and 0 for loser
                        pointsToAdd[0] = 3;
                        pointsToAdd[1] = 0;
                    } else if (scoreHome < scoreAway) {
                        pointsToAdd[0] = 0;
                        pointsToAdd[1] = 3;
                    } else { // in a tie, each team gets 1 point 
                        pointsToAdd[0] = 1;
                        pointsToAdd[1] = 1;
                    }

                    for (int i = 0; i <= 1; i++) {
                        if (statsMap.get(pair[i]) == null) {
                            statsMap.put(pair[i], new String[]{null,"0","0","0"});
                        }
                        // goals scored is index 2 for home and 3 for away
                        goalsScored = Integer.parseInt(statsMap.get(pair[i])[2]) + Integer.parseInt(pair[i + 2]);
                        // goals conceded is index 4 for home and 3 for away
                        goalsConceded = Integer.parseInt(statsMap.get(pair[i])[3]) + Integer.parseInt(pair[(i == 0) ? 3 : 2]);
                        points = Integer.parseInt(statsMap.get(pair[i])[1]) + pointsToAdd[i];
                        stats = new String[]{pair[i], Integer.toString(points), Integer.toString(goalsScored), Integer.toString(goalsConceded)};
                        statsMap.put(pair[i], stats);
                    }
                }
            }
        }
        // create list to sort
        int i = 0;
        for (String key : statsMap.keySet()) {
            unsortedTable[i] = statsMap.get(key);
            i++;
        }
        
        // sort values by points, goal average, most goals scored, alphabetically
        LeagueTableSorter sorter = new LeagueTableSorter(unsortedTable);
        sortedTable = sorter.getLeagueTable();
        
        // add sorted elements to statsMap
        for (String[] element : sortedTable) {
            position = Integer.toString(positionCounter);
            if (!leagueTable.isEmpty()) {
                if (leagueTable.get(positionCounter - 1)[2].equals(element[1]) && tiePreference) {
                    position = "-";
                }
            }
            goalAverage = Integer.toString(Integer.parseInt(element[2]) - Integer.parseInt(element[3]));
            goalAverage = (Character.toString(goalAverage.charAt(0)).equals("-")) ? goalAverage : "+" + goalAverage;
            leagueTable.put(positionCounter, new String[]{ position, element[0], element[1], element[2], element[3], goalAverage });
            positionCounter++;
        }
        
        return leagueTable;
    }

}
