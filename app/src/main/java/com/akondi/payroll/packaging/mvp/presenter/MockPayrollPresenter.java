package com.akondi.payroll.packaging.mvp.presenter;

import com.akondi.payroll.packaging.mvp.view.ViewLoader;
import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;

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
