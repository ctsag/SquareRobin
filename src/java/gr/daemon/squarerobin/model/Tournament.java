package gr.daemon.squarerobin.model;

import java.util.Collection;
import java.util.HashMap;

public class Tournament {

	private String name;
	private final HashMap<String, Season> seasons = new HashMap<>();

	public Tournament(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public Season[] getSeasons() {
		final Collection<Season> seasons = this.seasons.values();
		return seasons.toArray(new Season[seasons.size()]);
	}

	public Season getSeason(final String id) {
		return this.seasons.get(id);
	}

	public void addSeason(final Season season) {
		this.seasons.put(season.getId(), season);
	}

	public void removeSeason(final String id) {
		this.seasons.remove(id);
	}

	public void clearSeasons() {
		this.seasons.clear();
	}

}