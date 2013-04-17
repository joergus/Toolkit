package de.wwservices.util.time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.wwservices.util.time.OpeningTimeCalculator.DayConfig;

/**
 * Test for {@link TravelAgencyHelper}.
 * 
 * @author joergw
 * 
 */
public class OpeningTimeCalculatorTest {

    private List<DayConfig> openings;

    private String dayStartDefault = "09:00";
    private String dayEndDefault = "18:00";

    private String breakStart = "12:30";
    private String breakEnd = "13:30";

    @Before
    public void before() {
        openings = new ArrayList<OpeningTimeCalculator.DayConfig>();
    }

    @Test
    public void testEmptyOpenings() {
        List<List<String>> calculateOpenings = OpeningTimeCalculator
                .calculateOpenings(openings);
        Assert.assertNotNull(calculateOpenings);
        Assert.assertTrue(calculateOpenings.isEmpty());
    }

    @Test
    public void testStandardCaseWithoutSaturday() {
        setDefaultsOnDay(2);
        setDefaultsOnDay(3);
        setDefaultsOnDay(4);
        setDefaultsOnDay(5);
        setDefaultsOnDay(6);
        setClosedDay(7);
        setClosedDay(1); // Sonntag
        List<List<String>> calculateOpenings = OpeningTimeCalculator
                .calculateOpenings(openings);
        Assert.assertEquals(1, calculateOpenings.size());
        List<String> list = calculateOpenings.get(0);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("Mo-Fr ", list.get(0));
        Assert.assertEquals("09:00 - 18:00", list.get(1));
    }

    @Test
    public void testStandardCaseWithBreak() {
        setDefaultsOnDayWithBreak(2);
        setDefaultsOnDayWithBreak(3);
        setDefaultsOnDayWithBreak(4);
        setDefaultsOnDayWithBreak(5);
        setDefaultsOnDayWithBreak(6);
        setClosedDay(7);
        setClosedDay(Calendar.SUNDAY); // Sonntag
        List<List<String>> calculateOpenings = OpeningTimeCalculator
                .calculateOpenings(openings);
        Assert.assertEquals(1, calculateOpenings.size());
        List<String> list = calculateOpenings.get(0);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("Mo-Fr ", list.get(0));
        Assert.assertEquals("09:00 - 12:30, 13:30 - 18:00", list.get(1));
    }

    @Test
    public void testStandardCaseWithSaturday() {
        setDefaultsOnDay(2);
        setDefaultsOnDay(3);
        setDefaultsOnDay(4);
        setDefaultsOnDay(5);
        setDefaultsOnDay(6);
        setDefaultsOnDay(7);
        setClosedDay(1); // Sonntag
        List<List<String>> calculateOpenings = OpeningTimeCalculator
                .calculateOpenings(openings);
        Assert.assertEquals(1, calculateOpenings.size());
        List<String> list = calculateOpenings.get(0);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("Mo-Sa ", list.get(0));
        Assert.assertEquals("09:00 - 18:00", list.get(1));
    }

    @Test
    public void testStandardCaseWithSaturdayDifferent() {
        setDefaultsOnDay(2);
        setDefaultsOnDay(3);
        setDefaultsOnDay(4);
        setDefaultsOnDay(5);
        setDefaultsOnDay(6);
        setValuesOnDay(7, "10:00", "13:00");
        setClosedDay(1); // Sonntag
        List<List<String>> calculateOpenings = OpeningTimeCalculator
                .calculateOpenings(openings);
        Assert.assertEquals(2, calculateOpenings.size());
        List<String> list = calculateOpenings.get(0);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("Mo-Fr ", list.get(0));
        Assert.assertEquals("09:00 - 18:00", list.get(1));

        list = calculateOpenings.get(1);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("Sa ", list.get(0));
        Assert.assertEquals("10:00 - 13:00", list.get(1));
    }

    @Test
    public void testStandardCaseWithBreakAndSaturdayDifferent() {
        setDefaultsOnDayWithBreak(2);
        setDefaultsOnDayWithBreak(3);
        setDefaultsOnDayWithBreak(4);
        setDefaultsOnDayWithBreak(5);
        setDefaultsOnDayWithBreak(6);
        setValuesOnDay(7, "09:00", "13:00");
        setClosedDay(1); // Sonntag
        List<List<String>> calculateOpenings = OpeningTimeCalculator
                .calculateOpenings(openings);
        Assert.assertEquals(2, calculateOpenings.size());
        List<String> list = calculateOpenings.get(0);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("Mo-Fr ", list.get(0));
        Assert.assertEquals("09:00 - 12:30, 13:30 - 18:00", list.get(1));

        list = calculateOpenings.get(1);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("Sa ", list.get(0));
        Assert.assertEquals("09:00 - 13:00", list.get(1));
    }

    @Test
    public void testOneDayDifferent() {
        setDefaultsOnDay(2);
        setDefaultsOnDay(3);
        setValuesOnDay(4, "08:00", "19:00");
        setDefaultsOnDay(5);
        setDefaultsOnDay(6);
        setClosedDay(7);
        setClosedDay(1); // Sonntag
        List<List<String>> calculateOpenings = OpeningTimeCalculator
                .calculateOpenings(openings);
        Assert.assertEquals(3, calculateOpenings.size());
        List<String> list = calculateOpenings.get(0);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("Mo-Di ", list.get(0));
        Assert.assertEquals("09:00 - 18:00", list.get(1));

        list = calculateOpenings.get(1);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("Mi ", list.get(0));
        Assert.assertEquals("08:00 - 19:00", list.get(1));

        list = calculateOpenings.get(2);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("Do-Fr ", list.get(0));
        Assert.assertEquals("09:00 - 18:00", list.get(1));
    }

    private void setClosedDay(int day) {
        setValuesOnDay(day, "", "");
    }

    private void setValuesOnDay(int day, String dayStart, String dayEnd) {
        setValuesOnDay(day, dayStart, dayEnd, "", "");

    }

    private void setValuesOnDay(int day, String dayStart, String dayEnd,
            String breakStart, String breakEnd) {
        openings.add(new DayConfig(day, dayStart, dayEnd, breakStart, breakEnd));
    }

    private void setDefaultsOnDayWithBreak(int day) {
        setValuesOnDay(day, dayStartDefault, dayEndDefault, breakStart,
                breakEnd);
    }

    private void setDefaultsOnDay(int day) {
        setValuesOnDay(day, dayStartDefault, dayEndDefault);
    }

}
