package com.akondi.business.packaging.transactionimplementation;


import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.Employee;
import com.akondi.business.packaging.payrollfactory.PayrollFactory;
import com.akondi.business.packaging.payrollimplementation.CommissionedClassification;
import com.akondi.business.packaging.transactionapplication.Transaction;

import java.util.Date;

public class SalesReceiptTransaction extends Transaction {

    private final Date date;
    private final double amount;
    private final int empId;

    public SalesReceiptTransaction(Date date, double amount, int empId, PayrollDatabase payrollDatabase) {
        super(payrollDatabase);
        this.date = date;
        this.amount = amount;
        this.empId = empId;
    }

    @Override
    public void execute() {
        Employee e = payrollDatabase.getEmployee(empId);
        if (e != null) {
            CommissionedClassification commissionedClassification = (CommissionedClassification) e.getPaymentClassification();
            if (commissionedClassification != null)
                commissionedClassification.addSalesReceipt(PayrollFactory.payrollFactory.makeSalesReceipt(date, amount));
            else
                throw new UnsupportedOperationException(
                        "Tried to add timecard to" +
                                "non-hourly employee");
        } else
            throw new UnsupportedOperationException("No such employee.");
    }
}

