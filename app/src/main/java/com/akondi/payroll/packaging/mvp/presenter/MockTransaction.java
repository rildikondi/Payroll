package com.akondi.payroll.packaging.mvp.presenter;

import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.payroll.packaging.transactionapplication.Transaction;

public class MockTransaction extends Transaction {

    public boolean wasExecuted;

    public MockTransaction(PayrollDatabase payrollDatabase) {
        super(payrollDatabase);
    }

    @Override
    public void execute() {
        wasExecuted = true;
    }
}
