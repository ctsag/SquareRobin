package gr.daemon.squarerobin.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Scheduler {

	public static final int DEFAULT_ROUNDS = 2;
	private static final int FIXED_TEAM = 0;
	private final Season season;
	private final int rounds;
	private final ArrayList<Team> teams = new ArrayList<>();

	public Scheduler(final String season, final String[] teams) throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, ThreeInARowException, DuplicateEntryException, GameAlreadySettledException {
		this(season, teams, Scheduler.DEFAULT_ROUNDS);
	}

	public Scheduler(final String season, final String[] teams, final int rounds) throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, ThreeInARowException, DuplicateEntryException, GameAlreadySettledException {
		this.validateTeams(teams);
		this.validateRounds(rounds);
		this.season = new Season(season);
		this.rounds = rounds;
		this.generateTeams(teams);
		this.generateFirstRound();
		this.normalizeFirstRound();
		this.generateAdditionalRounds();
	}
	
	public Season getSeason() {
		return this.season;
	}
	
	public ArrayList<Team> getTeams() {
		return this.teams;
	}
	
	public int getRounds() {
		return this.rounds;
	}

	private void validateTeams(final String[] teams) throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException {
		// Check for duplicates
		final HashSet<String> uniqueSet = new HashSet<String>(Arrays.asList(teams));
		final String[] uniqueTeams = uniqueSet.toArray(new String[uniqueSet.size()]);
		if (teams.length != uniqueTeams.length) {
			throw new DuplicateTeamsException("Duplicate teams provided");
		}
		// Check for insufficient teams
		if (teams.length < 2) {
			throw new InsufficientTeamsException("Insufficient teams provided");
		}
		// Check for odd list
		if (teams.length % 2 == 1) {
			throw new OddTeamNumberException("Odd number of teams provided");
		}
	}

	private void validateRounds(final int rounds) throws InvalidRoundsException {
		if (rounds <= 0) {
			throw new InvalidRoundsException("The number of rounds must be at least one");
		}
	}

	private void generateTeams(final String[] teams) {
		for (final String team : teams) {
			final Team generatedTeam = new Team(team);
			this.teams.add(generatedTeam);
		}
	}

	private void generateFirstRound() throws DuplicateEntryException {
		final Round firstRound = new Round(1);
		final ArrayList<Team> teams = new ArrayList<>(this.teams);
		final Team fixedTeam = this.teams.get(Scheduler.FIXED_TEAM);
		
		teams.remove(Scheduler.FIXED_TEAM);
		for (int i = 1; i <= teams.size(); i++) {
			teams.add(0, fixedTeam);
			final Slot slot = new Slot(i);
			for (int j = 1; j <= teams.size() / 2; j++) {
				final Team home = teams.get(j - 1);
				final Team away = teams.get(teams.size() - j);
				final int index = (i - 1) * (teams.size() / 2) + j;
				final Game game = new Game(index, home, away);
				slot.addGame(game);
			}
			firstRound.addSlot(slot);
			teams.remove(0);
			Collections.rotate(teams, -1);
		}
		this.season.addRound(firstRound);
	}

	private void calculateBreaks(final HashMap<Team, Integer> breaksCounter, final Team team, final boolean home) throws ThreeInARowException{
		int breaks = breaksCounter.get(team);
		if (breaks >= 0) {
			if (home) {
				// if home team previously played home, points increased by 1
				breaksCounter.put(team, ++breaks);			
			} else {
				// if away team previously played home, points reset to -1
				breaksCounter.put(team, -1);				
			}
		} else {
			if (home) {
				// if home team previously played away, points reset to 1
				breaksCounter.put(team, 1);				
			} else {
				// if away team previously played away, points decreased by 1				
				breaksCounter.put(team, --breaks);
			}
		}
		if (Math.abs(breaksCounter.get(team)) == 3) {
			throw new ThreeInARowException("A team has reached three consecutive games in a row at home or away");
		}
	}
	
	private void normalizeFirstRound() throws ThreeInARowException, GameAlreadySettledException {
		final HashMap<Team, Integer> breaksCounter = new HashMap<>();

		for (final Team team : this.teams) {
			breaksCounter.put(team, 0);
		}
		for (final Slot slot : this.season.getRound(1).getSlots()) {
			for (final Game game : slot.getGames()) {
				final Team home = game.getHomeTeam();
				final Team away = game.getAwayTeam();
				// Can the home team become away? And CAN the away team become home?
				if (breaksCounter.get(home) > -2 && breaksCounter.get(away) < 2) {
					// Yes, both can. But should the home team become away? 
					if (breaksCounter.get(home) > 0) {
						// Yes, reverse
						game.resetTeams(away, home);
					} else {
						// No the home team should not become away. But should the away team become home?
						if (breaksCounter.get(away) < -1) {
							// Yes, reverse
							game.resetTeams(away, home);
						}
					}
				}
				this.calculateBreaks(breaksCounter, game.getHomeTeam(), true);
				this.calculateBreaks(breaksCounter, game.getAwayTeam(), false);				
			}
		}
	}

	private void generateAdditionalRounds() throws DuplicateEntryException {
		int currentRound = 1;
		
		while (currentRound < this.rounds) {
			final Round nextRound = new Round(currentRound + 1);
			for (final Slot slot : this.season.getRound(currentRound).getSlots()) {
				final int slotIndex = (currentRound) * (this.teams.size() - 1) + slot.getIndex();
				final Slot nextSlot = new Slot(slotIndex);
				for (final Game game : slot.getGames()) {
					final int gameIndex = nextRound.getIndex() * nextSlot.getIndex() + nextSlot.getGames().length - 1;
					final Game nextGame = new Game(gameIndex, game.getAwayTeam(), game.getHomeTeam());
					nextSlot.addGame(nextGame);
				}
				nextRound.addSlot(nextSlot);
			}
			this.season.addRound(nextRound);
			currentRound++;
		}
	}

}
