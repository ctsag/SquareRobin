package gr.daemon.squarerobin.model;

import java.util.HashMap;

public class Season {
	
	private String id = "";
	private HashMap<Integer, Round> rounds = new HashMap<>();
	
	public Season() {
		this("");
	}
	
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
	
	public Round getRound() {
		
	}

	
	
}
