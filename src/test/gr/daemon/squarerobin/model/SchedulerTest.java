package gr.daemon.squarerobin.model;

import java.util.ArrayList;
import java.util.Arrays;
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
	public void testConstructorWithoutRoundsSetsDefaultRounds() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, ThreeInARowException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture
		final int expected = Scheduler.DEFAULT_ROUNDS;
		
		// Match
		final Scheduler scheduler = new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_EVEN);		
		
		// Assertion
		assertEquals(expected, scheduler.getRounds());
	}
	
	@Test
	public void testConstructorCreatesExpectedNumberOfRounds() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, ThreeInARowException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture
		final int expected = SchedulerTest.ROUNDS_A;
		
		// Match
		final Scheduler scheduler = new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_EVEN, SchedulerTest.ROUNDS_A);
		final int rounds = scheduler.getSeason().getRounds().length;
		
		// Assertion
		assertEquals(expected, rounds);
	}
	
	@Test
	public void testConstructorCreatesExpectedNumberOfSlots() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, ThreeInARowException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture
		final int expected = SchedulerTest.ROUNDS_A * (SchedulerTest.TEAMS_EVEN.length - 1);
		
		// Match
		final Scheduler scheduler = new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_EVEN, SchedulerTest.ROUNDS_A);
		int slots = 0;
		for (final Round round : scheduler.getSeason().getRounds()) {
			slots += round.getSlots().length;
		}
		
		// Assertion
		assertEquals(expected, slots);
	}
	
	@Test
	public void testConstructorCreatesExpectedNumberOfGames() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, ThreeInARowException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture
		final int expected = SchedulerTest.ROUNDS_A * (SchedulerTest.TEAMS_EVEN.length - 1) * (SchedulerTest.TEAMS_EVEN.length / 2);
		
		// Match
		final Scheduler scheduler = new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_EVEN, SchedulerTest.ROUNDS_A);
		int games = 0;
		for (final Round round : scheduler.getSeason().getRounds()) {
			for (final Slot slot : round.getSlots()) {
				games += slot.getGames().length;
			}
		}
		
		// Assertion
		assertEquals(expected, games);
	}

	@Test
	public void testValidateRoundsRejectsZeroOrLessRounds() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, ThreeInARowException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture
		final Scheduler scheduler;
		
		// Match
		try {
			scheduler = new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_EVEN, SchedulerTest.ROUNDS_B);
			fail("Exception not thrown for zero or less rounds");
		} catch(InvalidRoundsException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());			
		}		
	}
	
	@Test
	public void testValidateTeamsRejectsOddTeams() throws DuplicateTeamsException, InsufficientTeamsException, InvalidRoundsException, ThreeInARowException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture
		final Scheduler scheduler;

		// Match
		try {
			scheduler = new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_ODD);
			fail("Exception not thrown for odd teams");
		} catch(OddTeamNumberException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());		
		}
	}
	
	@Test
	public void testValidateTeamsRejectsDuplicateTeams() throws InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, ThreeInARowException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture
		final Scheduler scheduler;
		
		// Match
		try {
			scheduler = new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_DUPLICATE);
			fail("Exception not thrown for duplicate teams");
		} catch(DuplicateTeamsException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());			
		}
	}
	
	@Test
	public void testValidateTeamsRejectsInsufficientTeams() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, ThreeInARowException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture
		final Scheduler scheduler;
		
		// Match
		try {
			scheduler = new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_INSUFFICIENT);
			fail("Exception not thrown for insufficient teams");
		} catch(InsufficientTeamsException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());			
		}
	}
	
	@Test
	public void testGetRoundsReturnsExpectedRounds() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, ThreeInARowException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture
		final int expected = SchedulerTest.ROUNDS_A;
		
		// Match
		final Scheduler scheduler = new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_EVEN, SchedulerTest.ROUNDS_A);		
		
		// Assertion
		assertEquals(expected, scheduler.getRounds());
	}
	
	@Test
	public void testGetTeamsReturnsExpectedTeams() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, ThreeInARowException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture
		final Scheduler scheduler = new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_EVEN);
		final String[] expected = SchedulerTest.TEAMS_EVEN;
		
		// Match
		final String[] teams = new String[scheduler.getTeams().size()];
		int i = 0;
		for (final Team team : scheduler.getTeams()) {
			teams[i] = team.getName();
			i++;
		}
		Arrays.sort(expected);
		Arrays.sort(teams);
		
		// Assertion
		assertArrayEquals(expected, teams);
	}
	
	@Test
	public void testGenerateTeamsCreatesExpectedTeams() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, ThreeInARowException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture
		final Scheduler scheduler = new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_EVEN);
		final String[] expected = SchedulerTest.TEAMS_EVEN;
		
		// Match
		final String[] teams = new String[scheduler.getTeams().size()];
		int i = 0;
		for (final Team team : scheduler.getTeams()) {
			teams[i] = team.getName();
			i++;
		}
		Arrays.sort(expected);
		Arrays.sort(teams);
		
		// Assertion
		assertArrayEquals(expected, teams);
	}
	
	@Test
	public void testGenerateFirstRoundCreatesDistinctPairs() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, ThreeInARowException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture
		
		// Match
		
		// Assertion
		
	}
	
	@Test
	public void testGenerateNormalizeRoundCreatesExpectedHomeToAwayRatio() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, ThreeInARowException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture
		
		// Match
		
		// Assertion
		
	}
	
	@Test
	public void testGenerateAdditionalRoundsCreatesMirroredRounds() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, ThreeInARowException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture
		
		// Match
		
		// Assertion
		
	}
	
	@Test
	public void testCalculateBreaksPreventsThreeInARow() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, ThreeInARowException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture
		
		// Match
		
		// Assertion
		
	}
	
}
