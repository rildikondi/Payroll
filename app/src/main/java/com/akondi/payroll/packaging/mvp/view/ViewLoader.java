package com.akondi.payroll.packaging.mvp.view;

import com.akondi.payroll.packaging.mvp.presenter.TransactionContainer;

public interface ViewLoader {

    void loadPayrollView();

    void loadAddEmployeeView(TransactionContainer transactionContainer);
}
