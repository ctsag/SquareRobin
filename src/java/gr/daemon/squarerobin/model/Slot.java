package gr.daemon.squarerobin.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Slot {
    
    private final int index;
    private final HashMap<Integer, Game> games = new HashMap<>();
    private Round round;
    
    public Slot(final int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
    
	public Round getRound() {
		return this.round;
	}
	
	public void setRound(final Round round) throws DuplicateEntryException {
		final List<Slot> roundSlots = Arrays.asList(round.getSlots());
		if (!roundSlots.contains(this)) {
			round.addSlot(this);
		}
		this.round = round;
	}
    
	public Game[] getGames() {
		final Collection<Game> games = this.games.values();
		
		return games.toArray(new Game[games.size()]);
	}

	public Game getGame(final int index) {
		return this.games.get(index);
	}

	public void addGame(final Game game) throws DuplicateEntryException {
		final int index = game.getIndex();
		
		if (this.games.containsKey(index)) {
			throw new DuplicateEntryException("A game of index " + index + " already exists");
		} else {
			this.games.put(index, game);
			game.setSlot(this);			
		}
	}

	public void removeGame(final int index) throws InexistentEntryException {
		if (this.games.containsKey(index)) {
			this.games.remove(index);
		} else {
			throw new InexistentEntryException("Game " + index + " does not exist");
		}
	}

	public void clearGames() {
		this.games.clear();
	}

}