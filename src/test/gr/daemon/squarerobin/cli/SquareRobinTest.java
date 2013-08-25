package gr.daemon.squarerobin.cli;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;
import static org.junit.Assert.*;
import org.junit.Test;

public class SquareRobinTest {
	
	private static final String NL = "\n";
	private static final String CRNL = "\r\n";
	private static final String EXIT = SquareRobinTest.NL + SquareRobinTest.NL;
	private static final String VS = " - ";	
	private static final String SYSTEM_EXIT_MESSAGE = "System.exit() expected";
	private static final String TEAM_A = "Panathinaikos";
	private static final String TEAM_B = "Olympiakos";
	private static final String TEAM_C = "AEK";
	private static final String TEAM_D = "PAOK";
	private static final String[] TEAM_SET_A = {SquareRobinTest.TEAM_A, SquareRobinTest.TEAM_B, SquareRobinTest.TEAM_C, SquareRobinTest.TEAM_D};
	private static final String[] TEAM_SET_B = {SquareRobinTest.TEAM_A, SquareRobinTest.TEAM_B, SquareRobinTest.TEAM_C, SquareRobinTest.TEAM_D};
	private static final String LEFT_OUT_TEAM = SquareRobinTest.TEAM_D;

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
	public void testDefaultUsage() {
		final ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
		final String[] clubs = SquareRobinTest.TEAM_SET_A;
		String systemIn = "";

		// Setup input and output.
		for (final String club : clubs) {
			systemIn += club + SquareRobinTest.NL;
		}
		systemIn += SquareRobinTest.NL;
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));
		System.setOut(new PrintStream(systemOut));

		SquareRobin.main(new String[]{});

		// Assert number of days
		for (int i = 1; i < clubs.length; i++) {
			assertEquals(1, this.countOccurences(systemOut.toString(), "Day " + i));
		}
	}

	@Test
	public void testNoDays() {
		final ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
		final String[] clubs = SquareRobinTest.TEAM_SET_A;
		String systemIn = "";

		// Setup input and output.
		for (final String club : clubs) {
			systemIn += club + SquareRobinTest.NL;
		}
		systemIn += SquareRobinTest.NL;
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));
		System.setOut(new PrintStream(systemOut));

		SquareRobin.main(new String[]{"-nodays"});

		// Assert days are not printed
		for (int i = 1; i < clubs.length; i++) {
			assertEquals(0, this.countOccurences(systemOut.toString(), "Day " + i));
		}
	}

	@Test
	public void testNoRounds() {
		final ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
		final String[] clubs = SquareRobinTest.TEAM_SET_A;
		String systemIn = "";

		// Setup input and output.
		for (final String club : clubs) {
			systemIn += club + SquareRobinTest.NL;
		}
		systemIn += SquareRobinTest.NL;
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));
		System.setOut(new PrintStream(systemOut));

		SquareRobin.main(new String[]{"-norounds"});

		// Assert rounds are not printed
		for (int i = 1; i <= 2; i++) {
			assertEquals(0, this.countOccurences(systemOut.toString(), "Round " + i));
		}
	}

	@Test
	public void testOnlyOneClub() {
		final ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
		final String[] clubs = SquareRobinTest.TEAM_SET_A;
		final String[] otherClubs = SquareRobinTest.TEAM_SET_B;
		final String onlyForClub = SquareRobinTest.LEFT_OUT_TEAM;

		String systemIn = "";

		// Setup input and output.
		for (final String club : clubs) {
			systemIn += club + SquareRobinTest.NL;
		}
		systemIn += SquareRobinTest.NL;
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));
		System.setOut(new PrintStream(systemOut));

		SquareRobin.main(new String[]{"-only", onlyForClub});

		// Assert only the matches of the specified club are printed
		for (final String club : otherClubs) {
			assertEquals(1, this.countOccurences(systemOut.toString(), SquareRobinTest.CRNL + club + SquareRobinTest.VS) + this.countOccurences(systemOut.toString(), SquareRobinTest.VS + club + SquareRobinTest.CRNL));
		}
	}

	@Test
	public void testHelp() {
		final ByteArrayOutputStream systemOut = new ByteArrayOutputStream();

		// Setup input and output.
		System.setOut(new PrintStream(systemOut));

		SquareRobin.main(new String[]{"-help"});

		// Assert help is printed
		assertEquals(1, this.countOccurences(systemOut.toString(), "usage: " + SquareRobin.APPLICATION_NAME));
	}

	@Test
	public void testVersion() {
		final ByteArrayOutputStream systemOut = new ByteArrayOutputStream();

		// Setup input and output.
		System.setOut(new PrintStream(systemOut));

		SquareRobin.main(new String[]{"-version"});

		// Assert help is printed
		assertEquals(1, this.countOccurences(systemOut.toString(), SquareRobin.APPLICATION_VERSION));
	}

	@Test
	public void testOddClubs() {
		final String[] clubs = SquareRobinTest.TEAM_SET_B;
		String systemIn = "";

		// Setup input and output.
		for (final String club : clubs) {
			systemIn += club + SquareRobinTest.NL;
		}
		systemIn += SquareRobinTest.NL;
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));

		// Assert odd number of clubs produces appropriate exit code
		System.setSecurityManager(new NoExitSecurityManager());
		try {
			SquareRobin.main(new String[]{});
			fail(SquareRobinTest.SYSTEM_EXIT_MESSAGE);
		} catch(ExitException e) {
			assertEquals(State.ODD_CLUBS.getValue(), e.getExitCode());
		}
		System.setSecurityManager(null);
	}

	@Test
	public void testInsufficientClubs() {
		final String systemIn = SquareRobinTest.EXIT;

		// Setup input and output.
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));

		// Assert insufficient number of clubs produces appropriate exit code
		System.setSecurityManager(new NoExitSecurityManager());
		try {
			SquareRobin.main(new String[]{});
			fail(SquareRobinTest.SYSTEM_EXIT_MESSAGE);
		} catch(ExitException e) {
			assertEquals(State.INSUFFICIENT_CLUBS.getValue(), e.getExitCode());
		}
		System.setSecurityManager(null);
	}

	@Test
	public void testInvalidNonGNUArguments() {
		// Assert invalid non-GNU arguments produce appropriate exit code
		System.setSecurityManager(new NoExitSecurityManager());
		try {
			SquareRobin.main(new String[]{"invalid arguments"});
			fail(SquareRobinTest.SYSTEM_EXIT_MESSAGE);
		} catch(ExitException e) {
			assertEquals(State.INVALID_ARGUMENTS.getValue(), e.getExitCode());
		}
		System.setSecurityManager(null);
	}

	@Test
	public void testInvalidGNUArguments() {
		// Assert invalid GNU arguments produce appropriate exit code
		System.setSecurityManager(new NoExitSecurityManager());
		try {
			SquareRobin.main(new String[]{"-invalid"});
			fail(SquareRobinTest.SYSTEM_EXIT_MESSAGE);
		} catch(ExitException e) {
			assertEquals(State.INVALID_ARGUMENTS.getValue(), e.getExitCode());
		}
		System.setSecurityManager(null);
	}

	@Test
	public void testUnspecifiedError() {
		final String[] clubs = SquareRobinTest.TEAM_SET_A;
		String systemIn = "";

		// Setup input and output.
		for (final String club : clubs) {
			systemIn += club + SquareRobinTest.NL;
		}
		systemIn += SquareRobinTest.NL;
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));

		// Assert an uncaught exception produces the appropriate exit code
		System.setSecurityManager(new NoExitSecurityManager());
		try {
			SquareRobin.main(new String[]{});
			fail(SquareRobinTest.SYSTEM_EXIT_MESSAGE);
		} catch(ExitException e) {
			assertEquals(State.UNSPECIFIED_ERROR.getValue(), e.getExitCode());
		}
		System.setSecurityManager(null);
	}

}