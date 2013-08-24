package gr.daemon.squarerobin.model;

public class Team {

	private String name = "";
	private int points = 0;
	private int position = 0;
	private int goalsFor = 0;
	private int goalsAgainst = 0;
	private int goalAverage = 0;
	
	public Team(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getGoalsFor() {
		return goalsFor;
	}

	public void setGoalsFor(int goalsFor) {
		this.goalsFor = goalsFor;
	}

	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	public int getGoalAverage() {
		return goalAverage;
	}

	public void setGoalAverage(int goalAverage) {
		this.goalAverage = goalAverage;
	}

}
