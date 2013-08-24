package gr.daemon.squarerobin.model;

import java.util.HashMap;

public class Round {
    
    private int index;
    private HashMap<Integer, Slot> slots;
    
    public Round(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
    
    public void setIndex(int index) {
        this.index = index;
    }
    
    public void addSlot(Slot slot) {
        this.slots.put(slot.getIndex(), slot);
    }
    
    public void removeSlot(Slot slot) {
        this.slots.remove(slot.getIndex());
    }
    
    public void removeSlot(int index) {
        this.slots.remove(index);
    }
    
    public void clearSlots() {
        this.slots.clear();
    }
    
    public Slot[] getSlots() {
        return (Slot[]) this.slots.values().toArray();
    }
    
    public Slot getSlot(int index) {
        return this.slots.get(index);
    }
}