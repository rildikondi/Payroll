package com.akondi.payroll.packaging.util;

import java.util.Date;

public class DateUtil {
    public static boolean isInPayPeriod(
            Date theDate, Date startDate, Date endDate)
    {
        return (theDate.getTime() >= startDate.getTime()) &&
                (theDate.getTime() <= endDate.getTime());
    }
}
