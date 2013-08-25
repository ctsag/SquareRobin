package gr.daemon.squarerobin.model;

public class Team {

	private String name = "";
	private int points;
	private int position;
	private int goalsFor;
	private int goalsAgainst;
	private int goalAverage;

	public Team(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(final int points) {
		this.points = points;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(final int position) {
		this.position = position;
	}

	public int getGoalsFor() {
		return goalsFor;
	}

	public void setGoalsFor(final int goalsFor) {
		this.goalsFor = goalsFor;
		this.calculateGoalAverage();
	}

	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	public void setGoalsAgainst(final int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
		this.calculateGoalAverage();
	}

	public int getGoalAverage() {
		return goalAverage;
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
