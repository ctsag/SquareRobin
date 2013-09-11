package gr.daemon.squarerobin.model;

import java.util.Comparator;

public class GoalAverageComparator implements Comparator<Team> {
	
	@Override
	public int compare(final Team first, final Team second) {
		return second.getGoalAverage() - first.getGoalAverage();
	}

}
