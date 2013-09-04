package gr.daemon.squarerobin.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class SchedulerTest {

	private static final String SEASON_A = "2013";
	private static final String[] TEAMS_EVEN = {"Panathinaikos", "Olympiakos", "AEK", "PAOK"};
	private static final String[] TEAMS_ODD = {"Panathinaikos", "Olympiakos", "AEK"};
	private static final String[] TEAMS_DUPLICATE = {"Panathinaikos", "Olympiakos", "AEK", "AEK"};
	private static final String[] TEAMS_INSUFFICIENT = {"Panathinaikos"};
	private static final int ROUNDS_A = 3;
	private static final int ROUNDS_B = 0;
	
	@Test
	public void testConstructorWithoutRoundsSetsDefaultRounds() {
		
	}
	
	@Test
	public void testClassCreatesExpectedSeason() {
		
	}
	
	@Test
	public void testValidateRoundsRejectsZeroOrLessRounds() {
		
	}
	
	@Test
	public void testValidateTeamsRejectsOddTeams() {
		
	}
	
	@Test
	public void testValidateTeamsRejectsDuplicateTeams() {
		
	}
	
	@Test
	public void testValidateTeamsRejectsInsufficientTeams() {
		
	}
	
	@Test
	public void testGetRoundsReturnsExpectedRounds() {
		
	}
	
	@Test
	public void testGetTeamsReturnsExpectedTeams() {
		
	}
	
	@Test
	public void testGenerateTeamsCreatesExpectedTeams() {
		
	}
	
	@Test
	public void testGenerateFirstRoundCreatesDistinctPairs() {
		
	}
	
	@Test
	public void testGenerateNormalizeRoundCreatesExpectedHomeToAwayRatio() {
		
	}
	
	@Test
	public void testGenerateAdditionalRoundsCreatesMirroredRounds() {
		
	}
	
	@Test
	public void testCalculateBreaksPreventsThreeInARow() {
		
	}
	
}
