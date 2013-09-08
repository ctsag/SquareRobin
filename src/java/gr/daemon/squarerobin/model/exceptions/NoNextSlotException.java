package gr.daemon.squarerobin.model.exceptions;

public class NoNextSlotException extends RuntimeException {

	public NoNextSlotException() {
		super("");
	}
	
	public NoNextSlotException(final String message) {
		super(message);
	}

}
