package gr.nothingness.squarerobin.cli;

import gr.nothingness.squarerobin.model.Scheduler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class SquareRobin {	
	
	public static final String APPLICATION_NAME = "SquareRobin";
	public static final String APPLICATION_VERSION = "v0.3.0"; 
	private State state = State.OK;
	private ArrayList<String> clubs = new ArrayList<>();
	private CommandLine cli;
	private Options options = new Options();	
	
	private String joinArray(String[] array) {
		String joined = "";
		for (String string : array) {
			joined += string + " ";
		}
		return joined;
	}

	private void constructOptions() {
		Option noRounds = new Option("norounds", "do not print round number");
		Option noDays = new Option("nodays", "do not print day number");		
		Option only = OptionBuilder.withArgName("club").hasArg().withDescription("only display schedule for this club").create("only");		
		Option version = new Option("version", "print the version information and exit");
		Option help = new Option("help", "print this message");		

		this.options.addOption(noRounds);
		this.options.addOption(noDays);
		this.options.addOption(only);
		this.options.addOption(version);
		this.options.addOption(help);
	}
	
	private void parseOptions(String[] args) {
		CommandLineParser parser = new GnuParser();		
		
		try {
			this.cli = parser.parse(this.options, args);
			ArrayList<String> options = new ArrayList<>();
			for (Option option : this.cli.getOptions()) {
				options.add("-" + option.getOpt());
				options.add(option.getValue());
			}
			if (options.containsAll(Arrays.asList(args))) {
				if (this.cli.hasOption("version")) {
					this.printVersion();
				} else if (this.cli.hasOption("help")) {
					this.printUsage();
				} else {
					this.getInput();
					this.printDraw();
				}
			} else {
				this.handleError(State.INVALID_ARGUMENTS, this.joinArray(args));
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
			HashMap<Integer, TreeMap<Integer, ArrayList<String[]>>> schedule = scheduler.getSchedule();
			for (int round : schedule.keySet()) {
				if (!this.cli.hasOption("norounds")) {
					System.out.println("Round " + round);
				}
				for (int day : schedule.get(round).keySet()) {					
					if (!this.cli.hasOption("nodays")) {
						System.out.println("Day " + day);
					}
					for (String[] pair : schedule.get(round).get(day)) {
						if (this.cli.getOptionValue("only") != null) {
							if (Arrays.asList(pair).contains(this.cli.getOptionValue("only"))) {
								System.out.println(pair[0] + " - " + pair[1]);
							}
						} else {
							System.out.println(pair[0] + " - " + pair[1]);
						}
					}
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
