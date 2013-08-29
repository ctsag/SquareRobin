package gr.daemon.squarerobin.model;

public class Team {

	private final String name;
	private int points;
	private int games;
	private int goalsFor;
	private int goalsAgainst;
	private int goalAverage;

	public Team(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getPoints() {
		return this.points;
	}

	public int getGames() {
		return this.games;
	}

	public int getGoalsFor() {
		return this.goalsFor;
	}

	public int getGoalsAgainst() {
		return this.goalsAgainst;
	}

	public int getGoalAverage() {
		return this.goalAverage;
	}

	public void win(final int goalsFor, final int goalsAgainst) {
		this.points += 3;
		this.addGoals(goalsFor, goalsAgainst);
	}

	public void lose(final int goalsFor, final int goalsAgainst) {		
		this.addGoals(goalsFor, goalsAgainst);
	}

	public void draw(final int goalsFor, final int goalsAgainst) {
		this.points += 1;
		this.addGoals(goalsFor, goalsAgainst);
	}

	private void addGoals(final int goalsFor, final int goalsAgainst) {
		this.goalsFor += goalsFor;
		this.goalsAgainst += goalsAgainst;
		this.calculateGoalAverage();
	}

	private void calculateGoalAverage() {
		this.goalAverage = this.goalsFor - this.goalsAgainst;
	}

}
