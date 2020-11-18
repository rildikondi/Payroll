package com.akondi.business.packaging.transactionimplementation;


import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.Employee;
import com.akondi.business.packaging.payrollfactory.PayrollFactory;
import com.akondi.business.packaging.payrollimplementation.HourlyClassification;
import com.akondi.business.packaging.transactionapplication.Transaction;

import java.util.Date;

public class TimeCardTransaction extends Transaction {

    private final Date date;
    private final double hours;
    private final int empId;

    public TimeCardTransaction(Date date, double hours, int empId, PayrollDatabase payrollDatabase) {
        super(payrollDatabase);
        this.date = date;
        this.hours = hours;
        this.empId = empId;
    }

    @Override
    public void execute() {
        Employee e = payrollDatabase.getEmployee(empId);
        if (e != null) {
            HourlyClassification hc = (HourlyClassification) e.getPaymentClassification();
            if (hc != null)
                hc.addTimeCard(PayrollFactory.payrollFactory.makeTimeCard(date, hours));
            else
                throw new UnsupportedOperationException(
                        "Tried to add timecard to" +
                                "non-hourly employee");
        } else
            throw new UnsupportedOperationException(
                    "No such employee.");
    }
}
