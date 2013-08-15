package gr.daemon.squarerobin.cli;

import gr.daemon.squarerobin.cli.State;
import gr.daemon.squarerobin.model.Scheduler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class SquareRobin {	
	
	public static final String APPLICATION_NAME = "SquareRobin";
	public static final String APPLICATION_VERSION = "0.3"; 
	private State state = State.OK;
	private ArrayList<String> clubs = new ArrayList<>();
	private Options options = new Options();
	private boolean noDays = false;
	
	private String joinArray(String[] array) {
		String joined = "";
		for (String string : array) {
			joined += string + " ";
		}
		return joined;
	}

	private void constructOptions() {
		Option noDays = new Option("nodays", "do not print day number");
		Option version = new Option("version", "print the version information and exit");
		Option help = new Option("help", "print this message");		

		this.options.addOption(noDays);
		this.options.addOption(version);
		this.options.addOption(help);
	}
	
	private void parseOptions(String[] args) {
		CommandLineParser parser = new GnuParser();		
		
		try {
			CommandLine cmd = parser.parse(this.options, args);
			if (cmd.hasOption("version")) {
				this.printVersion();
			} else if (cmd.hasOption("help")) {
				this.printUsage();
			} else {
				if (cmd.hasOption("nodays")) {
					this.noDays = true;
				} else {
					if (args.length > 0) {						
						this.handleError(State.INVALID_ARGUMENTS, this.joinArray(args));
					}
				}
				this.getInput();
				this.printDraw();
			}
		} catch(ParseException e) {			
			this.handleError(State.INVALID_ARGUMENTS, this.joinArray(args));
		}
	}
	
	private void printUsage() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(SquareRobin.APPLICATION_NAME, this.options);
	}
	
	private void printVersion() {
		System.out.println(SquareRobin.APPLICATION_VERSION);
	}

	private void handleError(State state, String message) {
		this.state = state;
		switch (this.state) {
			case OK :
				break;
			case INVALID_ARGUMENTS :				
				System.err.println(this.state.toString() + " : " + message + "\n");
				this.printUsage();
				System.exit(this.state.getValue());
				break;
			case UNSPECIFIED_ERROR :
			case INSUFFICIENT_CLUBS :
			case ODD_CLUBS :
				if (!message.equals("")) {
					message = " : " + message;
				}
				System.err.println(this.state.toString() + message);
				System.exit(this.state.getValue());
			default :
				break;
		}		
	}
	
	private void handleError(State state) {
		this.handleError(state, "");
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
		try {
			Scheduler scheduler = new Scheduler(this.clubs);
			HashMap<Integer, ArrayList<String[]>> schedule = scheduler.getSchedule();
			for (int day : schedule.keySet()) {
				if (!this.noDays) {
					System.out.println("Day " + day);
				}
				for (String[] pair : schedule.get(day)) {
					System.out.println(pair[0] + " - " + pair[1]);
				}			
			}
		} catch(IllegalArgumentException e) {
			handleError(State.UNSPECIFIED_ERROR, e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		SquareRobin robin = new SquareRobin();
		
		robin.constructOptions();
		robin.parseOptions(args);
	}
	
}
