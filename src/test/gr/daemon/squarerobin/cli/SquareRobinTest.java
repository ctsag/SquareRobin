package gr.daemon.squarerobin.cli;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;
import static org.junit.Assert.*;
import org.junit.Test;

public class SquareRobinTest {
	
	private static final String NL = "\n";
	private static final String EXIT = SquareRobinTest.NL + SquareRobinTest.NL;
	private static final String VS = " - ";	
	private static final String SYSTEM_EXIT_MESSAGE = "System.exit() expected";
	private static final String TEAM_A = "Panathinaikos";
	private static final String TEAM_B = "Olympiakos";
	private static final String TEAM_C = "AEK";
	private static final String TEAM_D = "PAOK";
	private static final String[] TEAM_SET_EVEN = {SquareRobinTest.TEAM_A, SquareRobinTest.TEAM_B, SquareRobinTest.TEAM_C, SquareRobinTest.TEAM_D};
	private static final String[] TEAM_SET_ODD = {SquareRobinTest.TEAM_A, SquareRobinTest.TEAM_B, SquareRobinTest.TEAM_C};
	private static final String[] TEAM_SET_NOT_UNIQUE = {SquareRobinTest.TEAM_A, SquareRobinTest.TEAM_A, SquareRobinTest.TEAM_B, SquareRobinTest.TEAM_C};
	private static final int NO_OCCURENCES = 0;
	private static final int SINGLE_OCCURENCE = 1;

	private class ExitException extends SecurityException {

		final private int exitCode;

		public ExitException(final int code) {
			super("System.exit(" + code + ") detected");
			this.exitCode = code;
		}

		public int getExitCode() {
			return this.exitCode;
		}

	}

	private class NoExitSecurityManager extends SecurityManager {

		@Override
		public void checkPermission(final Permission perm) {
			// allow anything.
		}

		@Override
		public void checkPermission(final Permission perm, final Object context) {
			// allow anything.
		}

		@Override
		public void checkExit(final int status) {
			super.checkExit(status);
			throw new ExitException(status);
		}

	}

	private int countOccurences(final String string, final String substring) {
		int count = 0;
		int lastIndex = 0;

		while (lastIndex != -1) {
			lastIndex = string.indexOf(substring, lastIndex);
			if (lastIndex != -1) {
				count++;
				lastIndex += substring.length();
			}
		}
		return count;
	}

	@Test
	public void testMainProducesFullOutputWithDefaultInput() {
		// Fixture
		final String[] arguments = {};
		final ByteArrayOutputStream systemOut = new ByteArrayOutputStream();		
		final String[] teams = SquareRobinTest.TEAM_SET_EVEN;
		String systemIn = "";
		for (final String team : teams) {
			systemIn += team + SquareRobinTest.NL;
		}
		systemIn += SquareRobinTest.NL;
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));
		System.setOut(new PrintStream(systemOut));
		final int expected = (teams.length - 1) * 2;
		
		// Match
		SquareRobin.main(arguments);
		
		// Assertion
		for (final String team : teams) {
			final int homeOccurences = this.countOccurences(systemOut.toString(), team + SquareRobinTest.VS);
			final int awayOccurences = this.countOccurences(systemOut.toString(), SquareRobinTest.VS + team);
			final int totalOccurences = homeOccurences  + awayOccurences;
			assertEquals(expected, totalOccurences);
		}
	}
	
	@Test
	public void testMainDoesNotDisplaySlotLinesWhenTheNoDaysSwitchIsOn() {
		// Fixture
		final String[] arguments = {"-noslots"};
		final ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
		final String[] teams = SquareRobinTest.TEAM_SET_EVEN;
		String systemIn = "";
		for (final String team : teams) {
			systemIn += team + SquareRobinTest.NL;
		}
		systemIn += SquareRobinTest.NL;
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));
		System.setOut(new PrintStream(systemOut));
		final int expected = SquareRobinTest.NO_OCCURENCES;

		// Match
		SquareRobin.main(arguments);

		// Assertion
		for (int i = 1; i < teams.length; i++) {
			final int slotOccurences = this.countOccurences(systemOut.toString(), "Slot " + i);
			assertEquals(expected, slotOccurences);
		}
	}

	@Test
	public void testMainDoesNotDisplayRoundLinesWhenTheNoRoundsSwitchIsOn() {
		// Fixture
		final String[] arguments = {"-norounds"};
		final ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
		final String[] teams = SquareRobinTest.TEAM_SET_EVEN;
		String systemIn = "";
		for (final String team : teams) {
			systemIn += team + SquareRobinTest.NL;
		}
		systemIn += SquareRobinTest.NL;
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));
		System.setOut(new PrintStream(systemOut));
		final int expected = SquareRobinTest.NO_OCCURENCES;

		// Match
		SquareRobin.main(arguments);

		// Assertion
		for (int i = 1; i <= teams.length; i++) {
			final int roundOccurences = this.countOccurences(systemOut.toString(), "Round " + i); 
			assertEquals(expected, roundOccurences);
		}
	}
	
	@Test
	public void testMainDoesNotDisplayGameLinesWhenTheNoGamesSwitchIsOn() {
		// Fixture
		final String[] arguments = {"-nogames"};
		final ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
		final String[] teams = SquareRobinTest.TEAM_SET_EVEN;
		String systemIn = "";
		for (final String team : teams) {
			systemIn += team + SquareRobinTest.NL;
		}
		systemIn += SquareRobinTest.NL;
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));
		System.setOut(new PrintStream(systemOut));
		final int expected = SquareRobinTest.NO_OCCURENCES;

		// Match
		SquareRobin.main(arguments);

		// Assertion
		for (int i = 1; i <= teams.length; i++) {
			final int gameOccurences = this.countOccurences(systemOut.toString(), "Game " + i); 
			assertEquals(expected, gameOccurences);
		}
	}
	
	@Test
	public void testMainDisplaysNeitherRoundNorSlotLinesWhenTheBothSwitchesAreOn() {
		// Fixture
		final String[] arguments = {"-norounds", "-noslots"};
		final ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
		final String[] teams = SquareRobinTest.TEAM_SET_EVEN;
		String systemIn = "";
		for (final String team : teams) {
			systemIn += team + SquareRobinTest.NL;
		}
		systemIn += SquareRobinTest.NL;
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));
		System.setOut(new PrintStream(systemOut));
		final int expected = SquareRobinTest.NO_OCCURENCES;

		// Match
		SquareRobin.main(arguments);

		// Assertion
		for (int i = 1; i <= teams.length; i++) {
			final int roundOccurences = this.countOccurences(systemOut.toString(), "Round " + i);
			final int slotOccurences = this.countOccurences(systemOut.toString(), "Slot " + i);
			assertEquals(expected, roundOccurences + slotOccurences);
		}
	}

	@Test
	public void testMainProducesUsageOutputWhenTheHelpSwitchIsOn() {
		// Fixture
		final String[] arguments = {"-help"};
		final ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(systemOut));
		final int expected = SquareRobinTest.SINGLE_OCCURENCE;

		// Match
		SquareRobin.main(arguments);

		// Assertion
		final int usageOccurences = this.countOccurences(systemOut.toString(), "usage: " + SquareRobin.APPLICATION_NAME); 
		assertEquals(expected, usageOccurences);
	}

	@Test
	public void testMainProducesVersionOutputWhenTheVersionSwitchIsOn() {
		// Fixture
		final String[] arguments = {"-version"};
		final ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(systemOut));
		final int expected = SquareRobinTest.SINGLE_OCCURENCE;

		// Match
		SquareRobin.main(arguments);

		// Assertion
		final int versionOccurences = this.countOccurences(systemOut.toString(), SquareRobin.APPLICATION_VERSION); 
		assertEquals(expected, versionOccurences);
	}

	@Test
	public void testMainExitsWhenTheNumberOfClubsIsOdd() {
		// Fixture
		final String[] arguments = {};
		final String[] teams = SquareRobinTest.TEAM_SET_ODD;
		String systemIn = "";
		for (final String team : teams) {
			systemIn += team + SquareRobinTest.NL;
		}
		systemIn += SquareRobinTest.NL;
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));
		final int expected = State.SCHEDULE_ERROR.getValue();

		// Match		
		try {
			System.setSecurityManager(new NoExitSecurityManager());
			SquareRobin.main(arguments);
			fail(SquareRobinTest.SYSTEM_EXIT_MESSAGE);
		} catch(ExitException e) {
			// Assertion
			assertEquals(expected, e.getExitCode());
		} finally {
			System.setSecurityManager(null);
		}
	}

	@Test
	public void testMainExitsWhenThereAreInsufficientClubs() {
		// Fixture
		final String[] arguments = {};
		final String systemIn = SquareRobinTest.EXIT;
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));
		final int expected = State.SCHEDULE_ERROR.getValue();

		// Match		
		try {
			System.setSecurityManager(new NoExitSecurityManager());
			SquareRobin.main(arguments);
			fail(SquareRobinTest.SYSTEM_EXIT_MESSAGE);
		} catch(ExitException e) {
			// Assertion
			assertEquals(expected, e.getExitCode());
		} finally {
			System.setSecurityManager(null);
		}
	}

	@Test
	public void testMainExitsWhenInvalidNonGNUArgumentsAreDetected() {
		// Fixture
		final String[] arguments = {"invalid arguments"};
		final int expected = State.INVALID_ARGUMENTS.getValue();
		
		// Match		
		try {
			System.setSecurityManager(new NoExitSecurityManager());
			SquareRobin.main(arguments);
			fail(SquareRobinTest.SYSTEM_EXIT_MESSAGE);
		} catch(ExitException e) {
			// Assertion
			assertEquals(expected, e.getExitCode());
		} finally {
			System.setSecurityManager(null);
		}
	}

	@Test
	public void testMainExitsWhenInvalidGNUArgumentsAreDetected() {
		// Fixture
		final String[] arguments = {"-invalid"};
		final int expected = State.INVALID_ARGUMENTS.getValue();

		// Match		
		try {
			System.setSecurityManager(new NoExitSecurityManager());
			SquareRobin.main(arguments);
			fail(SquareRobinTest.SYSTEM_EXIT_MESSAGE);
		} catch(ExitException e) {
			assertEquals(expected, e.getExitCode());
		} finally {
			System.setSecurityManager(null);
		}
	}

	@Test
	public void testMainExitsWhenDuplicateTeamsAreDetected() {
		// Fixture
		final String[] arguments = {};
		final String[] teams = SquareRobinTest.TEAM_SET_NOT_UNIQUE;
		String systemIn = "";
		for (final String team : teams) {
			systemIn += team + SquareRobinTest.NL;
		}
		systemIn += SquareRobinTest.NL;
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));
		final int expected = State.SCHEDULE_ERROR.getValue(); 

		// Match		
		try {
			System.setSecurityManager(new NoExitSecurityManager());
			SquareRobin.main(arguments);
			fail(SquareRobinTest.SYSTEM_EXIT_MESSAGE);
		} catch(ExitException e) {
			// Assertion
			assertEquals(expected , e.getExitCode());
		} finally {
			System.setSecurityManager(null);
		}
	}

}