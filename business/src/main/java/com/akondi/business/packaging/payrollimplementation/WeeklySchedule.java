package com.akondi.business.packaging.payrollimplementation;

import com.akondi.business.packaging.payrolldomain.PaymentSchedule;

import java.util.Calendar;
import java.util.Date;

public class WeeklySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDay(Date date) {
        return isFriday(date);
    }

    @Override
    public Date getPayPeriodStartDate(Date payDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(payDate);
        cal.add(Calendar.DAY_OF_WEEK, -4);
        return cal.getTime();
    }

    private boolean isFriday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
    }
}
