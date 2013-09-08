package gr.daemon.squarerobin.model.exceptions;

public class BreaksLimitException extends RuntimeException {

	public BreaksLimitException() {
		super("");
	}
	
	public BreaksLimitException(final String message) {
		super(message);
	}

}
