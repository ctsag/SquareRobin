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

	private void calculateGoalAverage() {
		this.goalAverage = this.goalsFor - this.goalsAgainst;
	}

}
