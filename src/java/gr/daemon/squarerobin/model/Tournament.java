package gr.daemon.squarerobin.model;

import gr.daemon.squarerobin.model.exceptions.DuplicateEntryException;
import gr.daemon.squarerobin.model.exceptions.InexistentEntryException;
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

	public void addSeason(final Season season) throws DuplicateEntryException {
		final String name = season.getName();
		
		if (this.seasons.containsKey(name)) {
			throw new DuplicateEntryException("A season named " + name + " already exists");
		} else {			
			this.seasons.put(name, season);
			season.setTournament(this);
		}
	}

	public void removeSeason(final String name) throws InexistentEntryException {
		if (this.seasons.containsKey(name)) {
			this.seasons.remove(name);	
		} else {
			throw new InexistentEntryException("Season " + name + " does not exist");
		}		
	}

	public void clearSeasons() {
		this.seasons.clear();
	}

}