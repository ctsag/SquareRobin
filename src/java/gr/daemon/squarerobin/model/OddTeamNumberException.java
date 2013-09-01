package gr.daemon.squarerobin.model;

public class OddTeamNumberException extends RuntimeException {

	public OddTeamNumberException() {
		super("");
	}
	
	public OddTeamNumberException(final String message) {
		super(message);
	}

}
