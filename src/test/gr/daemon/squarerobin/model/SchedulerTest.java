package gr.daemon.squarerobin.model;

import gr.daemon.squarerobin.model.exceptions.DuplicateEntryException;
import gr.daemon.squarerobin.model.exceptions.DuplicateGamesFoundException;
import gr.daemon.squarerobin.model.exceptions.DuplicateTeamsException;
import gr.daemon.squarerobin.model.exceptions.GameAlreadySettledException;
import gr.daemon.squarerobin.model.exceptions.InsufficientTeamsException;
import gr.daemon.squarerobin.model.exceptions.InvalidRoundsException;
import gr.daemon.squarerobin.model.exceptions.NoGamesFoundException;
import gr.daemon.squarerobin.model.exceptions.OddTeamNumberException;
import gr.daemon.squarerobin.model.exceptions.BreaksLimitException;
import java.util.Arrays;
import java.util.HashMap;
import static org.junit.Assert.*;
import org.junit.Test;

public class SchedulerTest {

	private static final String SEASON_A = "2013";
	private static final String[] TEAMS_EVEN = {"Panathinaikos", "Olympiakos", "AEK", "PAOK"};
	private static final String[] TEAMS_ODD = {"Panathinaikos", "Olympiakos", "OFI"};
	private static final String[] TEAMS_DUPLICATE = {"Panathinaikos", "Olympiakos", "AEK", "AEK"};
	private static final String[] TEAMS_INSUFFICIENT = {"PAOK"};
	private static final int ROUNDS_A = 3;
	private static final int ROUNDS_B = 0;
	private static final int ROUNDS_C = 4;
	private static final int FIRST_ROUND = 1;
	
	@Test
	public void testConstructorWithoutRoundsSetsDefaultRounds() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, BreaksLimitException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture
		final int expected = Scheduler.DEFAULT_ROUNDS;
		
		// Match
		final Scheduler scheduler = new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_EVEN);		
		
		// Assertion
		assertEquals(expected, scheduler.getRounds());
	}
	
	@Test
	public void testConstructorCreatesExpectedNumberOfRounds() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, BreaksLimitException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture
		final int expected = SchedulerTest.ROUNDS_A;
		
		// Match
		final Scheduler scheduler = new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_EVEN, SchedulerTest.ROUNDS_A);
		final int rounds = scheduler.getSeason().getRounds().length;
		
		// Assertion
		assertEquals(expected, rounds);
	}
	
	@Test
	public void testConstructorCreatesExpectedNumberOfSlots() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, BreaksLimitException, DuplicateEntryException, GameAlreadySettledException {
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
	public void testConstructorCreatesExpectedNumberOfGames() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, BreaksLimitException, DuplicateEntryException, GameAlreadySettledException {
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
	public void testValidateRoundsRejectsZeroOrLessRounds() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, BreaksLimitException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture		
		
		// Match
		try {
			new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_EVEN, SchedulerTest.ROUNDS_B);
			fail("Exception not thrown for zero or less rounds");
		} catch(InvalidRoundsException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());			
		}		
	}
	
	@Test
	public void testValidateTeamsRejectsOddTeams() throws DuplicateTeamsException, InsufficientTeamsException, InvalidRoundsException, BreaksLimitException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture		

		// Match
		try {
			new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_ODD);
			fail("Exception not thrown for odd teams");
		} catch(OddTeamNumberException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());		
		}
	}
	
	@Test
	public void testValidateTeamsRejectsDuplicateTeams() throws InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, BreaksLimitException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture		
		
		// Match
		try {
			new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_DUPLICATE);
			fail("Exception not thrown for duplicate teams");
		} catch(DuplicateTeamsException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());			
		}
	}
	
	@Test
	public void testValidateTeamsRejectsInsufficientTeams() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, BreaksLimitException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture		 
		
		// Match
		try {
			new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_INSUFFICIENT);
			fail("Exception not thrown for insufficient teams");
		} catch(InsufficientTeamsException e) {
			// Assertion
			assertFalse(e.getMessage().isEmpty());			
		}
	}
	
	@Test
	public void testGetRoundsReturnsExpectedRounds() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, BreaksLimitException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture
		final int expected = SchedulerTest.ROUNDS_A;
		
		// Match
		final Scheduler scheduler = new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_EVEN, SchedulerTest.ROUNDS_A);		
		
		// Assertion
		assertEquals(expected, scheduler.getRounds());
	}
	
	@Test
	public void testGetTeamsReturnsExpectedTeams() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, BreaksLimitException, DuplicateEntryException, GameAlreadySettledException {
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
	public void testGenerateTeamsCreatesExpectedTeams() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, BreaksLimitException, DuplicateEntryException, GameAlreadySettledException {
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
	public void testGenerateFirstRoundCreatesDistinctPairs() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, BreaksLimitException, DuplicateEntryException, GameAlreadySettledException, DuplicateGamesFoundException {
		// Fixture		
		final Scheduler scheduler = new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_EVEN);
		
		// Match
		final Round round = scheduler.getSeason().getRound(SchedulerTest.FIRST_ROUND);
		for (final Slot slot : round.getSlots()) {				
			for (final Game game : slot.getGames()) {
				try {
					slot.getGame(game.getHomeTeam(), game.getAwayTeam());
					slot.getGame(game.getAwayTeam(), game.getHomeTeam());
				} catch(NoGamesFoundException e) {
					continue;
				}				
			}
		}
		
		// Assertion
		assertTrue(true);
	}
	
	@Test
	public void testNormalizeRoundCreatesExpectedHomeToAwayRatio() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, BreaksLimitException, DuplicateEntryException, GameAlreadySettledException {
		// Fixture
		final Scheduler scheduler = new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_EVEN);
		final HashMap<Team, Integer> homeGames = new HashMap<>();
		final int expected = (scheduler.getTeams().size() - 1) / 2 + 1;

		// Match
		for (final Team team : scheduler.getTeams()) {
			homeGames.put(team, 0);
		}
		for (final Slot slot : scheduler.getSeason().getRound(SchedulerTest.FIRST_ROUND).getSlots()) {
			for (final Game game : slot.getGames()) {
				int previousGameCount = homeGames.get(game.getHomeTeam());
				homeGames.put(game.getHomeTeam(), ++previousGameCount);
			}
		}
		
		// Assertion
		for (final Team team : homeGames.keySet()) {
			assertTrue(expected - homeGames.get(team) >= 0 && expected - homeGames.get(team) <= 1 );
		}
	}
	
	@Test
	public void testGenerateAdditionalRoundsCreatesMirroredRounds() throws DuplicateTeamsException, InsufficientTeamsException, OddTeamNumberException, InvalidRoundsException, BreaksLimitException, DuplicateEntryException, GameAlreadySettledException, DuplicateGamesFoundException, NoGamesFoundException {
		// Fixture
		final Scheduler scheduler = new Scheduler(SchedulerTest.SEASON_A, SchedulerTest.TEAMS_EVEN, SchedulerTest.ROUNDS_C);		
		
		// Match
		for (int i = SchedulerTest.FIRST_ROUND; i < SchedulerTest.ROUNDS_C; i++) {
			final Round currentRound = scheduler.getSeason().getRound(i);
			final Round nextRound = scheduler.getSeason().getRound(i + 1);			
			for (final Slot slot : currentRound.getSlots()) {
				final int mirroredSlot = slot.getIndex() + currentRound.getSlots().length;
				for (final Game game : slot.getGames()) {					
					assertNotNull(nextRound.getSlot(mirroredSlot).getGame(game.getAwayTeam(), game.getHomeTeam()));
				}
			}
		}
	}

}
