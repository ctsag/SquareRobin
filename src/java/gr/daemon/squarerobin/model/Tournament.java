package gr.daemon.squarerobin.model;

import java.util.Collection;
import java.util.HashMap;

public class Tournament {

	private final String name;
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

	public Season getSeason(final String name) {
		return this.seasons.get(name);
	}

	public void addSeason(final Season season) {
		this.seasons.put(season.getName(), season);
	}

	public void removeSeason(final String name) {
		this.seasons.remove(name);
	}

	public void clearSeasons() {
		this.seasons.clear();
	}

}