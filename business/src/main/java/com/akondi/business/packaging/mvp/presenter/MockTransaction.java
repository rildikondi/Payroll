package com.akondi.business.packaging.mvp.presenter;

import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.transactionapplication.Transaction;

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
