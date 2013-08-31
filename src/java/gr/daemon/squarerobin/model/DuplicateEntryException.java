package gr.daemon.squarerobin.model;

public class DuplicateEntryException extends RuntimeException {

	public DuplicateEntryException() {
		super();
	}
	
	public DuplicateEntryException(String message) {
		super(message);
	}

}
