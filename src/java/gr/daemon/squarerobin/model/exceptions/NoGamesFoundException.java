package gr.daemon.squarerobin.model.exceptions;

public class NoGamesFoundException extends RuntimeException {

	public NoGamesFoundException() {
		super("");
	}
	
	public NoGamesFoundException(final String message) {
		super(message);
	}

}
