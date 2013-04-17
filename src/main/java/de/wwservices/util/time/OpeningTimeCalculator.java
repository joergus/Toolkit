package de.wwservices.util.time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OpeningTimeCalculator {

    public static class DayConfig {
        private String openFrom;
        private String openUntil;
        private String breakFrom;
        private String breakUntil;
        private int dayOfWeek;

        public DayConfig(int dayOfWeek, String openFrom, String openUntil) {
            this(dayOfWeek, openFrom, openUntil, "", "");
        }

        public DayConfig(int dayOfWeek, String openFrom, String openUntil,
                String breakFrom, String breakUntil) {
            this.dayOfWeek = dayOfWeek;
            this.openFrom = openFrom;
            this.openUntil = openUntil;
            this.breakFrom = breakFrom;
            this.breakUntil = breakUntil;
        }

        public String getOpenFrom() {
            return openFrom;
        }

        public String getOpenUntil() {
            return openUntil;
        }

        public String getBreakFrom() {
            return breakFrom;
        }

        public String getBreakUntil() {
            return breakUntil;
        }

        public int getDayOfWeek() {
            return dayOfWeek;
        }

        public boolean isOpen() {
            return openFrom != null && !"".equals(openFrom);
        }
        
        @Override
        public String toString() {
            String string = openFrom + " - " + openUntil +" on day "+dayOfWeek;
            return string;
        }

    }

    public static List<List<String>> calculateOpenings(List<DayConfig> openings) {
        Map<Integer, DayConfig> days = prepareMapping(openings);
        List<List<String>> openingRes = new ArrayList<>();
        
        int daysStart = 0;
        int daysEnd = 0;
        for (DayConfig currentDay : days.values()) {
            DayConfig nextDay = nextDay(days, currentDay);
            if (sameOpenings(currentDay, nextDay)) {
                daysEnd++;
            } else {
                if (currentDay.isOpen()) {
                    ArrayList<String> dayList = generateOpeningVisulization(
                            daysStart, daysEnd, currentDay);
                    openingRes.add(dayList);
                }

                daysEnd++;
                daysStart = daysEnd;
            }
        }

        return openingRes;

    }

    private static Map<Integer, DayConfig> prepareMapping(List<DayConfig> data) {
        Map<Integer, DayConfig> days = new TreeMap<>();
        days.put(Calendar.MONDAY, new DayConfig(Calendar.MONDAY, "", ""));
        days.put(Calendar.TUESDAY, new DayConfig(Calendar.TUESDAY, "", ""));
        days.put(Calendar.WEDNESDAY, new DayConfig(Calendar.WEDNESDAY, "", ""));
        days.put(Calendar.THURSDAY, new DayConfig(Calendar.THURSDAY, "", ""));
        days.put(Calendar.FRIDAY, new DayConfig(Calendar.FRIDAY, "", ""));
        days.put(Calendar.SATURDAY, new DayConfig(Calendar.SATURDAY, "", ""));
        days.put(Calendar.SUNDAY, new DayConfig(Calendar.SUNDAY, "", ""));
        for (DayConfig config : data) {
            days.put(config.getDayOfWeek(), config);
        }
        // Set Sunday to end of week
        days.put(8, days.get(Calendar.SUNDAY));
        days.remove(Calendar.SUNDAY);
        return days;
    }

    private static ArrayList<String> generateOpeningVisulization(int daysStart, int daysEnd, DayConfig currentDay) {
        String[] weekdays = { "Mo", "Di", "Mi", "Do", "Fr", "Sa", "So" };
        ArrayList<String> dayList = new ArrayList<String>();
        if (daysStart != daysEnd) {
            dayList.add(weekdays[daysStart] + "-"
                    + weekdays[daysEnd] + " ");
        } else {
            dayList.add(weekdays[daysStart] + " ");
        }
        // if there is no break defined
        if (currentDay.getBreakFrom().equals("")) {
            dayList.add(currentDay.getOpenFrom() + " - " + currentDay.getOpenUntil());
        } else {
            dayList.add(currentDay.getOpenFrom() + " - " + currentDay.getBreakFrom()
                    + ", " + currentDay.getBreakUntil() + " - " + currentDay.getOpenUntil());
        }
        return dayList;
    }
    
    private static DayConfig nextDay(Map<Integer, DayConfig> map, DayConfig current) {
        return map.get(current.getDayOfWeek() + 1);
    }
    
    private static boolean sameOpenings(DayConfig config1, DayConfig config2){
        if(config2 == null){
            return false;
        }
        return config1.getOpenFrom().equals(config2.getOpenFrom())
                && config1.getBreakFrom().equals(config2.getBreakFrom())
                && config1.getBreakUntil().equals(config2.getBreakUntil())
                && config1.getOpenUntil().equals(config2.getOpenUntil());
    }
}
