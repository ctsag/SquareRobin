package gr.daemon.squarerobin.model.exceptions;

public class DuplicateGamesFoundException extends RuntimeException {

	public DuplicateGamesFoundException() {
		super("");
	}
	
	public DuplicateGamesFoundException(final String message) {
		super(message);
	}

}
