package de.wwservices.util.time;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

import de.wwservices.util.time.TimeDurationConverter.TimeUnit;

public class TimeDurationConverterTest {

    @Test
    public void validMinute() {
       TimeDurationConverter timeConverter = new TimeDurationConverter(TimeUnit.MINUTE);
       timeConverter.setHour(12);
       timeConverter.setMinute(5);
       long inTimeUnit = timeConverter.duration();
       assertThat(inTimeUnit, equalTo(12L*60+5));
    }
    
    @Test
    public void minuteThoughSecond() {
        TimeDurationConverter timeConverter = new TimeDurationConverter(
                TimeUnit.MINUTE, 12, 5, 5);
        long inTimeUnit = timeConverter.duration();
        assertThat(inTimeUnit, equalTo(12L * 60 + 5));
    }
    
    @Test
    public void noHourValidMinute(){
        TimeDurationConverter timeConverter = new TimeDurationConverter(TimeUnit.SECOND);
        timeConverter.setMinute(5);
        timeConverter.setSecond(5);
        long inTimeUnit = timeConverter.duration();
        assertThat(inTimeUnit, equalTo(5L*60+5));
    }
    
    @Test
    public void noValueInTarget(){
        TimeDurationConverter timeConverter = new TimeDurationConverter(TimeUnit.SECOND);
        timeConverter.setHour(5);
        timeConverter.setMinute(5);
        long inTimeUnit = timeConverter.duration();
        assertThat(inTimeUnit, equalTo(5L*60*60+5*60));
    }
    
    @Test
    public void hourNoMinute(){
        TimeDurationConverter timeConverter = new TimeDurationConverter(TimeUnit.SECOND);
        timeConverter.setHour(5);
        timeConverter.setSecond(5);
        long inTimeUnit = timeConverter.duration();
        assertThat(inTimeUnit, equalTo(5L*60*60+5));
    }
    
    @Test
    public void hourTarget(){
        TimeDurationConverter timeConverter = new TimeDurationConverter(TimeUnit.HOUR);
        timeConverter.setHour(5);
        timeConverter.setMinute(5);
        long inTimeUnit = timeConverter.duration();
        assertThat(inTimeUnit, equalTo(5L));
    }
    
    @Test
    public void splitHoursNoMinutesFromSeconds(){
        long durationInSec = 5*60*60;
        Map<TimeUnit, Long> split = TimeDurationConverter.split(durationInSec, TimeUnit.SECOND);
        assertThat(split.keySet(), hasSize(3));
        assertThat(split.get(TimeUnit.SECOND), is(Long.valueOf(0)));
        assertThat(split.get(TimeUnit.MINUTE), is(Long.valueOf(0)));
        assertThat(split.get(TimeUnit.HOUR), is(Long.valueOf(5)));
        
    }
    
    @Test
    public void splitHoursMinutesFromMinutes(){
        long durationInMinutes = 3*60+20;
        Map<TimeUnit, Long> split = TimeDurationConverter.split(durationInMinutes, TimeUnit.MINUTE);
        assertThat(split.keySet(), hasSize(2));
        assertThat(split.get(TimeUnit.SECOND), nullValue());
        assertThat(split.get(TimeUnit.MINUTE), is(Long.valueOf(20)));
        assertThat(split.get(TimeUnit.HOUR), is(Long.valueOf(3)));
        
    }
    
    
    @Test
    public void splitHours(){
        long durationInHours = 3;
        Map<TimeUnit, Long> split = TimeDurationConverter.split(durationInHours, TimeUnit.HOUR);
        assertThat(split.keySet(), hasSize(1));
        assertThat(split.get(TimeUnit.SECOND), nullValue());
        assertThat(split.get(TimeUnit.MINUTE), nullValue());
        assertThat(split.get(TimeUnit.HOUR), is(Long.valueOf(3)));
        
    }

}
