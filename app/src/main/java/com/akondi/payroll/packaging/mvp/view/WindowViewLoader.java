package com.akondi.payroll.packaging.mvp.view;

import packaging.mvp.presenter.AddEmployeePresenter;
import packaging.mvp.presenter.PayrollPresenter;
import packaging.mvp.presenter.TransactionContainer;
import packaging.payrolldatabase.PayrollDatabase;

import javax.swing.*;

public class WindowViewLoader implements ViewLoader {
    private final PayrollDatabase database;
    private JFrame lastLoadedView;

    public WindowViewLoader(PayrollDatabase database) {
        this.database = database;
    }

    public void loadPayrollView() {
        PayrollWindow view = new PayrollWindow();
        PayrollPresenter presenter = new PayrollPresenter(database, this);
        view.setPresenter(presenter);
        presenter.setView(view);
        loadView(view);
    }

    public void loadAddEmployeeView(TransactionContainer transactionContainer) {
        AddEmployeeWindow view = new AddEmployeeWindow();
        AddEmployeePresenter presenter = new AddEmployeePresenter(
                view, transactionContainer, database);
        view.setPresenter(presenter);
        loadView(view);
    }

    private void loadView(JFrame view) {
        view.setVisible(true);
        lastLoadedView = view;
    }

    public JFrame getLastLoadedView() {
        return lastLoadedView;
    }
}
