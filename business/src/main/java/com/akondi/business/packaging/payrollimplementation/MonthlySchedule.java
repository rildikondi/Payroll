package com.akondi.business.packaging.payrollimplementation;

import com.akondi.business.packaging.payrolldomain.PaymentSchedule;

import java.util.Calendar;
import java.util.Date;

public class MonthlySchedule implements PaymentSchedule {

    private boolean isLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int m1 = cal.get(Calendar.MONTH);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        int m2 = cal.get(Calendar.MONTH);
        return (m1 != m2);
    }

    @Override
    public boolean isPayDay(Date payDate) {
        return isLastDayOfMonth(payDate);
    }

    @Override
    public Date getPayPeriodStartDate(Date payDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(payDate);
        int  lastDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        cal.add(Calendar.DAY_OF_MONTH, -lastDayOfMonth + 1);
        return cal.getTime();
    }
}
