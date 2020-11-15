package com.akondi.payroll.packaging.transactionimplementation;



import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.payroll.packaging.payrolldomain.Employee;
import com.akondi.payroll.packaging.payrollimplementation.HourlyClassification;
import com.akondi.payroll.packaging.transactionapplication.Transaction;

import java.util.Date;

import static com.akondi.payroll.packaging.payrollfactory.PayrollFactory.payrollFactory;

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
                hc.addTimeCard(payrollFactory.makeTimeCard(date, hours));
            else
                throw new UnsupportedOperationException(
                        "Tried to add timecard to" +
                                "non-hourly employee");
        } else
            throw new UnsupportedOperationException(
                    "No such employee.");
    }
}
