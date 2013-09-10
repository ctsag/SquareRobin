package gr.daemon.squarerobin.model;

import gr.daemon.squarerobin.model.exceptions.EndOfLeagueException;
import java.util.ArrayList;

public class LeagueRunner {
	
	private final Season season;
	private final ArrayList<Slot> slots = new ArrayList<>();
	private Slot currentSlot;
	
	protected LeagueRunner(final Season season) {
		this.season = season;
	}
	
	protected void ready() {
		this.populateSlots();
		this.currentSlot = this.slots.get(0);
	}
	
	private void populateSlots() {
		for (final Round round : this.season.getRounds()) {
			for (final Slot slot : round.getSlots()) {
				this.slots.add(slot);
			}
		}		
	}
	
	private Slot getNextSlot() {
		final int index = this.slots.indexOf(this.currentSlot);
		
		if (this.slots.size() - index > 1) {
			return this.slots.get(index + 1);
		} else {
			return this.currentSlot;			
		}
	}
	
	private void setCurrentSlot() {		
		Slot slot = null;
		
		for (final Game game : this.currentSlot.getGames()) {
			if (!game.isSettled()) {
				slot = this.currentSlot;
				break;
			}
		}
		if (slot == null) {
			this.currentSlot = this.getNextSlot();
		}
	}	
	
	public Game getNextGame() throws EndOfLeagueException {
		Game foundGame = null;		
		
		this.setCurrentSlot();		
		for (final Game game : this.currentSlot.getGames()) {
			if (!game.isSettled()) {
				foundGame = game; 
				break;
			}
		}
		if (foundGame == null) {
			throw new EndOfLeagueException("No games remaining");
		} else {
			return foundGame;
		}		
	}	
		
	public Game runGame() {
		final Game game = this.getNextGame();		
		
		game.settle();
		this.season.getLeagueTable().update();
		return game;
	}
	
//	public Game[] runGames(final int count) {
//		
//	}
//	
//	public Game[] runSlot() {
//		
//	}
//	
//	public Game[] runSlots(final int count) {
//		
//	}
//	
//	public Game[] runSeason() {
//		
//	}

}
