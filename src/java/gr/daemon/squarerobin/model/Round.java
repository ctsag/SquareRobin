package gr.daemon.squarerobin.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Round {
    
    private final int index;
    private final HashMap<Integer, Slot> slots = new HashMap<>();
    private Season season;
    
    public Round(final int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
    
	public Season getSeason() {
		return this.season;
	}
	
	public void setSeason(final Season season) {
		final List<Round> seasonRounds = Arrays.asList(season.getRounds());
		if (!seasonRounds.contains(this)) {
			season.addRound(this);
		}
		this.season = season;
	}
    
	public Slot[] getSlots() {
		final Collection<Slot> slots = this.slots.values();
		
		return slots.toArray(new Slot[slots.size()]);
	}

	public Slot getSlot(final int index) {
		return this.slots.get(index);
	}

	public void addSlot(final Slot slot) throws DuplicateEntryException {
		final int index = slot.getIndex();
		
		if (this.slots.containsKey(index)) {
			throw new DuplicateEntryException("A slot of index " + index + " already exists");			
		} else {
			this.slots.put(index, slot);
			slot.setRound(this);
		}
	}

	public void removeSlot(final int index) throws InexistentEntryException {
		if (this.slots.containsKey(index)) {
			this.slots.remove(index);
		} else {
			throw new InexistentEntryException("Slot " + index + " does not exist");
		}
	}

	public void clearSlots() {
		this.slots.clear();
	}

}