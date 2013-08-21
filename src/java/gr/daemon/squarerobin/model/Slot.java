package gr.daemon.squarerobin.model;

import java.util.ArrayList;

public class Slot {
    int index;
    ArrayList<Game> games;

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

    public ArrayList<Game> getGames() {
        return this.games;
    }
}