package com.akondi.payroll.packaging.transactionimplementation;

import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldomain.Employee;
import packaging.payrollimplementation.CommissionedClassification;
import packaging.transactionapplication.Transaction;

import java.awt.dnd.InvalidDnDOperationException;
import java.util.Date;

import static packaging.payrollfactory.PayrollFactory.payrollFactory;

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
        Employee e =  payrollDatabase.getEmployee(empId);
        if (e != null) {
            CommissionedClassification commissionedClassification = (CommissionedClassification) e.getPaymentClassification();
            if (commissionedClassification != null)
                commissionedClassification.addSalesReceipt(payrollFactory.makeSalesReceipt(date, amount));
            else
                throw new InvalidDnDOperationException(
                        "Tried to add timecard to" +
                                "non-hourly employee");
        } else
            throw new InvalidDnDOperationException(
                    "No such employee.");
    }
}

