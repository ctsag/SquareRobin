package gr.daemon.squarerobin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;


public class Scheduler {
    private HashMap<Integer,ArrayList> fullSchedule = new HashMap<>();
    private ArrayList<String> teams = new ArrayList<>();
    private static final int FIXED_TEAM_NUMBER = 0;
    
    Scheduler(ArrayList<String> teamList) throws IllegalArgumentException {
        if ((teamList.size() % 2) == 1) {
            throw new IllegalArgumentException("Input list size must be an even number");
        }
        teams = (ArrayList<String>) teamList.clone();
    }

    public void schedule() {
        ArrayList<String> pair;
        String fixedTeam;

        // get content of fixed team position in order to add it in first position
        fixedTeam = teams.get(FIXED_TEAM_NUMBER);
        teams.remove(FIXED_TEAM_NUMBER);
        
        for (int j = 0; j < (teams.size() - 1); j++) {
            // add fixed team in first position
            teams.add(0, fixedTeam);
            
            for (int i = 0; i < (teams.size() / 2); i++) {
                pair = new ArrayList<>();
                pair.add(teams.get(i));
                pair.add(teams.get(teams.size()-(i+1)));
                
                System.out.println("round: " + j + " a: " + pair.get(0) + " b: " + pair.get(1));
                fullSchedule.put(j, pair);
            }
            // remove fixed team and rotate
            teams.remove(0);
            Collections.rotate(teams, 1);
        }
    }
    
    public HashMap<Integer,ArrayList> getSchedule() {
        return fullSchedule;
    }

}
