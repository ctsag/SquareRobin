package gr.daemon.squarerobin.model;

import gr.daemon.squarerobin.model.exceptions.DuplicateEntryException;
import gr.daemon.squarerobin.model.exceptions.DuplicateGamesFoundException;
import gr.daemon.squarerobin.model.exceptions.InexistentEntryException;
import gr.daemon.squarerobin.model.exceptions.NoGamesFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Slot {
    
    private final int index;
    private final HashMap<Integer, Game> games = new HashMap<>();
    private Round round;
    
    protected Slot(final int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
    
	public Round getRound() {
		return this.round;
	}
	
	protected void setRound(final Round round) throws DuplicateEntryException {
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
	
	public Game getGame(final Team home, final Team away) throws DuplicateGamesFoundException, NoGamesFoundException {
		Game gameFound = null;
		for (final Game game : games.values()) {
			if (game.getHomeTeam().equals(home) && game.getAwayTeam().equals(away)) {
				if (gameFound == null) {
					gameFound = game;
				} else {
					throw new DuplicateGamesFoundException("More than one games found for the provided teams");
				}
			}
		}
		if (gameFound  == null) {
			throw new NoGamesFoundException("No games found for the provided teams");
		} else {
			return gameFound;
		}
	}
	
	public Game getGame(final Team team) throws DuplicateGamesFoundException, NoGamesFoundException {
		Game gameFound = null;
		for (final Game game : games.values()) {
			if (game.getHomeTeam().equals(team) || game.getAwayTeam().equals(team)) {
				if (gameFound == null) {
					gameFound = game;
				} else {
					throw new DuplicateGamesFoundException("More than one games found for the provided team");
				}
			}
		}
		if (gameFound  == null) {
			throw new NoGamesFoundException("No games found for the provided team");
		} else {
			return gameFound;
		}
	}

	protected void addGame(final Game game) throws DuplicateEntryException {
		final int index = game.getIndex();
		
		if (this.games.containsKey(index)) {
			throw new DuplicateEntryException("A game of index " + index + " already exists");
		} else {
			this.games.put(index, game);
			game.setSlot(this);			
		}
	}

	protected void removeGame(final int index) throws InexistentEntryException {
		if (this.games.containsKey(index)) {
			this.games.remove(index);
		} else {
			throw new InexistentEntryException("Game " + index + " does not exist");
		}
	}

	protected void clearGames() {
		this.games.clear();
	}

}