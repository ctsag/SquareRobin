package gr.daemon.squarerobin.model.exceptions;

public class InexistentEntryException extends RuntimeException {

	public InexistentEntryException() {
		super("");
	}
	
	public InexistentEntryException(final String message) {
		super(message);
	}

}
