package com.akondi.business.packaging.mvp.presenter;

import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.mvp.view.ViewLoader;

public class MockPayrollPresenter extends PayrollPresenter {

    public boolean addEmployeeActionInvoked;
    public boolean runTransactionCalled;

    public MockPayrollPresenter(PayrollDatabase database, ViewLoader viewLoader) {
        super(database, viewLoader);
    }

    @Override
    public void addEmployeeActionInvoked() {
//        super.addEmployeeActionInvoked();
        addEmployeeActionInvoked = true;
    }

    @Override
    public void runTransactions() {
//        super.runTransactions();
        runTransactionCalled = true;
    }
}
