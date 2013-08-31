package gr.daemon.squarerobin.model;

public class InvalidScoreException extends RuntimeException {

	public InvalidScoreException() {
		super("");
	}
	
	public InvalidScoreException(final String message) {
		super(message);
	}

}
