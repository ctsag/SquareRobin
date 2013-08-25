package gr.daemon.squarerobin.cli;

import gr.daemon.squarerobin.engine.Scheduler;
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
	private final ArrayList<String> clubs = new ArrayList<>();
	private CommandLine cli;
	private final Options options = new Options();

	private String joinArray(final String[] array) {
		String joined = "";
		for (final String string : array) {
			joined += string + " ";
		}
		return joined;
	}

	private void constructOptions() {
		final Option noRounds = new Option("norounds", "do not print round number");
		final Option noDays = new Option("nodays", "do not print day number");
		final Option only = OptionBuilder.withArgName("club").hasArg().withDescription("only display schedule for this club").create("only");
		final Option version = new Option("version", "print the version information and exit");
		final Option help = new Option("help", "print this message");

		this.options.addOption(noRounds);
		this.options.addOption(noDays);
		this.options.addOption(only);
		this.options.addOption(version);
		this.options.addOption(help);
	}

	private void parseOptions(final String[] args) {
		final CommandLineParser parser = new GnuParser();

		try {
			this.cli = parser.parse(this.options, args);
			final ArrayList<String> options = new ArrayList<>();
			for (final Option option : this.cli.getOptions()) {
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
		final HelpFormatter formatter = new HelpFormatter();

		formatter.printHelp(SquareRobin.APPLICATION_NAME, this.options);
	}

	private void printVersion() {
		System.out.println(SquareRobin.APPLICATION_VERSION);
	}

	private void handleError(final State state, String message) {
		switch (state) {
			case OK :
				break;
			case INVALID_ARGUMENTS :				
				System.err.println(state.toString() + " : " + message + "\n");
				this.printUsage();
				System.exit(state.getValue());
				break;
			case UNSPECIFIED_ERROR :
			case INSUFFICIENT_CLUBS :
			case ODD_CLUBS :
				if (message.isEmpty()) {
					message = " : " + message;
				}
				System.err.println(state.toString() + message);
				System.exit(state.getValue());
			default :
				break;
		}
	}

	private void handleError(final State state) {
		this.handleError(state, "");
	}

	private void getInput() {
		final Scanner input = new Scanner(System.in);

		String club;
		System.out.println("Please enter your clubs, one club per line. To terminate input, please enter a blank line.");
		while (true) {
			club = input.nextLine();
			if (club.isEmpty()) {
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
			final Scheduler scheduler = new Scheduler(this.clubs);
			final HashMap<Integer, TreeMap<Integer, ArrayList<String[]>>> schedule = scheduler.getSchedule();
			for (final int round : schedule.keySet()) {
				if (!this.cli.hasOption("norounds")) {
					System.out.println("Round " + round);
				}
				for (final int day : schedule.get(round).keySet()) {
					if (!this.cli.hasOption("nodays")) {
						System.out.println("Day " + day);
					}
					for (final String[] pair : schedule.get(round).get(day)) {
						if (this.cli.getOptionValue("only") == null) {
							System.out.println(pair[0] + " - " + pair[1]);
						} else {
							if (Arrays.asList(pair).contains(this.cli.getOptionValue("only"))) {
								System.out.println(pair[0] + " - " + pair[1]);
							}
						}
					}
				}
			}
		} catch(IllegalArgumentException e) {
			handleError(State.UNSPECIFIED_ERROR, e.getMessage());
		}
	}

	public static void main(final String[] args) {
		final SquareRobin robin = new SquareRobin();

		robin.constructOptions();
		robin.parseOptions(args);
	}

}
