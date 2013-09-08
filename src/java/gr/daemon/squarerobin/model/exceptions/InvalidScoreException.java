package gr.daemon.squarerobin.model.exceptions;

public class InvalidScoreException extends RuntimeException {

	public InvalidScoreException() {
		super("");
	}
	
	public InvalidScoreException(final String message) {
		super(message);
	}

}
