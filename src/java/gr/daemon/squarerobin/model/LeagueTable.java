package gr.daemon.squarerobin.model;

import java.util.ArrayList;
import java.util.Collections;

public class LeagueTable {
	
	private final ArrayList<Team> teams;

	protected LeagueTable(ArrayList<Team> teams) {
		this.teams = teams;
	}
	
	private Team[] toArray() {
		return this.teams.toArray(new Team[this.teams.size()]);
	}
	
	public Team[] getTeams() {
		return this.toArray();		
	}

	public Team[] sortByPoints() {
		PointsComparator comparator = new PointsComparator();
		Collections.sort(teams, comparator);
		return this.toArray();
	}

}
