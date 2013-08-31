package gr.daemon.squarerobin.model;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game {
    
	private static final int MAX_GOALS = 6;
	private final int index;
	private final Team homeTeam;
    private final Team awayTeam;
    private int homeGoals;
    private int awayGoals;
    private Slot slot;
    private boolean settled;

    public Game(final int index, final Team homeTeam, final Team awayTeam) {
    	this.index = index;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }
    
    public int getIndex() {
    	return this.index;
    }
    
    public Team getHomeTeam() {
    	return this.homeTeam;
    }
    
    public Team getAwayTeam() {
    	return this.awayTeam;
    }
    
    public Slot getSlot() {
    	return this.slot;
    }
    
	public void setSlot(final Slot slot) {
		final List<Game> slotGames = Arrays.asList(slot.getGames());
		if (!slotGames.contains(this)) {
			slot.addGame(this);
		}
		this.slot = slot;
	}
    
    public boolean isSettled() {
    	return this.settled;
    }
    
    public int getHomeGoals() throws GameNotSettledException {
    	if (this.isSettled()) {
    		return this.homeGoals;
    	} else {
    		throw new GameNotSettledException("Unable to access score data because the game has not been settled");
    	}
    }
    
    public int getAwayGoals() throws GameNotSettledException {
    	if (this.isSettled()) {
    		return this.awayGoals;
    	} else {
    		throw new GameNotSettledException("Unable to access score data because the game has not been settled");
    	}
    }
    
    public String getScore() throws GameNotSettledException {
    	if (this.isSettled()) {
    		return this.homeGoals + " - " + this.awayGoals;
    	} else {
    		throw new GameNotSettledException("Unable to access score data because the game has not been settled");
    	}
    }
    
    public void settle() {
   		final Random generator = new Random();
   		final int homeGoals = generator.nextInt(Game.MAX_GOALS);
   		final int awayGoals = generator.nextInt(Game.MAX_GOALS);
   		this.settle(homeGoals, awayGoals);
    }
    
    public void settle(final int homeGoals, final int awayGoals) throws GameAlreadySettledException {
    	if (this.settled) {
    		throw new GameAlreadySettledException("You cannot settle an already settled game");    		
    	} else {
    		this.homeGoals = homeGoals;
    		this.awayGoals = awayGoals;
    		this.settled = true;
    	}
    }

}