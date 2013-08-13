package gr.daemon.squarerobin.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;

public class Scheduler {
    
    private HashMap<Integer, ArrayList<String[]>> fullSchedule = new HashMap<>();
    private ArrayList<String> teams = new ArrayList<>();
    private static final int FIXED_TEAM_NUMBER = 0; // number of array index
    public static final String ERR_ODDEMPTY_CLUBS = "Input list size must be an even number and not empty";
    public static final String ERR_CLUBS_NOTUNIQUE = "Input list is not unique";
    
    
    public Scheduler(ArrayList<String> teamList) throws IllegalArgumentException {

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
        schedule();
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
