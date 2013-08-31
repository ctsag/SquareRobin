package gr.daemon.squarerobin.model;

public class InexistentEntryException extends RuntimeException {

	public InexistentEntryException() {
		super();
	}
	
	public InexistentEntryException(String message) {
		super(message);
	}

}
