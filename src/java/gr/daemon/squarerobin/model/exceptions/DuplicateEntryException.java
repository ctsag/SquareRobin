package gr.daemon.squarerobin.model.exceptions;

public class DuplicateEntryException extends RuntimeException {

	public DuplicateEntryException() {
		super("");
	}
	
	public DuplicateEntryException(final String message) {
		super(message);
	}

}
