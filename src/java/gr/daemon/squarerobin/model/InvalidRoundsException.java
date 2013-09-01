package gr.daemon.squarerobin.model;

public class InvalidRoundsException extends RuntimeException {

	public InvalidRoundsException() {
		super("");
	}
	
	public InvalidRoundsException(final String message) {
		super(message);
	}

}
