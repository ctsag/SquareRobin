package gr.daemon.squarerobin.model;

public class DuplicateTeamsException extends RuntimeException {

	public DuplicateTeamsException() {
		super("");
	}
	
	public DuplicateTeamsException(final String message) {
		super(message);
	}

}
