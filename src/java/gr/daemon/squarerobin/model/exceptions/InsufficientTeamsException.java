package gr.daemon.squarerobin.model.exceptions;

public class InsufficientTeamsException extends RuntimeException {

	public InsufficientTeamsException() {
		super("");
	}
	
	public InsufficientTeamsException(final String message) {
		super(message);
	}

}
