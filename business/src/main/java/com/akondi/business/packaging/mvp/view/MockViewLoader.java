package com.akondi.business.packaging.mvp.view;


import com.akondi.business.packaging.mvp.presenter.TransactionContainer;

public class MockViewLoader implements ViewLoader {
    public boolean addEmployeeViewWasLoaded;
    private boolean payrollViewWasLoaded;

    @Override
    public void loadPayrollView() {
        payrollViewWasLoaded = true;
    }

    @Override
    public void loadAddEmployeeView(TransactionContainer transactionContainer) {
        addEmployeeViewWasLoaded = true;
    }
}
