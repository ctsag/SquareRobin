package gr.nothingness.squarerobin.model;

public enum State {
	
    ERR_ODD_CLUBS(0),
    ERR_EMPTY_INPUT(1),
    ERR_CLUBS_NOT_UNIQUE(2),
    ERR_HOME_AWAY(3),
    ERR_ROUNDS(4);
    
    private int value;

    private State(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        String message = "";

        switch (this) {
            case ERR_ODD_CLUBS :
                message = "Input list size must be an even number";
                break;
            case ERR_EMPTY_INPUT :
                message = "Input list cannot empty";
                break;
            case ERR_CLUBS_NOT_UNIQUE :
                message = "Input list elements are not unique";
                break;
            case ERR_HOME_AWAY :
                message = "Found 3 home/away matches in a row";
                break;
            case ERR_ROUNDS :
                message = "Rounds must be greater than zero";
                break;
            default :				
                break;
        }
        return message;
    }

}
