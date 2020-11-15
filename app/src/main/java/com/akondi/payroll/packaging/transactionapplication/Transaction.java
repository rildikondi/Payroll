package com.akondi.payroll.packaging.transactionapplication;

import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;

public abstract class Transaction {
    protected final PayrollDatabase payrollDatabase;

    public Transaction(PayrollDatabase payrollDatabase) {
        this.payrollDatabase = payrollDatabase;
    }

    public abstract void execute();
}
