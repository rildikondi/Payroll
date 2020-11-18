package com.akondi.business.packaging.mvp.view;

import com.akondi.business.packaging.mvp.presenter.PayrollPresenter;

public interface PayrollView {
    void setTransactionsText(String text);

    void setEmployeesText(String text);

    void setPresenter(PayrollPresenter payrollPresenter);
}
