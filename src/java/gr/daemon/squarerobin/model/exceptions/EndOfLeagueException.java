package gr.daemon.squarerobin.model.exceptions;

public class EndOfLeagueException extends RuntimeException {

	public EndOfLeagueException() {
		super("");
	}
	
	public EndOfLeagueException(final String message) {
		super(message);
	}

}
