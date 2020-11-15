package com.akondi.payroll.packaging.mvp.view;

import packaging.mvp.presenter.PayrollPresenter;

public interface PayrollView {
    void setTransactionsText(String text);

    void setEmployeesText(String text);

    void setPresenter(PayrollPresenter payrollPresenter);
}
