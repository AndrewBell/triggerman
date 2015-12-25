/**
 * Created by Andrew Bell 12/24/2015
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2015. See license.txt for details.
 */

package com.recursivechaos.triggerman;

import java.time.DayOfWeek;

public class CronUtil {

    // Support a "last" w/ an 'L' value?
    // It feels awkward passing an nDate in on Daily.

    public static String createCronExpression(Frequency frequency, int nDate) {
        int hour = 4;
        switch (frequency) {
            case DAILY:
                return String.format("0 0 %d 1/1 * ? *", hour);
            case WEEKLY:
                return String.format("0 0 %d ? * %s *", hour, getWeekday(nDate));
            case MONTHLY:
                validateMonthDay(nDate);
                return String.format("0 0 %d %d 1/1 ? *", hour, nDate);
            case QUARTERLY:
                return String.format("0 0 %d %d 1/3 ?", hour, nDate);
        }
        return "";
    }

    static void validateMonthDay(int nDate) {
        if ((nDate > 28) || (nDate < 1)) {
            throw new IndexOutOfBoundsException("nDate must be between 1 and 28. " + nDate + " was passed.");
        }
    }

    // How to handle nulls?
    static String getWeekday(int nDate) {
        if ((nDate > 7) || (nDate < 1)) {
            throw new IndexOutOfBoundsException("nDate must be between 1(MON) and 7(SUN). " + nDate + " was passed.");
        }
        return DayOfWeek.of(nDate).name().substring(0, 3);
    }

}
