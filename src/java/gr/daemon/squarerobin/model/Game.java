package gr.daemon.squarerobin.model;

import java.util.ArrayList;

public class Game {
    Team homeTeam;
    Team awayTeam;
    int homeGoals;
    int awayGoals;

    public Game(Team home, Team away) {
        this.homeTeam = home;
        this.awayTeam = away;
    }
    
    public void setHomeGoals(int goals) {
        this.homeGoals = goals;
    }
    
    public void setAwayGoals(int goals) {
        this.awayGoals = goals;
    }
    
    public int getHomeGoals() {
        return this.homeGoals;
    }
    
    public int getAwayGoals() {
        return this.awayGoals;
    }
    
    public int[] getScore() {
        return new int[]{this.homeGoals, this.awayGoals};
    }
    
    public ArrayList<Team> getTeams() {
        
    }
}
// add getTeams