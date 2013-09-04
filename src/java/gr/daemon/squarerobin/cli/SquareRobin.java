package gr.daemon.squarerobin.cli;

import gr.daemon.squarerobin.model.DuplicateEntryException;
import gr.daemon.squarerobin.model.DuplicateTeamsException;
import gr.daemon.squarerobin.model.Game;
import gr.daemon.squarerobin.model.GameAlreadySettledException;
import gr.daemon.squarerobin.model.InsufficientTeamsException;
import gr.daemon.squarerobin.model.InvalidRoundsException;
import gr.daemon.squarerobin.model.OddTeamNumberException;
import gr.daemon.squarerobin.model.Round;
import gr.daemon.squarerobin.model.Scheduler;
import gr.daemon.squarerobin.model.Season;
import gr.daemon.squarerobin.model.Slot;
import gr.daemon.squarerobin.model.ThreeInARowException;
import java.util.ArrayList;
import java.util.Arrays;
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
	public static final String APPLICATION_VERSION = "v0.5.0";
	private final ArrayList<String> teams = new ArrayList<>();
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
		final Option noSlots = new Option("noslots", "do not print slot number");
		final Option noGames = new Option("nogames", "do not print game number");		
		final Option version = new Option("version", "print the version information and exit");
		final Option help = new Option("help", "print this message");

		this.options.addOption(noRounds);
		this.options.addOption(noSlots);
		this.options.addOption(noGames);		
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
			default :
				if (!message.isEmpty()) {
					message = " : " + message;
				}
				System.err.println(state.toString() + message);
				System.exit(state.getValue());
				break;
		}
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
			this.teams.add(club);
		}
		input.close();
	}
	
	private String tabify(final int count) {
		String tabs = "";
		for (int i = 0; i < count; i++) {
			tabs += "\t";
		}
		return tabs;
	}

	private void printDraw() {
		int tabs = 0;
		try {
			final String[] teams = this.teams.toArray(new String[this.teams.size()]);
			final Scheduler scheduler = new Scheduler("2013", teams);
			final Season season = scheduler.getSeason();			
			for (final Round round : season.getRounds()) {
				if (!this.cli.hasOption("norounds")) {
					System.out.println(this.tabify(tabs++) + "Round " + round.getIndex());
				}
				for (final Slot slot : round.getSlots()) {
					if (!this.cli.hasOption("noslots")) {
						System.out.println(this.tabify(tabs++) + "Slot " + slot.getIndex());
					}
					for (final Game game : slot.getGames()) {
						if (!this.cli.hasOption("nogames")) {							
							System.out.println(this.tabify(tabs++) + "Game" + game.getIndex());
						}
						System.out.println(this.tabify(tabs) + game.getHomeTeam().getName() + " - " + game.getAwayTeam().getName());
						if (!this.cli.hasOption("nogames")) {
							tabs--;
						}
					}
					if (!this.cli.hasOption("noslots")) {
						tabs--;
					}
				}
				if (!this.cli.hasOption("norounds")) {
					tabs--;
				}
			}
		} catch(DuplicateTeamsException | InsufficientTeamsException | OddTeamNumberException | InvalidRoundsException | ThreeInARowException | DuplicateEntryException | GameAlreadySettledException e) {
			handleError(State.SCHEDULE_ERROR, e.getMessage());
		}
	}

	public static void main(final String[] args) {
		final SquareRobin robin = new SquareRobin();

		robin.constructOptions();
		robin.parseOptions(args);
	}

}
