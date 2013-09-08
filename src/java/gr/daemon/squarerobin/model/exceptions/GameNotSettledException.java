package gr.daemon.squarerobin.model.exceptions;

public class GameNotSettledException extends RuntimeException {

	public GameNotSettledException() {
		super("");
	}
	
	public GameNotSettledException(final String message) {
		super(message);
	}

}
