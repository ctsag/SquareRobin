package gr.daemon.squarerobin.model;

public class Game {
    
    private Team homeTeam;
    private Team awayTeam;
    private int homeGoals;
    private int awayGoals;

    public Game(Team home, Team away) {
        this.homeTeam = home;
        this.awayTeam = away;
    }
    
    public void setHomeGoals(int goals) {
        this.homeGoals = goals;
    }
    
    public int getHomeGoals() {
        return this.homeGoals;
    }
    
    public int getAwayGoals() {
        return this.awayGoals;
    }
    
    public void setAwayGoals(int goals) {
        this.awayGoals = goals;
    }

    public int[] getScore() {
        return new int[]{this.homeGoals, this.awayGoals};
    }
    
    public Team[] getTeams() {
        return new Team[]{this.homeTeam, this.awayTeam};
    }
}