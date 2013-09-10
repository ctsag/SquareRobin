package gr.daemon.squarerobin.model;

import java.util.ArrayList;
import java.util.Collections;

public class LeagueTable {
	
	private final ArrayList<Team> teams;

	protected LeagueTable(final ArrayList<Team> teams) {
		this.teams = teams;
	}
	
	private Team[] toArray() {
		return this.teams.toArray(new Team[this.teams.size()]);
	}
	
	public Team[] getTeams() {
		return this.toArray();		
	}

	public Team[] sortByPoints() {
		final PointsComparator comparator = new PointsComparator();
		
		Collections.sort(teams, comparator);		
		return this.toArray();
	}
	
	public Team[] sortFormally() {
		final PointsComparator comparator = new PointsComparator();
		
		Collections.sort(teams, comparator);		
		return this.toArray();
	}
	
	public void update() {
		this.sortFormally();
		int previousPoints = -1;
		int position = 1;
		for (Team team : teams) {
			team.setAbsolutePosition(position);
			if (team.getPoints() == previousPoints) {
				team.setRelativePosition("-");
			} else {
				team.setRelativePosition(String.valueOf(position));
			}
			previousPoints = team.getPoints();
			position++;
		}
	}

}
