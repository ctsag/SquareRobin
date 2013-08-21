package gr.daemon.squarerobin.model;

import java.util.ArrayList;

public class Slot {
    private int index;
    private ArrayList<Game> games;

    public int getIndex() {
        return this.index;
    }
    
    public void setIndex(int index) {
        this.index = index;
    }
    
    public void addGame(Game game) {
        this.games.add(game);
    }
    
    public void removeGame(Game game) {
        this.games.remove(game);
    }

    public Game[] getGames() {
        return (Game[]) this.games.toArray();
    }
}