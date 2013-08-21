package gr.daemon.squarerobin.model;

import java.util.HashMap;

public class Tournament {
	
	private String name = "";
	private HashMap<String, Season> seasons = new HashMap<>();
	
	public Tournament() {
		this("");		
	}

	public Tournament(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Season[] getSeasons() {
		return (Season[])this.seasons.values().toArray();
	}
	
	public Season getSeason(String id) {
		return this.seasons.get(id);
	}
	
	public void addSeason(Season season) {
		this.seasons.put(season.getId(), season);
	}
	
	public void removeSeason(Season season) {
		this.seasons.remove(season.getId());
	}
	
	public void removeSeason(String id) {
		this.seasons.remove(id);
	}
	
	public void clearSeasons() {
		this.seasons.clear();
	}

}