package gr.daemon.squarerobin.model;

import gr.daemon.squarerobin.model.Game;
import gr.daemon.squarerobin.model.Team;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

	private Team home = new Team("PAO");
	private Team away = new Team("OSFP");
	private Game game = new Game(home, away);
	
	@Test
	public void testHomeGoals() {
		game.setHomeGoals(3);
		assertEquals(3, game.getHomeGoals());
	}
	
	@Test
	public void testAwayGoals() {
		game.setAwayGoals(3);
		assertEquals(3, game.getAwayGoals());
	}
	
	@Test
	public void testScore() {
		int[] score = game.getScore();
		assertArrayEquals(new int[]{game.getHomeGoals(), game.getAwayGoals()}, score);
	}
	
	@Test
	public void testTeams() {
		Team[] teams = game.getTeams();
		assertArrayEquals(new Team[]{this.home, this.away}, teams);
	}
}
