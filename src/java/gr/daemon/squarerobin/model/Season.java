package gr.daemon.squarerobin.model;

import java.util.Collection;
import java.util.HashMap;

public class Season {
	
	private String id = "";
	private HashMap<Integer, Round> rounds = new HashMap<>();	
	
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
		Collection<Round> rounds = this.rounds.values();
		return rounds.toArray(new Round[rounds.size()]);
	}
	
	public Round getRound(int index) {
		return this.rounds.get(index); 
	}
	
	public void addRound(Round round) {
		this.rounds.put(round.getIndex(), round);
	}
	
	public void removeRound(int index) {
		this.rounds.remove(index);
	}
	
	public void clearRounds() {
		this.rounds.clear();
	}
	
}