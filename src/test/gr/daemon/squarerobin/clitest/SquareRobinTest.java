package gr.daemon.squarerobin.clitest;

import gr.daemon.squarerobin.cli.SquareRobin;
import gr.daemon.squarerobin.cli.State;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;
import static org.junit.Assert.*;
import org.junit.Test;

public class SquareRobinTest {

	private class ExitException extends SecurityException {

		private static final long serialVersionUID = 1L;
		private int exitCode;

		public ExitException(int code) {
			super("System.exit(" + code + ") detected");
			this.exitCode = code;
		}
		
		public int getExitCode() {
			return this.exitCode;
		}

	}

	private class NoExitSecurityManager extends SecurityManager {
		
		@Override
		public void checkPermission(Permission perm) {
			// allow anything.
		}
		
		@Override
		public void checkPermission(Permission perm, Object context) {
			// allow anything.
		}

		@Override
		public void checkExit(int status) {
			super.checkExit(status);
			throw new ExitException(status);
		}

	}
	
	private int countOccurences(String string, String substring) {
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
		String[] clubs = new String[]{"PAO", "OSFP", "AEK", "PAOK"};
		String systemIn = "";
		ByteArrayOutputStream systemOut = new ByteArrayOutputStream();

		// Setup input and output.
		for (String club : clubs) {
			systemIn += club + "\n";
		}
		systemIn += "\n";
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));		
		System.setOut(new PrintStream(systemOut));

		SquareRobin.main(new String[]{});

		// Assert number of days
		for (int i = 1; i < clubs.length; i++) {
			assertEquals(1, this.countOccurences(systemOut.toString(), "Day " + i));			
		}

		// Assert number of occurences per club
		for (String club : clubs) {
			assertEquals(clubs.length - 1, this.countOccurences(systemOut.toString(), "\r\n" + club + " - ") + this.countOccurences(systemOut.toString(), " - " + club + "\r\n"));
		}
	}
	
	@Test
	public void testNoDays() {
		String[] clubs = new String[]{"PAO", "OSFP", "AEK", "PAOK"};		
		String systemIn = "";
		ByteArrayOutputStream systemOut = new ByteArrayOutputStream();

		// Setup input and output.
		for (String club : clubs) {
			systemIn += club + "\n";
		}
		systemIn += "\n";
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));		
		System.setOut(new PrintStream(systemOut));

		SquareRobin.main(new String[]{"-nodays"});

		// Assert days are not printed
		for (int i = 1; i < clubs.length; i++) {
			assertEquals(0, this.countOccurences(systemOut.toString(), "Day " + i));			
		}

		// Assert number of occurences per club
		for (String club : clubs) {
			assertEquals(clubs.length - 1, this.countOccurences(systemOut.toString(), "\r\n" + club + " - ") + this.countOccurences(systemOut.toString(), " - " + club + "\r\n"));
		}
	}
	
	@Test
	public void testOnlyOneClub() {
		String[] clubs = new String[]{"PAO", "OSFP", "AEK", "PAOK"};
		String onlyForClub = "PAO";
		String[] otherClubs = new String[]{"OSFP", "AEK", "PAOK"};
		String systemIn = "";
		ByteArrayOutputStream systemOut = new ByteArrayOutputStream();

		// Setup input and output.
		for (String club : clubs) {
			systemIn += club + "\n";
		}
		systemIn += "\n";
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));		
		System.setOut(new PrintStream(systemOut));

		SquareRobin.main(new String[]{"-only", onlyForClub});

		// Assert only the matches of the specified club are printed
		for (String club : otherClubs) {
			assertEquals(1, this.countOccurences(systemOut.toString(), "\r\n" + club + " - ") + this.countOccurences(systemOut.toString(), " - " + club + "\r\n"));
		}
		assertEquals(clubs.length - 1, this.countOccurences(systemOut.toString(), "\r\n" + onlyForClub + " - ") + this.countOccurences(systemOut.toString(), " - " + onlyForClub + "\r\n")); 
	}
	
	@Test
	public void testMultipleFlags() {
		String[] clubs = new String[]{"PAO", "OSFP", "AEK", "PAOK"};
		String onlyForClub = "PAO";
		String[] otherClubs = new String[]{"OSFP", "AEK", "PAOK"};
		String systemIn = "";
		ByteArrayOutputStream systemOut = new ByteArrayOutputStream();

		// Setup input and output.
		for (String club : clubs) {
			systemIn += club + "\n";
		}
		systemIn += "\n";
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));		
		System.setOut(new PrintStream(systemOut));

		SquareRobin.main(new String[]{"-only", onlyForClub, "-nodays"});
		
		// Assert days are not printed
		for (int i = 1; i < clubs.length; i++) {
			assertEquals(0, this.countOccurences(systemOut.toString(), "Day " + i));			
		}

		// Assert only the matches of the specified club are printed
		for (String club : otherClubs) {
			assertEquals(1, this.countOccurences(systemOut.toString(), "\r\n" + club + " - ") + this.countOccurences(systemOut.toString(), " - " + club + "\r\n"));
		}
		assertEquals(clubs.length - 1, this.countOccurences(systemOut.toString(), "\r\n" + onlyForClub + " - ") + this.countOccurences(systemOut.toString(), " - " + onlyForClub + "\r\n")); 
	}	
	
	@Test
	public void testHelp() {
		ByteArrayOutputStream systemOut = new ByteArrayOutputStream();

		// Setup input and output.
		System.setOut(new PrintStream(systemOut));

		SquareRobin.main(new String[]{"-help"});

		// Assert help is printed
		assertEquals(1, this.countOccurences(systemOut.toString(), "usage: " + SquareRobin.APPLICATION_NAME));
	}
	
	@Test
	public void testVersion() {
		ByteArrayOutputStream systemOut = new ByteArrayOutputStream();

		// Setup input and output.
		System.setOut(new PrintStream(systemOut));

		SquareRobin.main(new String[]{"-version"});

		// Assert help is printed
		assertEquals(1, this.countOccurences(systemOut.toString(), SquareRobin.APPLICATION_VERSION));
	}	
	
	@Test
	public void testOddClubs() {
		ByteArrayOutputStream systemErr = new ByteArrayOutputStream();
		String[] clubs = new String[]{"PAO", "OSFP", "AEK"};
		String systemIn = "";		

		// Setup input and output.
		for (String club : clubs) {
			systemIn += club + "\n";
		}
		systemIn += "\n";
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));
		System.setErr(new PrintStream(systemErr));

		// Assert odd number of clubs produces appropriate exit code
		System.setSecurityManager(new NoExitSecurityManager());
		try {
			SquareRobin.main(new String[]{});
			fail("System.exit() expected");
		} catch(ExitException e) {			
			assertEquals(State.ODD_CLUBS.getValue(), e.getExitCode());
			assertEquals(1, this.countOccurences(systemErr.toString(), State.ODD_CLUBS.toString()));
		}
		System.setSecurityManager(null);
	}

	@Test
	public void testInsufficientClubs() {
		ByteArrayOutputStream systemErr = new ByteArrayOutputStream();
		String systemIn = "\n\n";

		// Setup input and output.
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));
		System.setErr(new PrintStream(systemErr));

		// Assert insufficient number of clubs produces appropriate exit code
		System.setSecurityManager(new NoExitSecurityManager());
		try {
			SquareRobin.main(new String[]{});
			fail("System.exit() expected");
		} catch(ExitException e) {
			assertEquals(State.INSUFFICIENT_CLUBS.getValue(), e.getExitCode());
			assertEquals(1, this.countOccurences(systemErr.toString(), State.INSUFFICIENT_CLUBS.toString()));
		}
		System.setSecurityManager(null);
	}
	
	@Test
	public void testInvalidArguments() {
		ByteArrayOutputStream systemErr = new ByteArrayOutputStream();		
		
		System.setErr(new PrintStream(systemErr));
		System.setSecurityManager(new NoExitSecurityManager());
		
		// Assert invalid GNU arguments produce appropriate exit code		
		try {
			SquareRobin.main(new String[]{"-invalid"});
			fail("System.exit() expected");
		} catch(ExitException e) {
			assertEquals(State.INVALID_ARGUMENTS.getValue(), e.getExitCode());
			assertEquals(1, this.countOccurences(systemErr.toString(), State.INVALID_ARGUMENTS.toString()));
		} finally {
			systemErr.reset();
		}
		
		// Assert invalid non-GNU arguments produce appropriate exit code		
		try {
			SquareRobin.main(new String[]{"invalid arguments"});
			fail("System.exit() expected");
		} catch(ExitException e) {
			assertEquals(State.INVALID_ARGUMENTS.getValue(), e.getExitCode());
			assertEquals(1, this.countOccurences(systemErr.toString(), State.INVALID_ARGUMENTS.toString()));
		} finally {
			systemErr.reset();
		}
		
		System.setSecurityManager(null);
	}
	
	@Test
	public void testUnspecifiedError() {
		String[] clubs = new String[]{"PAO", "OSFP", "AEK", "PAO"};
		String systemIn = "";
		ByteArrayOutputStream systemErr = new ByteArrayOutputStream();

		// Setup input and output.
		for (String club : clubs) {
			systemIn += club + "\n";
		}
		systemIn += "\n";
		System.setIn(new ByteArrayInputStream(systemIn.getBytes()));
		System.setErr(new PrintStream(systemErr));

		// Assert an uncaught exception (such as duplicate teams) produces the appropriate exit code and outputs the exception message properly
		System.setSecurityManager(new NoExitSecurityManager());
		try {
			SquareRobin.main(new String[]{});
			fail("System.exit() expected");
		} catch(ExitException e) {			
			assertEquals(State.UNSPECIFIED_ERROR.getValue(), e.getExitCode());
			assertEquals(1, this.countOccurences(systemErr.toString(), State.UNSPECIFIED_ERROR.toString()));
		}
		System.setSecurityManager(null);
	}

}