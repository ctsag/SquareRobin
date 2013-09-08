package gr.daemon.squarerobin.model.exceptions;

public class SlotNotFoundException extends RuntimeException {

	public SlotNotFoundException() {
		super("");
	}
	
	public SlotNotFoundException(final String message) {
		super(message);
	}

}
