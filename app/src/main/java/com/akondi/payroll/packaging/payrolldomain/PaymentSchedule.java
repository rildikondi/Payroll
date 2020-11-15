package com.akondi.payroll.packaging.payrolldomain;

import java.util.Date;

public interface PaymentSchedule {

    boolean isPayDay(Date date);

    Date getPayPeriodStartDate(Date payDate);
}
