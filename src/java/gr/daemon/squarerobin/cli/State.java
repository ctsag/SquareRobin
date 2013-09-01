package gr.daemon.squarerobin.cli;

public enum State {
	
	OK(0),
	SCHEDULE_ERROR(1),
	INVALID_ARGUMENTS(2);

	private int value;

	private State(final int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;		
	}

	@Override
	public String toString() {
		String message = "";

		switch (this) {
			case OK :                               
				break;
			case SCHEDULE_ERROR :
				message = "An error has occured while generating the schedule";
				break;
			case INVALID_ARGUMENTS :
				message = "Invalid arguments";
				break;
			default :                               
				break;
		}
		return message;
	}

}
