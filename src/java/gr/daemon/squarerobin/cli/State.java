package gr.daemon.squarerobin.cli;

public enum State {
	
	OK(0),
	UNSPECIFIED_ERROR(1),
	INVALID_ARGUMENTS(2),
	INSUFFICIENT_CLUBS(3),
	ODD_CLUBS(4);

	private int value;

	private State(final int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;		
	}

	@Override
	public String toString() {
		String message;

		if (this == UNSPECIFIED_ERROR) {
			message = "An error has occured";			
		} else if (this == INVALID_ARGUMENTS) {
			message = "Invalid arguments";
		} else if (this == INSUFFICIENT_CLUBS) {
			message = "At least one club is required";
		} else if (this == ODD_CLUBS) {
			message = "An even number of clubs are is required";
		} else {
			message = "";
		}
		return message;
	}

}
