package gr.daemon.squarerobin.model;

public class ThreeInARowException extends RuntimeException {

	public ThreeInARowException() {
		super("");
	}
	
	public ThreeInARowException(final String message) {
		super(message);
	}

}
