package gr.daemon.squarerobin.model;

public class GameNotSettledException extends RuntimeException {

	public GameNotSettledException() {
		super("");
	}
	
	public GameNotSettledException(final String message) {
		super(message);
	}

}
