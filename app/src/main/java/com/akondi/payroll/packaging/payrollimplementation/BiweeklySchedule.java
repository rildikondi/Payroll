package com.akondi.payroll.packaging.payrollimplementation;

import packaging.payrolldomain.PaymentSchedule;

import java.util.Calendar;
import java.util.Date;

public class BiweeklySchedule implements PaymentSchedule {

    @Override
    public boolean isPayDay(Date date) {
        return isOtherFriday(date);
    }

    @Override
    public Date getPayPeriodStartDate(Date payDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(payDate);
        cal.add(Calendar.DAY_OF_MONTH, -11);
        return cal.getTime();
    }

    private boolean isOtherFriday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return (cal.get(Calendar.WEEK_OF_MONTH) == 2 || cal.get(Calendar.WEEK_OF_MONTH) == 4)
                && cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
    }
}
