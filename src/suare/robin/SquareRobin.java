package suare.robin;

import java.util.*;


public class SquareRobin {
    private HashMap<Integer,ArrayList> full_schedule = new HashMap<Integer,ArrayList>();
    private ArrayList<Integer> teams = new ArrayList<Integer>();
    private int fixed_team = 1;

    public void fill_teams() {
        for (int i=1; i<=14; i++) {
            teams.add(i);
        }
    }
    
    public void schedule() {
        ArrayList<Integer> tmpList = (ArrayList<Integer>) teams.clone();
        ArrayList<Integer> pair;
        
        tmpList.remove(0);
        for (int j=0; j<teams.size(); j++) {
            for (int i=0; i<(teams.size()/2); i++) {
                pair = new ArrayList<Integer>();
                if (i == 0) {
                    pair.add(fixed_team);
                    pair.add(tmpList.get(teams.size()-2));
                } else {
                    if (i+1 == fixed_team) {
                        pair.add(tmpList.get(0));
                    } else {
                        pair.add(tmpList.get(i-1));
                    }
                    pair.add(tmpList.get(tmpList.size()-(i+1)));
                }
                //System.out.println("round: " + j + " a: " + pair.get(0) + " b: " + pair.get(1));
                full_schedule.put(j, pair);
            }
            Collections.rotate(tmpList, 1);
        }
    }
    
    public HashMap<Integer,ArrayList> get_schedule() {
        return full_schedule;
    }
    
    public void go() {
        fill_teams();
        schedule();
    }
    
    public static void main(String[] args) {
        SquareRobin squareRobin = new SquareRobin();
        squareRobin.go();
    }
}
