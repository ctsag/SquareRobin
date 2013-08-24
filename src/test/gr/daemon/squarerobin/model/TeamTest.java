package gr.daemon.squarerobin.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class TeamTest {
	
	@Test
	public void testConstructor() {
		String expected = "Panathinaikos";
		Team team = new Team(expected);
		assertEquals(expected, team.getName());
	}
	
	@Test
	public void testName() {
		String expected = "Panathinaikos";
		Team team = new Team("Olympiakos");
		team.setName(expected);
		assertEquals(expected, team.getName());		
	}
	
	@Test 
	public void testPoints() {		
		int expected = 8;
		Team team = new Team("Panathinaikos");
		team.setPoints(expected);
		assertEquals(expected, team.getPoints());
	}
	
	@Test 
	public void testPosition() {		
		int expected = 1;
		Team team = new Team("Panathinaikos");
		team.setPosition(expected);
		assertEquals(expected, team.getPosition());
	}
	
	@Test 
	public void testGoalsFor() {		
		int expected = 32;
		Team team = new Team("Panathinaikos");
		team.setGoalsFor(expected);
		assertEquals(expected, team.getGoalsFor());
	}
	
	@Test 
	public void testGoalsAgainst() {		
		int expected = 22;
		Team team = new Team("Panathinaikos");
		team.setGoalsAgainst(expected);
		assertEquals(expected, team.getGoalsAgainst());
	}
	
	@Test 
	public void testGoalAverage() {		
		int expected = 10;
		Team team = new Team("Panathinaikos");
		team.setGoalAverage(expected);
		assertEquals(expected, team.getGoalAverage());
	}
	
}
