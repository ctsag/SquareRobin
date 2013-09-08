package gr.daemon.squarerobin.model;

import java.util.Comparator;

public class PointsComparator implements Comparator<Team> {
	
	@Override
	public int compare(Team first, Team second) {
		return first.getPoints() - second.getPoints();
	}

}
