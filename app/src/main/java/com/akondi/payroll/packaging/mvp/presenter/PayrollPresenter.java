package com.akondi.payroll.packaging.mvp.presenter;

import com.akondi.payroll.packaging.mvp.view.PayrollView;
import com.akondi.payroll.packaging.mvp.view.ViewLoader;
import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.payroll.packaging.payrolldomain.Employee;
import com.akondi.payroll.packaging.transactionapplication.Transaction;

import java.util.ArrayList;


public class PayrollPresenter {
    private PayrollView view;
    private final PayrollDatabase database;
    private final ViewLoader viewLoader;
    private TransactionContainer transactionContainer;

    public PayrollPresenter(PayrollDatabase database, ViewLoader viewLoader) {
        this.view = view;
        this.database = database;
        this.viewLoader = viewLoader;
        transactionContainer = new TransactionContainer(new ArrayList<>());
        transactionContainer.setAction(this::transactionAdded);
    }

    public PayrollView getView() {
        return view;
    }

    public void setView(PayrollView view) {
        this.view = view;
    }

    public TransactionContainer getTransactionContainer() {
        return transactionContainer;
    }

    public void transactionAdded() {
        updateTransactionsTextBox();
    }

    private void updateTransactionsTextBox() {
        StringBuilder builder = new StringBuilder();
        for (Transaction transaction : transactionContainer.getTransactions()) {
            builder.append(transaction.toString());
            builder.append(System.lineSeparator());
        }
        view.setTransactionsText( builder.toString());
    }

    public PayrollDatabase getDatabase() {
        return database;
    }

    public void addEmployeeActionInvoked() {
        viewLoader.loadAddEmployeeView(transactionContainer);
    }

    public void runTransactions() {
        for(Transaction transaction : transactionContainer.getTransactions())
        transaction.execute();
        transactionContainer.clear();
        updateTransactionsTextBox();
        updateEmployeesTextBox();
    }

    private void updateEmployeesTextBox() {
        StringBuilder builder = new StringBuilder();
        for(Employee employee : database.getAllEmployees())
        {
            builder.append(employee.toString());
            builder.append(System.lineSeparator());
        }
        view.setEmployeesText(builder.toString());
    }
}
