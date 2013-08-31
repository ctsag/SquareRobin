package gr.daemon.squarerobin.model;

public class Team {

	public static final int POINTS_FOR_WIN = 3;
	public static final int POINTS_FOR_DRAW = 1;
	public static final int POINTS_FOR_LOSS = 0;
	private final String name;
	private int homeGoalsFor;
	private int homeGoalsAgainst;
	private int awayGoalsFor;
	private int awayGoalsAgainst;
	private int homeGamesPlayed;
	private int awayGamesPlayed;
	private int homeWins;
	private int awayWins;
	private int homeDraws;
	private int awayDraws;
	private int homeLosses;
	private int awayLosses;
	private int homePoints;
	private int awayPoints;
	
	public Team(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getHomeGoalsFor() {
		return this.homeGoalsFor;
	}

	public int getHomeGoalsAgainst() {
		return this.homeGoalsAgainst;
	}

	public int getAwayGoalsFor() {
		return this.awayGoalsFor;
	}

	public int getAwayGoalsAgainst() {
		return this.awayGoalsAgainst;
	}

	public int getHomeGamesPlayed() {
		return this.homeGamesPlayed;
	}

	public int getAwayGamesPlayed() {
		return this.awayGamesPlayed;
	}

	public int getHomeWins() {
		return this.homeWins;
	}

	public int getAwayWins() {
		return this.awayWins;
	}

	public int getHomeDraws() {
		return this.homeDraws;
	}

	public int getAwayDraws() {
		return this.awayDraws;
	}

	public int getHomeLosses() {
		return this.homeLosses;
	}

	public int getAwayLosses() {
		return this.awayLosses;
	}

	public int getHomePoints() {
		return this.homePoints;
	}

	public int getAwayPoints() {
		return this.awayPoints;
	}

	public int getGoalsFor() {
		return this.homeGoalsFor + this.awayGoalsFor;
	}
	
	public int getGoalsAgainst() {
		return this.homeGoalsAgainst + this.awayGoalsAgainst;
	}
	
	public int getHomeGoalAverage() {
		return this.homeGoalsFor - this.homeGoalsAgainst;
	}
	
	public int getAwayGoalAverage() {
		return this.awayGoalsFor - this.awayGoalsAgainst;
	}
	
	public int getGoalAverage() {
		return this.getGoalsFor() - this.getGoalsAgainst();
	}
	
	public int getWins() {
		return this.homeWins + this.awayWins;
	}
	
	public int getDraws() {
		return this.homeDraws + this.awayDraws;
	}

	public int getLosses() {
		return this.homeLosses + this.awayLosses;
	}
	
	public int getGamesPlayed() {
		return this.homeGamesPlayed + this.awayGamesPlayed;
	}
	
	public int getPoints() {
		return this.homePoints + this.awayPoints;
	}
	
	public void win(final int goalsFor, final int goalsAgainst, final boolean homeGame) throws InvalidScoreException {
		if (goalsFor > goalsAgainst) {
			if (homeGame) {
				this.homeGamesPlayed++;
				this.homeWins++;
				this.homeGoalsFor += goalsFor;
				this.homeGoalsAgainst += goalsAgainst;
				this.homePoints += Team.POINTS_FOR_WIN;
			} else {
				this.awayGamesPlayed++;
				this.awayWins++;
				this.awayGoalsFor += goalsFor;
				this.awayGoalsAgainst += goalsAgainst;
				this.awayPoints += Team.POINTS_FOR_WIN;
			}
		} else {
			throw new InvalidScoreException("Goals against cannot be more than the goals for on a win scenario");
		}
	}
	
	public void draw(final int goalsFor, final int goalsAgainst, final boolean homeGame) throws InvalidScoreException {
		if (goalsFor == goalsAgainst) {
			if (homeGame) {
				this.homeGamesPlayed++;
				this.homeWins++;
				this.homeGoalsFor += goalsFor;
				this.homeGoalsAgainst += goalsAgainst;
				this.homePoints += Team.POINTS_FOR_DRAW;
			} else {
				this.awayGamesPlayed++;
				this.awayWins++;
				this.awayGoalsFor += goalsFor;
				this.awayGoalsAgainst += goalsAgainst;
				this.awayPoints += Team.POINTS_FOR_DRAW;
			}
		} else {
			throw new InvalidScoreException("Goals for and goals against must be equal on a draw scenario");
		}
	}
	
	public void lose(final int goalsFor, final int goalsAgainst, final boolean homeGame) throws InvalidScoreException {
		if (goalsFor < goalsAgainst) {
			if (homeGame) {
				this.homeGamesPlayed++;
				this.homeWins++;
				this.homeGoalsFor += goalsFor;
				this.homeGoalsAgainst += goalsAgainst;
				this.homePoints += Team.POINTS_FOR_LOSS;
			} else {
				this.awayGamesPlayed++;
				this.awayWins++;
				this.awayGoalsFor += goalsFor;
				this.awayGoalsAgainst += goalsAgainst;
				this.awayPoints += Team.POINTS_FOR_LOSS;
			}
		} else {
			throw new InvalidScoreException("Goals for cannot be more than the goals against on a loss scenario");
		}
	}
	
}
