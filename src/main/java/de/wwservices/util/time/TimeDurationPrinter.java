package de.wwservices.util.time;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.wwservices.util.time.TimeDurationConverter.TimeUnit;

/**
 * Erzeugt eine hübsche Visualisierung für einen Zeitraum.
 * 
 * @author joergw
 * 
 */
public class TimeDurationPrinter {

    private Map<TimeUnit, String[]> localisation = defaultLocalisation();

    /**
     * Visulisiert die Dauer bei beliebiger Angabe.
     * 
     * @param durationInSeconds
     * @return
     */
    public String printDuration(long durationInSeconds,
            TimeUnit timeUnit) {
        Map<TimeUnit, Long> split = TimeDurationConverter.split(
                durationInSeconds, timeUnit);
        String result = "";
        for (Entry<TimeUnit, Long> entry : split.entrySet()) {
            Long value = entry.getValue();
            if (value > 0) {
                int pluralIndex = (value == 1 ? 0 : 1);
                result += value + " "
                        + localisation.get(entry.getKey())[pluralIndex] + " ";
            }
        }
        return result.trim();
    }

    /**
     * Visulisiert die Dauer bei Angabe von Sekunden.
     * 
     * @param durationInSeconds
     * @return
     */
    public String printDurationFromSeconds(long durationInSeconds) {
        return printDuration(durationInSeconds, TimeUnit.SECOND);
    }

    private static Map<TimeUnit, String[]> defaultLocalisation() {
        Map<TimeUnit, String[]> map = new HashMap<>();
        map.put(TimeUnit.HOUR, new String[] { "Stunde", "Stunden" });
        map.put(TimeUnit.MINUTE, new String[] { "Minute", "Minuten" });
        map.put(TimeUnit.SECOND, new String[] { "Sekunde", "Sekunden" });
        return map;
    }
    
    /**
     * Setzt die Daten für die Lokalisierung der Ausgabe.
     * @param localisation
     */
    public void setLocalisation(Map<TimeUnit, String[]> localisation){
        this.localisation = localisation; 
    }

}
