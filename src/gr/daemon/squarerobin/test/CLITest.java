package gr.daemon.squarerobin.test;

import gr.daemon.squarerobin.cli.CLI;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;
import static org.junit.Assert.*;

public class CLITest {
	
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
	public void testMain() {
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
		
		// Call the main method
		CLI.main(new String[]{});
		
		// Assert number of days
		for (int i = 0; i < clubs.length - 1; i++) {
			assertEquals(1, this.countOccurences(systemOut.toString(), "Day " + i));			
		}
		
		// Assert number of occurences per club
		for (String club : clubs) {
			assertEquals(clubs.length - 1, this.countOccurences(systemOut.toString(), "\r\n" + club + " - ") + this.countOccurences(systemOut.toString(), " - " + club + "\r\n"));
		}
	}

}
