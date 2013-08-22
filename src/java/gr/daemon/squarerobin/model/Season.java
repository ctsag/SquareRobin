package gr.daemon.squarerobin.model;

import java.util.HashMap;

public class Season {
	
	private String id = "";
	private HashMap<Integer, Round> rounds = new HashMap<>();
	private HashMap<String, Team> teams = new HashMap<>();
	
	public Season(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Round[] getRounds() {
		return (Round[])this.rounds.values().toArray();
	}
	
	public Round getRound(int index) {
		return this.rounds.get(index); 
	}
	
	public void addRound(Round round) {
		this.rounds.put(round.getIndex(), round);
	}

	public void removeRound(Round round) {
		this.rounds.remove(round.getIndex());
	}
	
	public void removeRound(int index) {
		this.rounds.remove(id);
	}
	
	public void clearRounds() {
		this.rounds.clear();
	}
	
	public Team[] getTeams() {
		return (Team[])this.teams.values().toArray();
	}
	
	public Team getTeam(String name) {
		return this.teams.get(name); 
	}
	
	public void addteam(Team team) {
		this.teams.put(team.getName(), team);
	}

	public void removeteam(Team team) {
		this.teams.remove(team.getName());
	}
	
	public void removeTeam(String name) {
		this.teams.remove(id);
	}
	
	public void clearTeams() {
		this.teams.clear();
	}
	
}