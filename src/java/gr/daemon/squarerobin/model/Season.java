package gr.daemon.squarerobin.model;

import java.util.Collection;
import java.util.HashMap;

public class Season {

	private String id;
	private final HashMap<Integer, Round> rounds = new HashMap<>();
	private final HashMap<String, Team> teams = new HashMap<>();

	public Season(final String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public Round[] getRounds() {
		final Collection<Round> rounds = this.rounds.values();
		return rounds.toArray(new Round[rounds.size()]);
	}

	public Round getRound(final int index) {
		return this.rounds.get(index); 
	}

	public void addRound(final Round round) {
		this.rounds.put(round.getIndex(), round);
	}

	public void removeRound(final int index) {
		this.rounds.remove(index);
	}

	public void clearRounds() {
		this.rounds.clear();
	}
	
	public Team[] getTeams() {
		final Collection<Team> teams = this.teams.values();
		return teams.toArray(new Team[teams.size()]);
	}

	public Team getTeam(final int index) {
		return this.teams.get(index); 
	}

	public void addTeam(final Team team) {
		this.teams.put(team.getName(), team);
	}

	public void removeTeam(final String name) {
		this.teams.remove(name);
	}

	public void clearTeams() {
		this.teams.clear();
	}

}