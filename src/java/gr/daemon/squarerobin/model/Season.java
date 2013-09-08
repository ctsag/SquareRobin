package gr.daemon.squarerobin.model;

import gr.daemon.squarerobin.model.exceptions.DuplicateEntryException;
import gr.daemon.squarerobin.model.exceptions.InexistentEntryException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

public class Season {

	private final String name;
	private final HashMap<Integer, Round> rounds = new HashMap<>();
	private Tournament tournament;
	private LeagueTable leagueTable;
	private LeagueRunner leagueRunner;

	protected Season(final String name, ArrayList<Team> teams) {
		this.name = name;
		this.leagueTable = new LeagueTable(teams);
		this.leagueRunner = new LeagueRunner(this);
	}

	public String getName() {
		return this.name;
	}

	public Tournament getTournament() {
		return this.tournament;
	}
	
	public void setTournament(final Tournament tournament) throws DuplicateEntryException {
		final List<Season> tournamentSeasons = Arrays.asList(tournament.getSeasons());
		if (!tournamentSeasons.contains(this)) {
			tournament.addSeason(this);
		}
		this.tournament = tournament;
	}
	
	public LeagueTable getLeagueTable() {
		return this.leagueTable;
	}
	
	public LeagueRunner getLeagueRunner() {
		return this.leagueRunner;
	}

	public Round[] getRounds() {
		final Collection<Round> rounds = this.rounds.values();
		
		return rounds.toArray(new Round[rounds.size()]);
	}

	public Round getRound(final int index) {
		return this.rounds.get(index);
	}

	protected void addRound(final Round round) throws DuplicateEntryException {
		final int index = round.getIndex();
		
		if (this.rounds.containsKey(index)) {
			throw new DuplicateEntryException("A round of index " + index + " already exists");
		} else {
			this.rounds.put(index, round);
			round.setSeason(this);
		}
	}

	protected void removeRound(final int index) throws InexistentEntryException {
		if (this.rounds.containsKey(index)) {
			this.rounds.remove(index);
		} else {
			throw new InexistentEntryException("Round " + index + " does not exist");
		}
	}

	protected void clearRounds() {
		this.rounds.clear();
	}

}