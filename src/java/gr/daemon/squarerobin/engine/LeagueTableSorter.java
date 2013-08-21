package gr.daemon.squarerobin.engine;

import java.util.Arrays;
import java.util.Comparator;

public class LeagueTableSorter {
    private String[][] leagueTable;
    
    public LeagueTableSorter(String[][] table) {
        leagueTable = table;
        Arrays.sort(leagueTable, ORDER_BY_RULES);
    }

    private static final Comparator<String[]> ORDER_BY_POINTS = new Comparator<String[]>() {
        @Override
        public int compare(String[] s1, String[] s2) {
            return Integer.valueOf(s2[1]).compareTo(Integer.valueOf(s1[1]));
        }
    };
    private static final Comparator<String[]> ORDER_BY_GOALAVERAGE = new Comparator<String[]>() {
        @Override
        public int compare(String[] s1, String[] s2) {
            // goal average = goals scored - goals conceded
            Integer average1 = Integer.valueOf(Integer.parseInt(s1[2]) - Integer.parseInt(s1[3]));
            Integer average2 = Integer.valueOf(Integer.parseInt(s2[2]) - Integer.parseInt(s2[3]));
            return average2.compareTo(average1);
        }
    };
    private static final Comparator<String[]> ORDER_BY_GOALS = new Comparator<String[]>() {
        @Override
        public int compare(String[] s1, String[] s2) {
            return Integer.valueOf(s2[3]).compareTo(Integer.valueOf(s1[3]));
        }
    };
    private static final Comparator<String[]> ORDER_BY_NAME = new Comparator<String[]>() {
        @Override
        public int compare(String[] s1, String[] s2) {
            return s1[0].compareTo(s2[0]);
        }
    };

    private static final Comparator<String[]> ORDER_BY_RULES = new Comparator<String[]>() {
        @Override
        public int compare(String[] s1, String[] s2) {
            int i = ORDER_BY_POINTS.compare(s1, s2);
            if (i == 0) {
                i = ORDER_BY_GOALAVERAGE.compare(s1, s2);
                if (i == 0) {
                    i = ORDER_BY_GOALS.compare(s1, s2);
                    if (i == 0) {
                        i = ORDER_BY_NAME.compare(s1, s2);
                    }
                }
            }
            return i;
        }
    };
    
    public String[][] getLeagueTable() {
        return leagueTable;
    }
}
