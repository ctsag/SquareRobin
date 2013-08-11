package gr.daemon.squarerobin.cli;

import gr.daemon.squarerobin.cli.State;
import gr.daemon.squarerobin.model.Scheduler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CLI {	
	
	private State state = State.OK;
	private ArrayList<String> clubs = new ArrayList<>();
	
	private void printUsage() {
		System.out.println("Please run the CLI command without any arguments");
	}
	
	private void handleError(State state) {
		this.state = state;
		switch (this.state) {
			case OK :
				break;
			case INVALID_ARGUMENTS :
				this.printUsage();
				System.exit(this.state.getValue());
				break;
			case INSUFFICIENT_CLUBS :
			case ODD_CLUBS :
				System.out.println(this.state.toString());
				System.exit(this.state.getValue());
			default :
				break;
		}
	}
	
	private boolean validateArgs(String[] args) {
		if (args.length > 0) {
			handleError(State.INVALID_ARGUMENTS);
			return false;
		}
		this.state = State.OK;
		return true;
	}
	
	private void getInput() {
		Scanner input = new Scanner(System.in);
		
		String club;
		System.out.println("Please enter your clubs, one club per line. To terminate input, please enter a blank line.");
		while (true) {
			club = input.nextLine();
			if (club.equals("")) {
				break;
			}			
			this.clubs.add(club);			
		}
		input.close();
		if (this.clubs.size() == 0) {			
			handleError(State.INSUFFICIENT_CLUBS);
		} else if (this.clubs.size() % 2 == 1) {
			handleError(State.ODD_CLUBS);
		}		
	}
	
	private void printDraw() {
		Scheduler scheduler = new Scheduler(this.clubs);
		HashMap<Integer, ArrayList<String[]>> schedule = scheduler.getSchedule();	
		
		for (int day : schedule.keySet()) {
			System.out.println("Day " + day);
			for (String[] pair : schedule.get(day)) {
				System.out.println(pair[0] + " - " + pair[1]);
			}			
		}
	}
	
	public static void main(String[] args) {
		CLI robin = new CLI();
		
		if (robin.validateArgs(args)) {
			robin.getInput();
			robin.printDraw();			
		}
	}
	
}
