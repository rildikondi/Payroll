package com.akondi.business.packaging.mvp.view;


import com.akondi.business.packaging.mvp.presenter.PayrollPresenter;

public class MockPayrollView implements PayrollView {
    public String transactionsText;
    public String employeesText;
    public PayrollPresenter presenter;

    @Override
    public void setTransactionsText(String text) {
        transactionsText = text;
    }

    @Override
    public void setEmployeesText(String text) {
        employeesText = text;
    }

    @Override
    public void setPresenter(PayrollPresenter payrollPresenter) {
        presenter = payrollPresenter;
    }
}