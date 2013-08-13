package gr.daemon.squarerobin.cli;

public enum State {
	
	OK(0),
	INVALID_ARGUMENTS(1),
	INSUFFICIENT_CLUBS(2),
	ODD_CLUBS(3);
	
	private int value;
	
	private State(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public String toString() {
		String message = "";
		
		switch (this) {
			case OK :				
				break;
			case INVALID_ARGUMENTS :
				message = "Invalid number of arguments";
				break;
			case INSUFFICIENT_CLUBS :
				message = "At least one club is required";
				break;
			case ODD_CLUBS :
				message = "An even number of clubs are is required";
				break;
			default :				
				break;
		}
		return message;
	}
	
}
