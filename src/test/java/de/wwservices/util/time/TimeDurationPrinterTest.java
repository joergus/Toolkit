package de.wwservices.util.time;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import de.wwservices.util.time.TimeDurationConverter.TimeUnit;

public class TimeDurationPrinterTest {

    @Test
    public void printFullSingular() {
        TimeDurationConverter conv = new TimeDurationConverter(TimeUnit.SECOND, 1,1,1);
        String printDurationFromSeconds = new TimeDurationPrinter().printDurationFromSeconds(conv.duration());
        assertThat(printDurationFromSeconds, equalTo("1 Stunde 1 Minute 1 Sekunde"));
    }
    
    @Test
    public void printFullPlural() {
        TimeDurationConverter conv = new TimeDurationConverter(TimeUnit.SECOND, 2,2,2);
        String printDurationFromSeconds = new TimeDurationPrinter().printDurationFromSeconds(conv.duration());
        assertThat(printDurationFromSeconds, equalTo("2 Stunden 2 Minuten 2 Sekunden"));
    }
    
    @Test
    public void printFullmixed() {
        TimeDurationConverter conv = new TimeDurationConverter(TimeUnit.SECOND, 2,1,2);
        String printDurationFromSeconds = new TimeDurationPrinter().printDurationFromSeconds(conv.duration());
        assertThat(printDurationFromSeconds, equalTo("2 Stunden 1 Minute 2 Sekunden"));
    }
    
    @Test
    public void printPartmixed() {
        TimeDurationConverter conv = new TimeDurationConverter(TimeUnit.SECOND);
        conv.setHour(1);
        conv.setSecond(12);
        String printDurationFromSeconds = new TimeDurationPrinter().printDurationFromSeconds(conv.duration());
        assertThat(printDurationFromSeconds, equalTo("1 Stunde 12 Sekunden"));
    }
    
    @Test
    public void printMinuteMixed() {
        TimeDurationConverter conv = new TimeDurationConverter(TimeUnit.MINUTE, 2, 5, 6);
        String printDurationFromSeconds = new TimeDurationPrinter().printDuration(conv.duration(), TimeUnit.MINUTE);
        assertThat(printDurationFromSeconds, equalTo("2 Stunden 5 Minuten"));
    }

}
