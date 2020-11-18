package com.akondi.business.packaging.mvp.view;

import com.akondi.business.packaging.mvp.presenter.TransactionContainer;

public interface ViewLoader {

    void loadPayrollView();

    void loadAddEmployeeView(TransactionContainer transactionContainer);
}
