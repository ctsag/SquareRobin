package gr.daemon.squarerobin.model;

public class GameAlreadySettledException extends RuntimeException {

	public GameAlreadySettledException() {
		super("");
	}
	
	public GameAlreadySettledException(final String message) {
		super(message);
	}

}
