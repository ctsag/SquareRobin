package gr.daemon.squarerobin.model;

import java.util.Comparator;

public class PointsComparator implements Comparator<Team> {
	
	@Override
	public int compare(final Team first, final Team second) {
		return second.getPoints() - first.getPoints();
	}

}
