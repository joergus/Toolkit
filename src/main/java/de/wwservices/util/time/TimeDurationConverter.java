package de.wwservices.util.time;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Konverter um die Dauer anhand von Stunden Minuten und Sekunden in einer
 * beliebigen Einheit zu ermitteln. Es werden aber keine Bruchteile unterstützt.
 * Das bedeutet, dass 3 Stunden 30 Minuten mit dem Ziel Stunden in 3 Stunden
 * konvertiert wird.
 * 
 * @author joergw
 * 
 */
public class TimeDurationConverter {

    private Map<TimeUnit, Long> timeData = new TreeMap<>(
            new TimeUnitComparator());
    private Map<TimeUnit, Long> calcTime = new TreeMap<>(
            new TimeUnitComparator());

    /**
     * Definition der Zeiteinheit.
     * 
     * @author joergw
     *
     */
    public enum TimeUnit {
        HOUR(60), MINUTE(60), SECOND(1000);

        private int calcToSmaller;

        TimeUnit(int calcValue) {
            calcToSmaller = calcValue;
        }

        TimeUnit next() {
            int target = this.ordinal() + 1;
            return fromOrdinal(target);
        }

        TimeUnit previuos() {
            int target = this.ordinal() - 1;
            return fromOrdinal(target);
        }

        boolean hasPrevious() {
            int target = this.ordinal() - 1;
            return fromOrdinal(target) != null;
        }

        boolean hasNext() {
            int target = this.ordinal() + 1;
            return fromOrdinal(target) != null;
        }

        private TimeUnit fromOrdinal(int targetOrdinal) {
            for (TimeUnit time : values()) {
                if (time.ordinal() == targetOrdinal) {
                    return time;
                }
            }
            return null;
        }
    }

    private static class TimeUnitComparator implements Comparator<TimeUnit>, Serializable {

        private static final long serialVersionUID = 2455651352022116332L;

        @Override
        public int compare(TimeUnit o1, TimeUnit o2) {
            return ((Integer) o1.ordinal()).compareTo(o2.ordinal());
        }

    }

    private TimeUnit targetTimeUnit = TimeUnit.MINUTE;

    /**
     * @param timeUnit
     */
    public TimeDurationConverter(TimeUnit timeUnit) {
        targetTimeUnit = timeUnit;
    }
    
    /**
     * Konstruktor mit allen Zeitwerten und der Zielzeiteinheit.
     * @param timeUnit
     */
    public TimeDurationConverter(TimeUnit timeUnit, int hours, int minutes, int seconds) {
        targetTimeUnit = timeUnit;
        setHour(hours);
        setMinute(minutes);
        setSecond(seconds);
    }

    /**
     * Setzt die Anzahl der Stunden für die Berechnung
     * @param hour
     */
    public void setHour(int hour) {
        timeData.put(TimeUnit.HOUR, (long) hour);
    }

    /**
     * Setzt die Anzahl der Minuten für die Berechnung
     * @param minute
     */
    public void setMinute(int minute) {
        timeData.put(TimeUnit.MINUTE, (long) minute);
    }

    /**
     * Setzt die Anzahl der Sekunden für die Berechnug
     * @param second
     */
    public void setSecond(int second) {
        timeData.put(TimeUnit.SECOND, (long) second);
    }

    /**
     * Dauer in der angeforderten Zeiteinheit.
     * 
     * @return
     */
    public long duration() {
        calcTime.clear();
        for (TimeUnit timeUnit : TimeUnit.values()) {
            if (timeUnit.equals(targetTimeUnit)) {
                Long long1 = calcTime.get(timeUnit);
                Long long2 = timeData.get(timeUnit);
                calcTime.put(timeUnit, (long1 != null ? long1 : 0)
                        + (long2 != null ? long2 : 0));
            } else if (timeUnit.hasNext()) {
                Long long1 = timeData.get(timeUnit);
                Long long2 = calcTime.get(timeUnit);
                calcTime.put(timeUnit.next(),
                        ((long1 != null ? long1 : 0) + (long2 != null ? long2
                                : 0)) * timeUnit.calcToSmaller);
            }

        }
        return calcTime.get(targetTimeUnit);
    }

    public static Map<TimeUnit, Long> split(long time, TimeUnit startFrom) {
        TimeUnit current = startFrom;
        Map<TimeUnit, Long> result = new TreeMap<>(new TimeUnitComparator());
        if (!current.hasPrevious()) {
            result.put(current, time);
        } else {
            while (current.hasPrevious()) {
                TimeUnit previous = current.previuos();
                result.put(current, time % previous.calcToSmaller);
                time = time / previous.calcToSmaller;
                current = previous;
            }
            result.put(current, time);
        }
        return result;
    }

}
