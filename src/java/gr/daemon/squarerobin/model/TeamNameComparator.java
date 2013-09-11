package gr.daemon.squarerobin.model;

import java.util.Comparator;

public class TeamNameComparator implements Comparator<Team> {
	
	@Override
	public int compare(final Team first, final Team second) {
		return first.getName().compareToIgnoreCase(second.getName());
	}

}
