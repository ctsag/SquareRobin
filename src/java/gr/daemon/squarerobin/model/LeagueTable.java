package gr.daemon.squarerobin.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
	
	public Team[] sortFormally() {
		final LeagueTable.FormalComparator comparator = new LeagueTable.FormalComparator();
		
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
	
	protected static class FormalComparator implements Comparator<Team> {
		
		@Override
		public int compare(final Team first, final Team second) {
			return second.getPoints() - first.getPoints();
		}

	}

}
