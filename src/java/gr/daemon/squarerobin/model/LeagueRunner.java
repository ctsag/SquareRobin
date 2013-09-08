package gr.daemon.squarerobin.model;

import gr.daemon.squarerobin.model.exceptions.EndOfLeagueException;
import gr.daemon.squarerobin.model.exceptions.NoNextSlotException;
import gr.daemon.squarerobin.model.exceptions.SlotNotFoundException;
import java.util.Arrays;

public class LeagueRunner {
	
	private final Season season;
	private Slot slot;
	
	protected LeagueRunner(final Season season) {
		this.season = season;
	}
	
	private Slot getNextSlot(final Slot slot) throws NoNextSlotException, SlotNotFoundException {
		final Round round = slot.getRound();
		final Slot[] slots = round.getSlots();
		final int index = Arrays.asList(slots).indexOf(slot);		
		
		if (index > -1) {
			if (slots.length - index > 1) {
				return slots[index + 1];
			} else {
				throw new NoNextSlotException("Unable to find next slot as this is the last slot");
			}
		} else {
			throw new NoNextSlotException("Specified slot not found");
		}
	}
	
	private void setCurrentSlot() throws NoNextSlotException, SlotNotFoundException {		
		if (this.slot == null) {
			this.slot = this.season.getRounds()[0].getSlots()[0];
		} else {
			Slot slot = null;
			for (final Game game : this.slot.getGames()) {
				if (!game.isSettled()) {
					slot = this.slot;
					break;
				}
			}
			if (slot == null) {
				this.slot = this.getNextSlot(this.slot);
			}
		}
	}
	
	public Game getNextGame() throws NoNextSlotException, SlotNotFoundException, EndOfLeagueException {
		Game foundGame = null;
		
		this.setCurrentSlot();
		for (final Game game : this.slot.getGames()) {
			if (!game.isSettled()) {
				foundGame = game; 
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
