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
		
	public Game runGame() throws EndOfLeagueException {
		final Game game = this.getNextGame();		
		
		game.settle();
		this.season.getLeagueTable().update();
		return game;
	}
	
	public Game[] runSlot() throws EndOfLeagueException {
		final ArrayList<Game> games = new ArrayList<>();
		final Slot startingSlot = this.currentSlot;
		Game currentGame = this.getNextGame();
		
		while (this.currentSlot.equals(startingSlot)) {			
			currentGame.settle();
			games.add(currentGame);
			currentGame = this.getNextGame();
		} 
		this.season.getLeagueTable().update();
		return games.toArray(new Game[games.size()]);
	}

	public Game[] runSeason() throws EndOfLeagueException {
		final ArrayList<Game> games = new ArrayList<>();		
		boolean endReached = false;
		int gamesFound = 0;
		
		while (!endReached) {
			try {
				final Game currentGame = this.getNextGame();
				games.add(currentGame);
				currentGame.settle();
				gamesFound++;
			} catch(EndOfLeagueException e) {
				endReached = true;
			}
		}
		if (gamesFound > 0) {
			this.season.getLeagueTable().update();
			return games.toArray(new Game[games.size()]);
		} else {
			throw new EndOfLeagueException("No games remaining");
		}
	}

}
