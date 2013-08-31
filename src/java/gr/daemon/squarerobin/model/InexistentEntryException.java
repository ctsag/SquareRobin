package gr.daemon.squarerobin.model;

public class InexistentEntryException extends RuntimeException {

	public InexistentEntryException() {
		super("");
	}
	
	public InexistentEntryException(final String message) {
		super(message);
	}

}
