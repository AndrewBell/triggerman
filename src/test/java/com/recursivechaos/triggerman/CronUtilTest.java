/**
 * Created by Andrew Bell 12/25/2015
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2015. See license.txt for details.
 */

package com.recursivechaos.triggerman;

import org.junit.Test;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CronUtilTest {

    @Test
    public void testCreateCronExpression() throws Exception {
        assertEquals("0 0 4 1/1 * ? *", CronUtil.createCronExpression(Frequency.DAILY, 1));
        assertEquals("0 0 4 ? * MON *", CronUtil.createCronExpression(Frequency.WEEKLY, 1));
        assertEquals("0 0 4 1 1/1 ? *", CronUtil.createCronExpression(Frequency.MONTHLY, 1));
        assertEquals("0 0 4 1 1/3 ?", CronUtil.createCronExpression(Frequency.QUARTERLY, 1));
    }

    @Test
    public void testGetWeekday() throws Exception {
        for (DayOfWeek day : DayOfWeek.values()) {
            String cronDate = CronUtil.getWeekday(day.getValue());
            assertEquals("Given day " + day.getValue() + ", When I get cron weekday, Then it should be a substring of the represented day.", day.name().substring(0, 3), cronDate);
        }
    }

    @Test
    public void testOutOfBoundsWeekly() throws Exception {
        List<Integer> testDates = Arrays.asList(0, 8, 15, -1);
        for (Integer nDate : testDates) {
            System.out.println("Testing weekly: " + nDate);
            try {
                CronUtil.createCronExpression(Frequency.WEEKLY, nDate);
            } catch (Exception e) {
                assertTrue(e.getClass().equals(IndexOutOfBoundsException.class));
                assertEquals("nDate must be between 1(MON) and 7(SUN). " + nDate + " was passed.", e.getMessage());
            }
        }
    }

    @Test
    public void testOutOfBoundsMonthly() throws Exception {
        List<Integer> testDates = Arrays.asList(0, 29, -1, 55);
        for (Integer nDate : testDates) {
            System.out.println("Testing monthly: " + nDate);
            try {
                CronUtil.createCronExpression(Frequency.MONTHLY, nDate);
            } catch (Exception e) {
                assertTrue(e.getClass().equals(IndexOutOfBoundsException.class));
                assertEquals("nDate must be between 1 and 28. " + nDate + " was passed.", e.getMessage());
            }
        }
    }

}