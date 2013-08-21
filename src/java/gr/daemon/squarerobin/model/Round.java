package gr.daemon.squarerobin.model;

import java.util.ArrayList;

public class Round {
    private int index;
    private ArrayList<Slot> slots;

    public int getIndex() {
        return this.index;
    }
    
    public void setIndex(int index) {
        this.index = index;
    }
    
    public void addSlot(Slot slot) {
        this.slots.add(slot);
    }
    
    public void removeSlot(Slot slot) {
        this.slots.remove(slot);
    }
    
    public ArrayList<Slot> getGames() {
        return (ArrayList<Slot>) this.slots.clone();
    }
}
